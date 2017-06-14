package covfefe.util;

import covfefe.types.*;

/**
 * @author David Liebl
 */
public final class MazeComFactory {

    private static ObjectFactory objectFactory = new ObjectFactory();

    public static MazeCom createMoveMessage(int playerID, MoveMessageType move) {
        MazeCom mc = objectFactory.createMazeCom();
        mc.setMcType(MazeComType.MOVE);
        mc.setId(playerID);

        mc.setMoveMessage(move);
        return mc;
    }
}
