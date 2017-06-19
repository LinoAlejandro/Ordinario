package modelo;

import java.io.Serializable;
import java.util.ArrayList;

import modelo.exceptions.NoTableInExistanceException;

public class DataBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9163008359223124087L;
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
	
	public Table getTabla(String nombreTabla)  throws NoTableInExistanceException{
		for (Table table : tablas) {
			if(table.getName().equals(nombreTabla)){
				return table;
			}
		}
		throw new NoTableInExistanceException();
	}
	
	public boolean tableExists(String nombreTabla){
		for (Table table : tablas) {
			if(table.getName().equals(nombreTabla)){
				return true;
			}
		}
		return false;
	}
}
