package Chess;

import java.awt.Point;

public class Knight extends Chessmen {

	public Knight(int intx, int inty, boolean blnColour) {
		super(intx, inty, blnColour);
		// TODO Auto-generated constructor stub
	}
	
	public boolean ValidateMove(Point ptDest) {
		return ((Math.abs(getXPos() - (int)ptDest.getX()) == 2) && (Math.abs(getYPos() - (int)ptDest.getY()) == 1) || (Math.abs(getXPos() - (int)ptDest.getX()) == 1) && (Math.abs(getYPos() - (int)ptDest.getY()) == 2));		//if the length of x distance equals length of y distance
	}
	
}
