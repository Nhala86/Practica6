package es.ucm.fdi.tp.practica5.ttt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.RectBoardSwingView;
import es.ucm.fdi.tp.practica5.view.sound.MakeSound;

public class TicTacToeSwingView extends RectBoardSwingView {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Parametro de jugador de la SwingPlayer
	 */
	private TicTacToeSwingPlayer player;
	
	/**
	 * Metodo constructor al que se le pasan los parametos de juego, controlador, piezas y jugadores
	 * @param game parametro observable del juego
	 * @param c parametro del controlador
	 * @param localPiece parametro de las piezas con las que se inicia el juego
	 * @param randPlayer parametro de jugador random
	 * @param aiPlayer parametro de jugador IA
	 */
	public TicTacToeSwingView(Observable<GameObserver> game, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(game, c, localPiece, randPlayer, aiPlayer);
		MakeSound.RunPlaySound("sound/tttopen.wav"); // Hilo musical al comienzo de cada juego
		player = new TicTacToeSwingPlayer();
	}

	@Override
	protected void handelMouseClick(int row, int col, int clickcounter, int mouseButton) {
		if(this.inPlay){
			player.setMoveValue(row, col);
			this.caseMakeManualMove(player);
		}
		
	}

}
