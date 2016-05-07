package es.ucm.fdi.tp.practica6.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica6.conection.Connection;

public class Client extends Controller implements Observable<GameObserver> {
	
	private String host;
	private int port;
	private List<GameObserver> observers;
	private Piece localPiece;
	private GameFactory gameFactory;
	private Connection connectioToServer;
	private boolean gameOver;

	public Client(String host, int port) throws Exception {
		super(null, null);
		this.host = host;
		this.port = port;
		connect();
	}

	private void connect() throws Exception {
		connectioToServer = new Connection(new Socket(host, port));
		connectioToServer.sendObject("Connect");
		
		
		Object response = this.host;
		if(response instanceof Exception){
			throw (Exception) response;			
		}
		try{
			gameFactory = (GameFactory) connectioToServer.getObject();
			localPiece = (Piece) connectioToServer.getObject();
		}catch(Exception e){
			throw new GameError("Unknown server response: " + e.getMessage()); 
		}
		//this.observers.add(null);
	}

	@Override
	public void addObserver(GameObserver o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(GameObserver o) {
		
		
	}
	
	public GameFactory getGameFactory(){
		return this.gameFactory;
	}
	
	public Piece getPlayerPiece(){
		return this.localPiece;
	}
	
	@Override
	public void makeMove(Player p) {
		forwardCommand(new PlayCommand(p));
	}
	
	@Override
	public void stop() {
		forwardCommand(new QuitCommand());
	}
	
	@Override
	public void restart() {
		forwardCommand(new RestartCommand());
	}
	
	private void forwardCommand(Command cmd) {
	// if the game is over do nothing, otherwise
	// send the object cmd to the server
	}	
	
	public void star(){
		this.observers.add(port, null);
		gameOver = false;
		while(!gameOver){
			try{
				
			}catch(ClassNotFoundException | IOException e){}
		}
	}
	
}
