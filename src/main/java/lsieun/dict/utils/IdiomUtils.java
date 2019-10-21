package lsieun.dict.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lsieun.utils.StringUtils;
import lsieun.utils.io.FileUtils;

public class IdiomUtils {

    public static void addIdiom(String idiom) {
        addItem(idiom, "idiom");
    }

    public static void addPhrase(String phrase) {
        addItem(phrase, "phrase");
    }

    public static void addSentence(String sentence) {
        addItem(sentence, "sentence");
    }

    public static void addItem(String item, String kind) {
        if (StringUtils.isBlank(item)) return;
        item = processItem(item);

        String filepath = getFilePath(item, kind);
        List<String> lines = getIdiomLines(item);
        FileUtils.writeLines(filepath, lines);
    }

    private static String processItem(String item) {
        item = item.trim();
        item = item.replaceAll("( )\\1+", " ");
        return item;
    }

    private static List<String> getIdiomLines(String idiom) {
        return getLines(idiom,"english.dictionary.idiom" );
    }

    private static List<String> getPhraseLines(String phrase) {
        return getLines(phrase,"english.dictionary.phrase" );
    }

    private static List<String> getSentenceLines(String sentence) {
        return getLines(sentence,"english.dictionary.sentence" );
    }

    private static List<String> getLines(String str, String property) {
        String line = PropertyUtils.getProperty(property);
        String[] array = line.split(",");

        List<String> lines = new ArrayList();
        lines.add(String.format("# %s", str));
        lines.add("");

        for(String element : array) {
            lines.add(String.format("- %s: ", element));
        }
        return lines;

    }

    private static String getFilePath(String idiom, String kind) {
        if (StringUtils.isBlank(idiom)) return null;
        idiom = idiom.replaceAll(" ", "_").replaceAll("'","");
        idiom = idiom.toLowerCase();

        String root_path = PropertyUtils.getProperty("english.dictionary.filepath") + File.separator + kind;
        String filepath = String.format("%s%s%s.md", root_path, File.separator, idiom);
        System.out.println("file://" + filepath);
        return filepath;
    }

    public static void main(String[] args) {
        List<String> word_field_list = new ArrayList();
        List<String> def_field_list = new ArrayList();

        String word_line = PropertyUtils.getProperty("english.dictionary.word");
        String def_line = PropertyUtils.getProperty("english.dictionary.definition");

        String[] word_field_array = word_line.split(",");
        for (String field : word_field_array) {
            word_field_list.add(field);
        }

        String[] def_field_array = def_line.split(",");
        for (String field : def_field_array) {
            def_field_list.add(field);
        }

        System.out.println(word_field_list);
        System.out.println(def_field_list);

        List<String> lines = FileUtils.readLines("/home/liusen/Workspace/git-repo/EnglishDictionary/vocabulary/r/rivet.md");

        for (String line : lines) {
            if (StringUtils.isBlank(line)) continue;
            if (line.startsWith("#")) continue;
            int index = line.indexOf(":");

            String firstPart = line.substring(0, index);
            String secondPart = line.substring(index + 1);

            if (firstPart.startsWith("- ")) {
                firstPart = firstPart.replaceFirst("- ", "");
            }
            if (StringUtils.isBlank(firstPart)) continue;

            secondPart = secondPart.trim();
            secondPart = secondPart.replaceAll("( )\\1+", "");
            if (StringUtils.isBlank(secondPart)) continue;
        }

    }
}
