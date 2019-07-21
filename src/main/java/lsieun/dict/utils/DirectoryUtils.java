package lsieun.dict.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryUtils {

    public static File getDirFile(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            throw new RuntimeException("File Not Exist: " + dir);
        }
        return dirFile;
    }

    public static List<String> getAllMarkdownFiles(String filepath) {
        File dirFile = getDirFile(filepath);

        File[] files = dirFile.listFiles();
        if (files == null || files.length < 1) return null;

        List<String> fileNameList = new ArrayList();
        for (File file : files) {
            String fileName = file.getName();

            //使用正则表达式匹配
            if (fileName.matches(".+\\.md") && file.isFile()) {
                String absolutePath = file.getAbsolutePath();
                fileNameList.add(absolutePath);
            }

        }
        return fileNameList;
    }

    public static List<String> getVocabularyFiles(String filepath) {
        String vocabularyPath = getVocabularyPath(filepath);
        File[] files = getDirFile(vocabularyPath).listFiles();
        if (files == null || files.length < 1) return null;

        List<String> fileNameList = new ArrayList();
        for (File file : files) {
            if(!file.isDirectory()) continue;
            String fileName = file.getName();
            String subDir = vocabularyPath + File.separator + fileName;
            List<String> list = getAllMarkdownFiles(subDir);
            fileNameList.addAll(list);
        }
        return fileNameList;
    }

    public static List<String> getIdiomFiles(String filepath) {
        return getAllMarkdownFiles(getIdiomPath(filepath));
    }

    public static String getVocabularyPath(String filepath) {
        return filepath + File.separator + "vocabulary";
    }

    public static String getIdiomPath(String filepath) {
        return filepath + File.separator + "idiom";
    }

}
