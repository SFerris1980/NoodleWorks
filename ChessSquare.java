package Chess;

import javax.swing.*;


@SuppressWarnings("serial")
public class ChessSquare extends JButton {

	private final int XPos;
	private final int YPos;

	
	public ChessSquare(String str, int col, int row) {
		
		super(str);
		this.XPos = col;
		this.YPos = row;
	}
	
	public int getXPos() {
		return XPos;
	}
	
	public int getYPos() {
		return YPos;
	}
}
