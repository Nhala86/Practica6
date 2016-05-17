package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayer;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.DummyAIPlayer;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;

public class AtaxxFactory implements GameFactory {
	private int dim;
	private int obstaculo;
	
	/**
	 * Metodo constructor sin parametros de entrada,
	 * que iguala la dimension a 5 y los obstaculos a 0 por defecto.  
	 */
	public AtaxxFactory(){
		this.dim = 5;
		this.obstaculo = 0;
		
	}
	
	/**
	 * Constructora que recibe el parametro de entrada de la dimension del tablero.
	 * Si la dimension es menor que 5, devuelve un mensaje de error
	 * @param dim valor entero positivo de la dimension del tablero
	 */
	public AtaxxFactory(int dim){
		if(dim < 5){
			throw new GameError("La dimension debe ser mayor o igual que 5. PARAMETRO:" + dim);
		}
		else{
			this.dim = dim;
			this.obstaculo = 0;
		}
	}
	
	/**
	 * Constructora que recibe los parametro de entrada de dimension y obstaculos del juego
	 * @param dim valor entero positivo de la dimension del tablero
	 * @param obstaculo valor entero positivo del la cantidad de obstaculos que hay en el tablero
	 */
	public AtaxxFactory(int dim, int obstaculo){
		if(dim < 5){
			throw new GameError("La dimension debe ser mayor o igual que 5. PARAMETRO:" + dim);
		}
		else{
			if(obstaculo > (dim * dim) / 10){
				throw new GameError("El numero de obstaculos deben ser menor que el 10 % de la superficie. PARAMETRO: " + obstaculo);
			}
			else{
				this.dim = dim;
				this.obstaculo = obstaculo;
			}
		}
	}

	@Override
	public GameRules gameRules() {
		return new AtaxxRules(this.dim, this.obstaculo);
	}

	@Override
	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new AtaxxMove());
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}

	@Override
	public Player createRandomPlayer() {
		return new AtaxxRandomPlayer();
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		return new DummyAIPlayer(createRandomPlayer(), 1000);
	}

	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> game, Controller ctrl) {
		new GenericConsoleView(game, ctrl);
		
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer,
			Player aiPlayer) {
		throw new UnsupportedOperationException("There is no swing view");
		
	}

}
