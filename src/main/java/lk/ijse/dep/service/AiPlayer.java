package lk.ijse.dep.service;

public class AiPlayer extends Player{

    public AiPlayer(Board board) {
        super(board);
    }

    public void movePiece(int col) {
        int maxEval =(int) Double.NEGATIVE_INFINITY;
        int row=0;
        for (int newcol =0; newcol <Board.NUM_OF_COLS; newcol++){
            if (board.isLegalMove(newcol)){
                int temRow = board.findNextAvailableSpot(newcol);
                board.updateMove(newcol,temRow,Piece.GREEN);
                int heuristicValue = minimax(0,false);
                board.updateMove(newcol,temRow,Piece.EMPTY);
                if (maxEval < heuristicValue){
                    maxEval = heuristicValue;
                    col = newcol;
                    row = temRow;
                }
            }
        }
        board.updateMove(col,row,Piece.GREEN);
        board.getBoardUi().update(col,false);
        if (board.findWinner().getWinningPiece() != Piece.EMPTY){
            board.getBoardUi().notifyWinner(board.findWinner());
        } else if (!board.existLegalMoves()) {
            board.getBoardUi().notifyWinner(new Winner(Piece.EMPTY));
        }

    }

   public int minimax(int depth, boolean maximizingPlayer){
       Winner winner = board.findWinner();
       if (depth == 4 || winner.getWinningPiece() != Piece.EMPTY){
           if (winner.getWinningPiece() == Piece.EMPTY){
               return 0;
           }
           if (winner.getWinningPiece() == Piece.GREEN){
               return 1;
           }
           if (winner.getWinningPiece() == Piece.BLUE){
               return -1;
           }
       }
       if (maximizingPlayer){
           int maxEval = (int) Double.NEGATIVE_INFINITY;
           for (int col =0; col<board.NUM_OF_COLS; col++){
               if (board.isLegalMove(col)){
                   int row = board.findNextAvailableSpot(col);
                   board.updateMove(col,row,Piece.GREEN);
                   int heuristicVal = minimax(depth +1,false);
                   board.updateMove(col,row,Piece.EMPTY);
                   maxEval = Math.max(heuristicVal,maxEval);
               }
           }
           return maxEval;
       }else {
           int minEval = (int) Double.POSITIVE_INFINITY;
           for (int col = 0; col<board.NUM_OF_COLS; col++) {
               if (board.isLegalMove(col)) {
                   int row = board.findNextAvailableSpot(col);
                   board.updateMove(col, row, Piece.BLUE);
                   int heuristicVal = minimax(depth + 1, true);
                   board.updateMove(col, row, Piece.EMPTY);
                   minEval = Math.min(heuristicVal, minEval);
               }
           }
           return minEval;
       }
   }
}
