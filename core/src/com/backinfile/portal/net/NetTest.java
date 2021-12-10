package com.backinfile.portal.net;

import com.backinfile.support.Time2;
import com.backinfile.support.Utils;

import java.io.IOException;

public class NetTest {

}


class TestClient {
    public static void main(String[] args) throws IOException {
        ServerLocateManager.startSearch();

        while (true) {
            Utils.sleep(Time2.SEC);
            if (!ServerLocateManager.serverAddressList.isEmpty()) {
                String serverAddress = ServerLocateManager.serverAddressList.stream().findAny().get();
                Client client = new Client(serverAddress);
                client.start();
                break;
            }
        }
        Utils.readExit();
    }
}

class TestServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();

        ServerLocateManager.serverStartResponse();

        Utils.readExit();
    }
}