package Chess;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.image.*;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class FraGame extends JFrame implements ActionListener{
	
	ChessSquare[][] btnSquares = 	new ChessSquare[8][8];				//Buttons that make up the board. JButton with x and y integer variables and methods for returning same
	JButton btnPlay = 				new JButton("Restart Game");		//Start button
	JButton btnShow = 				new JButton("Valid Piece Moves");	//ShowMoves
	JButton btnSafe = 				new JButton("All Safe Moves");	//ShowMoves
	JButton btnFlip = 				new JButton("Undo");				//Toggle between flipping moves and playing game
	JButton btnFlipF =				new JButton("Fwd >>");			//Cycle ahead on moveslist
	JButton btnFlipB = 				new JButton("<< Back");			//Cycle backward on moveslist
	
	JLabel lblTurn = 				new JLabel();						//Displays the Colour whose turn it is
	JLabel lblCheck =				new JLabel();						//Displays the Colour who is in Check
	
	JPanel pnlBoard =				new JPanel();						//Contains the board buttons
	JPanel pnlMenu = 				new JPanel();						//Contains the game controls and move display
	JPanel pnlAdvanced =			new JPanel();						//Contains advanced features
	
	File fimgPieces = 				new File("Chessmensprite.png");
	BufferedImage imgPieces =		new BufferedImage(300,300,2);
	ImageIcon[] icnPiece = 			new ImageIcon[12];					//icons for all the pieces
	
	Game ChessGame;									//Game object that contains Board and moves history
	
	int intSrcPiece;								//Array position of piece to be moved
	
	int intXDest;									//X-coordinate for piece move
	int intYDest;									//Y-coordinate for piece move
	boolean blnSrcChosen;							//whether source of move is chosen, so that the next button press will be destination
	
	int intFlipCounter;								//move number when flipping through moves								
	
	Color clrValid =	new Color(175,250,255);		//Background colours for labels
	Color clrInvalid =	new Color(255,175,175);
	Color clrBlack =	new Color(0,0,0);
	Color clrWhite = 	new Color(255,255,255);
	Color DkGray =		new Color(160,160,160);
	Color LtGray = 		new Color(255,255,153);
	Color clrPath =		new Color(180,100,255);
	
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
	
	public FraGame() {
		
		int intCol;									//Counter used for cycling through X-Axis
		int intRow;									//Counter used for cycling through Y-Axis
		
		setSize(475,660);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//**** Lay out the Chess Board ****
		
		GridLayout glBoard = new GridLayout(8,8);
		pnlBoard.setLayout(glBoard);
		pnlBoard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlBoard.setPreferredSize(new Dimension(450,450));
		
		for (intRow = 0; intRow < 8; intRow++) {					//for each row
			for (intCol = 0; intCol < 8; intCol++) {				//place a row of buttons		
				btnSquares[intCol][intRow] = new ChessSquare("", intCol, intRow);
				pnlBoard.add(btnSquares[intCol][intRow]);
				btnSquares[intCol][intRow].addActionListener(this);
			}
		}
		
		//****Set Up sprites for the pieces. Buffered Image and ImageIcons
		
		try {														//set up the image of the piece sprites
			imgPieces = ImageIO.read(fimgPieces);
		}
		catch (IOException e) { }
		
		icnPiece[KINGWHITE] =	new ImageIcon(imgPieces.getSubimage(0, 45, 45, 45));
		icnPiece[QUEENWHITE] = 	new ImageIcon(imgPieces.getSubimage(45, 45, 45, 45));
		icnPiece[BISHOPWHITE] = new ImageIcon(imgPieces.getSubimage(90, 45, 45, 45));		
		icnPiece[KNIGHTWHITE] = new ImageIcon(imgPieces.getSubimage(135, 45, 45, 45));
		icnPiece[ROOKWHITE] = 	new ImageIcon(imgPieces.getSubimage(180, 45, 45, 45));
		icnPiece[PAWNWHITE] = 	new ImageIcon(imgPieces.getSubimage(225, 45, 45, 45));		
		icnPiece[KINGBLACK] = 	new ImageIcon(imgPieces.getSubimage(0, 0, 45, 45));
		icnPiece[QUEENBLACK] = 	new ImageIcon(imgPieces.getSubimage(45, 0, 45, 45));
		icnPiece[BISHOPBLACK] = new ImageIcon(imgPieces.getSubimage(90, 0, 45, 45));		
		icnPiece[KNIGHTBLACK] = new ImageIcon(imgPieces.getSubimage(135, 0, 45, 45));
		icnPiece[ROOKBLACK] = 	new ImageIcon(imgPieces.getSubimage(180, 0, 45, 45));
		icnPiece[PAWNBLACK] = 	new ImageIcon(imgPieces.getSubimage(225, 0, 45, 45));
		
		add(pnlBoard);
		
		//****Lay out the Menu Buttons****
		btnPlay.addActionListener(new ActionListener(){				//Play Button

	         @Override
	         public void actionPerformed(ActionEvent arg0) {
	            PlayGame();
	         }
		});
		btnPlay.setPreferredSize(new Dimension(120,45));
		
		lblTurn.setPreferredSize(new Dimension(80,45));
		lblTurn.setBorder(BorderFactory.createRaisedBevelBorder());
		lblTurn.setHorizontalAlignment(JLabel.CENTER);
		lblTurn.setOpaque(true);
		
		lblCheck.setPreferredSize(new Dimension(45,45));
		lblCheck.setBorder(BorderFactory.createRaisedBevelBorder());
		lblCheck.setOpaque(true);
		
		pnlMenu.add(btnPlay);
		pnlMenu.add(lblTurn);
		pnlMenu.add(lblCheck);
		pnlMenu.setPreferredSize(new Dimension(getWidth(),50));
		add(pnlMenu);
		
		//****Lay out the Advanced panel****
		btnShow.addActionListener(new ActionListener(){				//Show Moves Button

	         @Override
	         public void actionPerformed(ActionEvent arg0) {
	            ShowMoves();
	         }
		});
		btnShow.setPreferredSize(new Dimension(210,45));
		
		btnSafe.addActionListener(new ActionListener(){				//Show Moves Button

	         @Override
	         public void actionPerformed(ActionEvent arg0) {
	            ShowSafeMoves();
	         }
		});
		btnSafe.setPreferredSize(new Dimension(210,45));
		
		btnFlipB.addActionListener(new ActionListener() {			//Cycle backward through movesets
			
	         @Override
	         public void actionPerformed(ActionEvent arg0) {
	            Flip(false);
	         }
		});
		btnFlipB.setPreferredSize(new Dimension(85,45));
		btnFlipB.setEnabled(false);
		
		btnFlipF.addActionListener(new ActionListener() {			//Cycle forward through movesets
			
	         @Override
	         public void actionPerformed(ActionEvent arg0) {
	            Flip(true);
	         }
		});
		btnFlipF.setPreferredSize(new Dimension(85,45));
		btnFlipF.setEnabled(false);
		
		btnFlip.addActionListener(new ActionListener() {			//Pause/unPause game
			
	         @Override
	         public void actionPerformed(ActionEvent arg0) {
	            Flip();
	         }
		});
		btnFlip.setPreferredSize(new Dimension(85,45));
		
		pnlAdvanced.add(btnFlipB);
		pnlAdvanced.add(btnFlipF);
		pnlAdvanced.add(btnFlip);
		pnlAdvanced.add(btnShow);
		pnlAdvanced.add(btnSafe);
		pnlAdvanced.setBackground(DkGray);
		pnlAdvanced.setPreferredSize(new Dimension(getWidth(),100));
		add(pnlAdvanced);
		
		setTitle("Save the King!");
		setVisible(true);
		
		PlayGame();
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		if(!blnSrcChosen) {						//if source hasn't been chosen, we are setting the source
			
			intSrcPiece = ChessGame.getPiece(new Point(((ChessSquare) evt.getSource()).getXPos(), ((ChessSquare) evt.getSource()).getYPos())); 	//Get the piece that is to be moved
			
			if(intSrcPiece != 32) {												//if the square contains a piece	
				if(ChessGame.isPieceTurn(intSrcPiece)) {						//if right colour selected
					blnSrcChosen = true;										//Source has been chosen
					((ChessSquare) evt.getSource()).setBackground(clrValid);
				}
			}
		}else {																	//we're setting the destination
			intXDest = ((ChessSquare) evt.getSource()).getXPos();
			intYDest = ((ChessSquare) evt.getSource()).getYPos();
			
			if(ChessGame.getPiece(new Point(intXDest, intYDest)) != intSrcPiece) {	//if we DIDN'T click on the piece we selected as the source				
				if(ChessGame.Turn(intSrcPiece, intXDest, intYDest)) {				//Attempt the turn
					blnSrcChosen = false;											//Must choose source again
					intSrcPiece = 32;
					RedrawBoard(ChessGame.getMoveCounter());
				}		
			}else {																	//if clicked on the same piece we selected as the source
				blnSrcChosen = false;												//Must choose source again
				intSrcPiece = 32;
				RedrawBoard(ChessGame.getMoveCounter());
			}
		}
	}

	public boolean RedrawBoard(int MoveNumber) {								//update all squares with positions of all pieces in array, as well as update labels in the Menu Panel
		
		int intCol;
		int intRow;
		int intPiece;
		int intIcon;
		int intCheck;
		boolean blnValidRedraw = true;
		
		if(ChessGame != null) {												//if the ChessBoard has been initialized (Game Started)	
				//*****Check is Snapshot array is null****
			for(intCol = 0; intCol < 8; intCol++) {							//for each column
				for(intRow = 0; intRow < 8; intRow++) {						//for each row
						btnSquares[intCol][intRow].setIcon(null);			//Remove icons
					if((intRow + intCol) % 2 == 0) {						//COLOUR THE SQUARES 
						btnSquares[intCol][intRow].setBackground(LtGray);	//if number is even, colour light
					}else {													
						btnSquares[intCol][intRow].setBackground(DkGray);	//if number is odd, colour dark
					}
				}
			}
			
			for(intPiece = 0; intPiece<=31; intPiece++) {				//cycle through all pieces and place on board
				if(ChessGame.SnapShots[MoveNumber][intPiece][0] >=0) {	//if the piece is on the board
					intIcon = ChessGame.getPieceDescr(intPiece);
					btnSquares[ChessGame.SnapShots[MoveNumber][intPiece][0]][ChessGame.SnapShots[MoveNumber][intPiece][1]].setIcon(icnPiece[intIcon]);
				}
				if(intPiece == intSrcPiece) {							//if the piece is the selected piece
					btnSquares[ChessGame.SnapShots[MoveNumber][intPiece][0]][ChessGame.SnapShots[MoveNumber][intPiece][1]].setBackground(clrValid);
				}
			}

			lblTurn.setText(String.valueOf(ChessGame.getMoveCounter()));	//Display current move number on lblTurn
			if(ChessGame.getTurn()) {										//Set lblTurn to the colour of current turn
				lblTurn.setBackground(clrWhite);
			}else {
				lblTurn.setBackground(clrBlack);
			}
			if((intCheck = ChessGame.getCheck()) != 32) {					//If in check
				lblCheck.setBackground(clrInvalid);
				lblCheck.setIcon(icnPiece[ChessGame.getPieceDescr(intCheck)]);
				if(ChessGame.getCheckMate()) {								//if CheckMate
					lblTurn.setText("CHECKMATE!");
				}
			}else {
				lblCheck.setBackground(getBackground());
				lblCheck.setIcon(null);
			}
		}
	return blnValidRedraw;
	}
	
	public void PlayGame() {									//"Starts" the game, initiating the gameboard
		
		ChessGame = new Game();
		intSrcPiece = 32;
		RedrawBoard(ChessGame.getMoveCounter());
	}
	
	public void ShowMoves() {
		
		int intYCounter;
		int intXCounter;
		boolean blnMoves[][] = new boolean[8][8];
		
		blnMoves = ChessGame.ChessBoard.getMoves(intSrcPiece);
		RedrawBoard(ChessGame.getMoveCounter());
		for (intXCounter=0; intXCounter<=7; intXCounter++) {
			for (intYCounter=0; intYCounter<=7; intYCounter++) {
				if(blnMoves[intXCounter][intYCounter]) {
					btnSquares[intXCounter][intYCounter].setBackground(clrValid);
				}
			}
		}
		
	}
	
	public void ShowSafeMoves() {									//Shows all possible moves for selected piece
		
		int intYCounter;
		int intXCounter;
		boolean blnMoves[][] = new boolean[8][8];
		
		blnMoves = ChessGame.ChessBoard.getSafeMoves(ChessGame.getTurn());	
		RedrawBoard(ChessGame.getMoveCounter());
		for (intXCounter=0; intXCounter<=7; intXCounter++) {
			for (intYCounter=0; intYCounter<=7; intYCounter++) {
				if(blnMoves[intXCounter][intYCounter]) {
					btnSquares[intXCounter][intYCounter].setBackground(clrValid);
				}
			}
		}
	}
	
	public void Flip() {						//disables/enables board, while enabling.disabling flipping buttons
	
		int XCounter;
		int YCounter;
		
		if(btnFlipB.isEnabled()) {						//if flip buttons were enabled
			ChessGame.LoadMove(intFlipCounter);			//Load the current move's positions
			ChessGame.setMoveCounter(intFlipCounter);	//update game counter to current move
			btnFlip.setText("Undo");
		}else {
			btnFlip.setText("Load");
		}											//enable/disable forward/backward
		btnFlipB.setEnabled(!btnFlipB.isEnabled());
		btnFlipF.setEnabled(!btnFlipF.isEnabled());
		for (XCounter=0; XCounter<=7; XCounter++) {		//enable/disable board buttons
			for (YCounter=0; YCounter<=7; YCounter++) {
				btnSquares[YCounter][XCounter].setEnabled(!btnFlipB.isEnabled());
			}
		}
		intFlipCounter = ChessGame.getMoveCounter();	//sync FlipCounter to moveCounter
		
		RedrawBoard(ChessGame.getMoveCounter());
			//set MoveCounter to current snapshot and all piece positions to current snapshot
		//}
	}
	
	public void Flip(boolean dir) {							//cycle through snapshots
		
		int intdir = dir ? 1 : -1;							//change boolean to integer positive or negative 1
		int intNextFlip = intFlipCounter + intdir;
		if(intNextFlip >= 0 && ChessGame.MoveisPlayed(intNextFlip)) {	//if move number is above zero and contains pieces on the board
			intFlipCounter = intNextFlip;
		}
		RedrawBoard(intFlipCounter);											
	}
}