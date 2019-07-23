package lsieun.dict.core;

import java.util.List;

public class Word {
    public String name;
    public String cognate; // 同源词
    public List<String> stories;
    public List<Definition> definitions;

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: %s%s", "Word", name, separator));
        if (cognate != null && !"".equals(cognate)) {
            sb.append(String.format("%s: %s%s", "Cognate", cognate, separator));
        }
        if (stories != null && stories.size() > 0) {
            for (String story : stories) {
                sb.append(String.format("%s: %s%s", "Story", story, separator));
            }
        }
        sb.append(separator);
        if (definitions != null) {
            for (Definition def : definitions) {
                sb.append(def);
            }
        }

        return sb.toString();
    }
}
