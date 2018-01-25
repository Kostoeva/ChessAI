package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] CANDIDATE_COORDINATE = {8};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, true);
    }

    public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int destPos : CANDIDATE_COORDINATE) {
            int destCoordinate = this.piecePosition  + (this.getPieceAlliance().getDirection() * destPos);

                if(!BoardUtils.isValidTileCoordinate(destCoordinate)) {
                    continue;
                }

                if(destPos == 8 && board.getTile(destCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.PawnMove(board, this, destCoordinate));
                } else if (destPos == 16 && this.isFirstMove() &&
                        ((BoardUtils.SEVENTH_RANK[this.piecePosition] &&
                                this.getPieceAlliance().isBlack())
                        || (BoardUtils.SECOND_RANK[this.piecePosition] &&
                                this.getPieceAlliance().isWhite()))) {
                    final int behindDestCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                    if (!board.getTile(behindDestCoordinate).isTileOccupied() && (!board.getTile(destCoordinate).isTileOccupied())) {
                        legalMoves.add(new Move.PawnJump(board, this, destCoordinate));
                    }
                    //edge cases
                } else if(destPos == 7 &&
                        !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite()) ||
                        (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack() ))) {
                    if(board.getTile(destCoordinate).isTileOccupied()) {
                        final Piece pieceOnDest = board.getTile(destCoordinate).getPiece();
                        if(this.pieceAlliance != pieceOnDest.getPieceAlliance()) {
                            legalMoves.add(new Move.PawnAttackMove(board, this, destCoordinate, pieceOnDest));
                        }
                    } else if(board.getEnPassantPawn() != null) {
                        if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
                            final Piece pieceOnCandidate = board.getEnPassantPawn();
                            if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, destCoordinate, pieceOnCandidate));
                            }
                        }
                    }
                    //edge cases
                } else if(destPos == 9 &&
                        !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite()) ||
                                (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack() ))) {
                            if(board.getTile(destCoordinate).isTileOccupied()) {
                                final Piece pieceOnDest = board.getTile(destCoordinate).getPiece();
                                if(this.pieceAlliance != pieceOnDest.getPieceAlliance()) {
                                    legalMoves.add(new Move.PawnAttackMove(board, this, destCoordinate, pieceOnDest));
                                }
                            } else if(board.getEnPassantPawn() != null) {
                                if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
                                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                                        legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, destCoordinate, pieceOnCandidate));
                                    }
                                }
                            }
                }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }
}
