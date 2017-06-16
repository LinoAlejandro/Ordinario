package modelo;

import java.util.ArrayList;

public class Table {
	
	private String name;
	private ArrayList<Column> columnas;
	
	//El valor de la propiedad determina si existio un cambió en algún otro lado en el archivo fuera de la aplicación
	private boolean changed;
	
	public Table(String name) {
		this.name = name;
		columnas = new ArrayList<Column>();
	}
	
	public Table() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Column> getColumna() {
		return columnas;
	}
	
	public void setColumna(ArrayList<Column> columna) {
		this.columnas = columna;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void addColumn(Column columna) {
		columnas.add(columna);
	}
}