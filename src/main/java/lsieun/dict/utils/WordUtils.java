package lsieun.dict.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lsieun.dict.core.Definition;
import lsieun.dict.core.Word;
import lsieun.utils.StringUtils;
import lsieun.utils.io.FileUtils;

public class WordUtils {

    public static Word parseWord(String word) {
        String filepath = getWordFilePath(word);
        return parseFile(filepath);
    }

    public static Word parseFile(String filepath) {
        checkFileExist(filepath);
        List<String> lines = FileUtils.readLines(filepath);

        Word w = null;
        Definition def = null;
        for (String line : lines) {
            if (StringUtils.isBlank(line)) continue;
            if (line.startsWith("#")) continue;

            int index = line.indexOf(":");
            if (index < 0) continue;

            String firstPart = line.substring(0, index);
            String secondPart = line.substring(index + 1);

            if (firstPart.startsWith("- ")) {
                firstPart = firstPart.replaceFirst("- ", "");
            }
            firstPart = firstPart.trim();
            if (StringUtils.isBlank(firstPart)) continue;

            secondPart = secondPart.trim();
            secondPart = secondPart.replaceAll("( )\\1+", " ");
            if (StringUtils.isBlank(secondPart)) continue;

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
                    w.stories = new ArrayList<>();
                }
                w.stories.add(secondPart);
                continue;
            }


            if ("Type".equals(firstPart)) {
                def = new Definition();
                def.type = secondPart;

                if (w.definitions == null) {
                    w.definitions = new ArrayList<>();
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
            } else if ("Synonyms".equals(firstPart)) {
                def.synonyms = secondPart;
            } else if ("Antonyms".equals(firstPart)) {
                def.antonyms = secondPart;
            } else if ("Similar".equals(firstPart)) {
                def.similar = secondPart;
            } else if ("Use".equals(firstPart)) {
                def.use = secondPart;
            } else if ("Tags".equals(firstPart)) {
                def.tags = secondPart;
            } else if ("Eg.".equals(firstPart)) {
                if (def.examples == null) {
                    def.examples = new ArrayList<>();
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
                    def.pictures = new ArrayList<>();
                    def.picture_pathes = new ArrayList<>();
                }
                def.pictures.add(secondPart);
                def.picture_pathes.add(picture);
            } else {
                throw new RuntimeException("Unknown Attribute: '" + firstPart + "' in file://" + filepath);
            }
        }

        return w;
    }

    public static void rewriteWord(String word) {
        String filepath = getWordFilePath(word);
        rewriteFile(filepath);
    }

    public static void rewriteFile(String filepath) {
        checkFileExist(filepath);
        Word w = parseFile(filepath);
        List<String> lines = getWordLines(w);
        FileUtils.writeLines(filepath, lines);
    }

    public static void rewriteAll() {
        String filepath = PropertyUtils.getProperty("english.dictionary.filepath");
        List<String> fileList = DirectoryUtils.getVocabularyFiles(filepath);
        if (fileList == null || fileList.size() < 1) return;
        for (String file : fileList) {
            WordUtils.rewriteFile(file);
        }
    }

    public static List<String> getWordLines(Word w) {
        List<String> lines = new ArrayList<>();

        lines.add(String.format("# %s", w.name));
        lines.add("");

        lines.add(String.format("- %s: %s", "Word", w.name));
        if (StringUtils.isNotBlank(w.cognate)) {
            lines.add(String.format("- %s: %s", "Cognate", w.cognate));
        }
        if (w.stories != null && w.stories.size() > 0) {
            for (String story : w.stories) {
                lines.add(String.format("- %s: %s", "Story", story));
            }
        }
        lines.add("");

        if (w.definitions != null) {
            for (Definition def : w.definitions) {
                lines.add(String.format("- %s: %s", "Type", def.type));
                if (StringUtils.isNotBlank(def.plural)) {
                    lines.add(String.format("- %s: %s", "Plural", def.plural));
                }
                if (StringUtils.isNotBlank(def.single)) {
                    lines.add(String.format("- %s: %s", "Single", def.single));
                }
                if (StringUtils.isNotBlank(def.comparative)) {
                    lines.add(String.format("- %s: %s", "Comparative", def.comparative));
                }
                lines.add(String.format("- %s: %s", "Meaning", def.meaning_en));
                lines.add(String.format("- %s: %s", "Chinese", def.meaning_ch));
                if (StringUtils.isNotBlank(def.tags)) {
                    lines.add(String.format("- %s: %s", "Tags", def.tags));
                } else {
                    lines.add(String.format("- %s: ", "Tags"));
                }
                if (StringUtils.isNotBlank(def.synonyms)) {
                    lines.add(String.format("- %s: %s", "Synonyms", def.synonyms));
                }
                if (StringUtils.isNotBlank(def.antonyms)) {
                    lines.add(String.format("- %s: %s", "Antonyms", def.antonyms));
                }
                if (StringUtils.isNotBlank(def.similar)) {
                    lines.add(String.format("- %s: %s", "Similar", def.similar));
                }
                if (StringUtils.isNotBlank(def.use)) {
                    lines.add(String.format("- %s: %s", "Use", def.use));
                }
                if (def.examples != null && def.examples.size() > 0) {
                    for (String str : def.examples) {
                        lines.add(String.format("- %s: %s", "Eg.", str));
                    }
                } else {
                    lines.add(String.format("- %s: ", "Eg."));
                }

                if (def.pictures != null) {
                    for (String str : def.pictures) {
                        lines.add(String.format("- %s: %s", "Picture", str));
                    }
                }

                lines.add("");
            }

        }
        return lines;
    }

    public static List<String> getWordLines(String vocabulary, String type) {
        List<String> lines = new ArrayList<>();

        lines.add(String.format("# %s", vocabulary));
        lines.add("");

        lines.add(String.format("- %s: %s", "Word", vocabulary));
        lines.add(String.format("- %s: ", "Cognate"));
        lines.add(String.format("- %s: ", "Story"));
        lines.add("");

        lines.addAll(getDefinitionLines(type));

        return lines;
    }


    public static List<String> getDefinitionLines(String type) {
        if ("adj".equalsIgnoreCase(type)) {
            type = "adjective";
        } else if ("nc".equalsIgnoreCase(type)) {
            type = "noun";
        } else if ("nu".equalsIgnoreCase(type)) {
            type = "noun";
        } else if ("ns".equalsIgnoreCase(type)) {
            type = "noun";
        } else if ("ncu".equalsIgnoreCase(type)) {
            type = "noun";
        } else if ("vi".equalsIgnoreCase(type)) {
            type = "verb";
        } else if ("vt".equalsIgnoreCase(type)) {
            type = "verb";
        } else if ("vit".equalsIgnoreCase(type)) {
            type = "verb";
        } else if ("adv".equalsIgnoreCase(type)) {
            type = "adverb";
        }

        List<String> lines = new ArrayList<>();
        lines.add(String.format("- %s: %s", "Type", type));
        lines.add(String.format("- %s: ", "Plural"));
        lines.add(String.format("- %s: ", "Single"));
        lines.add(String.format("- %s: ", "Comparative"));
        lines.add(String.format("- %s: ", "Meaning"));
        lines.add(String.format("- %s: ", "Chinese"));
        lines.add(String.format("- %s: ", "Tags"));
        lines.add(String.format("- %s: ", "Synonyms"));
        lines.add(String.format("- %s: ", "Antonyms"));
        lines.add(String.format("- %s: ", "Similar"));
        lines.add(String.format("- %s: ", "Use"));
        lines.add(String.format("- %s: ", "Eg."));
        lines.add(String.format("- %s: ", "Picture"));
        lines.add("");
        return lines;
    }

    /**
     * @param word 单词
     * @param type 单词类型
     *             adj=adjective, nc=noun [C], nu=noun [U], ns=noun [S],
     *             ncu=noun [C or U], vi=verb [I], vt=verb [T], vit=verb [I or T],
     *             adv=adverb
     */
    public static void create(String word, String type) {
        if (StringUtils.isBlank(word)) return;
        String filepath = getWordFilePath(word);
        create(filepath, word, type);
    }

    public static void try_create(String word, String type) {
        try {
            create(word, type);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    public static void create(String filepath, String word, String type) {
        File file = new File(filepath);
        if (file.exists()) {
            throw new RuntimeException("File Exist: file://" + filepath);
        }
        File dirFile = file.getParentFile();
        if (!dirFile.exists()) {
            boolean flag = dirFile.mkdirs();
            if (!flag) throw new RuntimeException("Folder can not be created: " + dirFile);
        }

        List<String> lines = getWordLines(word, type);
        FileUtils.writeLines(filepath, lines);
    }

    public static void addDefinition(String vocabulary, String type) {
        if (StringUtils.isBlank(vocabulary)) return;
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
        Word w = parseFile(filepath);
        List<String> lines = getWordLines(w);
        lines.addAll(getDefinitionLines(type));
        FileUtils.writeLines(filepath, lines);
    }

    private static String getWordFilePath(String word) {
        if (StringUtils.isBlank(word)) return null;
        word = word.trim();

        char ch = word.charAt(0);
        String childpath = String.format("%s/%s.md", ch, word);
        childpath = childpath.toLowerCase();

        String root_path = PropertyUtils.getProperty("english.dictionary.filepath") + File.separator + "vocabulary";
        String filepath = root_path + File.separator + childpath.toLowerCase();
        System.out.println("file://" + filepath);
        return filepath;
    }

    private static void checkFileExist(String filepath) {
        if (StringUtils.isNotBlank(filepath)) {
            File file = new File(filepath);
            if (file.exists()) {
                return;
            }
        }
        throw new RuntimeException("File Not Exist: file://" + filepath);
    }
}
