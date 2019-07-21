package lsieun.dict.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
    public Map<Character, TrieNode> children = new HashMap();
    public String content;
    public boolean endOfWord;


}
