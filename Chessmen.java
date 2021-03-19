package Chess;

import java.awt.Point;

public class Chessmen {

	private Point ptPos = new Point();
	private Point ptAttPos = new Point();
	public boolean blnColour;
	
	public boolean ValidateMove(Point ptDest) {
		
		return true;
	}
	
	public boolean AttemptMove(int intx, int inty) {
		
		ptAttPos.setLocation(intx, inty);
		return true;
	}
	
	public boolean AttemptMove(Point pntDest) {	
		ptAttPos.setLocation(pntDest);
		return true;
	}
	
	public boolean FinalizeMove() {						//Set position to Attempted position, (move has been successful)
		ptPos.setLocation(ptAttPos);
		return true;
	}
	
	public boolean ResetMove() {						//Reset the attempted position to the current position (move is not successful)
		
		ptAttPos.setLocation(ptPos);
		return true;
	}
	
	public Point getPos() {
		return ptPos;
	}
	
	public int getXPos() {
		return (int)ptPos.getX();
	}

	public int getYPos() {
		return (int)ptPos.getY();
	}
	
	public Point getAttPos() {
		return ptAttPos;
	}
	
	public int getAttemptXPos() {
		return (int)ptAttPos.getX();
	}
	
	public int getAttemptYPos() {
		return (int)ptAttPos.getY();
	}
	
	public boolean getColour() {
		return blnColour;
	}
	
	public boolean CapturePiece() {
		
		ptPos.move(-1,-1);
		ResetMove();
		return true;
	}
	
	public Chessmen(int intx, int inty, boolean blnColor) {
		
		ptPos.setLocation(intx, inty);
		blnColour = blnColor;
		
		ResetMove();
	}

}
