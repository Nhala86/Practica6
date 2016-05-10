package es.ucm.fdi.tp.practica6.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Buffer de comunicacion
 * @author Kadaiser
 *
 */
public class Connection {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	/**
	 * <b>Connection</b>
	 * <p> Constructor de clase connection almacenar los streams de entrada y salida en atributos</p>
	 * @param socket
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException{
		this.socket = socket;
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
	}
	
	/**
	 * <b>sendObject</b>
	 * <p>Procedimeinto de envio de objetos</p>
	 * @param r
	 * @param piece 
	 * @param gameFactory 
	 * @throws IOException
	 */
	public void sendObject(Object r) throws IOException{
		out.writeObject(r);
		out.flush();
		out.reset();
	}
	
	/**
	 * <b>getObject</b>
	 * <p>Procedimiento de recepcion de objetos</p>
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public Object getObject() throws IOException, ClassNotFoundException{
		return in.readObject();
		
	}
	
	/**
	 * <b>stop</b>
	 * <p>Procedimiento de cierre de un canal de comunicacion</p>
	 * @throws IOException
	 */
	public void stop() throws IOException{
		this.socket.close();
	}

	public void sendObject(GameError gameError) {
		try {
			out.writeObject(gameError);
			out.flush();
			out.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
