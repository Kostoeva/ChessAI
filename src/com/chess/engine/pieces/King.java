package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.KING, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int destOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            final int destCoordinate = this.piecePosition + destOffset;

            //edge cases
            if (isFirstColumnExclusion(this.piecePosition, destCoordinate) || isEighthColumnExclusion(this.piecePosition, destCoordinate)) {
                continue;
            }

            if (BoardUtils.isValidTileCoordinate(destCoordinate)) {
                final Tile candidateDestinationTile =
                        board.getTile(destCoordinate);

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, destCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance =
                            pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, destCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPos, final int candidatePos) {
        return BoardUtils.FIRST_COLUMN[currentPos] &&
                ((candidatePos == -9) ||
                        (candidatePos == -1) ||
                        (candidatePos == 7));
    }

    private static boolean isEighthColumnExclusion(final int currentPos, final int candidatePos) {
        return BoardUtils.EIGHTH_COLUMN[currentPos] && ((candidatePos == -7) ||
                (candidatePos == 1) || (candidatePos == 9));
    }
}
