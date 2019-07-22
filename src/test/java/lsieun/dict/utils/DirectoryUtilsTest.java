package lsieun.dict.utils;

import java.util.List;

import org.junit.Test;

public class DirectoryUtilsTest {

    @Test
    public void testGetAllMarkdownFile() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary/vocabulary/a";
        List<String> fileList = DirectoryUtils.getAllMarkdownFiles(filepath);
        for(String str : fileList) {
            System.out.println(str);
        }
    }

    @Test
    public void testGetVocabularyFiles() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary";
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);
        for(String str : fileList) {
            System.out.println(str);
        }
    }

    @Test
    public void testGetIdiomFiles() {
        String filepath = "/home/liusen/Workspace/git-repo/EnglishDictionary";
        List<String> fileList = DirectoryUtils.getIdiomFiles(filepath);
        for(String str : fileList) {
            System.out.println(str);
        }
    }
}