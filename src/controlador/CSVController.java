package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Arbol;
import modelo.Column;
import modelo.Csv;
import modelo.Data;
import modelo.DataBase;
import modelo.Tipo;
import modelo.exceptions.DbInExistanceException;
import modelo.exceptions.TypeNotSupportedException;
import modelo.types.Time;

public class CSVController {
	private Csv csv;
	private ArrayList<Arbol> arboles;
	
	public CSVController(File file, String nombre, ArrayList<Column> columnas) throws TypeNotSupportedException{
		cargarCsv(file, nombre, columnas);
		
	}
	
	public CSVController(){
	}
	
	public boolean cargarCsv(File file, String nombre, ArrayList<Column> columnas) throws TypeNotSupportedException{
		if(file.exists()){
			arboles = new ArrayList<Arbol>();
			csv = new Csv(nombre);
		    FileReader fr = null;
		    BufferedReader br = null;

		    try {
		       fr = new FileReader (file);
		       br = new BufferedReader(fr);

		       String linea;
		       
		       while((linea=br.readLine())!=null){
		    	   String[] lineas = linea.split(",");
		    	   
		    	   for(int c = 0; c < lineas.length; c++) {
		    		   //control type throug aplication todo
		    		   columnas.get(c).addData(lineas);
		    	   }
		       }
		    } catch(Exception e){
		       e.printStackTrace();
		    } finally{
		        try{                    
		        	if( null != fr ){   
		        		fr.close();     
		        	}
		        }catch (Exception e2){ 
		        	e2.printStackTrace();
		        }
		    }
		    
		    csv.setColumnas(columnas);
		    
		    for(Column columna : columnas){
		    	Tipo tipo = columna.getType();
		    	Arbol arbol = new Arbol();
		    	switch(tipo) {
		    		case BYTE:
		    			ArrayList<Data<Byte>> dataByte = columna.getDatos();
		    			for(Data<Byte> info : dataByte){
		    				arbol.insertar(info);
		    			}
		    		break;
		    		case TIME:
		    			ArrayList<Data<Time>> dataTime = columna.getDatos();
		    			for(Data<Time> info : dataTime){
		    				arbol.insertar(info);
		    			}
		    		break;
		    		case DOUBLE:
		    			ArrayList<Data<Double>> dataDouble = columna.getDatos();
		    			for(Data<Double> info : dataDouble){
		    				arbol.insertar(info);
		    			}
		    		break;
		    		case DATE:
		    			ArrayList<Data<LocalDate>> dataDate = columna.getDatos();
		    			for(Data<LocalDate> info : dataDate){
		    				arbol.insertar(info);
		    			}
		    		break;
		    		case INTEGER:
		    			ArrayList<Data<Integer>> dataInteger = columna.getDatos();
		    			for(Data<Integer> info : dataInteger){
		    				arbol.insertar(info);
		    			}
		    		break;
		    		case STRING:
		    			ArrayList<Data<String>> dateString = columna.getDatos();
		    			for(Data<String> info : dateString){
		    				arbol.insertar(info);
		    			}
		    		break;
		    	}
		    	
		    	arboles.add(arbol);
		    	try {
					serializarArbol(arbol, columna.getNombre());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    try {
				serializarCsv(csv);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return true;
		} else {
			return false;
		}
	}

	private void serializarArbol(Arbol arbol, String nombre) throws IOException {
		FileOutputStream fs = new FileOutputStream(csv.getNombre() + "//" + nombre + ".treeConfig");//Creamos el archivo
	    ObjectOutputStream os = new ObjectOutputStream(fs);//Esta clase tiene el método writeObject() que necesitamos
	    os.writeObject(arbol);
	    os.close();//Hay que cerrar siempre el archivo
	}

	private void serializarCsv(Csv csv) throws IOException {
		Csv datos;
		FileOutputStream fs = new FileOutputStream(csv.getNombre() + "//" + csv.getNombre() + ".configCsv");//Creamos el archivo
	    ObjectOutputStream os = new ObjectOutputStream(fs);//Esta clase tiene el método writeObject() que necesitamos
	    os.writeObject(csv);
	    os.close();//Hay que cerrar siempre el archivo
	}
}
