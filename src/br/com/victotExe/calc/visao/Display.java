package br.com.victotExe.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;//componete que agrupa outros componentes

import br.com.victotExe.calc.modelo.Memoria;

@SuppressWarnings("serial")
public class Display extends JPanel{
//	TODO pesquisar como diminuir a letra automaticamente
	private final JLabel label;//atributo que exibe o resultado
	
	public Display() {
		setBackground(new Color(46, 49, 50));
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("courier", Font.PLAIN, 30));
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
		
		add(label);
	}
}
