package br.com.victotExe.calc.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame{
	
	public Calculadora() {
		organizarLayout();
		
		setSize(232, 322);
//		setUndecorated(true);//sumir com a barra de cima(close, min e max)
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//fecha a tela atual
		setDefaultCloseOperation(EXIT_ON_CLOSE);//fecha a aplicação
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void organizarLayout() {
		setLayout(new BorderLayout());//organiza o layout em North, South, East e West.
		
		Display display = new Display();
		display.setPreferredSize(new Dimension(233, 60));
		add(display, BorderLayout.NORTH);
		
		Teclado teclado = new Teclado();
		add(teclado, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new Calculadora();
	}
}
