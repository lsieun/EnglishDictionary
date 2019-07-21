package lsieun.dict.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lsieun.dict.core.Definition;
import lsieun.dict.core.Word;
import lsieun.utils.StringUtils;
import lsieun.utils.io.FileUtils;

public class WordUtils {

    public static Word parse(String filepath) {
        List<String> lines = FileUtils.readLines(filepath);

        Word w = null;
        Definition def = null;
        for(String line : lines) {
            if(StringUtils.isBlank(line)) continue;
            if(line.startsWith("#")) continue;
            int index = line.indexOf(":");

            String firstPart = line.substring(0, index);
            String secondPart = line.substring(index + 1);

            if(firstPart.startsWith("- ")) {
                firstPart = firstPart.replaceFirst("- ", "");
            }
            secondPart = secondPart.trim();

            if("Word".equals(firstPart)) {
                w = new Word();
                w.name = secondPart;
                continue;
            }
            if (w == null) continue;

            if("Cognate".equals(firstPart)) {
                w.cognate = secondPart;
                continue;
            }


            if ("Type".equals(firstPart)) {
                def = new Definition();
                def.type = secondPart;

                if (w.definitions == null) {
                    w.definitions = new ArrayList();
                }
                w.definitions.add(def);
                continue;
            }
            if (def == null) continue;

            if ("Meaning".equals(firstPart)) {
                def.meaning_en = secondPart;
            }
            else if("Meaning_ch".equals(firstPart)) {
                def.meaning_ch = secondPart;
            }
            else if("Chinese".equals(firstPart)) {
                def.meaning_ch = secondPart;
            }
            else if("Tags".equals(firstPart)) {
                def.tags = secondPart;
            }
            else if("Eg.".equals(firstPart)) {
                if (def.examples == null) {
                    def.examples = new ArrayList();
                }
                def.examples.add(secondPart);
            }
            else if("Picture".equals(firstPart)) {
                int lastIndex = filepath.lastIndexOf("/");
                String dir = filepath.substring(0, lastIndex);
                int left = secondPart.indexOf("(");
                int right = secondPart.indexOf(")");
                String picture = dir + File.separator + secondPart.substring(left + 1, right);
                if (def.pictures == null) {
                    def.pictures = new ArrayList();
                    def.picture_pathes = new ArrayList();
                }
                def.pictures.add(secondPart);
                def.picture_pathes.add(picture);
            }
        }

        return w;
    }

    public static void rewrite(String filepath) {
        Word w = parse(filepath);
        String separator = System.getProperty("line.separator");
        List<String> lines = new ArrayList();

        lines.add(String.format("# %s%s", w.name, separator));
        lines.add(separator);

        lines.add(String.format("- %s: %s%s", "Word", w.name, separator));
        if (w.cognate != null) {
            lines.add(String.format("- %s: %s%s", "Cognate", w.cognate, separator));
        }
        lines.add(separator);

        if (w.definitions != null) {
            for (Definition def : w.definitions) {
                lines.add(String.format("- %s: %s%s", "Type", def.type, separator));
                lines.add(String.format("- %s: %s%s", "Meaning", def.meaning_en, separator));
                lines.add(String.format("- %s: %s%s", "Chinese", def.meaning_ch, separator));
                if (def.tags != null) {
                    lines.add(String.format("- %s: %s%s", "Tags", def.tags, separator));
                }
                if (def.synonyms != null) {
                    lines.add(String.format("- %s: %s%s", "Synonyms", def.synonyms, separator));
                }
                if (def.antonyms != null) {
                    lines.add(String.format("- %s: %s%s", "Antonyms", def.antonyms, separator));
                }
                if (def.similars != null) {
                    lines.add(String.format("- %s: %s%s", "Similar", def.similars, separator));
                }
                if (def.examples != null) {
                    for (String str : def.examples) {
                        lines.add(String.format("- %s: %s%s", "Eg.", str, separator));
                    }
                }

                if (def.pictures != null) {
                    for (String str : def.pictures) {
                        lines.add(String.format("- %s: %s%s", "Picture", str, separator));
                    }
                }
                lines.add(separator);
            }

        }

        FileUtils.writeLines(filepath, lines);
    }

}
