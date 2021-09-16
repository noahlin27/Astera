package top.noahlin.astera.util;

import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilterUtil implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilterUtil.class);

    private final TrieNode rootNode = new TrieNode();

    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }

        StringBuilder result = new StringBuilder();
        String replacement = "***";
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
     * 从敏感词文件读取敏感词
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            if (is == null) {
                throw new Exception("打开敏感词文件错误");
            }
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            while ((lineText=bufferedReader.readLine()) != null) {
                addKeyWord(lineText.trim());        // 删除字符串两端的空白
            }
            reader.close();
        } catch (Exception e) {
            logger.error("创建敏感词字典失败 " + e.getMessage());
        }
    }

    /**
     * 判断字符是否为符号，即非东南亚文字（0x2E80-0x9FFF 东亚文字范围）
     * @param c
     * @return
     */
    public boolean isSymbol(char c) {
        return !CharUtils.isAsciiAlphanumeric(c) && ((int) c < 0x2E80 || (int) c > 0x9FFF);
    }

    /**
     * 将字符串添加到字典树
     * @param lineText
     */
    public void addKeyWord(String lineText) {
        TrieNode temp = rootNode;

        for (int i = 0; i < lineText.length(); ++i) {
            Character c = lineText.charAt(i);
            TrieNode subNode = temp.getSubNode(c);
            if (subNode == null) {
                subNode = new TrieNode();
                temp.addSubNode(c, subNode);
            }
            temp = subNode;
        }

        temp.setEnd();
    }

    static class TrieNode {
        /** 是否为关键词结尾 */
        boolean end = false;

        /** key为节点的字符，value为节点的子节点 */
        final Map<Character, TrieNode> subNodeMap = new HashMap<>();

        void addSubNode(Character key, TrieNode node) {
            subNodeMap.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodeMap.get(key);
        }

        boolean isEnd() {
            return end;
        }

        void setEnd() {
            this.end = true;
        }
    }
}
