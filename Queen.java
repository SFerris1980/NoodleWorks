package Chess;

import java.awt.Point;

public class Queen extends Chessmen {

	public Queen(int intx, int inty, boolean blnColour) {
		super(intx, inty, blnColour);
		// TODO Auto-generated constructor stub
	}
		
	public boolean ValidateMove(Point ptDest) {
		
		return ((Math.abs(getXPos() - (int)ptDest.getX()) == Math.abs(getYPos() - (int)ptDest.getY())) || (getXPos() == (int)ptDest.getX() || getYPos() == (int)ptDest.getY()));		//if the length of x distance equals length of y distance (diagonal), or the y or x axis is the same
	}
}
