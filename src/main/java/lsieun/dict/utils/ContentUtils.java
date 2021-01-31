package lsieun.dict.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ContentUtils {

    public static String toStr(byte[] bytes) {
        return toStr(bytes, StandardCharsets.UTF_8);
    }

    public static String toStr(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }

    public static void story(String html) {
        final Document doc = Jsoup.parse(html);
        Element divBlock = doc.select("div.word-area").first();
        final Elements ps = divBlock.select("p");
        ps.forEach(item -> System.out.println(item.text()));
    }

}
