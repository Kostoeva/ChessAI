package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        int candidateDestinationCoordinate;
        List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidate) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
                    continue;
                }

                final Tile candidateDestinationTile =
                        board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move());
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance =
                            pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move());
                    }
                }
            }
            return ImmutableList.copyOf(legalMoves);
        }
        return null;
    }

    private static boolean isFirstColumnExclusion(final int currentPos, final int candidatePos) {
        return BoardUtils.FIRST_COLUMN[currentPos] &&
                ((candidatePos == -17) ||
                        (candidatePos == -10) ||
                        (candidatePos == 6) ||
                        (candidatePos == 15));
    }

    private static boolean isSecondColumnExclusion(final int currentPos, final int candidatePos) {
        return BoardUtils.SECOND_COLUMN[currentPos] && ((candidatePos == -10) || (candidatePos == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPos, final int candidatePos) {
        return BoardUtils.SEVENTH_COLUMN[currentPos] && ((candidatePos == 10) || (candidatePos == -6));
    }

    private static boolean isEighthColumnExclusion(final int currentPos, final int candidatePos) {
        return BoardUtils.EIGHTH_COLUMN[currentPos] &&
                ((candidatePos == 17) ||
                        (candidatePos == 10) ||
                        (candidatePos == -6) ||
                        (candidatePos == -15));
    }
}

