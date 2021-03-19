package Chess;

import java.awt.Point;

public class Board {
	
	public Chessmen[] ChessSet = new Chessmen[32];				//16 pieces per side
	Point ptBounds = new Point();								//length of board on the Y-axis
	
	final int UP 		= 1;									//Cardinal directions, solely for code clarity
	final int LEFTUP	= 2;
	final int LEFT		= 3;
	final int LEFTDOWN	= 4;
	final int DOWN		= 5;
	final int RIGHTDOWN = 6;
	final int RIGHT 	= 7;
	final int RIGHTUP 	= 8;
	
	public Board(Point ptSize) {
		
		ptBounds.setLocation(ptSize);
		
		int intColour = 0;												//Used to cycle through colours.
		int intCounter = 0;												//Used to cycle through array positions to set up pawns
 
		for(intColour=0; intColour<=1; intColour++) {					//set up all the pieces' positions and colour
		
			ChessSet[0 + (intColour * 16)] = new Rook(0,(intColour*7),(intColour == 1));					
			ChessSet[1 + (intColour * 16)] = new Knight(1,(intColour*7),(intColour == 1));
			ChessSet[2 + (intColour * 16)] = new Bishop(2,(intColour*7),(intColour == 1));
			ChessSet[3 + (intColour * 16)] = new King(3+(intColour*1),(intColour*7),(intColour == 1));
			ChessSet[4 + (intColour * 16)] = new Queen(4-(intColour*1),(intColour*7),(intColour == 1));
			ChessSet[5 + (intColour * 16)] = new Bishop(5,(intColour*7),(intColour == 1));
			ChessSet[6 + (intColour * 16)] = new Knight(6,(intColour*7),(intColour == 1));
			ChessSet[7 + (intColour * 16)] = new Rook(7,(intColour*7),(intColour == 1));
			
			for(intCounter=0; intCounter<=7; intCounter++) {	
				ChessSet[intCounter+8 + (intColour * 16)] = new Pawn(intCounter,(intColour*5) + 1,(intColour==1));
			}
		}		
	}
	
	public int getPiece(Point ptSquare) {					//Check if a piece exists at supplied coordinates
				
		int intCounter;
		
		for(intCounter=0; intCounter<=31; intCounter++) {
			if(ChessSet[intCounter].getPos().equals(ptSquare)){		//if coordinates match
				return intCounter;		
			}
		}
		return 32;								//no piece found
	}

	public int getAttemptedPiece(Point ptSquare, int intPiece) {					//Check if a piece is attempting to move at supplied coordinates, exclude array position specified with intpiece
				
		int intCounter;
		
		for(intCounter=0; intCounter<=31; intCounter++) {
			if(intCounter != intPiece) {																														//exclude supplied piece
				if(ChessSet[intCounter].getAttPos().equals(ptSquare)){		//if coordinates match
					return intCounter;		
				}
			}
		}
		return 32;								//no piece found
	}
	
	public int FindDirection(Point ptOrig, Point ptDest) {	//determine direction of movement.
		
		if(ptOrig.getX() < ptDest.getX()) {		//if direction is to the right			
			if (ptOrig.getY() < ptDest.getY()) {	//if direction is down 		
				return RIGHTDOWN;						//RIGHT AND DOWN		
			}else if(ptOrig.getY() > ptDest.getY()){//if direction is up		
				return RIGHTUP;							//RIGHT AND UP		
			}else {									//not up or down	
				return RIGHT;							//RIGHT	
			}	
		}else if(ptOrig.getX() > ptDest.getX()) {//if direction is to the left
			if (ptOrig.getY() < ptDest.getY()) {	//if direction is down 	
				return LEFTDOWN;						//LEFT AND DOWN	
			}else if(ptOrig.getY() > ptDest.getY()){//if direction is up	
				return LEFTUP;							//LEFT AND UP	
			}else {									//not up or down	
				return LEFT;							//LEFT	
			}	
		}else if(ptOrig.getY() < ptDest.getY()) {//if direction is down	
			return DOWN;							//DOWN
		}else {
			return UP;								//UP
		}
	}
	
	public Point Step(Point ptStep, int intDirection) {					//Step along path toward a destination. Default handles handles vertical/horizontal directions 
		
		int PathX = 0;
		int PathY = 0;
		Point ptStepped = new Point();

		switch(intDirection){									
			
			case UP:								//up		
				PathY--;
				break;	
				
			case LEFTUP:							//left and up	
				PathX--;
				PathY--;
				break;
				
			case LEFT:								//left	
				PathX--;
				break;
				
			case LEFTDOWN:							//left and down	
				PathX--;
				PathY++;
				break;
	
			case DOWN:								//down	
				PathY++;
				break;
					
			case RIGHTDOWN:							//right and down
				PathX++;
				PathY++;
				break;
				
			case RIGHT:								//right
				PathX++;
				break;
				
			case RIGHTUP:							//right and up
				PathX++;
				PathY--;
				break;	
		}
		ptStepped.setLocation(ptStep.getX()+PathX, ptStep.getY()+PathY);
		return ptStepped;
}
	
	public boolean AttemptPath(int intPiece, Point ptDest) {		//Checks if there is a clear path from piece position to destination by stepping the piece along
		
		boolean blnPathClear = true;
		int intDirection;													//holds the direction of piece
		Point ptPath = new Point();
		
		ptPath.setLocation(ChessSet[intPiece].getAttPos());
		intDirection = FindDirection(ptPath, ptDest);			//determine direction and hold it in variable
		while(!(ptPath.equals(ptDest)) && blnPathClear){			//While piece's desired position is not the destination
			if(getAttemptedPiece(ptPath, intPiece) == 32){			//If there's NOT a piece at the current step
				ptPath = Step(ptPath, intDirection);					//step along path						
			}else {
				blnPathClear = false;																									//piece on path, move failed
			}
		}
		return blnPathClear;
	}
	
	public boolean CheckBounds(Point ptPos) {									//Check if coordinates are on the board
		
		return (ptBounds.getX() >= ptPos.getX() && ptPos.getX() >= 0 && ptBounds.getY() >= ptPos.getY() && ptPos.getY() >= 0);
	}

	public boolean AttemptMove(int intPiece, Point ptDest) {					//change a pieces attemptedposition on the board, if valid
		
		boolean blnValidMove = true;																//variable contains whether move is valid
		int intPieceonPath;																			//variable reference to position in array of piece that is in the way
		
		if(CheckBounds(ptDest) ){												//If destination is on board
			if (!(ChessSet[intPiece].getPos().equals(ptDest))) { 						//if destination is not the origin
				if (ChessSet[intPiece].ValidateMove(ptDest)){											//if move follows rules for piece
					if(ChessSet[intPiece].getClass().getName() != "Chess.Knight") {								//if the piece is NOT a knight	
						blnValidMove = AttemptPath(intPiece, ptDest); 										//check that path is free of pieces
					}
					ChessSet[intPiece].AttemptMove(ptDest);						//set piece's attempted position		
					if(blnValidMove) {																	//if move is valid so far
						intPieceonPath = getAttemptedPiece(ptDest, intPiece);								//check for piece at destination
						if(intPieceonPath != 32) {																//if there's a piece on the final step
							if(ChessSet[intPiece].blnColour == ChessSet[intPieceonPath].blnColour) {				//if colours are the same		
								blnValidMove = false;																	//same colour at destination, move failed
							}			
							if(ChessSet[intPiece].getClass().getName() == "Chess.Pawn") {							//if the piece is a pawn
								if(ChessSet[intPiece].getXPos() == ptDest.getX()) {								//if the move is vertical
									blnValidMove = false;															//move is invalid, as pawns can only capture on a diagonal
								}			
							}					
						}else {
							if(ChessSet[intPiece].getClass().getName() == "Chess.Pawn") {				//if the piece is a pawn
								if(ChessSet[intPiece].getXPos() != ptDest.getX()) {							//if the move is NOT vertical
									blnValidMove = false;														//move is invalid, as pawns can only move vertically when not capturing pieces
								}
							}
						}
					}
				}
				else {
					blnValidMove = false;
				}
			}else {
				blnValidMove = false;
			}
		}else {
			blnValidMove = false;
		}
	return blnValidMove;
	}
	
	public boolean[][] getMoves(int intPiece) {					//Returns an array of all possible valid moves for a given piece
		
		int intXCounter;
		int intYCounter;
		boolean[][] blnMoves = new boolean[8][8]; 
		
		if(intPiece != 32) {
			for(intXCounter = 0; intXCounter <= 7; intXCounter++) {
				for(intYCounter = 0; intYCounter <= 7; intYCounter++) {
					blnMoves[intXCounter][intYCounter] = AttemptMove(intPiece, new Point(intXCounter, intYCounter));
					ChessSet[intPiece].ResetMove();
				}
			}
		}
		return blnMoves;
	}
	
	public boolean[][] getSafeMoves(boolean Colour){		//Returns an array of all possible valid moves for a given colour
	
		boolean[][] blnMoves= new boolean[8][8];
		int intPiece;
		int intXCounter;
		int intYCounter;
		int intAttPiece;
		
		for(intPiece=0; intPiece<32; intPiece++) {							//for every piece
			if(ChessSet[intPiece].getColour() == Colour) {						//if Colour is selected colour
				for(intXCounter = 0; intXCounter <= 7; intXCounter++) {				//for every X-Coord
					for(intYCounter = 0; intYCounter <= 7; intYCounter++) {				//for every Y-Coord
						if(AttemptMove(intPiece, new Point(intXCounter, intYCounter))) {	//if the piece can move there	
							blnMoves[intXCounter][intYCounter] = true;
							for(intAttPiece=0; intAttPiece<32; intAttPiece++) {					//for every piece
								if(ChessSet[intAttPiece].getColour() != Colour) {					//if Colour is attacker's colour
									if(AttemptMove(intAttPiece, new Point(intXCounter, intYCounter))) {	//if the attacking piece can move there
										blnMoves[intXCounter][intYCounter] = false;
									}
									ChessSet[intAttPiece].ResetMove();
								}
							}
						}
						ChessSet[intPiece].ResetMove();
					}
				}
			}
		}
		return blnMoves;
	}
	
	public boolean[] getThreatenedPieces(boolean Colour){			//return an array matching the pieces array, indicating TRUE for any pieces currently threatened by opposing colour

		boolean[] blnThreatened = new boolean[16];
		int intColour = Colour ? 0 : 1;
		int intAttColour = !Colour ? 1 : 0;
		int intPiece;
		int intAttPiece;
		
		
		for(intPiece=0; intPiece<16; intPiece++) {									//for every piece
			for(intAttPiece=0; intAttPiece<16; intAttPiece++) {							//for every piece
				if(AttemptMove((intAttPiece + (intAttColour * 16)), ChessSet[intPiece + (intColour * 16)].getPos())) {	//if piece can attack
					blnThreatened[intPiece] = true;
				}
				ChessSet[intAttPiece].ResetMove();
			}
		}
		return blnThreatened;
	}
	
	public int[][] getPositions(){						//returns an array of all positions on board
		
		int intPiece;									//Counts through pieces
		int intPositions[][] = new int[32][2];			//array of positions to return
		
		for (intPiece=0; intPiece <=31; intPiece++) {
			intPositions[intPiece][0] = ChessSet[intPiece].getXPos();
			intPositions[intPiece][1] = ChessSet[intPiece].getYPos();
		}
		return intPositions;
	}
	public boolean setPositions(int intPositions[][]) {	//sets all pieces to positions in supplied array [piece][xPos, YPos]
		
		int intPiece;
		
		for (intPiece=0; intPiece <=31; intPiece++) {
			if(ChessSet[intPiece].getXPos() >= 0) {														//if piece is on the board
				ChessSet[intPiece].AttemptMove(intPositions[intPiece][0],intPositions[intPiece][1]);	//set attemptedposition
				ChessSet[intPiece].FinalizeMove();														//finalize to set position
			}
		}
		return true;
	}
	
	public int getCheck(boolean blnColour) {			//Returns array location of Piece that threatens player's King**
		
		int intCounter;
		int intColour;
		int intKing;
		int intCheck = 32;
		
		intColour = blnColour ? 1 : 0;					//convert boolean to integer
		intKing = 3 + (intColour * 16);					//set the integer to the appropriate number for the King in the Chessmen array
		
		for (intCounter = 0; intCounter <=31; intCounter++) {				//cycle through each piece
			if (ChessSet[intCounter].blnColour != blnColour) {				//if the piece is opposing colour
				if(AttemptMove(intCounter, new Point(ChessSet[intKing].getAttPos()))) {			//Try moving the piece to take the King						
					intCheck = intCounter;
				}
				ChessSet[intCounter].ResetMove();						//return attempted positions to current positions
			}
		}
		return intCheck;
	}
	
	public boolean getCheckMate(int intAttacker) {		//Determines if any move can break the check created by piece intCheck, without resulting in check
		
		int intCounter;
		int intColour;									//Colour of piece threatening King
		int intKing;									//Array position of King in check
		int intDirection;								//holds the direction of piece
		Point attPath = new Point();
		
		boolean blnCheckMate = true;
		
		intColour = !ChessSet[intAttacker].getColour() ? 1 : 0;								//convert boolean to integer
		intKing = 3 + (intColour * 16);														//set the integer to the appropriate number for the King in the Chessmen array

				//***Try moving every piece to capture the attacking piece, or move into its path***
		if(ChessSet[intAttacker].getClass().getName() != "Chess.Knight") {	//if piece is NOT a knight
			intDirection = FindDirection(ChessSet[intAttacker].getPos(), ChessSet[intKing].getPos());	//determine direction of Attacker to King and hold it in variable
			attPath = ChessSet[intAttacker].getAttPos();
			while(!(attPath.equals(ChessSet[intKing].getAttPos()))) { //for each position along the path from attacker to King			
				for (intCounter = 0; intCounter <=31; intCounter++) {														//cycle through each piece
					if ((ChessSet[intCounter].getColour() != ChessSet[intAttacker].getColour()) && (intCounter != intKing)) {		//if the piece is opposing colour AND not the King
						if(AttemptMove(intCounter, attPath)) {		//Try moving the piece to the threatening piece, or in its path						
							if(getCheck(ChessSet[intKing].getColour()) == 32) {														//if King is no longer in check				
								blnCheckMate = false;																					//CheckMate isn't true
							}
						}
						ChessSet[intCounter].ResetMove();									//return attempted positions to current positions	
					}
				}
				attPath = Step(attPath, intDirection);								//move to next position on path from attacker to King
			}
		}									
				//***Try moving every piece to the attacker's position - capture the attacker***//
		for (intCounter = 0; intCounter <=31; intCounter++) {														//cycle through each piece
			if ((ChessSet[intCounter].getColour() != ChessSet[intAttacker].getColour()) && (intCounter != intKing)) {		//if the piece is opposing colour AND not the King
				if(AttemptMove(intCounter, ChessSet[intAttacker].getAttPos())) {		//Try moving the piece to the threatening piece,					
					blnCheckMate = false;													//CheckMate isn't true
				}
				ChessSet[intCounter].ResetMove();									//return attempted positions to current positions	
			}
		}
		
				//***Try moving the King in every direction***
		for(intDirection = 1; intDirection <= 8; intDirection++) {			//for each direction
			attPath = Step(ChessSet[intKing].getAttPos(), intDirection);
			if(AttemptMove(intKing, attPath)){									//attempt to move King one step		
				if(getCheck(ChessSet[intKing].getColour()) == 32) {				//if King is no longer in check
					blnCheckMate = false;											//CheckMate isn't true
				}
			}
			ChessSet[intKing].ResetMove();									//reset King to original position
		}
		return blnCheckMate;
	}
	
	public boolean Move (int intPiece, int Destx, int Desty) {								//Changes coordinates of pieces and captures pieces, if move is valid
		
		Point ptDest = new Point (Destx, Desty);
		
		if(CheckBounds(ptDest) ){														//If destination is on board
			if (AttemptMove(intPiece, ptDest)) {									//if the move is successful
				if (getCheck(ChessSet[intPiece].blnColour) == 32 || getCheck(ChessSet[intPiece].blnColour) == getPiece(ptDest)) { //if piece is not in check, or is in check from a piece at the destination
					if (getPiece(ptDest) != 32){
						ChessSet[getPiece(ptDest)].CapturePiece();			//kill piece at destination
					}
					ChessSet[intPiece].FinalizeMove();								//Set piece coordinates to attempted position
					
					if(ChessSet[intPiece].getClass().getName() == "Chess.Pawn") {
						if(ChessSet[intPiece].getYPos() == 7 || ChessSet[intPiece].getYPos() == 0) {	//if Pawn is at either end of board
							Promote(intPiece);
						}	
					}
					return true;
				}
			}
		}
		ChessSet[intPiece].ResetMove();									//return attempted positions to current positions
		return false;
	}
	
	public boolean Castle(int intPiece, Point ptDest) {			//Perform a castle move, determine Rook based on King movement
		
		int intColour = ChessSet[intPiece].getColour() ? 1 : 0;
		
		if((ChessSet[intPiece].getXPos() == 3+(intColour*1)) || (ChessSet[intPiece].getYPos() == intColour*7)) {	//if King is in original position
			if(AttemptPath(intPiece, ptDest)){					//if Path is clear
				ChessSet[intPiece].FinalizeMove();
			}
		}
		return true;
	}
	
	public boolean Promote(int intPiece) {						//replace a piece in the array with a queen
			
		boolean Colour;
		Point ptPos = new Point(ChessSet[intPiece].getPos());	//save values of original piece

		Colour = ChessSet[intPiece].getColour();
		
		ChessSet[intPiece] = new Queen((int)ptPos.getX(), (int)ptPos.getY(), Colour);		//Create new piece
		
		return true;
	}

	public String getPieceType(int intPiece) {							//return the name of a piece in the array
		return ChessSet[intPiece].getClass().getName();
	}
	
	public boolean getColour(int intPiece) {							//return the colour of a piece in the array
		return ChessSet[intPiece].getColour();
	}
}
					





