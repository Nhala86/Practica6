package es.ucm.fdi.tp.practica5.view.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComponent;
//import javax.swing.SwingUtilities;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

public abstract class RectBoardComponent extends JComponent implements GameObserver  {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Atributo de tablero
	 */
	protected Board board;

	/**
	 * Atributo HasMap de pieza y color, color de las piezas
	 */
	protected HashMap<Piece,Color> colorPieces;

	/**
	 * Atributo entero que indica el alto de la casilla
	 */
	protected int _CELL_HEIGHT = 500;
	
	/**
	 * Atributo entero que indica el ancho de la casilla
	 */
	protected int _CELL_WIDTH = 500;
	
	/**
	 * Parametros enumerados de los tipos de objetos en el tablero: piezas, casillas y obstaculos
	 * @author Lidia
	 * 
	 */
	public enum Tipes{
		PIECE, CELL, OBSTACLE
	}
	
	/**
	 * Metodo constructor con parametros de entrada del observable y el tablero
	 * @param game parametro del observable del juego
	 * @param board parametro del tablero
	 */
	public RectBoardComponent(final Observable<GameObserver> game, Board board){
		this.board = board;
		initGUI();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				game.addObserver(RectBoardComponent.this);
			}
		});
	}
	
	/**
	 * Metodo que procedimenta los clicks del mouse
	 */	
	protected void initGUI() {		
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Mouse Released: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse Pressed: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("Mouse Exited Component: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("Mouse Entered Component: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(
						"Mouse Button " + e.getButton() + " Clicked at " + "(" + e.getX() + "," + e.getY() + ")");
				int row = (e.getX()/_CELL_WIDTH);
				int col = (e.getY()/_CELL_HEIGHT);
				RectBoardComponent.this.mouseClicked(row, col, e.getClickCount(), e.getButton());
			}
		});
		this.setSize(new Dimension(board.getRows() * _CELL_HEIGHT, board.getCols() * _CELL_WIDTH));
		repaint();
	}
	
	/**
	 * Metodo abstracto que indica la fila y columna a la que clicka el mouse	
	 * @param row entero positivo de fila
	 * @param col entero positivo de columna
	 * @param clickCount contador de clicks del mouse
	 * @param button indicador del boton
	 */
	protected abstract void mouseClicked(int row, int col, int clickCount, int button);
	
	/**
	 * Metodo abstracto que pinta las piezas 
	 * @param piece que se le pasa del jugador 
	 * @return la pieza con el color
	 */
	protected abstract Color getPieceColor(Piece piece);
	
	/**
	 * Metodo abstracto que indica el tipo de pieza que se le pasa
	 * @param p pieza que se le pasa como parametro
	 * @return el tipo de pieza 
	 */
	protected abstract Tipes getPieceTipe(Piece p);

	/**
	 * Fuincion que dibuja los objetos
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fillBoard(g);
	}

	/**
	 * Coloca en el tablero el tipo de pieza que se le pasa
	 * @param g parametro grafico que dibuja los objetos
	 */
	private void fillBoard(Graphics g) {
		int x, y;
		this._CELL_WIDTH  = this.getWidth()  / board.getCols();
		this._CELL_HEIGHT = this.getHeight() / board.getRows();
	  
		for (int i = 0; i < board.getRows(); i++){
			for (int j = 0; j < board.getCols(); j++) {
				
				x = i * _CELL_WIDTH;
				y = j * _CELL_HEIGHT;
				
				drawCell(x,y,g);

				switch(getPieceTipe(board.getPosition(i, j))){
					case PIECE:
						g.setColor(getPieceColor(board.getPosition(i, j)));
						drawPiece(x,y,g);
						break;
					case OBSTACLE:
						drawObtacle(x,y,g);
						break;
					default:
						drawCell(x,y,g);
						break;
				}
			}
		}

	}
	
	/**
	 * Metodo que dibuja y pinta las casillas
	 * @param x entero de indica el alto de la casilla
	 * @param y entero de indica el ancho de la casilla
	 * @param g parametro grafico que dibuja los objetos
	 */
	protected void drawCell(int x, int y, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);
		
	}
	
	/**
	 * Metodo que dibuja y pinta las piezas
	 * @param x entero de indica el alto de la casilla
	 * @param y entero de indica el ancho de la casilla
	 * @param g parametro grafico que dibuja los objetos
	 */
	protected void drawPiece(int x, int y, Graphics g){
		g.setPaintMode();
		g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		g.setColor(Color.black);
		g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
	}
	
	/**
	 * Metodo que dibuja y pinta los obstaculos
	 * @param x entero de indica el alto de la casilla
	 * @param y entero de indica el ancho de la casilla
	 * @param g parametro grafico que dibuja los objetos
	 */
	protected void drawObtacle(int x, int y, Graphics g){
		g.setColor(Color.white);
		g.fillRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		repaint();
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		this.board = board;
		repaint();
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		this.board = board;
		repaint();
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		this.board = board;
		repaint();
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		this.board = board;
		repaint();
	}

	@Override
	public void onError(String msg) {
	}	
	
}
