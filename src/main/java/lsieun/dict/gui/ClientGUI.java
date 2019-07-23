package lsieun.dict.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import lsieun.dict.core.Word;
import lsieun.dict.utils.*;
import lsieun.utils.StringUtils;

public class ClientGUI extends JFrame {
    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;
    public static final String DEFAULT_SEARCH_TEXT = "请输入单词...";
    public static final int CANDIDATE_NUM = 20;
    public static final String CANDIDATE_DEFAULT_TEXT = "                    ";

    public static int total_words = 0;

    private Trie trie;
    private Map<String, Word> dict_map;

    private JPanel topPanel;
    private JPanel leftPanel;
    private JButton btnSearch;
    private JButton btnClear;
    private JButton btnRebuid;
    private JTextField searchField = new JTextField(DEFAULT_SEARCH_TEXT, 25);
    private List<JButton> candidate_button_list = new ArrayList();
    private JTextArea textArea = new JTextArea("", 20, 30);

    public void init() {
        System.out.println(CANDIDATE_DEFAULT_TEXT.length());
        ClientGUI.total_words = 0;
        trie = new Trie();
        dict_map = new HashMap();

        String filepath = PropertyUtils.getProperty("english.dictionary.filepath");
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);

        List<Word> wordList = new ArrayList();
        for (String str : fileList) {
            Word w = WordUtils.parse(str);
            wordList.add(w);
            trie.insert(w.name);
            dict_map.put(w.name, w);
            ClientGUI.total_words++;
        }
    }

    public void launchFrame() {
        // top textArea
        topPanel = new JPanel();
//        topPanel.setBackground(Color.RED);
        top();
        this.add(topPanel, BorderLayout.NORTH);

        // left textArea
        leftPanel = new JPanel();
//        leftPanel.setBackground(Color.GREEN);
        left();
        this.add(leftPanel, BorderLayout.WEST);

        // right textArea
        textArea.setFont(new Font("黑体", Font.BOLD, 24));
//        textArea.setSelectedTextColor(Color.RED);
        textArea.setLineWrap(true);        // 激活自动换行功能
        textArea.setWrapStyleWord(true);   // 激活断行不断字功能
        textArea.setText("Total words: " + ClientGUI.total_words);
        this.add(textArea, BorderLayout.CENTER);

        // init
        FrameUtils.initFrame(this, WIDTH, HEIGHT);
    }

    private void top() {
        searchField.setFont(new Font("黑体", Font.BOLD, 20));
        topPanel.add(searchField);
        btnSearch = new JButton("Search");
        btnClear = new JButton("Clear");
        btnRebuid = new JButton("rebuild");
        topPanel.add(btnSearch);
        topPanel.add(btnClear);
        topPanel.add(btnRebuid);

        Document doc = searchField.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ClientGUI.this.search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ClientGUI.this.search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // do nothing
            }
        });

        //给输入框添加事件
        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JTextField field = (JTextField) e.getSource();
                if (field.getText().equals(DEFAULT_SEARCH_TEXT)) {
                    field.setText("");
                }
                field.requestFocus();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JTextField field = (JTextField) e.getSource();
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
                ClientGUI.this.search();
            }
        });

        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClientGUI.this.clear();
            }
        });

        btnRebuid.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClientGUI.this.init();
                ClientGUI.this.clear();
            }
        });
    }

    private void left() {
        GridLayout layout = new GridLayout(CANDIDATE_NUM,1,1,1);
        leftPanel.setLayout(layout);
        for(int i=0; i<CANDIDATE_NUM; i++) {
            JButton btn = new JButton(CANDIDATE_DEFAULT_TEXT);
            candidate_button_list.add(btn);
            leftPanel.add(btn);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String str = btn.getText();
                    if (StringUtils.isBlank(str)) return;
                    ClientGUI.this.displayWord(str);
                }
            });

        }

    }

    private void resetCandidateButtions() {
        for (int i=0; i<candidate_button_list.size(); i++) {
            JButton btn = candidate_button_list.get(i);
            btn.setText(CANDIDATE_DEFAULT_TEXT);
        }
    }

    private void resetTextArea() {
        textArea.setText(" ");
    }

    public void search() {
        resetCandidateButtions();
        resetTextArea();
        //
        String str = searchField.getText();
        if(StringUtils.isBlank(str) || DEFAULT_SEARCH_TEXT.equalsIgnoreCase(str)) return;
        System.out.println(str);
        TrieNode node = ClientGUI.this.trie.getNode(str);
        if(node == null) {
            return;
        }

        if (node.endOfWord) {
            Word w = dict_map.get(node.content);
            textArea.setText(w.toString());
        } else {
            List<String> allwords = Trie.findAllWords(node);
            textArea.setText(allwords.toString());
        }

        List<String> words = Trie.findAllWords(node, CANDIDATE_NUM);
        for (int i=0;i<words.size(); i++) {
            JButton btn = candidate_button_list.get(i);
            btn.setText(words.get(i));
        }
    }

    private void displayWord(String str) {
        Word word = dict_map.get(str);
        textArea.setText(word.toString());
    }

    public void clear() {
        searchField.setText(DEFAULT_SEARCH_TEXT);
        textArea.setText("Total words: " + ClientGUI.total_words);
    }

    public static void main(String[] args) {
        ClientGUI client = new ClientGUI();
        client.init();
        client.launchFrame();
    }
}
