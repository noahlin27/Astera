package top.noahlin.astera.service.impl;

import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import top.noahlin.astera.service.SensitiveFilterService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveFilterServiceImpl implements SensitiveFilterService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilterService.class);

    private final TrieNode rootNode = new TrieNode();

    @Override
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        String replacement = "*";
        TrieNode temp = rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()) {
            char c = text.charAt(position);

            if (isSymbol(c)) {
                if (temp == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            //查c是否在子节点之中
            temp = temp.getSubNode(c);
            if (temp == null) {
                result.append(text.charAt(begin));
                position = ++begin;
                temp = rootNode;
            } else if (temp.isEnd()) {
                result.append(replacement.repeat(position - begin));
                begin = ++position;
            } else {
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    /**
     * 从敏感词汇总文件读取敏感词
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            if (is == null) {
                throw new Exception("敏感词文件错误");
            }
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (bufferedReader.readLine() != null) {
                addKeyWord(bufferedReader.readLine().trim());  //删除字符串两端的空格
            }
            reader.close();
        } catch (Exception e) {
            logger.error("读取敏感词文件失败 " + e.getMessage());
        }
    }

    /**
     * 判断字符是否为符号，即非东南亚文字（0x2E80-0x9FFF 东亚文字范围）
     *
     * @param c
     * @return
     */
    public boolean isSymbol(char c) {
        return !CharUtils.isAsciiAlphanumeric(c) && ((int) c < 0x2E80 || (int) c > 0x9FFF);
    }

    /**
     * 将关键词字符串添加到字典树
     *
     * @param lineText 一个关键词字符串
     */
    public void addKeyWord(String lineText) {
        TrieNode temp = rootNode;
        for (int i = 0; i < lineText.length(); ++i) {
            Character c = lineText.charAt(i);
            TrieNode subNode = temp.getSubNode(c);

            //临时节点的子节点不包含字符c，则添加一个c的新子节点
            if (subNode == null) {
                subNode = new TrieNode();
                temp.addSubNode(c, subNode);
            }

            temp = subNode;

            if (i == lineText.length() - 1) {
                temp.setEnd();
            }
        }
    }

    private static class TrieNode {
        //判断是否为关键词结尾
        private boolean end = false;

        /**
         * key为子节点的字符，value为字符的子节点
         */
        private final Map<Character, TrieNode> subNodeMap = new HashMap<>();

        private void addSubNode(Character key, TrieNode node) {
            subNodeMap.put(key, node);
        }

        public TrieNode getSubNode(Character key) {
            return subNodeMap.get(key);
        }

        public boolean isEnd() {
            return end;
        }

        public void setEnd() {
            this.end = true;
        }
    }
}
