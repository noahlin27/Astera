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

    @Override
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        String replacement = "**";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()) {
            char c = text.charAt(position);
            if(isSymbol(c)){
                if(tempNode==rootNode){
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                result.append((replacement));
                ++position;
                begin = position;
            } else {
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null) {
                addWord(lineText.trim());
            }
            reader.close();
        } catch (Exception e) {
            logger.error("读取敏感词列表失败 " + e.getMessage());
        }
    }

    @Override
    public boolean isSymbol(char c) {
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && ((int) c < 0x2E80 || (int) c > 0x9FFF);
    }

    //增加关键词
    public void addWord(String lineText) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineText.length(); ++i) {
            Character c = lineText.charAt(i);
            TrieNode node = tempNode.getSubNode(c);

            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }

            tempNode = node;

            if (i == lineText.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    private static class TrieNode {
        //关键词结尾判定
        private boolean end = false;

        //当前节点下的所有子节点
        private final Map<Character, TrieNode> subNodes = new HashMap<>();

        private void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }
    }

    private final TrieNode rootNode = new TrieNode();


}
