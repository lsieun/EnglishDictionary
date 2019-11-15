package lsieun.dict.utils;

/**
 * Hello World
 */
public class TempTest {
    /**
     * my parameter
     * @param args
     */
    public static void main(String[] args) {
        String line = "how   are    you";
        String result = line.replaceAll("( )\\1+", " ");
        System.out.println(result);
    }
}
