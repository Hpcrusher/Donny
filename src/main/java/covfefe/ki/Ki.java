package covfefe.ki;

import covfefe.client.GameClient;
import covfefe.helpers.Board;
import covfefe.helpers.Card;
import covfefe.helpers.Position;
import covfefe.types.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        TreasureType currentTreasure = gameSituation.getTreasure();
        Card defaultCard = new Card(shiftCard);
        BoardType boardType = gameSituation.getBoard();
        Board board = new Board(boardType);

        CardType copyShiftCard;
        for (int i = 0; i <= 270; i += 90) {
            copyShiftCard = new Card(defaultCard.getShape(), Card.Orientation.fromValue(i), defaultCard.getTreasure());


            for (int row = 0; row < 7; row++) {
                for (int col = 0; col < 7; col++) {
                    Position shiftPosition = new Position(row, col);
                    if (shiftPosition.isLoosePosition()) {
                        if (checkMove(copyShiftCard, board, shiftPosition, currentTreasure)) {
                            System.out.println("Hab einen Weg gefunden!");
                            return getMazeCom(board.findTreasure(board.getTreasure()), copyShiftCard, shiftPosition);
                        }
                    }
                }
            }

        }
        System.out.println("Kein weg zum Ziel!");

        MoveMessageType moveMessageType = new MoveMessageType();
        moveMessageType.setShiftCard(shiftCard);
        PositionType shiftCardPosition = objectFactory.createPositionType();

        shiftCardPosition.setRow(0);
        shiftCardPosition.setCol(1);

        moveMessageType.setShiftPosition(shiftCardPosition);

        board.proceedShift(moveMessageType);

        Position myPosition = board.findPlayer(GameClient.PLAYER_ID);

        List<Position> allReachablePositions = board.getAllReachablePositions(myPosition);

        Random random = new Random();
        return getMazeCom(allReachablePositions.get(random.nextInt(allReachablePositions.size()-1)+1), shiftCard, shiftCardPosition);
    }

    private boolean checkMove(CardType shiftCard, Board board, PositionType shiftPosition, TreasureType treasure) {
        System.out.println(treasure.name());
        MoveMessageType move = new MoveMessageType();
        move.setShiftCard(shiftCard);

        move.setShiftPosition(shiftPosition);
        Board cBoard = board.fakeShift(move);

        PositionType treasurePosition = cBoard.findTreasure(treasure);
        List<Position> allReachablePositions = cBoard.getAllReachablePositions(cBoard.findPlayer(GameClient.PLAYER_ID));
        Optional<Position> position1 = allReachablePositions.stream().filter(position -> position.getCol() == treasurePosition.getCol() && position.getRow() == treasurePosition.getRow()).findAny();
        position1.ifPresent(System.out::println);
        return position1.isPresent();
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
