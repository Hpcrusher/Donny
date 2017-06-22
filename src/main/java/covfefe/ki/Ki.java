package covfefe.ki;

import covfefe.client.GameClient;
import covfefe.helpers.Board;
import covfefe.helpers.Card;
import covfefe.helpers.Position;
import covfefe.types.*;

import java.util.ArrayList;
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
            final MazeCom mazeCom = searchForDirectMove(currentTreasure, board, copyShiftCard);
            if (mazeCom != null) {
                return mazeCom;
            }
        }
        System.out.println("Kein direkter Weg zum Ziel!");

        return doRandomMove(shiftCard, board);
    }

    private MazeCom searchForDirectMove(TreasureType currentTreasure, Board board, CardType copyShiftCard) {
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                Position shiftPosition = new Position(row, col);
                if (shiftPosition.isLoosePosition() && !shiftPosition.equals(board.getForbidden())) {
                    PositionType positionType = checkMove(copyShiftCard, board, shiftPosition, currentTreasure);
                    if (positionType != null) {
                        System.out.println(positionType);
                        final MazeCom mazeCom = getMazeCom(positionType, copyShiftCard, shiftPosition);
                        if (board.validateTransition(mazeCom.getMoveMessage(), GameClient.PLAYER_ID)) {
                            System.out.println("Hab einen Weg gefunden!");
                            return mazeCom;
                        }
                    }
                }
            }
        }
        return null;
    }

    private MazeCom doRandomMove(CardType shiftCard, Board board) {
        MoveMessageType moveMessageType = new MoveMessageType();
        moveMessageType.setShiftCard(shiftCard);

        final PositionType randomShiftPosition = getRandomShiftPosition(board.getForbidden());
        moveMessageType.setShiftPosition(randomShiftPosition);

        board.proceedShift(moveMessageType);

        Position myPosition = board.findPlayer(GameClient.PLAYER_ID);

        List<Position> allReachablePositions = board.getAllReachablePositions(myPosition);

        Random random = new Random();
        final int size = allReachablePositions.size();
        int index = 0;
        if (size > 1) {
            index = random.nextInt(size - 1) + 1;
        }
        return getMazeCom(allReachablePositions.get(index), shiftCard, randomShiftPosition);
    }

    private PositionType checkMove(CardType shiftCard, final Board board, PositionType shiftPosition, TreasureType treasure) {
        MoveMessageType move = new MoveMessageType();
        move.setShiftCard(shiftCard);

        move.setShiftPosition(shiftPosition);
        Board cBoard = board.fakeShift(move);

        PositionType treasurePosition = cBoard.findTreasure(treasure);
        if (treasurePosition == null) {
            return null;
        }
        List<Position> allReachablePositions = cBoard.getAllReachablePositions(cBoard.findPlayer(GameClient.PLAYER_ID));
        Optional<Position> position1 = allReachablePositions.stream().filter(position -> position.getCol() == treasurePosition.getCol() && position.getRow() == treasurePosition.getRow()).findAny();
        return position1.orElse(null);
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

    public PositionType getRandomShiftPosition(PositionType forbidden) {
        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[]{0, 1});
        list.add(new int[]{0, 3});
        list.add(new int[]{0, 5});

        list.add(new int[]{1, 0});
        list.add(new int[]{3, 0});
        list.add(new int[]{5, 0});

        list.add(new int[]{1, 6});
        list.add(new int[]{3, 6});
        list.add(new int[]{5, 6});

        list.add(new int[]{6, 1});
        list.add(new int[]{6, 3});
        list.add(new int[]{6, 5});

        if (forbidden != null) {
            for (int[] i : list) {
                if (i[0] == forbidden.getRow() && i[1] == forbidden.getCol()) {
                    list.remove(i);
                    break;
                }
            }
        }

        PositionType position = objectFactory.createPositionType();

        int rnd = (int) (Math.random() * list.size());
        int[] posVals = list.get(rnd);

        position.setRow(posVals[0]);
        position.setCol(posVals[1]);

        return position;
    }

}
