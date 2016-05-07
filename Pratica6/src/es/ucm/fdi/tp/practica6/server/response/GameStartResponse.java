package es.ucm.fdi.tp.practica6.server.response;

import java.io.IOException;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica6.conection.Connection;
import es.ucm.fdi.tp.practica6.server.response.Response;

public class GameStartResponse implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Board board;
	private String gameDesc;
	private List<Piece> pieces;
	private Piece turn;
	
	public GameStartResponse(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		this.gameDesc = gameDesc;
		this.pieces = pieces;
		this.turn = turn;
	}

	@Override
	public void rum(GameObserver o) {
		o.onGameStart(board, gameDesc, pieces, turn);
		
	}
	
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn){
		forwardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
	}

	private void forwardNotification(Response r) {
		try{
			for(Connection c : clients){
				c.sendObject(r);
			}
		}catch(IOException e){
			stopTheGame();
		}
		
	}

	private void stopTheGame() {
		// TODO Auto-generated method stub
		
	}

}
