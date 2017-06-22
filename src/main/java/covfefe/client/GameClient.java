package covfefe.client;

import covfefe.network.XmlInStream;
import covfefe.types.*;
import covfefe.ki.Ki;
import covfefe.network.XmlOutStream;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.UnmarshalException;
import java.io.IOException;
import java.net.Socket;

/**
 * @author David Liebl
 */
public final class GameClient {

    private static final String GROUPNAME = "covfefe";
    public static Integer PLAYER_ID = null;
    private XmlOutStream outToServer;
    private XmlInStream fromServer;
    private Ki ki;

    public GameClient() {
    }

    public void initialize() {
        System.setProperty("javax.net.ssl.trustStore", "truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "1234");
        String serverIP = "localhost";
        int serverPort = 5432;

        // Build socket
        try {
            Socket socket = SSLSocketFactory.getDefault().createSocket(serverIP, serverPort);
            outToServer = new XmlOutStream(socket.getOutputStream());
            fromServer = new XmlInStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ki = new Ki();
    }

    public void start() {
        login();

        while (true) {
            MazeCom message = waitForMessage();
            processReceivedMessage(message);
        }
    }

    private void login() {
        ObjectFactory objectFactory = new ObjectFactory();
        MazeCom mc_login = objectFactory.createMazeCom();
        LoginMessageType login = objectFactory.createLoginMessageType();
        mc_login.setMcType(MazeComType.LOGIN);
        login.setName(GROUPNAME);
        mc_login.setLoginMessage(login);
        outToServer.write(mc_login);
    }

    private void processReceivedMessage(MazeCom msg) {
        if (msg == null) {
            return;
        }

        switch (msg.getMcType()) {

            case LOGINREPLY:
                LoginReplyMessageType loginReplyMessage = msg.getLoginReplyMessage();
                PLAYER_ID = loginReplyMessage.getNewID();
                System.out.println("Successfully logged in.");
                break;
            case AWAITMOVE:
                System.out.println("Server awaits move.");
                awaitMove(msg.getAwaitMoveMessage());
                break;
            case ACCEPT:
                System.out.println("Server accepted last sent message.");
                break;
            case WIN:
                WinMessageType winMessage = msg.getWinMessage();
                if (winMessage != null) {
                    if (winMessage.getWinner() != null) {
                        System.out.println(winMessage.getWinner().getValue() + " has won the game.");
                    }
                }
                exit();
                break;
            case DISCONNECT:
                System.out.println("Lost Connection to the server.");
                exit();
                break;
        }
    }

    private MazeCom waitForMessage() {
        try {
            return fromServer.readMazeCom();
        } catch (UnmarshalException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void awaitMove(AwaitMoveMessageType oldSituation) {
        outToServer.write(ki.calculateTurn(oldSituation));
    }


    private void exit() {
        System.exit(0);
    }

}