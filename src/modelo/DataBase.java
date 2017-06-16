package modelo;

import java.util.ArrayList;

public class DataBase {
	
	private String nombre;
	private ArrayList<Table> tablas;
	
	public DataBase(String nombre) {
		this.nombre = nombre;
		tablas = new ArrayList<Table>();
	}
	
	public DataBase(){
		
	}
	
	public void addTable(Table table){
		tablas.add(table);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public ArrayList<Table> getTablas() {
		return tablas;
	}
	
	public void setTablas(ArrayList<Table> tablas) {
		this.tablas = tablas;
	}
	
}
