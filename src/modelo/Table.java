package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.exceptions.NoColumnFoundException;
import modelo.exceptions.NotCorrectFormatTimeException;
import modelo.exceptions.TypeNotSupportedException;
import modelo.types.Time;

public class Table implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6620896854733069330L;
	private String name;
	private ArrayList<Column> columnas;
	private File file;
	private ArrayList<File> arboles;
	
	//El valor de la propiedad determina si existio un cambió en algún otro lado en el archivo fuera de la aplicación
	private boolean changed;
	
	public Table(String name) {
		this.name = name;
		columnas = new ArrayList<Column>();
	}
	
	public Table() {
	}
	
	public Column getColumn(String nombreColumn) throws NoColumnFoundException{
		for (Column columna : columnas) {
			if(columna.getNombre().equals(nombreColumn)){
				return columna;
			}
		}
		throw new NoColumnFoundException();
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

	public File getData() {
		return file;
	}

	public void setData(String nombreBase, File data) throws IOException, NumberFormatException, TypeNotSupportedException, NotCorrectFormatTimeException {
		//",(?=(?:[^']*'[^']*')*[^']*$)"
		BufferedReader br = new BufferedReader(new FileReader(data));
		
		//generar arbol
		ArrayList<Arbol> arboles = new ArrayList<Arbol>();
	
		for (Column arbol : getColumna()) {
			arboles.add(new Arbol<>());
		}
		
		int d = 0;
		
		for(String line; (line = br.readLine()) != null; ) {
			d++;
			String[] stringcitos = line.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");
			Tipo tipo = null;
			for (int c = 0; c < getColumna().size(); c++) {
				tipo = getColumna().get(c).getType();
				switch(tipo){
					case BYTE:
						arboles.get(c).insertar(new Data<Byte>(new Byte(stringcitos[c])), d);
						break;
					case DATE:
						arboles.get(c).insertar(new Data<LocalDate>(LocalDate.parse(stringcitos[c])), d);
						break;
					case DOUBLE:
						arboles.get(c).insertar(new Data<Double>(new Double(stringcitos[c])), d);
						break;
					case INTEGER:
						arboles.get(c).insertar(new Data<Integer>(new Integer(stringcitos[c])), d);
						break;
					case STRING:
						arboles.get(c).insertar(new Data<StringBuffer>(new StringBuffer(stringcitos[c])), d);
						break;
					case TIME:
						arboles.get(c).insertar(new Data<Time>(new Time(stringcitos[c])), d);
						break;
				} 
			}   
		}
		serializarArboles(nombreBase, arboles);
		this.file = data;
	}
	
	public void setFile(File file){
		this.file = file;
	}

	private void serializarArboles(String nombreBase, ArrayList<Arbol> arboles) throws FileNotFoundException, IOException {
		ObjectOutputStream out;
		for (int c = 0; c < getColumna().size(); c++) {
			out = new ObjectOutputStream(new FileOutputStream("databases/" + nombreBase + "/" + getColumna().get(c).getNombre() + ".cl"));
			out.writeObject(getColumna().get(c));
			out.close();
		}
	}
	
	public boolean doesColumnExists(String nombre){
		for (Column columna : getColumna()) {
			if(columna.getNombre().equals(columna.getNombre())){
				return true;
			}
		}
		return false;
	}

	public void setArboles(ArrayList<File> arboles) {
		this.arboles = arboles;
	}
}