package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

public class Board {

    private final List<Tile> gameBoard;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
    }

    public Tile getTile(final int tileCoordinate) {
        return null;
    }

    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        //Black
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));

        //White
        builder.setPiece(new Rook(Alliance.BLACK, 63));
        builder.setPiece(new Knight(Alliance.BLACK, 62));
        builder.setPiece(new Bishop(Alliance.BLACK, 61));
        builder.setPiece(new Queen(Alliance.BLACK, 59));
        builder.setPiece(new King(Alliance.BLACK, 60));
        builder.setPiece(new Bishop(Alliance.BLACK, 58));
        builder.setPiece(new Knight(Alliance.BLACK, 57));
        builder.setPiece(new Rook(Alliance.BLACK, 56));
        builder.setPiece(new Pawn(Alliance.BLACK, 55));
        builder.setPiece(new Pawn(Alliance.BLACK, 54));
        builder.setPiece(new Pawn(Alliance.BLACK, 53));
        builder.setPiece(new Pawn(Alliance.BLACK, 52));
        builder.setPiece(new Pawn(Alliance.BLACK, 51));
        builder.setPiece(new Pawn(Alliance.BLACK, 50));
        builder.setPiece(new Pawn(Alliance.BLACK, 49));
        builder.setPiece(new Pawn(Alliance.BLACK, 48));

        //White goes first
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder() {

        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
