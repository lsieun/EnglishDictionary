package lsieun.dict.job;

import lsieun.dict.utils.ContentUtils;
import lsieun.dict.utils.SocketUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Vocabulary_Socket {
    private static final String REQUEST_LINE = "GET /dictionary/%s HTTP/1.1";

    public static void main(String[] args) {
        String word = Common.WORD;
        lookup(word);
    }

    public static void lookup(String word) {
        List<String> list = getRequestContent(word);
        final Optional<byte[]> result = SocketUtils.fetch(list);
        if (!result.isPresent()) {
            System.out.println("Can not find: " + word);
            return;
        }

        byte[] bytes = result.get();
        String html = ContentUtils.toStr(bytes);
        ContentUtils.story(html);
    }

    public static List<String> getRequestContent(final String word) {
        List<String> list = new ArrayList<>();
        list.add(String.format(REQUEST_LINE, word));
        list.add("Host: www.vocabulary.com");
        list.add("Referer: https://www.vocabulary.com/dictionary/");
        list.add("Connection: close");
        list.add("User-Agent: Mozilla/5.0");
        list.add("Accept: text/html");
        list.add("Accept-Language: en-US");
        return list;
    }
}
