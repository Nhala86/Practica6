package es.ucm.fdi.tp.practica5.ataxx;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxFactory;

public class AtaxxFactoryExt extends AtaxxFactory {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo constructor por defecto de la clase instanciado a vacio que llama al super()
	 */
	public AtaxxFactoryExt() {
		super();
	}
	
	/**
	 * Metodo constructor de la clase al que se le pasa dos parametros de entrada que llama al super() con parametros
	 * @param dimension entero positivo que indica la dimension del tablero
	 * @param obstacles entero positivo que indica el numero de obstaculos en el tablero
	 */
	public AtaxxFactoryExt(int dimension, int obstacles) {
		super(dimension, obstacles);
	}
	
	/**
	 * Metodo constructor de la clase al que se le pasa un parametro de entrada
	 * @param dimension entero positivo que indica la dimension del tablero
	 */
	public AtaxxFactoryExt(int dimension) {
		super(dimension);
	}
	
	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new AtaxxSwingView(game, ctrl, viewPiece, randPlayer, aiPlayer);
				}
			});

	}
}