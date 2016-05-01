package es.ucm.fdi.tp.practica5.view.color;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class ColorChooser extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Atributo de un JColorChooser
	 */
	protected JColorChooser colorChooser;
	
	/**
	 * Atributo de Color
	 */
	protected Color color;
	
	/**
	 * Metodo que genera los botones para cambiar los colores de los jugadores; muestra la paleta de colores
	 * @param parent es un JFrame para crear la paleta de colores
	 * @param title String para nombrar el panel
	 * @param initColor los colores que muestra para elegir
	 */

	public ColorChooser(JFrame parent, String title, Color initColor) {
		super(parent, title);
		setModalityType(DEFAULT_MODALITY_TYPE);
		colorChooser = new JColorChooser(initColor == null ? Color.WHITE : initColor);

		getContentPane().add(colorChooser);

		// Create a button
		JPanel buttonPane = new JPanel();
		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				color = colorChooser.getColor();
				closeDialog();
			}
		});
		buttonPane.add(buttonOK);

		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		buttonPane.add(buttonCancel);

		// set action listener on the button
		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * Funcion que cierra los dialogos
	 */
	private void closeDialog() {
		setVisible(false);
		dispose();
	}

	/**
	 * Override the createRootPane inherited by the JDialog, to create the rootPane.
	 * Create functionality to close the window when "Escape" button is pressed
	 */	
	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

		@SuppressWarnings("serial")
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", action);
		return rootPane;
	}

	/**
	 * Metodo del Color que indica el color
	 * @return el color
	 */
	public Color getColor() {
		return color;
	}

}