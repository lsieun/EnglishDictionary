package lsieun.dict.core;

import java.util.List;

public class Definition {
    // 指向word的link
    public Word word;
    // 真实存储的数据
    public String type;
    public String plural;
    public String single;
    public String comparative;
    public String meaning_en;
    public String meaning_ch;
    public String tags;
    public String synonyms; // 同义词
    public String antonyms; // 反义词
    public String similar; // 形近词
    public String use;
    public List<String> examples;
    public List<String> pictures;
    public List<String> picture_pathes;

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: %s%s", "Type", type, separator));
        if (plural != null && !"".equals(plural)) {
            sb.append(String.format("%s: %s%s", "Plural", plural, separator));
        }
        if (single != null && !"".equals(single)) {
            sb.append(String.format("%s: %s%s", "Single", single, separator));
        }
        if (comparative != null && !"".equals(comparative)) {
            sb.append(String.format("%s: %s%s", "Comparative", comparative, separator));
        }
        sb.append(String.format("%s: %s%s", "Meaning", meaning_en, separator));
        sb.append(String.format("%s: %s%s", "Chinese", meaning_ch, separator));
        if (tags != null && !"".equals(tags)) {
            sb.append(String.format("%s: %s%s", "Tags", tags, separator));
        }
        if (synonyms != null && !"".equals(synonyms)) {
            sb.append(String.format("%s: %s%s", "Synonyms", synonyms, separator));
        }
        if (antonyms != null && !"".equals(antonyms)) {
            sb.append(String.format("%s: %s%s", "Antonyms", antonyms, separator));
        }
        if (similar != null && !"".equals(similar)) {
            sb.append(String.format("%s: %s%s", "Similar", similar, separator));
        }
        if (use != null && !"".equals(use)) {
            sb.append(String.format("%s: %s%s", "Use", use, separator));
        }
        if (examples != null && examples.size() > 0) {
            for (String str : examples) {
                sb.append(String.format("%s: %s%s", "Eg.", str, separator));
            }
        }

        if (picture_pathes != null && picture_pathes.size() > 0) {
            for (String str : picture_pathes) {
                sb.append(String.format("%s: %s%s", "Picture", str, separator));
            }
        }
        sb.append(separator);

        return sb.toString();
    }
}
