package Chess;

import java.awt.Point;

public class Bishop extends Chessmen {

	public Bishop(int intx, int inty, boolean blnColour) {
		super(intx, inty, blnColour);
		
	}
	
	public boolean ValidateMove(Point ptDest) {
		
		return (Math.abs(getXPos() - (int)ptDest.getX()) == Math.abs(getYPos() - (int)ptDest.getY()));		//if the length of x distance equals length of y distance
	}
}
