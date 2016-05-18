package es.ucm.fdi.tp.practica6.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

	private Socket socket;
	//private PrintStream out;
	//private Scanner in;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	/**	 
	 * Constructor de clase connection almacenar los streams de entrada y salida en atributos
	 * @param socket
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException{
		this.socket = socket;		
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
	}
	
	/**	
	 * Procedimeinto de envio de objetos
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
	 * Procedimiento de recepcion de objetos
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public Object getObject() throws IOException, ClassNotFoundException{
		return in.readObject();		
	}
	
	/**	 
	 * Procedimiento de cierre de un canal de comunicacion
	 * @throws IOException
	 */
	public void stop() throws IOException{
		this.socket.close();
	}
	
}
