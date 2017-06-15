package covfefe;


import covfefe.client.GameClient;

/**
 * @author liebl
 */
public class Main {

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.initialize();
        client.start();
    }

}
