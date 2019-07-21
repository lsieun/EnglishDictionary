package lsieun.dict.core;

import java.util.List;

public class Definition {
    // 指向word的link
    public Word word;
    // 真实存储的数据
    public String type;
    public String meaning_en;
    public String meaning_ch;
    public String tags;
    public String synonyms; // 同义词
    public String antonyms; // 反义词
    public String similars; // 形近词
    public List<String> examples;
    public List<String> pictures;
    public List<String> picture_pathes;

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: %s%s", "Type", type, separator));
        sb.append(String.format("%s: %s%s", "Meaning", meaning_en, separator));
        sb.append(String.format("%s: %s%s", "Chinese", meaning_ch, separator));
        if (tags != null) {
            sb.append(String.format("%s: %s%s", "Tags", tags, separator));
        }
        if (synonyms != null) {
            sb.append(String.format("%s: %s%s", "Synonyms", synonyms, separator));
        }
        if (antonyms != null) {
            sb.append(String.format("%s: %s%s", "Antonyms", antonyms, separator));
        }
        if (similars != null) {
            sb.append(String.format("%s: %s%s", "Similar", similars, separator));
        }
        if (examples != null) {
            for (String str : examples) {
                sb.append(String.format("%s: %s%s", "Eg.", str, separator));
            }
        }

        if (picture_pathes != null) {
            for (String str : picture_pathes) {
                sb.append(String.format("%s: %s%s", "Picture", str, separator));
            }
        }
        sb.append(separator);

        return sb.toString();
    }
}
