package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Column<T> implements Serializable{
	

	private String nombre;
	private Tipo type;
	private ArrayList<Restriccion> restricciones;
	private Table tablaForanea;
	private Column columnaForanea;
	private Data valorDefault;

	public Column(String nombre){
		this.nombre = nombre;
		restricciones = new ArrayList<Restriccion>();
	}
	
	public void addRestriccion(Restriccion restriccion){
		restricciones.add(restriccion);
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

	public ArrayList<Restriccion> getRestricciones() {
		return restricciones;
	}

	public void setRestricciones(ArrayList<Restriccion> restricciones) {
		this.restricciones = restricciones;
	}
	
	public boolean isRestriccionInExistance(Restriccion restriccionComparar){
		for (Restriccion restriccion : restricciones) {
			if(restriccion == restriccionComparar){
				return true;
			}
		}
		return false;
	}

	public Column getColumnaForanea() {
		return columnaForanea;
	}

	public void setColumnaForanea(Column columnaForanea) {
		this.columnaForanea = columnaForanea;
	}

	public Table getTablaForanea() {
		return tablaForanea;
	}

	public void setTablaForanea(Table tablaForanea) {
		this.tablaForanea = tablaForanea;
	}

	public Data getValorDefault() {
		return valorDefault;
	}

	public void setValorDefault(Data valorDefault) {
		this.valorDefault = valorDefault;
	}
}
