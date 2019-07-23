package lsieun.dict.utils;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    public static final char[] chars = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '-'};

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String str) {
        TrieNode current = root;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            sb.append(ch);

            TrieNode subNode = current.children.get(ch);
            if (subNode == null) {
                subNode = new TrieNode();
                subNode.content = sb.toString();
                current.children.put(ch, subNode);
            }
            current = subNode;
        }
        current.endOfWord = true;
    }

    public TrieNode getNode(String str) {
        TrieNode current = root;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            TrieNode subNode = current.children.get(ch);

            if (subNode == null) {
                return null;
            }
            current = subNode;
        }
        return current;
    }

    public List<String> findNode(String str) {
        TrieNode current = getNode(str);
        if (current == null) return null;
        return null;
    }

    public boolean containsNode(String str) {
        TrieNode current = getNode(str);
        if (current == null) return false;

        return current.endOfWord;
    }

    boolean delete(String word) {
        return delete(root, word, 0);
    }


    boolean isEmpty() {
        return root == null;
    }

    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.endOfWord) {
                return false;
            }
            current.endOfWord = false;
            return current.children.isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1) && !node.endOfWord;

        if (shouldDeleteCurrentNode) {
            current.children.remove(ch);
            return current.children.isEmpty();
        }
        return false;
    }

    public static List<String> findAllWords(TrieNode current) {
        if (current == null) return null;

        List<String> resultList = new ArrayList();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            TrieNode subNode = current.children.get(ch);
            if (subNode == null) continue;
            if (subNode.endOfWord) {
                resultList.add(subNode.content);
            }

            List<String> list = findAllWords(subNode);
            resultList.addAll(list);

        }
        return resultList;
    }

    public static List<String> findAllWords(TrieNode current, int count) {
        if (count <= 0) return null;
        if (current == null) return null;

        List<String> resultList = new ArrayList();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            TrieNode subNode = current.children.get(ch);
            if (subNode == null) continue;
            if (subNode.endOfWord) {
                resultList.add(subNode.content);
                if (resultList.size() >= count) break;
            }

            int diff = count - resultList.size();
            List<String> list = findAllWords(subNode, diff);
            if (list == null || list.size() < 1) continue;
            resultList.addAll(list);
            if (resultList.size() >= count) break;

        }
        return resultList;
    }
}
