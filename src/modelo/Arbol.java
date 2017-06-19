package modelo;

import java.io.Serializable;
import java.io.ObjectInputStream.GetField;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.security.auth.login.AccountException;

import modelo.exceptions.NoSuchIdException;
import modelo.exceptions.NotCorrectTypeException;
import modelo.exceptions.TypeNotSupportedException;
import modelo.types.Time;;

public class Arbol<T> implements Serializable{
	
	private Nodo<T> root;
	
	public void insertar(Data data, int noFila) throws TypeNotSupportedException {
		T datos = (T) data.getInternalData();
		
		if(datos instanceof StringBuffer){
			StringBuffer sb = (StringBuffer)data.getInternalData();
			insertarData(sb, noFila);
		} else {
			insertarData(data, noFila);
		}
	}
	
	private void insertarData(Data data, int noFila) {
		
		Nodo<Data> nodo = new Nodo<Data>(data);
		nodo.setLineaArchivo(noFila);
		
		if(isVacio()){
			this.setRoot((Nodo<T>) nodo);
		} else {
			Nodo<Data> aux = (Nodo<Data>) this.root;
			Nodo<Data> ant = null;
			
			while(aux != null) {
				ant = aux;
				if(data.getValue() > aux.getContenido().getValue())
					aux = aux.getDerecha();
				else 
					aux = aux.getIzquierda();
				
			}
			
			if(data.getValue() > ant.getContenido().getValue())
				ant.setDerecha(nodo);
			else 
				ant.setIzquierda(nodo);
			
			balancear();
		}
	}
	
	public void insertarData(StringBuffer sb, int noFila) {
		Nodo<Data> nodo = new Nodo<Data>(new Data(sb));
		nodo.setLineaArchivo(noFila);
		if(isVacio()){
			this.setRoot((Nodo<T>) nodo);
		} else {
			String texto = sb.toString();
			Nodo<Data> aux = (Nodo<Data>) this.root;
			Nodo<Data> ant = null;
			
			while(aux != null) {
				ant = aux;
				if(texto.compareTo(aux.getContenido().toString()) > 0)
					aux = aux.getDerecha();
				else
					aux = aux.getIzquierda();
			}
		
			if(texto.compareTo(ant.getContenido().toString()) > 0) 
				ant.setDerecha(nodo);
			else 
				ant.setIzquierda(nodo);
			
			balancear();
		}
	}
	
	private void balancear() {
		setPesos();
		while(!isBalanceado()){
			Nodo<T> nodo = this.obtenerDesbalanceado();
			balancear(nodo);
			setPesos();
		}
	}
	
	private void balancear(Nodo<T> nodo){
		
		if(getGrado(nodo) > 0){
			//inclinado a la derecha
			//tres escenarios
			
			if(nodo.getDerecha().getIzquierda() == null) {;
				balancearDerechaEnLinea(nodo);
			} else {
				if(nodo.getDerecha().getIzquierda() != null && nodo.getDerecha().getDerecha() != null) {
					if(nodo.getDerecha().getIzquierda().isLeaf()){
						balancearDerechaHijos(nodo);
					} else {
						balancearDerechaHijosSegundaOpcion(nodo);
					}
				} else {
					balancearDerechaEnZigZag(nodo);
				}
			}
			
		} else {
			//inclinado a la izquierda
			//tres escenarios
			if(nodo.getIzquierda().getDerecha() == null) {
				balancearIzquierdaEnLinea(nodo);
			} else {
				if(nodo.getIzquierda().getDerecha() != null && nodo.getIzquierda().getIzquierda() != null){
					if(nodo.getIzquierda().getDerecha().isLeaf()){
						balancearIzquierdaHijos(nodo);
					} else {
						balancearIzquierdaHijosSegundaOpcion(nodo);
					}
				} else {
					balancearIzquierdaZigZag(nodo);
				}
			}
		}
	}

	private void balancearDerechaHijosSegundaOpcion(Nodo<T> nodo) {
		if(nodo.getDerecha().getIzquierda().getIzquierda() != null){
			Nodo nodoPorMover = nodo.getDerecha().getIzquierda().getIzquierda();
			
			nodoPorMover.setIzquierda(nodo.getDerecha());
			T contenido = nodo.getContenido();
			nodo.getIzquierda().getIzquierda().setIzquierda(null);
			
			nodo.setContenido((T) nodoPorMover.getContenido());
			nodoPorMover.setContenido(contenido);
			
			nodo.setIzquierda(nodoPorMover);
			
		} else {
			
			Nodo nodoPorMover = nodo.getDerecha().getIzquierda().getDerecha();
			Nodo ant = nodo.getDerecha().getIzquierda();
			
			T contenido = (T) ant.getContenido();
			ant.setContenido(nodoPorMover.getContenido());
			nodoPorMover.setContenido(contenido);
			
			nodoPorMover.setIzquierda(nodo.getDerecha());
			T content = nodo.getContenido();
			nodo.getDerecha().getIzquierda().setDerecha(null);
			
			nodo.setContenido((T) nodoPorMover.getContenido());
			nodoPorMover.setContenido(content);
			
			nodo.setIzquierda(nodoPorMover);
		}
	}

	private void balancearIzquierdaHijosSegundaOpcion(Nodo<T> nodo) {
		if(nodo.getIzquierda().getDerecha().getDerecha() != null){
			Nodo nodoPorMover = nodo.getIzquierda().getDerecha().getDerecha();
			
			nodoPorMover.setDerecha(nodo.getDerecha());
			T contenido = nodo.getContenido();
			nodo.getIzquierda().getDerecha().setDerecha(null);
			
			nodo.setContenido((T) nodoPorMover.getContenido());
			nodoPorMover.setContenido(contenido);
			
			nodo.setDerecha(nodoPorMover);
			
		} else {
			
			Nodo nodoPorMover = nodo.getIzquierda().getDerecha().getIzquierda();
			Nodo ant = nodo.getIzquierda().getDerecha();
			
			T contenido = (T) ant.getContenido();
			ant.setContenido(nodoPorMover.getContenido());
			nodoPorMover.setContenido(contenido);
			
			nodoPorMover.setDerecha(nodo.getDerecha());
			T content = nodo.getContenido();
			nodo.getIzquierda().getDerecha().setIzquierda(null);
			
			nodo.setContenido((T) nodoPorMover.getContenido());
			nodoPorMover.setContenido(content);
			
			nodo.setDerecha(nodoPorMover);
		}
	}

	private void balancearIzquierdaHijos(Nodo<T> nodo){
		//to do
		Nodo<T> hijo = nodo.getIzquierda().getDerecha();
		
		//desprender hijo
		nodo.getIzquierda().setDerecha(null);
		balancearIzquierdaEnLinea(nodo);
		nodo.getDerecha().setIzquierda(hijo);
	}

	private void balancearIzquierdaZigZag(Nodo<T> nodo){
		Nodo<T> nodoIzquierda = nodo.getIzquierda();
		Nodo<T> nodoIzquierdaDerecha = nodoIzquierda.getDerecha();
		
		T dato = nodoIzquierda.getContenido();
		
		nodoIzquierda.setContenido(nodoIzquierdaDerecha.getContenido());
		nodoIzquierdaDerecha.setContenido(dato);
		
		nodoIzquierda.setDerecha(null);
		nodoIzquierda.setIzquierda(nodoIzquierdaDerecha);
		
		balancearIzquierdaEnLinea(nodo);
	}
	
	private void balancearIzquierdaEnLinea(Nodo<T> nodo) {
		intercambiarValoresIzquierda(nodo, nodo.getIzquierda());
	}
	
	private void balancearDerechaHijos(Nodo<T> nodo) {
		Nodo<Integer> nodoHijoIzquierda = nodo.getDerecha().getIzquierda();
		
		//Desprender hijo
		nodo.getDerecha().setIzquierda(null);
		
		balancearDerechaEnLinea(nodo);
		
		nodo.getIzquierda().setDerecha(nodoHijoIzquierda);
	}

	private void balancearDerechaEnZigZag(Nodo<T> nodo) {
		Nodo<T> nodoDerecha = nodo.getDerecha();
		Nodo<T> nodoDerechaIzquierda = nodoDerecha.getIzquierda();
		
		T aux = nodoDerecha.getContenido();
		nodoDerecha.setContenido(nodoDerechaIzquierda.getContenido());
		nodoDerechaIzquierda.setContenido(aux);
		
		nodoDerecha.setIzquierda(null);
		nodoDerecha.setDerecha(nodoDerechaIzquierda);
		
		balancearDerechaEnLinea(nodo);
	}

	private void balancearDerechaEnLinea(Nodo<T> nodo) {
		intercambiarValoresDerecha(nodo, nodo.getDerecha());
	}
	
	private void intercambiarValoresDerecha(Nodo<T> padre, Nodo<T> hijo){
		T contenidoPadre = padre.getContenido();
		Nodo<T> nodoIzquierdaPadre = padre.getIzquierda();
		padre.setDerecha(null);
		
		Nodo<T> nodoNuevo = new Nodo<T>(contenidoPadre);
		nodoNuevo.setIzquierda(nodoIzquierdaPadre);
		
		T contenidoHijo = hijo.getContenido();
		Nodo<Integer> nodoDerechaHijo = hijo.getDerecha();
		
		padre.setContenido(contenidoHijo);
		padre.setDerecha(nodoDerechaHijo);
		padre.setIzquierda(nodoNuevo);
	}
	
	private void intercambiarValoresIzquierda(Nodo<T> padre, Nodo<T> hijo){
		T contenidoPadre = padre.getContenido();
		Nodo<T> nodoDerechaPadre = padre.getDerecha();
		padre.setIzquierda(null);
		
		Nodo<T> nodoNuevo = new Nodo<T>(contenidoPadre);
		nodoNuevo.setDerecha(nodoDerechaPadre);
		
		T contenidoHijo = hijo.getContenido();
		Nodo<T> nodoIzquierdaHijo = hijo.getIzquierda();
		
		padre.setContenido(contenidoHijo);
		padre.setIzquierda(nodoIzquierdaHijo);
		padre.setDerecha(nodoNuevo);
	}
	
	public void eliminarNodo(Data data) throws NoSuchIdException, NotCorrectTypeException{
		if(data.getInternalData() instanceof StringBuffer)
			eliminarString((Nodo<Data>) root, data);
		else 
			eliminarData((Nodo<Data>) root, data);
		
		balancear();
	}
	
	private void eliminarString(Nodo<Data> node, Data data) throws NoSuchIdException, NotCorrectTypeException {
		Nodo<Data> ant = null;
		String texto = data.getStringBufferValue().toString();
		while(node != null && !node.getContenido().getStringBufferValue().equals(data.getStringBufferValue()) ){
			ant = node;
			if(texto.compareTo(node.getContenido().getStringBufferValue().toString()) > 0){
				node = node.getDerecha();
			} else {
				node = node.getIzquierda();
			}
		}
		
		if(node != null){
			if(node.isLeaf()){
				eliminarHoja(ant, node);
			} else if(node.getDerecha() != null && node.getIzquierda() != null){
				eliminarConDosHijos(node);
			} else if((node.getDerecha() == null && node.getIzquierda() != null) || (node.getDerecha() != null && node.getIzquierda() == null)){
				eliminarConHijo(node);
			}
			
		} else {
			
			throw new NoSuchIdException();
		}
	}

	private void eliminarData(Nodo<Data> node, Data data) throws NoSuchIdException{
		
		Nodo<Data> ant = null;
		
		while(node != null && node.getContenido().getValue() != data.getValue()){
			ant = node;
			if(data.getValue() > node.getContenido().getValue()){
				node = node.getDerecha();
			} else {
				node = node.getIzquierda();
			}
		}
		
		if(node != null){
			if(node.isLeaf()){
				eliminarHoja(ant, node);
			} else if(node.getDerecha() != null && node.getIzquierda() != null) {
				eliminarConDosHijos(node);
			} else if((node.getDerecha() == null && node.getIzquierda() != null) || (node.getDerecha() != null && node.getIzquierda() == null)) {
				eliminarConHijo(node);
			}
			
		} else {
			throw new NoSuchIdException();
		}
	}

	private void eliminarConDosHijos(Nodo<Data> node) {
		ArrayList<Nodo<Data>> aux = getMinRight(node);
		node.setContenido(aux.get(0).getContenido());
		if(aux.get(0).isLeaf()){
			eliminarHoja(aux.get(1), aux.get(0));
		} else if((aux.get(0).getDerecha() == null && aux.get(0).getIzquierda() != null) || (aux.get(0).getDerecha() != null && aux.get(0).getIzquierda() == null)){
			eliminarConHijo(aux.get(0));
		}
	}
	
	private ArrayList<Nodo<Data>> getMinRight(Nodo<Data> node){
		Nodo<Data> son = node.getDerecha();
		Nodo<Data> father = node;
		
		while(son.getIzquierda() != null){
			father = son;
			son = son.getIzquierda();
		}
		
		ArrayList<Nodo<Data>> nodes = new ArrayList<Nodo<Data>>();
		nodes.add(son);
		nodes.add(father);
		return nodes; 
	}

	private void eliminarConHijo(Nodo<Data> node){
		Nodo<Data> nodoAuxiliar = null;
		if(node.getDerecha() != null){
			nodoAuxiliar = node.getDerecha();
		} else {
			nodoAuxiliar = node.getIzquierda();
		}
		
		Data datos = nodoAuxiliar.getContenido();
		node.setContenido(datos);
		node.setDerecha(nodoAuxiliar.getDerecha());
		node.setIzquierda(nodoAuxiliar.getIzquierda());
	}
	
	private void eliminarHoja(Nodo<Data> father, Nodo<Data> son){
		if(father != null){
			if(father.getDerecha() == son){
				father.setDerecha(null);
			} else {
				father.setIzquierda(null);
			}
		} else {
			setRoot(null);
		}
	}
	
	public boolean isVacio(){
		return root == null;
	}
	
	public int pesoNodo(Nodo nodo){
		if(nodo == null){
			return 0;
		} else {
			return 1 + Math.max(pesoNodo(nodo.getDerecha()), pesoNodo(nodo.getIzquierda()));
		}
	}
	
	public void setPesos(){
		setPesos(this.root);
	}
	
	public void setPesos(Nodo nodo){
		if(nodo != null){
			nodo.setPeso(pesoNodo(nodo));
			setPesos(nodo.getDerecha());
			setPesos(nodo.getIzquierda());
		}
	}
	
	public boolean isBalanceado(){
		Nodo<T> nodo = escanear();
		//checar otros camino
		if(nodo != null)
			nodo = this.getDesbalanceado(nodo);
		return nodo == null;
	}
	
	public Nodo<T> obtenerDesbalanceado(){
		Nodo<T> nodo= this.escanear();
		if(nodo != null)
			nodo = this.getDesbalanceado(nodo); 
		return nodo;
	}
	
	public Nodo<T> escanear(){
		return this.escanear(this.root);
	}
	
	public Nodo<T> escanear(Nodo<T> nodo){
		
		if(nodo != null){
			if(this.getGrado(nodo) >= 2 || this.getGrado(nodo) <= -2)
				return nodo;
			else {
				Nodo<T> derecha = escanear(nodo.getDerecha());
				Nodo<T> izquierda = escanear(nodo.getIzquierda());
				
				if(derecha != null){
					return derecha;
				} else if(izquierda != null){
					return izquierda;
				} else {
					return null;
				}
			}
		}
		
		return null;
	}
	
	public Nodo<T> getDesbalanceado(Nodo<T> nodo){
		
		Nodo<T> nodoAux = getNodoDesbalanceado(nodo);
		
		Nodo<T> ant = null;
		int grado = getGrado(nodoAux);
		
		while(nodoAux != null && (grado >= 2 || grado <= -2)){
			if(grado > 0){
				ant = nodoAux;
				nodoAux = nodoAux.getDerecha();
			} else if(grado < 0){
				ant = nodoAux;
				nodoAux = nodoAux.getIzquierda();
			}
			grado = nodoAux == null ? 0 : getGrado(nodoAux);
		}
		
		if(ant == null){
			
			return nodoAux;
			
		} else {
			
			return ant;
			
		}
	}

	private Nodo<T> getNodoDesbalanceado(Nodo<T> nodo) {
		
		if(nodo == null){
			
			return null;
			
		} else {
			
			int grado = getGrado(nodo);
			
			if(!(grado ==  0 || grado == -1 || grado == 1)){
				
				return nodo;
				
			} else {
				
				Nodo<T> aux = getNodoDesbalanceado(nodo.getDerecha());
				Nodo<T> aux2 = getNodoDesbalanceado(nodo.getIzquierda());
				
				if(aux != null){
					return aux;
				}
				
				if(aux2 != null){
					return aux2;
				}
				
			}
		}
		
		return null;
	}

	public int getGrado(Nodo<T> nodo) {
		
		if(nodo != null){
			
			int izquierdaPeso = nodo.getIzquierda() == null ? 0 : nodo.getIzquierda().getPeso();
			int derechaPeso = nodo.getDerecha() == null ? 0 : nodo.getDerecha().getPeso();
			
			return derechaPeso - izquierdaPeso;
			
		} else {
			return 0;
		}
	}

	public void inOrden() throws NotCorrectTypeException {
		inOrden((Nodo<Data>) this.root);
	}
	
	private void inOrden(Nodo<Data> nodo) throws NotCorrectTypeException{
		if(!(nodo == null)){
			inOrden(nodo.getIzquierda());
			if(nodo.getContenido().getInternalData() instanceof StringBuffer){
				System.out.println(nodo.getContenido().getStringBufferValue());
			} else {
				System.out.println(nodo.getContenido().getValue());
			}
			inOrden(nodo.getDerecha());
		}
	}
	
	
	public Nodo<T> getRoot() {
		return root;
	}

	public void setRoot(Nodo<T> root) {
		this.root = root;
	}
}
