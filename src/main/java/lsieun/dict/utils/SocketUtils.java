package lsieun.dict.utils;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public class SocketUtils {

    private static final InetSocketAddress address;

    static {
        byte[] ip_bytes = {99, 84, (byte) 233, (byte) 191};
        try {
            InetAddress inetAddress = InetAddress.getByAddress("www.vocabulary.com", ip_bytes);
            address = new InetSocketAddress(inetAddress, 443);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<byte[]> fetch(List<String> list) {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (
                Socket s = factory.createSocket();
        ) {
            s.setSoTimeout(Const.READ_TIMEOUT);
            s.connect(address, Const.CONNECT_TIMEOUT);
            try (
                    InputStream in = s.getInputStream();
                    BufferedInputStream bin = new BufferedInputStream(in);
                    OutputStream out = s.getOutputStream();
                    BufferedOutputStream bout = new BufferedOutputStream(out);
                    OutputStreamWriter writer = new OutputStreamWriter(bout)
            ) {
                for (String line : list) {
                    writer.write(line);
                    writer.write("\r\n");
                }
                writer.write("\r\n\r\n");
                writer.flush();

                byte[] marks = new byte[4];
                for (int value = bin.read(); value != -1; value = bin.read()) {
                    byte b = (byte) value;
                    marks[0] = marks[1];
                    marks[1] = marks[2];
                    marks[2] = marks[3];
                    marks[3] = b;
                    if (marks[0] == '\r' && marks[1] == '\n' && marks[2] == '\r' && marks[3] == '\n') {
                        break;
                    }
                }

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                byte[] buff = new byte[Const.BUFF_SIZE];
                for (int len = bin.read(buff); len != -1; len = bin.read(buff)) {
                    bao.write(buff, 0, len);
                }
                byte[] bytes = bao.toByteArray();
                return Optional.of(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
