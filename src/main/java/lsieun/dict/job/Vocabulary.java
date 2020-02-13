package lsieun.dict.job;

import lsieun.dict.utils.ContentUtils;
import lsieun.dict.utils.NetWorkUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * https://www.vocabulary.com/dictionary/
 */
public class Vocabulary {
    private static final String URL_FORMAT = "https://www.vocabulary.com/dictionary/definition.ajax?search=%s&lang=en";

    public static void main(String[] args) {
        lookup(Common.WORD);
    }

    public static void lookup(final String word) {
        String url = String.format(URL_FORMAT, word);
        Optional<byte[]> result = NetWorkUtils.fetch(url, getHeaders());
        if (!result.isPresent()) {
            System.out.println("Can not find: " + word);
            return;
        }

        final byte[] bytes = result.get();
        String html = ContentUtils.toStr(bytes);
        ContentUtils.story(html);

    }

    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "text/html, */*; q=0.01");
        headers.put("referer", "https://www.vocabulary.com/dictionary/");
//        headers.put("sec-fetch-dest", "empty");
//        headers.put("sec-fetch-mode", "cors");
//        headers.put("sec-fetch-site", "same-origin");
//        headers.put("x-requested-with", "XMLHttpRequest");
        return headers;
    }
}
