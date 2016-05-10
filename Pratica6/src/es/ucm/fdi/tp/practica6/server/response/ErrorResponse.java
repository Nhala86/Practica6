package es.ucm.fdi.tp.practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.practica6.server.response.Response;

public class ErrorResponse implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	public ErrorResponse(String msg) {
		this.msg = msg;
	}

	@Override
	public void rum(GameObserver o) {
		o.onError(msg);		
	}

}
