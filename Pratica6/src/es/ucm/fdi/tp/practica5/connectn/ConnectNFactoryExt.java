package es.ucm.fdi.tp.practica5.connectn;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;
import es.ucm.fdi.tp.basecode.connectn.ConnectNFactory;

public class ConnectNFactoryExt extends ConnectNFactory {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo constructor por defecto de la clase instanciado a vacio que llama al super()
	 */
	public ConnectNFactoryExt(){
		super();
	}
	
	/**
	 * Metodo constructor de la clase al que se le pasa dos parametros de entrada que llama al super() con parametros
	 * @param dim entero positivo que indica la dimension del tablero
	 */
	public ConnectNFactoryExt(int dim){
		super(dim);
	}

	@Override
	public void createConsoleView(Observable<GameObserver> g, Controller c) {
		new GenericConsoleView(g, c);
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer){
	
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					new ConnectnSwingview(game, ctrl, viewPiece, randPlayer, aiPlayer);
					}
				});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
