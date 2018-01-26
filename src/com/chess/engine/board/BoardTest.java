package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.player.AI.MinMax;
import com.chess.engine.player.AI.MoveStrategy;
import com.chess.engine.player.MoveTransition;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void initialBoard() {
        final Board board = Board.createStandardBoard();

        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        assertTrue(board.whitePlayer().toString().equals("White"));
        assertTrue(board.blackPlayer().toString().equals("Black"));
    }

    @Test
    public void testFoolsMate() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtCoordinate('e7'),
                BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtCoordinate('e7'),
                BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtCoordinate('e7'),
                BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveStrategy strategy = new MinMax(4);
        final Move AIMove = strategy.execute(t3.getBoard());
        final Move bestMove = Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                BoardUtils.getCoordinateAtPosition("h4"));

        assertEquals(AIMove, bestMove);
    }

}