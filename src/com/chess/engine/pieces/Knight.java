package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import static com.chess.engine.board.Move.*;

import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, true);
    }

    public Knight(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                //Edge cases
                if (isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidate) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
                    continue;
                }

                final Tile candidateDestinationTile =
                        board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance =
                            pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
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

