package es.ucm.fdi.tp.practica6;

import java.lang.reflect.InvocationTargetException;

import es.ucm.fdi.tp.Main;

public class Test2 {
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		String[] as = { "-am", "server", "-g", "at", "-p","X,O" };
		Main.main(as);
		}
}
