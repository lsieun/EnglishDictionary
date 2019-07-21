package lsieun.dict.utils;

import java.awt.*;

import javax.swing.*;

public class FrameUtils {
    public static void initFrame(JFrame frame, int width, int height) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();

        int screenX = (int) d.getWidth();
        int screenY = (int) d.getHeight();

        int leftTopX = (screenX - width) / 2;
        int leftTopY = (screenY - height) / 2;

        frame.setTitle("lsieun-English Dictionary");
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setBounds(leftTopX, leftTopY, width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置ICON
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("circle.png"));
        frame.setVisible(true);
    }

    public static void quit(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }
}
