package es.ucm.fdi.tp.practica5.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AdvancedTTTSwingPlayer extends Player {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * The row where the piece is return return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originRow;

	/**
	 * The column where the piece is return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originCol;
	
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
	public AdvancedTTTSwingPlayer() {
		
	}

	/**
	 * Metodo que setea los parametros de entrada de filas y columnas del tablero
	 * @param originRow entero positivo de la fila en la que se encuentra la ficha
	 * @param originCol entero positivo de la fila en la que se mueve la ficha
	 * @param destinyRow entero positivo de la columna en la que se encuentra la ficha
	 * @param destinyCol entero positivo de la columna en la que se mueve la ficha
	 */
	public void setMoveValue(int originRow, int originCol, int destinyRow, int destinyCol){
		this.originRow = originRow;
		this.originCol = originCol;
		this.destinyCol = destinyCol;
		this.destinyRow = destinyRow;
	}	

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return this.GameCreateMove(this.originRow, this.originCol, this.destinyRow, this.destinyCol, p);
	}
	
	/**
	 * Metodo que llama al juego original pasandole las filas y las columnas
	 * @param originRow entero positivo de la fila en la que se encuentra la ficha
	 * @param originCol entero positivo de la fila en la que se mueve la ficha
	 * @param destinyRow entero positivo de la columna en la que se encuentra la ficha
	 * @param destinyCol entero positivo de la columna en la que se mueve la ficha
	 * @param p ficha del jugador
	 * @return un nuevo juego
	 */
	protected GameMove GameCreateMove(int originRow, int originCol, int destinyRow, int destinyCol, Piece p){
		return new AdvancedTTTMove(originRow, originCol, destinyRow, destinyCol, p) ;
	}
}
