package es.ucm.fdi.tp.practica5.attt;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;

public class AdvancedTTTFactoryExt extends AdvancedTTTFactory {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo constructor por defecto sin parametros de entrada que llama al super()
	 */
	public AdvancedTTTFactoryExt() {
		super();
	}
	
	@Override
	public void createConsoleView(Observable<GameObserver> g, Controller c) {
		new GenericConsoleView(g, c);
	}
	
	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					new AdvancedTTTSwingView(game, ctrl, viewPiece, randPlayer, aiPlayer);
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
