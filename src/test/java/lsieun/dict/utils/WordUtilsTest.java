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
            Word w = WordUtils.parse(str);
            System.out.println(w);
        }
    }

    @Test
    public void testRewrite() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary/vocabulary/s/slap.md";
        WordUtils.rewrite(filepath);
    }

    @Test
    public void testRewriteAll() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary";
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);
        for (String str : fileList) {
            WordUtils.rewrite(str);
        }
    }

    @Test
    public void testCreate() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary/vocabulary";
        String vocabulary = "aesthetic";
        WordUtils.create(filepath, vocabulary, "");
    }

    @Test
    public void testNewCreate() {
        WordUtils.create("", "nc");
    }

    @Test
    public void testAddDefinition() {
        WordUtils.addDefinition("implication", "nc");
    }

}