package Chess;

import java.awt.Point;

public class Pawn extends Chessmen {

	public Pawn(int intx, int inty, boolean blnColour) {
		super(intx, inty, blnColour);
		// TODO Auto-generated constructor stub
	}

	public boolean ValidateMove(Point ptDest) {
		
		if (blnColour == (getYPos() > (int)ptDest.getY())){				//ColourTrue(White) must move to a lower y-position and vice-versa
			if (getXPos() == (int)ptDest.getX()) {							//if we're moving only vertically	
				if (Math.abs(getYPos() - (int)ptDest.getY()) == 1) {				//Only moving 1 square
					return true;
				}else {													//if moving more than 1 square
					
					int intColour;
					intColour = blnColour ? 1 : 0;
					
					if (getYPos() == ((intColour * 5) + 1) && (Math.abs(getYPos() - (int)ptDest.getY()) == 2)){	//at start position and moving 2 squares
						return true;
					}
				}
			}else {																//if not moving vertically
				if(Math.abs(getXPos() - (int)ptDest.getX()) == Math.abs(getYPos() - (int)ptDest.getY())) {		//if move is diagonal
					if (Math.abs(getYPos() - (int)ptDest.getY()) == 1) {								//if only moving 1 square)
						return true;
					}
				}
			}	
		}
		return false;
	}
}
