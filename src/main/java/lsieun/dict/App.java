package lsieun.dict;

import lsieun.dict.gui.ClientGUI;

public class App {
    public static void main(String[] args) {
        ClientGUI client = new ClientGUI();
        client.init();
        client.launchFrame();
    }
}
