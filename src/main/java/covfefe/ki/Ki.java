package covfefe.ki;

import covfefe.types.*;

/**
 * @author David Liebl
 */
public final class Ki {

    private ObjectFactory objectFactory;

    public Ki() {
        this.objectFactory = new ObjectFactory();
    }

    public MazeCom calculateTurn(AwaitMoveMessageType gameSituation) {

        CardType shiftCard = gameSituation.getBoard().getShiftCard();

        PositionType pinPosition = objectFactory.createPositionType();

        pinPosition.setCol(0);
        pinPosition.setRow(0);

        PositionType shiftCardPosition = objectFactory.createPositionType();

        shiftCardPosition.setRow(0);
        shiftCardPosition.setCol(1);

        return getMazeCom(pinPosition, shiftCard, shiftCardPosition);
    }

    private MazeCom getMazeCom(PositionType pinPosition, CardType shiftCard, PositionType shiftCardPosition) {
        MoveMessageType moveMessageType = objectFactory.createMoveMessageType();

        moveMessageType.setShiftCard(shiftCard);
        moveMessageType.setShiftPosition(shiftCardPosition);
        moveMessageType.setNewPinPos(pinPosition);

        MazeCom mazeCom = objectFactory.createMazeCom();

        mazeCom.setMcType(MazeComType.MOVE);
        mazeCom.setMoveMessage(moveMessageType);

        return mazeCom;
    }

}
