package com.chess.engine.player.AI;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class MinMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;

    public MinMax() {
        this.boardEvaluator = null;
    }

    @Override
    public String toString() {
        return "MinMax";
    }

    @Override
    public Move execute(Board board, int depth) {

        final long startTime = System.currentTimeMillis();
        Move bestMove = null;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowerstSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + " THINKING with depth = " + depth);

        int numMoves = board.currentPlayer().getLegalMoves().size();
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                //make move, if move made, if current player was white, then next move is minimizing (black moves
                //white = maximizing player, black = minimizing player
                currentValue = board.currentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getBoard(), depth - 1) :
                        max(moveTransition.getBoard(), depth - 1);
                if(board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (board.currentPlayer().getAlliance().isBlack() && currentValue <= lowerstSeenValue) {
                    lowerstSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;
        return bestMove;
    }

    public int min(final Board board, final int depth) {
        if(depth == 0) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowerstSeenValue = Integer.MAX_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getBoard(), depth - 1);
                if (currentValue <= lowerstSeenValue) {
                    lowerstSeenValue = currentValue;
                }
            }
        }
        return lowerstSeenValue;
    }

    public int max(final Board board, final int depth) {
        if(depth == 0) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()) {
                final int currentValue = min(moveTransition.getBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }
}
