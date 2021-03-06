package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

//Google lib
import com.google.common.collect.ImmutableMap;

//Java basics
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    //final => only set once during construction
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i ++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }

    private Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    /**
     * @return Piece if there, else null
     */
    public abstract Piece getPiece();

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    /**
     * Empty Tile class
     */
    public static final class EmptyTile extends Tile{

        private EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return "-";
        }
        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /**
     * Occupied Tile class
     */
    public static final class OccupiedTile extends Tile {
        //private = cannot reference w/o calling getPiece()
        private final Piece pieceOnTile;

        private OccupiedTile(int coordinate, Piece pieceOnTile) {
            super(coordinate);
            this.pieceOnTile = pieceOnTile;
        }

        //black pieces are lower numberwise
        @Override
        public String toString() {
            return this.pieceOnTile.getPieceAlliance().isWhite() ?
                    this.pieceOnTile.toString() :
                    this.pieceOnTile.toString().toLowerCase();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
