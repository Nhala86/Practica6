package es.ucm.fdi.tp.practica5.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.board.RectBoardComponent;
import es.ucm.fdi.tp.practica5.view.color.ColorChooser;
import es.ucm.fdi.tp.practica5.view.sound.MakeSound;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;

public abstract class SwingView extends JFrame implements GameObserver{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

//------------------------------MODOS DE JUEGO-------------------------------//		
	/**
	 * Player modes (manual, random, etc.)
	 * Modos de juego.
	 */
	public enum PlayerMode {
		MANUAL("Manual"), RANDOM("Random"), AI("Automatics");

		private String desc;

		PlayerMode(String desc){
			this.desc = desc;
		}
		
		@Override
		public String toString() {
			return desc;
		}
	}
	
	/**
	 * A map that associates pieces with players modes (manual, random, etc.).
	 * Map que asocia fichas con modos de jugadores (manual, random, etc.).
	 */
	private Map<Piece,PlayerMode> playersModes;
	
	/**
	 * Used for recieve the reference of game modes for the list of pieces
	 * @return the players modes Map of this object
	 */
	public Map<Piece,PlayerMode> getPlayerModes(){
		return this.playersModes;
	}

//------------------------------COLORES DE PIEZAS-------------------------------//		
	/**
	 * A map that associates pieces Colors).
	 * Map que asocia fichas con colores.
	 */
	private Map<Piece,Color> pieceColors;
	
	/**
	 * Metodo que mapea las piezas y el color de estas
	 * @return la pieza con color
	 */
	public Map<Piece,Color> getPieceColors(){
		return this.pieceColors;
	}
	
	/**
	 * Metodo del Color que devuelve el color de las piezas
	 * @param p atributo de la pieza
	 * @return la pieza y el color
	 */
	protected Color getPieceColor(Piece p) {		
		return pieceColors.get(p);
	}
	
	/**
	 * Metodo del Color que setea el color de las piezas
	 * @param p atributo de la pieza
	 * @param c atributo del color
	 * @return la pieza y el color
	 */
	protected Color setPieceColor(Piece p, Color c) {
		return pieceColors.put(p, c);
	}
	

//------------------------------LISTA DE PIEZAS-------------------------------//
	/**
	 * Lista de piezas
	 */
	private List<Piece>pieces;
	
	/**
	 * getPieces
	 * @return list of piece for this game
	 */
	final protected List<Piece> getPieces() {
		return this.pieces;
	}
	
//------------------------------PIEZA DE SWINGVIEW-------------------------------//	
	/**
	 * Parametro de la Piece del turno
	 */
	private Piece turn;
	/**
	 * getTurn
	 * @return the turn for this game
	 */
	final protected Piece getTurn() {
		return this.turn;
	}

//------------------------------REFERENCIA DE TABLERO-------------------------------//
	/**
	 * Parametro de Board
	 */
	protected Board board;
	/**
	 * getBoard
	 * @return the model board for this game
	 */
	final protected Board getBoard() {
		return this.board;
	}
	
//------------------------------COMPONENTE DE TABLERO-------------------------------//	
	/**
	 * ControllerPanel
	 * Panel aglomerador de componentes
	 */
	private JPanel boardPanel;
	/**
	 * Parametro de RectBoardComponent
	 */
	protected RectBoardComponent boardComponent;
	/**
	 * setBoardArea
	 * @param component to add to the boardPanel;
	 */
	final protected void setBoardArea(JComponent component) {
		this.boardPanel.add(component, BorderLayout.CENTER);
	}
	
//------------------------------ATRIBUTOS DE SWING VIEW-------------------------------//
	/**
	 * Prametro observable del juego
	 */
	protected Observable<GameObserver> game;
	
	/**
	 * Parametro del Controlador
	 */
	protected Controller controller;
	
	/**
	 * Parametro de la pieza
	 */
	private Piece localPiece;
	
	/**
	 * Parametro del jugador Random
	 */
	private Player randPlayer;
	
	/**
	 * Parametro del jugador IA
	 */
	private Player aiPlayer;
	
	/**
	 * Parametros boleanos de nuevo juego, del juego y del movimiento
	 */
	protected boolean newGame, inPlay, inMove;
		
	/**
	 * SwingView
	 * Constructor for the abstract father SwingView
	 * Constructor de la clase padre abstracta SwingView
	 * @param game observable del juego
	 * @param controller it a reference to call the methods of the controller of the MVC
	 * @param localPiece its an indicator of the owner for this SwingView
	 * @param randPlayer a builded random player for random moves
	 * @param aiPlayer a builded automatic player for automatics moves
	 */
	public SwingView(Observable<GameObserver> game, Controller controller, Piece localPiece, Player randPlayer, Player aiPlayer){
		
		this.game = game;
		this.controller = controller;
		this.localPiece = localPiece;
		this.randPlayer = randPlayer;
		this.aiPlayer = aiPlayer;
		this.pieceColors = new HashMap<Piece,Color>();
		this.playersModes = new HashMap<Piece,PlayerMode>();

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				game.addObserver(SwingView.this);
			}
		});	
		
		initGUI();
	}
	
	/**
	 * initGUI
	 * Creation procedure for the main frame
	 * Procedimiento de maquetación del frame principal
	 */
	private void initGUI() {

		this.setLayout(new BorderLayout(5, 5));	
		createPanels();	
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				quit();
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension(800,600));
		this.setVisible(true);
		
	}
	
//----------------------------------PANEL DE CONTROLADOR------------------------------------//
	
	/**
	 * ControllerPanel
	 * Panel aglomerador de componentes
	 */
	private JPanel ControllerPanel;
	
	/**
	 * gameMessagesPanel
	 * Creation procedure accumulator panel components
	 * Procedimiento de creación del panel aglomerador de componentes 
	 */
	final private void createPanels(){
		
		this.boardPanel = new JPanel(new BorderLayout(5, 5));
		this.ControllerPanel = new JPanel(new BorderLayout(5, 5));
		this.ControllerPanel.setLayout(new BoxLayout(ControllerPanel, BoxLayout.Y_AXIS));
		this.ControllerPanel.setPreferredSize(new Dimension(300,600));
		gameMessagesComponent();
		playerInformationComponent();
		selectColorComponent ();
		selectModePlayerComponent();
		automaticMoveComponent();
		exitComponent();
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(ControllerPanel, BorderLayout.LINE_END);
	}

	/**
	 * gameMessagesPanel
	 * Creation procedure accumulator panel components
	 * Procedimiento de creación de un panel etiquetado y centrado
	 * @param label String whit the label for the titled border
	 * @param color background configuration
	 * @return a instanced titled JPanel 
	 */
	protected JPanel createPanelLabeled(String label, Color color){
		JPanel panel = new JPanel();
		panel.setBackground(color);
		panel.setBorder(BorderFactory.createTitledBorder(label));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		return panel;
	}
	
//----------------------------------COMPONENTE DE TEXTO------------------------------------//
	
	/**
	 * gameMessagesPanel
	 * Panel contenedor del componente de mesajes del juego 
	 */
	private JPanel gameMessagesPanel;
	
	/**
	 * gameMessages
	 * Area de texto de salida de los mesajes del juego 
	 */
	private JTextArea gameMessages;
	
	/**
	 * gameMessagesPanel
	 * Component creation process for the status game messages 
	 * Procedimiento de creación del componente de mensajes de estado del juego
	 */
	private void gameMessagesComponent() {
		this.gameMessagesPanel = createPanelLabeled("Player Information", Color.LIGHT_GRAY);
		this.gameMessages = new JTextArea(48, 43);
		this.gameMessages.setEditable(false);
		this.gameMessages.setFont(new Font("SansSerif", Font.BOLD, 11));
		this.gameMessagesPanel.add(new JScrollPane(this.gameMessages), BorderLayout.CENTER);		
		this.ControllerPanel.add(this.gameMessagesPanel);
	}
	
//--------------------------METODOS DEL COMPONENTE DE TEXTO-------------------------------//
	/**
	 * gameMessagesPanel
	 * Component creation process for the status game messages 
	 * Procedimiento de insercion de mensajes en el area de texto
	 * @param message to show in the tex area
	 */
	final protected void addMessageToTextArea(String message) {
		this.gameMessages.append("* " + message + System.getProperty("line.separator"));		
	}
	
//-------------------------COMPONENTE DE INFORMACION DE JUGADORES--------------------------//	
	
	/**
	 * playerInformationPanel
	 * Panel contenedor del tablon de información de jugadores 
	 */
	private JPanel playerInformationPanel;
	
	/**
	 * infoTable
	 * Tabla contenedora de la informacion de los jugadores
	 */
	private PlayerInformationTable infoTable;
	
	/**
	 * playerInformationComponent
	 * Component creation process for the payers information 
	 * Procedimiento de creación del componente de informacion de jugadores
	 */	
	private void playerInformationComponent(){
		this.playerInformationPanel = createPanelLabeled("Player Information", Color.LIGHT_GRAY);		
		playerInformationPanel.setBackground(Color.LIGHT_GRAY);		
		this.infoTable = new PlayerInformationTable();
		
		@SuppressWarnings("serial")
		JTable infoPanel = new JTable(this.infoTable){
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				comp.setBackground(pieceColors.get(pieces.get(row)));
				return comp;
			}
		};
		infoPanel.setEnabled(false);
		infoPanel.setFillsViewportHeight(true);		
		JScrollPane scroll = new JScrollPane(infoPanel);		
		this.ControllerPanel.add(this.playerInformationPanel.add(scroll));	
	}
	
//-------------------------COMPONENTE DE CAMBIO DE COLORES-------------------------------//	
	/**
	 * selectColorPanel
	 * Panel contenedor del desplegable y boton de cambio de color 
	 */
	private JPanel selectColorPanel;
	
	/**
	 * pieceColorCombo
	 * desplegable con el contenido de piezas del juego para el cambio de colores
	 */
	private JComboBox<Piece> pieceColorCombo;
	
	/**
	 * selectColorComponent
	 * Component creation process for the colors change 
	 * Procedimiento de creación del componente de cambio de colores
	 */
	private void selectColorComponent (){
		this.selectColorPanel = createPanelLabeled("Color Selection", Color.LIGHT_GRAY);
		
		this.pieceColorCombo = new JComboBox<Piece>();
		this.selectColorPanel.add(pieceColorCombo);

		JButton setColor = new JButton("Choose Color");
		this.selectColorPanel.add(setColor);

			setColor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Piece p = (Piece) pieceColorCombo.getSelectedItem();
					ColorChooser choosedColor = new ColorChooser(new JFrame(), 
							"Select Piece Color", pieceColors.get(p));
					
					if (!choosedColor.getColor().equals(null)) {
						pieceColors.put(p, choosedColor.getColor());
						repaint();
					}
				}

			});
			this.ControllerPanel.add(this.selectColorPanel);
	}
	
	
//-------------------------COMPONENTE DE CAMBIO DE MODOS-------------------------------//	
	
	/**
	 * selectModePlayerPanel
	 * Panel contenedor del los desplegables y boton de cambio de modos 
	 */
	private JPanel selectModePlayerPanel;
		
	/**
	 * modeCombo
	 * desplegable con el contenido de piezas del juego para el cambio de modos
	 */
	private JComboBox<Piece> pieceModeCombo;
	
	/**
	 * modeCombo
	 * desplegable con el contenido de tipos de modos para los jugadores
	 */
	private JComboBox<PlayerMode> modeCombo;
	
	/**
	 * selectModePlayerComponent
	 * Component creation process for the modes change 
	 * Procedimiento de creación del componente de cambio de modos 
	 */
	@SuppressWarnings("serial")
	private void selectModePlayerComponent() {

		this.selectModePlayerPanel = createPanelLabeled("Select Mode", Color.LIGHT_GRAY);
		this.modeCombo = new JComboBox<PlayerMode>();
		this.modeCombo.addItem(PlayerMode.MANUAL);
		
		if (!randPlayer.equals(null)) {
			modeCombo.addItem(PlayerMode.RANDOM);
		}
		
		if (!aiPlayer.equals(null)) {
			modeCombo.addItem(PlayerMode.AI);
		}
		this.pieceModeCombo = new JComboBox<Piece>(new DefaultComboBoxModel<Piece>() {
			@Override
			public void setSelectedItem(Object o) {
				super.setSelectedItem(o);
				if (!playersModes.get(o).equals(PlayerMode.MANUAL)) {
					modeCombo.setSelectedItem(PlayerMode.AI);
				} else {
					modeCombo.setSelectedItem(PlayerMode.MANUAL);
				}
			}
		});
		
		this.selectModePlayerPanel.add(pieceModeCombo);
		this.selectModePlayerPanel.add(modeCombo);
		
		JButton setMode = new JButton("Set");
		this.selectModePlayerPanel.add(setMode);
			setMode.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Piece p = (Piece) pieceModeCombo.getSelectedItem();
					PlayerMode m = (PlayerMode) modeCombo.getSelectedItem();
					PlayerMode currMode = playersModes.get(p);
					playersModes.put(p, m);
					infoTable.refresh();
					if (currMode.equals(PlayerMode.MANUAL) && !m.equals(PlayerMode.MANUAL)){
						caseMakeAutomaticMove();
					}
				}
			});
		this.ControllerPanel.add(this.selectModePlayerPanel);
	}

	
//--------------------------COMPONENTE DE MOVIMIENTO AUTOMATICO-------------------------------//	
	
	/**
	 * automaticMovePanel
	 * Panel contenedor del los botones para la ejecucion de movimientos automaticos
	 * container panel buttons for the execution of automatic movements
	 */
	private JPanel automaticMovePanel;
	
	/**
	 * automaticMoveComponent
	 * Component creation process for the automatic movements 
	 * Procedimiento de creación del componente de movimientos automaticos 
	 */
	private void automaticMoveComponent(){		
		
		this.automaticMovePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		automaticMovePanel.setBorder(BorderFactory.createTitledBorder("Player Modes"));
		automaticMovePanel.setBackground(Color.LIGHT_GRAY);
		
		JButton random = new JButton("Random");		
			random.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					executeControllerMove(randPlayer);
				}
			});			
		this.automaticMovePanel.add(random);

		JButton intelligent = new JButton("Intelligent");
			intelligent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					executeControllerMove(randPlayer);
				}
			});
		this.automaticMovePanel.add(intelligent);
		
		this.ControllerPanel.add(this.automaticMovePanel);	
	}
	
	
//--------------------------COMPONENTE DE MOVIMIENTO AUTOMATICO-------------------------------//
	
	/**
	 * exitPanel
	 * Panel contenedor del los botones para salir o reiniciar el juego
	 * container panel buttons for exit or restart the game
	 */
	private JPanel exitPanel;

	/**
	 * exitComponent
	 * Component creation process for the automatic movements 
	 * Procedimiento de creación del componente de movimientos automaticos 
	 */
	private void exitComponent(){		
		this.exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		exitPanel.setBorder(BorderFactory.createTitledBorder("Player Modes"));
		exitPanel.setBackground(Color.LIGHT_GRAY);
		JButton quit = new JButton("Quit");
		
			quit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

					if (n == 0) {
						try {
							controller.stop();
						} catch (GameError _e) {

						}
						setVisible(false);
						dispose();
						System.exit(0);
						
					}
				}
			});
		
		this.exitPanel.add(quit);		
		
		JButton restartButton = new JButton("Restart");
		
			restartButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = JOptionPane.showOptionDialog(new JFrame(), "Are you sure you want to restart the game?", "Restart",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

					if (n == 0) {
						try {
							controller.restart();
							//gameMessages.setText("");
							newGame = true;
						} catch (GameError _e) {

						}
						setVisible(true);
						repaint();
					}
				}
			});
			
		this.exitPanel.add(restartButton);		
		this.ControllerPanel.add(this.exitPanel);		
	}
	
	/**
	 * Salir de la aplicacion.
	 */
	final protected void quit() {
		
		int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (n == 0) {
			try {
				this.controller.stop();
			} catch (GameError _e) {

			}
			setVisible(false);
			dispose();
			System.exit(0);
			
		}		
	}
	
//--------------------------METODOS DEL MOVIMIENTO AUTOMATICO-------------------------------//	
	
	/**
	 * decideMakeManualMove
	 * Execution of a procedure manual movement
	 * Procedimiento de ejecucion de un movimiento manual 
	 */
	final protected void caseMakeManualMove(Player manualPlayer) {
		if(this.inMove || !this.inPlay)
			return;
		if(this.localPiece != null && !this.localPiece.equals(turn))
			return;
		if(this.playersModes.get(turn) != PlayerMode.MANUAL)
			return;
		executeControllerMove(manualPlayer);
	}
	
	/**
	 * caseMakeAutomaticMove
	 * Procedure for execution of an automatic movement
	 * Procedimiento de ejecucion de un movimiento automatico 
	 */
	final protected void caseMakeAutomaticMove() {
		if(this.inMove || !this.inPlay)
			return;
		if(this.localPiece != null && !this.localPiece.equals(this.turn))
			return;
		switch(this.playersModes.get(this.turn)){
			case AI:
				executeControllerMove(this.aiPlayer);
				break;
			
			case RANDOM:
				executeControllerMove(this.randPlayer);
				break;
				
			default:
				break;
		}
	}
	
	/**
	 * decideMakeAutomaticMove
	 * Procedure for execution of an movement 
	 * Procedimiento de ejecucion de un movimiento
	 */
	private void executeControllerMove(final Player player){
		this.setEnabled(false);
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				try{
					controller.makeMove(player);
				}catch(GameError e){	
				}
			}
		});
		this.setEnabled(true);
	}	

//------------------------------EVENTOS DEL OBSERVADOR-------------------------------//		
	@Override
	public void onGameStart(final Board board, String gameDesc, List<Piece> pieces, Piece turn) { //El modelo llama a los métodos del GameObserver para notificar cambios, etc.
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				handleGameStart(board, gameDesc, pieces, turn);
			}
		});
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {	
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				handleGameOver(board, state, winner);
			}	
		});
	}
	
	@Override
	public void onMoveStart(Board board, Piece turn) {
		if(this.turn == turn)
			this.inMove = true;
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		if(this.turn == turn)
			this.inMove = false;
		if(!success)
			handleChangeTurn(board, turn);
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				handleChangeTurn(board, turn);
			}	
		});
	}

	@Override
	public void onError(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "Error",
				JOptionPane.ERROR_MESSAGE);
	}	
	
//-------------------------HANDLERS DE LOS EVENTOS OBSERVADOR-------------------------------//	
	/**
	 * Funcion que condiciona del comienzo del juego, pasandole parametros
	 * @param board of the model
	 * @param gameDesc of the model
	 * @param pieces of the model
	 * @param turn of the model
	 */
	private void handleGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.setTitle(gameDesc + (this.localPiece == null ? "" : "(" + this.localPiece + ")"));
		this.board = board;
		this.pieces = pieces;
		this.turn = turn;
		this.inPlay = true;
		this.newGame = true;

		initBoardGui(this.controller, this.game);
		//GENERAR COLORES PARA LAS PIEZAS
		pieceColorCombo.removeAllItems();
		for(Piece p : pieces){
			if(pieceColors.get(p) == null)
				pieceColors.put(p, Utils.randomColor());
			pieceColorCombo.addItem(p);
		}
		
		//GENERAR MODOS PARA LOS JUGADORES
		if(this.localPiece == null){	//MONOVISTA
			for(Piece p : pieces)
				if(this.playersModes.get(p) == null){
					this.playersModes.put(p, PlayerMode.MANUAL);
					pieceModeCombo.addItem(p);
				}
		}
		
		else{ //MULTIVISTA			
			if(this.playersModes.get(this.localPiece) == null){
				for(Piece localPiece : pieces){
					this.playersModes.put(localPiece, PlayerMode.MANUAL);					
				}
				pieceModeCombo.addItem(localPiece);
			}			
		}	
		this.gameMessages.setText("");
		this.addMessageToTextArea("* --  --   GAME START  --  -- *");		
		handleChangeTurn(board, turn);
	}	
	
	/**
	 * Funcion que condiciona el final del juego, pasandole parametros
	 * @param board parametro del tablero
	 * @param state parametro del estado del jugador
	 * @param winner la pieza ganadora 
	 */
	private void handleGameOver(Board board, State state, Piece winner) {
		this.infoTable.refresh();
		this.board = board;
		
		this.addMessageToTextArea(" GAME OVER *");
		
		switch(state){
		case Won:
			MakeSound.RunPlaySound("sound/winner.wav");
			this.addMessageToTextArea(" " + winner + " is the winner!!");
			break;
		case Draw:
			MakeSound.RunPlaySound("sound/draw.wav");
			this.addMessageToTextArea("Look at this, we have a draw!!");
			break;
		case Stopped:
			this.addMessageToTextArea(" Expecting to see you soon!! ");
			break;
		default:
			this.addMessageToTextArea(" Something is weird here.... ");
			break;		
			
		}
		
		int n = JOptionPane.showOptionDialog(new JFrame(), "Want another play?", "New Game",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (n == 0) {
			try {
				controller.restart();
				gameMessages.setText(null);
				newGame = true;
			} catch (GameError _e) {

			}
			this.setEnabled(true);
		}else{
			controller.stop();
			dispose();
			System.exit(0);
			}			
	}	

	/**
	 * Funcion que cambia el turno de los jugadores pasandole por parametros el tablero y la pieza de ese turno
	 * @param board parametro del tablero
	 * @param turn parametro de la pieza de ese turno
	 */
	private void handleChangeTurn(Board board, Piece turn) {
		this.infoTable.refresh();
		this.turn = turn;
		this.addMessageToTextArea("Turn for " + (turn.equals(localPiece) ? "You!" : turn.toString()));
		
		
		if((localPiece == null || localPiece.equals(turn) )  && playersModes.get(turn)==PlayerMode.MANUAL)
			this.setEnabled(true);
		else
			this.setEnabled(false);
		
		caseMakeAutomaticMove();
	}		
	
//-----------------------METODOS ABSTRACTOS DE CLASE--------------------------//
	/**
	 * Funcion abstracta que inicia el tablero con parametros
	 * @param ctrl parametro del controlador
	 * @param game observable del juego
	 */
	protected abstract void initBoardGui(Controller ctrl, Observable<GameObserver> game);
	
	/**
	 * Función abstracta que activa el tablero el tablero
	 */
	protected abstract void activateBoard();
	
	/**
	 * Función abstracta que desactiva el tablero el tablero
	 */
	protected abstract void deActivateBoard();
	
	/**
	 * Función abstracta que dibuja el tablero
	 */
	protected abstract void redrawBoard();	

//-----------------------MODELO DE TABLA PARA LA INFORMACION DE JUGADORES--------------------------//	
	
	
	@SuppressWarnings("serial")
	class PlayerInformationTable extends DefaultTableModel {
		
		String[] columnNames;
	/**
	 * 	Metodo que crea el panel de informacion sobre los jugadores
	 */
	public PlayerInformationTable (){
		
		this.columnNames = new String[] {"Player", "Mode", "#Pieces"};
		}
	
		/**
		 * Metodo String que nombre las columnas del panel
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		/**
		 * Metodo contador de columnas
		 */
		public int getColumnCount() {
			return columnNames.length;
		}
		
		/**
		 * Metodo contador de filas
		 */
		public int getRowCount() {
			return (pieces == null ? 0: pieces.size());
		}

		/**
		 * Metodo que genera el nombre de los jugadores, el modo en el que juega y cuantas piezas tiene en el tablero
		 */
		public Object getValueAt(int row, int col) {
			Object object = null;
			if(pieces != null){
			Piece piece = pieces.get(row);
			switch(col){
			case 0:
				object = piece;
				break;
			case 1:
				object = playersModes.get(piece);
				break;
			case 2:
				object = board.getPieceCount(piece);
				break;
			default:
				break;
			}
			}
			return object;
		}
		
		/**
		 * refresh
		 * Procedure to refresh the data in the table according to the current state of the model
		 * Procedimiento para refrescar los datos de la tabla segun el actual estado del modelo
		 */
		public void refresh(){
			this.fireTableDataChanged();
		}
	}
	
	
}
