package lk.ijse.dep.service;

public class HumanPlayer extends Player{
    public HumanPlayer(Board board) {
        super(board);
    }
    @Override
    public void movePiece(int col){
        if (board.isLegalMove(col)){
            board.updateMove(col,Piece.BLUE);
            board.getBoardUi().update(col,true);
            if (board.findWinner().getWinningPiece() != Piece.EMPTY){
                board.getBoardUi().notifyWinner(board.findWinner());
            } else if (!board.existLegalMoves()) {
                board.getBoardUi().notifyWinner(new Winner(Piece.EMPTY));
            }
        }
    }
}
