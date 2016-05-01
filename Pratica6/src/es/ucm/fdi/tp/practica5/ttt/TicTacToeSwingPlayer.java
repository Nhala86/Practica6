package es.ucm.fdi.tp.practica5.ttt;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

public class TicTacToeSwingPlayer extends Player {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int destinyRow;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int destinyCol;
	
	/**
	 * Metodo constructor por defecto instanciado a vacio
	 */
	public TicTacToeSwingPlayer(){
		
	}
	
	/**
	 * Metodo que setea los parametros de entrada de filas y columnas del tablero
	 * @param destinyRow entero positivo de la columna en la que se encuentra la ficha
	 * @param destinyCol entero positivo de la columna en la que se mueve la ficha
	 */
	public void setMoveValue(int destinyRow, int destinyCol){
		this.destinyCol = destinyCol;
		this.destinyRow = destinyRow;
	}
	
	
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return this.GameCreateMove(this.destinyRow, this.destinyCol, p);
	}
	
	/**
	 * Metodo que llama al juego original pasandole las filas y las columnas
	 * @param destinyRow entero positivo de la columna en la que se encuentra la ficha
	 * @param destinyCol entero positivo de la columna en la que se mueve la ficha
	 * @param p ficha del jugador
	 * @return un nuevo juego
	 */
	protected GameMove GameCreateMove(int destinyRow, int destinyCol, Piece p){
		return new ConnectNMove(destinyRow, destinyCol, p);
	}
	
}
