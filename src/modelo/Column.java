package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Column<T> implements Serializable{
	
	public ArrayList<Data<T>> getDatos() {
		return datos;
	}

	public void setDatos(ArrayList<Data<T>> datos) {
		this.datos = datos;
	}

	private String nombre;
	private Tipo type;
	private ArrayList<Data<T>> datos;
	
	public Column(String nombre){
		this.nombre = nombre;
		datos = new ArrayList<Data<T>>();
	}
	
	public void addData(T dato){
		Data data = new Data<T>(dato);
		datos.add(data);
	}
	
	public Column(){
		datos = new ArrayList<Data<T>>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Tipo getType() {
		return type;
	}
	
	public void setType(Tipo string) {
		this.type = string;
	}
}
