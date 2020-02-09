package lsieun.dict.job;

import lsieun.dict.utils.WordUtils;

public class AddNewDefinition {
    public static void main(String[] args) {
        WordUtils.addDefinition(Common.WORD, Common.TYPE);
    }
}
