package lsieun.dict.job;

import lsieun.dict.utils.WordUtils;

public class AddNewWord {
    public static void main(String[] args) {
        Common.set("agenda", "nu");
        WordUtils.try_create(Common.WORD, Common.TYPE);
        Vocabulary_Socket.lookup(Common.WORD);
    }
}
