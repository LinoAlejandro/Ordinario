package modelo;

import java.io.Serializable;

public class Nodo<T> implements Serializable{
	private T contenido;
	private int peso;
	private Nodo<T> derecha,izquierda;
	private int lineaArchivo;
	
	public Nodo(T contenido){
		this.contenido = contenido;
		peso = 0;
		lineaArchivo = 0;
	}
	
	public T getContenido() {
		return contenido;
	}
	
	public void setContenido(T contenido) {
		this.contenido = contenido;
	}
	
	public int getPeso() {
		return peso;
	}
	
	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Nodo getDerecha() {
		return derecha;
	}

	public void setDerecha(Nodo derecha) {
		this.derecha = derecha;
	}

	public Nodo getIzquierda() {
		return izquierda;
	}

	public void setIzquierda(Nodo izquierda) {
		this.izquierda = izquierda;
	}

	public int getLineaArchivo() {
		return lineaArchivo;
	}

	public void setLineaArchivo(int lineaArchivo) {
		this.lineaArchivo = lineaArchivo;
	}

	public boolean isLeaf() {
		return (this.getDerecha() == null && this.getIzquierda() == null);
	}
}
