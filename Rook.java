package Chess;

import java.awt.Point;

public class Rook extends Chessmen {

	public Rook(int intx, int inty, boolean blnColour) {
		super(intx, inty, blnColour);
		// TODO Auto-generated constructor stub
	}
	
	public boolean ValidateMove(Point ptDest) {
		
		return (getXPos() == (int)ptDest.getX()) || (getYPos() == (int)ptDest.getY());		//if either axis is the same value
	}
}
