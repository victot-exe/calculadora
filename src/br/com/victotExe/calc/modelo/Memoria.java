package br.com.victotExe.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

	private enum TipoComando {
		ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA, SINAL, PORCENTAGEM
	}

	private static final Memoria instancia = new Memoria();

	private final List<MemoriaObservador> observadores = new ArrayList<>();

	private TipoComando ultimaOperacao = null;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";

	// singleTon(padrão que permite apenas uma instância da classe
	private Memoria() {// método construtor privado
	}

	public static Memoria getInstancia() {
		return instancia;
	}

	public void AdcionarObservador(MemoriaObservador observador) {
		observadores.add(observador);

	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}

	public void processarComando(String texto) {

		TipoComando tipoComando = detectarTipoComando(texto);
		
		if(tipoComando == null) {
			return;
		} else if(tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			ultimaOperacao = null;
		} else if(tipoComando == TipoComando.SINAL && textoAtual.contains("-")) {
			textoAtual = textoAtual.substring(1);//método que vai remover o indice 0
		} else if(tipoComando == TipoComando.SINAL && !textoAtual.contains("-")) {
			textoAtual = "-" + textoAtual;
//		} else if(tipoComando == TipoComando.PORCENTAGEM) {
//			textoBuffer = textoAtual;
		} else if(tipoComando == TipoComando.NUMERO
				|| tipoComando == TipoComando.VIRGULA) {
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
		} else if(tipoComando == TipoComando.PORCENTAGEM) {
			textoAtual = obterResultadoOperacao();
			double porCento = Double.parseDouble(textoAtual) / 100;
			textoAtual = Double.toString(porCento);
			substituir = true;
			
		} else {//sobraram apenas as operações
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
		
		
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		
		double numeroBuffer =
				Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual =
				Double.parseDouble(textoAtual.replace(",", "."));
		
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		} else if(ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		} else if(ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		} else if(ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
//		} else if(ultimaOperacao == TipoComando.PORCENTAGEM) {
//			
		}
		
		String texto = Double.toString(resultado).replace(".", ",");
		boolean inteiro = texto.endsWith(",0");
		return inteiro ? texto.replace(",0", "")
				: texto;
	}

	private TipoComando detectarTipoComando(String texto) {

		if (textoAtual.isEmpty() && texto == "0") {
			return null;
		}

		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			// Quando não for numero...
			if ("AC".equals(texto)) {
				return TipoComando.ZERAR;
			} else if ("/".equals(texto)) {
				return TipoComando.DIV;
			} else if ("*".equals(texto)) {
				return TipoComando.MULT;
			} else if ("-".equals(texto)) {
				return TipoComando.SUB;
			} else if ("+".equals(texto)) {
				return TipoComando.SOMA;
			} else if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			} else if (",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}else if ("±".equals(texto)) {
				return TipoComando.SINAL;
			}else if ("%".equals(texto)) {
				return TipoComando.PORCENTAGEM;
			}
		}

		return null;
	}
}
