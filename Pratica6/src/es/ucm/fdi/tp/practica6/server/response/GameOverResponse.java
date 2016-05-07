package es.ucm.fdi.tp.practica6.server.response;

import es.ucm.fdi.tp.practica6.server.response.Response;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

public class GameOverResponse implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameOverResponse(Board board, State state, Piece winner) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void rum(GameObserver o) {
		// TODO Auto-generated method stub
		
	}

}
