package lsieun.dict.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import lsieun.dict.core.Word;
import lsieun.dict.utils.*;
import lsieun.utils.StringUtils;

public class ClientGUI extends JFrame {
    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;
    public static final String DEFAULT_SEARCH_TEXT = "请输入单词...";

    public static int total_words = 0;

    private Trie trie = new Trie();
    private Map<String, Word> dict_map = new HashMap();

    private JPanel topPanel;
    private JPanel leftPanel;
    private JButton btnSearch;
    private JButton btnClear;
    private JTextField field = new JTextField(DEFAULT_SEARCH_TEXT,25);
    private JTextArea textArea = new JTextArea("",20,30);

    public void init() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary";
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);
        ClientGUI.total_words = fileList.size();

        List<Word> wordList = new ArrayList();
        for(String str : fileList) {
            Word w = WordUtils.parse(str);
            wordList.add(w);
            trie.insert(w.name);
            dict_map.put(w.name, w);
        }
    }

    public void launchFrame() {
        // top textArea
        topPanel = new JPanel();
        topPanel.setBackground(Color.RED);
        top();
        this.add(topPanel, BorderLayout.NORTH);

        // left textArea
        leftPanel = new JPanel();
        leftPanel.add(new JButton("Hello"));
        leftPanel.setBackground(Color.GREEN);
        this.add(leftPanel, BorderLayout.WEST);

        // right textArea
        textArea.setFont(new Font("黑体",Font.BOLD,24));
        textArea.setSelectedTextColor(Color.RED);
        textArea.setLineWrap(true);        // 激活自动换行功能
        textArea.setWrapStyleWord(true);   // 激活断行不断字功能
        textArea.setText("Total words: " + ClientGUI.total_words);
        this.add(textArea, BorderLayout.CENTER);

        // init
        FrameUtils.initFrame(this, WIDTH, HEIGHT);
    }

    private void top() {
        field.setFont(new Font("黑体",Font.BOLD,20));
        topPanel.add(field);
        btnSearch = new JButton("Search");
        btnClear = new JButton("Clear");
        topPanel.add(btnSearch);
        topPanel.add(btnClear);

        //给输入框添加事件
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JTextField field =  (JTextField) e.getSource();
                if(field.getText().equals(DEFAULT_SEARCH_TEXT)){
                    field.setText("");
                }
                field.requestFocus();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                JTextField field =  (JTextField) e.getSource();
                String str = field.getText();
                if (StringUtils.isBlank(str)) {
                    field.setText(DEFAULT_SEARCH_TEXT);
                }
                //ClientGUI.this.textArea.requestFocus();
            }
        });

        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String str = field.getText();
                System.out.println(str);
                TrieNode node = ClientGUI.this.trie.getNode(str);
                if (node.endOfWord) {
                    Word w = dict_map.get(node.content);
                    textArea.setText(w.toString());
                }
                else {
                    List<String> words = Trie.findAllWords(node);
                    textArea.setText(words.toString());
                }

            }
        });

        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClientGUI.this.reset();
            }
        });
    }

    public void reset() {
        field.setText(DEFAULT_SEARCH_TEXT);
        textArea.setText("不乱于心，不困于情，不畏将来，不念过往");
    }

    public static void main(String[] args) {
        ClientGUI client = new ClientGUI();
        client.init();
        client.launchFrame();
    }
}
