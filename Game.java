package Chess;

import java.awt.Point;

public class Game {

	Board ChessBoard;				//Board object, containing array of pieces and rules for their movement
	boolean blnTurn;				//which side's turn it currently is
	boolean blnCheckMate;			//if player is checkmated
	int intCheck;					//if player is in check, and by what piece
	
	int intMoveCounter = 0;			//total number of moves
	int[][][] SnapShots;			//Array of all piece positions at each move - [MoveNumber] [Piece] [X-Coord, y-Coord]
	
	final int KINGWHITE = 	0;						//References to the array of icons
	final int QUEENWHITE = 	1;
	final int BISHOPWHITE = 2;
	final int KNIGHTWHITE = 3;
	final int ROOKWHITE = 	4;
	final int PAWNWHITE = 	5;
	final int KINGBLACK = 	6;
	final int QUEENBLACK = 	7;
	final int BISHOPBLACK = 8;
	final int KNIGHTBLACK = 9;
	final int ROOKBLACK = 	10;
	final int PAWNBLACK = 	11;
	
	public Game() {
					
		SnapShots = new int[40][32][2];		
		ChessBoard = new Board(new Point(7,7));
		PlayGame();										
	}
	
	public boolean Turn(int intPiece, int intX, int intY)
	{
		if(isPieceTurn(intPiece)) {									//if it's the piece's turn
			if(ChessBoard.Move(intPiece, intX, intY)) {					//Move the piece on the board
				intMoveCounter++;										//increment move counter
				SavePositions();										//save the piece positions to the array
				blnTurn = !blnTurn;										//next player's turn
				if((intCheck = ChessBoard.getCheck(blnTurn)) != 32){	//Evaluate if next player is in check
					blnCheckMate = ChessBoard.getCheckMate(intCheck);	//Evaluate if CheckMate applies
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean PlayGame() {								
		
		intCheck = 32;
		blnCheckMate = false;
		intMoveCounter = 0;
		SavePositions();
		blnTurn = true;
		return true;
	}
	
	public Boolean SavePositions() {						//Save positions to snapshot array
		
		SnapShots[intMoveCounter] = ChessBoard.getPositions();
		return true;
	}
	
	public boolean MoveisPlayed(int MoveNum) {				//returns true if supplied move number is recorded in the SnapShots array
		
		int intCounter;
		boolean blnPlayed = false;
		
		for(intCounter=0; intCounter<32; intCounter++) {	//cycle through each piece
			if(SnapShots[MoveNum][intCounter][0] > 0) {			//if the piece is on the board
				blnPlayed = true;
			}
		}
		
		return blnPlayed;
	}
	
	public boolean LoadMove(int MoveNum) {			//Load positions from specified move in SnapShot arrray, also adjust whose turn it is
		intMoveCounter = MoveNum;
		ChessBoard.setPositions(SnapShots[intMoveCounter]);
		blnTurn = (intMoveCounter % 2 == 0);
		return true;
	}
	
	public int getMoveCounter() {						//return number of moves
		return intMoveCounter;
	}
	
	public void setMoveCounter(int MoveNumber) {		//set number of moves, in order to load snapshot
		intMoveCounter = MoveNumber;
	}
	
	public boolean getTurn() {	
		return blnTurn;
	}
	
	public boolean isPieceTurn(int intPiece) {				//Returns true if it's the given piece's turn
		if(ChessBoard.getColour(intPiece) == blnTurn) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getCheck() {
		return intCheck;
	}
	
	public boolean getCheckMate() {
		return blnCheckMate;
	}
	
	public int getPiece(Point Pos) {				//search the current snapshot array for the supplied coordinates and return the piece number
		
		int intPiece = 32;
		int intCounter;
		
		for(intCounter=0; intCounter<32; intCounter++) {	//for each piece
			if((SnapShots[intMoveCounter][intCounter][0] == Pos.getX()) && (SnapShots[intMoveCounter][intCounter][1] == Pos.getY())) {
				intPiece = intCounter;
			}
		}
		return intPiece;
	}
	
	/*public boolean[][] getThreatenedPieces(boolean Colour)	{
		
		boolean[][] ThreatMap = new boolean[8][8];
		boolean[] ThreatList = new boolean[16];
		int intCounter;
		int XCoord;
		int YCoord;
		
		ThreatList = ChessBoard.getThreatenedPieces(Colour);
		for(intCounter=0; intCounter<=16; intCounter++) {
			XCoord = ChessBoard.get
		}
		
		return ThreatMap;
	}*/
	
	public int getPieceDescr(int intPiece) {			//pass the Piece description, for the icon
	
		int intPieceDescr;
		
		switch (ChessBoard.getPieceType(intPiece)) {
		
		case "Chess.King":
			
			intPieceDescr = KINGWHITE;
			break;
			
		case "Chess.Queen":
			
			intPieceDescr = QUEENWHITE;
			break;
			
		case "Chess.Bishop":
			
			intPieceDescr = BISHOPWHITE;
			break;
		
		case "Chess.Knight":
			
			intPieceDescr = KNIGHTWHITE;
			break;
			
		case "Chess.Rook":
			
			intPieceDescr = ROOKWHITE;
			break;
			
		case "Chess.Pawn":
			
			intPieceDescr = PAWNWHITE;
			break;
			
		default:
			
			intPieceDescr = 6;
			break;
		}
		intPieceDescr = intPieceDescr + 6*(ChessBoard.getColour(intPiece) ? 1 : 0);
		return intPieceDescr;
	}
	
}
