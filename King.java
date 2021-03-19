package Chess;

import java.awt.Point;

public class King extends Chessmen {

	public King(int intx, int inty, boolean blnColour) {
		super(intx, inty, blnColour);
		// TODO Auto-generated constructor stub
	}

	public boolean ValidateMove(Point ptDest) {	
		return ((Math.abs(getXPos() - (int)ptDest.getX()) <= 1) && (Math.abs(getYPos() - (int)ptDest.getY()) <= 1));		//if the length of distance on either axis is one or less
	}	
}
