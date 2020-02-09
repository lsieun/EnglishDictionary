package lsieun.dict.utils;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NetWorkUtils {
    static {
        byte[] address = {54, (byte) 192, (byte) 151,77};
        try {
            InetAddress lessWrongWithname = InetAddress.getByAddress("www.vocabulary.com", address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static Optional<byte[]> fetch(final String url, Map<String, String> headers) {
        try {
            URL u = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            uc.setConnectTimeout(10000);
            uc.setReadTimeout(30000);
            uc.setRequestProperty("user-agent", "Mozilla/5.0");

            for (Map.Entry<String, String> field : headers.entrySet()) {
                uc.setRequestProperty(field.getKey(), field.getValue());
            }

            uc.connect();

            System.out.println("HTTP/1.x " + uc.getResponseCode() + " " + uc.getResponseMessage());
//            for (Map.Entry<String, List<String>> entry : uc.getHeaderFields().entrySet()) {
//                String line = String.format("%s: %s", entry.getKey(), entry.getValue());
//                System.out.println(line);
//            }


            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            try (
                    final InputStream in = uc.getInputStream();
                    BufferedInputStream bin = new BufferedInputStream(in);
            ) {
                byte[] buff = new byte[256 * 1024];
                for (int len = bin.read(buff); len != -1; len = bin.read(buff)) {
                    bao.write(buff, 0, len);
                }
            }
            uc.disconnect();

            final byte[] bytes = bao.toByteArray();
            return Optional.of(bytes);

        } catch (MalformedURLException e) {
            System.out.println("Illegal URL: " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
