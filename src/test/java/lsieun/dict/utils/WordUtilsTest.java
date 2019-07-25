package lsieun.dict.utils;

import java.util.List;

import org.junit.Test;

import lsieun.dict.core.Word;

public class WordUtilsTest {

    @Test
    public void testParse() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary";
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);
        for (String str : fileList) {
            Word w = WordUtils.parseFile(str);
            System.out.println(w);
        }
    }

    @Test
    public void testRewriteWord() {
        WordUtils.rewriteWord("dutch");
    }

    @Test
    public void testRewriteFile() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary/vocabulary/p/perspective.md";
        WordUtils.rewriteFile(filepath);
    }

    @Test
    public void testRewriteDir() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary";
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);
        for (String str : fileList) {
            WordUtils.rewriteFile(str);
        }
    }

    @Test
    public void testRewriteAll() {
        WordUtils.rewriteAll();
    }

    @Test
    public void testCreate_Old() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary/vocabulary";
        String vocabulary = "aesthetic";
        WordUtils.create(filepath, vocabulary, "");
    }

    @Test
    public void testCreate() {
        WordUtils.create("", "vt");
    }

    @Test
    public void testAddDefinition() {
        WordUtils.addDefinition("", "adj");
    }

}