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
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica6.connection.Connection;
import es.ucm.fdi.tp.practica6.server.response.Response;

public class GameClient extends Controller implements Observable<GameObserver> {
	
	private String host;
	private int port;
	private List<GameObserver> observers;
	private Piece localPiece;
	private GameFactory gameFactory;
	private Connection connectioToServer;
	private boolean gameOver;

	public GameClient(String host, int port) throws Exception {
		super(null, null);
		this.host = host;
		this.port = port;
		try{
			connect();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	private void connect() throws IOException, ClassNotFoundException {		
		connectioToServer = new Connection(new Socket(host, port));
		connectioToServer.sendObject("Connect");
		
		
		Object response = this.connectioToServer.getObject();
		if(response instanceof Exception){
			throw (IOException) response;			
		}
		else if((response instanceof String) && ((String)response).equalsIgnoreCase("OK")){
			try{
				gameFactory = (GameFactory) connectioToServer.getObject();
				localPiece = (Piece) connectioToServer.getObject();
			}catch(Exception e){
				throw new GameError("Unknown server response: " + e.getMessage()); 
			}
		}		
	}

	@Override
	public void addObserver(GameObserver o) {
		this.observers.add(o);		
	}

	@Override
	public void removeObserver(GameObserver o) {
		this.observers.remove(o);
		
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
		if(!this.gameOver){
			try{
				this.connectioToServer.sendObject(cmd);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}	
	
	public void start(Player player) {
	
		this.observers.add((GameObserver) player);
		gameOver = false;
		while(!gameOver){
			try{
				Response response = (Response) this.connectioToServer.getObject();
				for(GameObserver o : observers){
					response.rum(o);
				}
			}catch(IOException | ClassNotFoundException e){}			
		}		
	}
	
}