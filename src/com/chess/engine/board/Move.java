package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;


    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
            //current player, keep all non-moved pieces at the same tile
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        //enemy pieces
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
            builder.setPiece(piece);
        }
        //move the moved piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    public static final class MajorMove extends Move {
        public MajorMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;
        public AttackMove(Board board, Piece movedPiece,
                          int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class PawnMove extends Move {
        public PawnMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static final class PawnAttackMove extends AttackMove {
        public PawnAttackMove(final Board board, final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

    }

    public static final class PawnEnPassantAttackMove extends AttackMove {
        public PawnEnPassantAttackMove(final Board board, final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

    }

    public static final class PawnJump extends Move {
        public PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    static abstract class CastleMove extends Move {
        public CastleMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static final class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static final class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    //Invalid move
    public static final class NullMove extends Move {
        public NullMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
}
