package com.chess.engine.player.AI;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MinMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;

    public MinMax() {
        //TODO: create board evaluator
        this.boardEvaluator = null;
    }

    @Override
    public String toString() {
        return "MinMax";
    }

    @Override
    public Move execute(Board board, int depth) {
        return null;
    }
}
