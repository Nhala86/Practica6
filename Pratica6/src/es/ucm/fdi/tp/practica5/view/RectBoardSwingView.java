 package es.ucm.fdi.tp.practica5.view;

import java.awt.Color;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.board.RectBoardComponent;


public abstract class RectBoardSwingView extends SwingView{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo constructor con parametros
	 * @param game observable del juego
	 * @param c controlador del juego
	 * @param localPiece piezas del jugador al inicio
	 * @param randPlayer jugador random
	 * @param aiPlayer jugador IA
	 */
	public RectBoardSwingView(Observable<GameObserver> game, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(game, c, localPiece, randPlayer, aiPlayer);
	}

	@SuppressWarnings("serial")
	@Override
	protected void initBoardGui(Controller ctrl, Observable<GameObserver> game) {
				
		boardComponent = new RectBoardComponent(game, board){

		/**
		 * Funcion que llama al handelMouseClick pasandole los atributos de fila, columna, contador de click y boton del mouse		
		 */
		protected void mouseClicked(int row, int col, int clickcounter, int mouseButton){
			handelMouseClick(row, col, clickcounter, mouseButton);
		}
			
		@Override
		protected Color getPieceColor (Piece piece){
			return RectBoardSwingView.this.getPieceColor(piece);
		}
	
		@Override
		protected Tipes getPieceTipe(Piece piece) {
			Tipes PieceTipe = null;
			if(getPieces().contains(piece))
				PieceTipe = Tipes.PIECE;
			else if(piece != null && piece.getId().equals("*"))
				PieceTipe = Tipes.OBSTACLE;
			else
				PieceTipe = Tipes.CELL;
	
			return PieceTipe;
			}		
		};
		setBoardArea(boardComponent);
	}
		
	@Override
	protected void activateBoard() {
		this.inPlay = true;
		this.inMove = false;
	}

	@Override
	protected void deActivateBoard() {	
		this.inPlay = false;
		this.inMove = true;
	}

	@Override
	protected void redrawBoard() {
		this.boardComponent.repaint();
	}
		
	/**
	 * Funcion abstracta del handleMouseClick con parametros de entrada
	 * @param row entero positivo de la fila
	 * @param col entero positivo de la columna
	 * @param clickcounter contador de click del mouse
	 * @param mouseButton indicador del boton del raton
	 */
	protected abstract void handelMouseClick(int row, int col, int clickcounter, int mouseButton);
}
