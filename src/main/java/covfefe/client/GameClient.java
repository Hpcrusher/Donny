package covfefe.client;

import covfefe.types.LoginMessageType;
import covfefe.types.MazeCom;
import covfefe.types.MazeComType;
import covfefe.types.ObjectFactory;
import covfefe.ki.Ki;
import covfefe.network.XmlOutStream;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author David Liebl
 */
public final class GameClient {

    private static final String GROUPNAME = "covfefe";
    private static XmlOutStream outToServer;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = true;
    private static Socket socket;
    private static ObjectFactory objectFactory = new ObjectFactory();

    private GameClient(Socket socket) throws IOException {
        GameClient.socket = socket;
        outToServer = new XmlOutStream(GameClient.socket.getOutputStream());
    }

    public static void main(String[] args) throws Throwable {

        System.setProperty("javax.net.ssl.trustStore", "truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "1234");
        String serverIP = "localhost";
        int serverPort = 5432;

        // Build socket
        socket = SSLSocketFactory.getDefault().createSocket(serverIP, serverPort);
        if (socket == null) {
            throw new IllegalArgumentException();
        }

        // Start client
        GameClient client = new GameClient(socket);
        ServerListener clientThread = new ServerListener(socket);
        clientThread.start();
        client.start();

        scanner.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void start() {
        login();

        while (isRunning()) {
            // keeps client alive
        }
    }

    private void login() {
        MazeCom mc_login = objectFactory.createMazeCom();
        LoginMessageType login = objectFactory.createLoginMessageType();
        mc_login.setMcType(MazeComType.LOGIN);
        login.setName(GROUPNAME);
        mc_login.setLoginMessage(login);
        outToServer.write(mc_login);
    }

    static void awaitMoveCallBack(MazeCom oldSituation) {
        outToServer.write(Ki.calculateTurn(oldSituation));
    }


    static void exitCallBack() {
        System.exit(0);
    }

    static boolean isRunning() {
        return running;
    }
}