package lsieun.dict.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lsieun.dict.core.Definition;
import lsieun.dict.core.Word;
import lsieun.utils.StringUtils;
import lsieun.utils.io.FileUtils;

public class WordUtils {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static Word parse(String filepath) {
        List<String> lines = FileUtils.readLines(filepath);

        Word w = null;
        Definition def = null;
        for (String line : lines) {
            if (StringUtils.isBlank(line)) continue;
            if (line.startsWith("#")) continue;
            int index = line.indexOf(":");

            String firstPart = line.substring(0, index);
            String secondPart = line.substring(index + 1);

            if (firstPart.startsWith("- ")) {
                firstPart = firstPart.replaceFirst("- ", "");
            }
            secondPart = secondPart.trim();
            secondPart = secondPart.replaceAll("( )\\1+", "");

            if ("Word".equals(firstPart)) {
                w = new Word();
                w.name = secondPart;
                continue;
            }
            if (w == null) continue;

            if ("Cognate".equals(firstPart)) {
                w.cognate = secondPart;
                continue;
            }

            if ("Story".equals(firstPart)) {
                if (StringUtils.isBlank(secondPart)) continue;

                if (w.stories == null) {
                    w.stories = new ArrayList();
                }
                w.stories.add(secondPart);
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
            } else if ("Meaning_ch".equals(firstPart)) {
                def.meaning_ch = secondPart;
            } else if ("Chinese".equals(firstPart)) {
                def.meaning_ch = secondPart;
            } else if ("Plural".equals(firstPart)) {
                def.plural = secondPart;
            } else if ("Single".equals(firstPart)) {
                def.single = secondPart;
            } else if ("Comparative".equals(firstPart)) {
                def.comparative = secondPart;
            }
            else if ("Synonyms".equals(firstPart)) {
                def.synonyms = secondPart;
            }
            else if ("Antonyms".equals(firstPart)) {
                def.antonyms = secondPart;
            }
            else if ("Similar".equals(firstPart)) {
                def.similar = secondPart;
            }
            else if ("Use".equals(firstPart)) {
                def.use = secondPart;
            } else if ("Tags".equals(firstPart)) {
                def.tags = secondPart;
            } else if ("Eg.".equals(firstPart)) {
                if (def.examples == null) {
                    def.examples = new ArrayList();
                }
                def.examples.add(secondPart);
            } else if ("Picture".equals(firstPart)) {
                if (StringUtils.isBlank(secondPart)) continue;

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
            } else {
                throw new RuntimeException("Unknown Attribute: '" + firstPart + "' in file://" + filepath);
            }
        }

        return w;
    }

    public static void rewriteWord(String vocabulary) {
        if(StringUtils.isBlank(vocabulary)) return;
        vocabulary = vocabulary.trim();
        char ch = vocabulary.charAt(0);
        String filepath = PropertyUtils.getProperty("english.dictionary.filepath");
        filepath += File.separator + "vocabulary";
        String dir = filepath + File.separator + ch;
        String newFilePath = dir + File.separator + vocabulary + ".md";
        System.out.println("file://" + newFilePath);

        File file = new File(newFilePath);
        if (!file.exists()) {
            System.out.println("File Not Exist: file://" + newFilePath);
            return;
        }
        rewriteFile(newFilePath);
    }

    public static void rewriteFile(String filepath) {
        Word w = parse(filepath);
        List<String> lines = getWordLines(w);
        FileUtils.writeLines(filepath, lines);
    }

    public static void rewriteAll() {
        String filepath = PropertyUtils.getProperty("english.dictionary.filepath");
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);
        for (String str : fileList) {
            WordUtils.rewriteFile(str);
        }
    }

    public static List<String> getWordLines(Word w) {
        List<String> lines = new ArrayList();

        lines.add(String.format("# %s%s", w.name, LINE_SEPARATOR));
        lines.add(LINE_SEPARATOR);

        lines.add(String.format("- %s: %s%s", "Word", w.name, LINE_SEPARATOR));
        if (StringUtils.isNotBlank(w.cognate)) {
            lines.add(String.format("- %s: %s%s", "Cognate", w.cognate, LINE_SEPARATOR));
        }
        if (w.stories != null && w.stories.size() > 0) {
            for (String story : w.stories) {
                lines.add(String.format("- %s: %s%s", "Story", story, LINE_SEPARATOR));
            }
        }
        lines.add(LINE_SEPARATOR);

        if (w.definitions != null) {
            for (Definition def : w.definitions) {
                lines.add(String.format("- %s: %s%s", "Type", def.type, LINE_SEPARATOR));
                if (StringUtils.isNotBlank(def.plural)) {
                    lines.add(String.format("- %s: %s%s", "Plural", def.plural, LINE_SEPARATOR));
                }
                if (StringUtils.isNotBlank(def.single)) {
                    lines.add(String.format("- %s: %s%s", "Single", def.single, LINE_SEPARATOR));
                }
                if (StringUtils.isNotBlank(def.comparative)) {
                    lines.add(String.format("- %s: %s%s", "Comparative", def.comparative, LINE_SEPARATOR));
                }
                lines.add(String.format("- %s: %s%s", "Meaning", def.meaning_en, LINE_SEPARATOR));
                lines.add(String.format("- %s: %s%s", "Chinese", def.meaning_ch, LINE_SEPARATOR));
                if (StringUtils.isNotBlank(def.tags)) {
                    lines.add(String.format("- %s: %s%s", "Tags", def.tags, LINE_SEPARATOR));
                }
                else {
                    lines.add(String.format("- %s: %s", "Tags", LINE_SEPARATOR));
                }
                if (StringUtils.isNotBlank(def.synonyms)) {
                    lines.add(String.format("- %s: %s%s", "Synonyms", def.synonyms, LINE_SEPARATOR));
                }
                if (StringUtils.isNotBlank(def.antonyms)) {
                    lines.add(String.format("- %s: %s%s", "Antonyms", def.antonyms, LINE_SEPARATOR));
                }
                if (StringUtils.isNotBlank(def.similar)) {
                    lines.add(String.format("- %s: %s%s", "Similar", def.similar, LINE_SEPARATOR));
                }
                if (StringUtils.isNotBlank(def.use)) {
                    lines.add(String.format("- %s: %s%s", "Use", def.use, LINE_SEPARATOR));
                }
                if (def.examples != null && def.examples.size() > 0) {
                    for (String str : def.examples) {
                        lines.add(String.format("- %s: %s%s", "Eg.", str, LINE_SEPARATOR));
                    }
                }
                else {
                    lines.add(String.format("- %s: %s", "Eg.", LINE_SEPARATOR));
                }

                if (def.pictures != null) {
                    for (String str : def.pictures) {
                        lines.add(String.format("- %s: %s%s", "Picture", str, LINE_SEPARATOR));
                    }
                }

                lines.add(LINE_SEPARATOR);
            }

        }
        return lines;
    }

    public static List<String> getWordLines(String vocabulary, String type) {
        List<String> lines = new ArrayList();

        lines.add(String.format("# %s%s", vocabulary, LINE_SEPARATOR));
        lines.add(LINE_SEPARATOR);

        lines.add(String.format("- %s: %s%s", "Word", vocabulary, LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Cognate", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Story", LINE_SEPARATOR));
        lines.add(LINE_SEPARATOR);

        lines.addAll(getDefinitionLines(type));

        return lines;
    }

    public static List<String> getDefinitionLines(String type) {
        if ("adj".equalsIgnoreCase(type)) {
            type = "adjective";
        }
        else if("nc".equalsIgnoreCase(type)) {
            type = "noun [C]";
        }
        else if("nu".equalsIgnoreCase(type)) {
            type = "noun [U]";
        }
        else if("ns".equalsIgnoreCase(type)) {
            type = "noun [S]";
        }
        else if("ncu".equalsIgnoreCase(type)) {
            type = "noun [C or U]";
        }
        else if("vi".equalsIgnoreCase(type)) {
            type = "verb [I]";
        }
        else if("vt".equalsIgnoreCase(type)) {
            type = "verb [T]";
        }
        else if("vit".equalsIgnoreCase(type)) {
            type = "verb [I or T]";
        }
        else if("adv".equalsIgnoreCase(type)) {
            type = "adverb";
        }

        List<String> lines = new ArrayList();
        lines.add(String.format("- %s: %s%s", "Type", type, LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Plural", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Single", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Comparative", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Meaning", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Chinese", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Tags", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Synonyms", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Antonyms", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Similar", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Use", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Eg.", LINE_SEPARATOR));
        lines.add(String.format("- %s: %s", "Picture", LINE_SEPARATOR));
        lines.add(LINE_SEPARATOR);
        return lines;
    }

    public static void create(String vocabulary, String type) {
        if(StringUtils.isBlank(vocabulary)) return;
        vocabulary = vocabulary.trim();
        String filepath = PropertyUtils.getProperty("english.dictionary.filepath");
        filepath += File.separator + "vocabulary";
        create(filepath, vocabulary, type);
    }

    public static void create(String filepath, String vocabulary, String type) {
        if(StringUtils.isBlank(vocabulary)) return;
        vocabulary = vocabulary.trim();
        char ch = vocabulary.charAt(0);
        String dir = filepath + File.separator + ch;
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String newFilePath = dir + File.separator + vocabulary + ".md";
        System.out.println("file://" + newFilePath);
        File file = new File(newFilePath);
        if (file.exists()) {
            throw new RuntimeException("File Exist: file://" + newFilePath);
        }

        List<String> lines = getWordLines(vocabulary, type);


        FileUtils.writeLines(newFilePath, lines);
    }

    public static void addDefinition(String vocabulary, String type) {
        if(StringUtils.isBlank(vocabulary)) return;
        vocabulary = vocabulary.trim();

        String dict_path = PropertyUtils.getProperty("english.dictionary.filepath");
        String vocabulary_path = dict_path + File.separator + "vocabulary";
        char ch = vocabulary.charAt(0);
        String dir = vocabulary_path + File.separator + ch;
        String filepath = dir + File.separator + vocabulary + ".md";
        File file = new File(filepath);

        if (!file.exists()) {
            throw new RuntimeException("File Not Exist: " + filepath);
        }

        System.out.println("file://" + filepath);
        Word w = parse(filepath);
        List<String> lines = getWordLines(w);
        lines.addAll(getDefinitionLines(type));
        FileUtils.writeLines(filepath, lines);
    }
}
