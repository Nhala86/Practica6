package es.ucm.fdi.tp.practica6;

import java.lang.reflect.InvocationTargetException;

import es.ucm.fdi.tp.Main;

public class Test1 {
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		String[] as = { "-am", "client", "-aialg", "minmax", "-md","5" };
		Main.main(as);
		}
}
