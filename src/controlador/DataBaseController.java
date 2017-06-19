package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Accion;
import modelo.Arbol;
import modelo.Column;
import modelo.Comparators;
import modelo.Data;
import modelo.DataBase;
import modelo.Restriccion;
import modelo.Table;
import modelo.Tipo;
import modelo.exceptions.BadTableRefenrece;
import modelo.exceptions.DataBaseAlreadyExistsExceptions;
import modelo.exceptions.DbInExistanceException;
import modelo.exceptions.NoColumnFoundException;
import modelo.exceptions.NoCompatibleTypesException;
import modelo.exceptions.NoDataBaseInExistance;
import modelo.exceptions.NoDataBaseSelectedException;
import modelo.exceptions.NoTableInExistanceException;
import modelo.exceptions.NotCorrectFormatTimeException;
import modelo.exceptions.NotCorrectTypeException;
import modelo.exceptions.SyntaxErrorException;
import modelo.exceptions.TableAlreadyExistsException;
import modelo.exceptions.TypeNotSupportedException;
import modelo.types.Time;

public class DataBaseController {
	
	private DataBase databaseInUse;
	
	public DataBaseController(){ }
	
	public ArrayList<String[]> executeSelection(String string) throws SyntaxErrorException, NoDataBaseSelectedException, NoColumnFoundException, IOException, NoTableInExistanceException{
		if(databaseInUse != null){
			if(string.startsWith("from ")){
				string = string.replaceFirst("from ", "");
				ArrayList<String[]> datos = new ArrayList<>();
				if(string.contains("where")){
					String tableName = string.substring(0, string.indexOf(" where"));
					if(databaseInUse.tableExists(tableName)) {
						Table table = databaseInUse.getTabla(tableName);
						string = string.substring(string.indexOf(" where "), string.length());
						String[] acciones = string.split(" ");
						if(acciones.length > 2) {
							if(table.doesColumnExists(acciones[0])){
								Comparators comparador = null;
								switch(acciones[1]){
									case ">=":
										comparador = Comparators.MAYORIGUAL;
									break;
									case "<=":
										comparador = Comparators.MENORIGUAL;
										break;
									case ">":
										comparador = Comparators.MAYOR;
										break;
									case "<":
										comparador = Comparators.MENOR;
										break;
									case "==":
										comparador = Comparators.IGUAL;
										break;
									default:
										throw new SyntaxErrorException();
								}
								switch(comparador) {
									case IGUAL:
										
										break;
									case MAYOR:
										break;
									case MAYORIGUAL:
										break;
									case MENOR:
										break;
									case MENORIGUAL:
										break;
								}
							} else {
								throw new NoColumnFoundException();
							}
						} else {
							throw new SyntaxErrorException();
						}
					} else {
						throw new NoTableInExistanceException();
					}
				} else {
					if(!(string.contains(" "))){
						if(databaseInUse.tableExists(string)){
							File file = databaseInUse.getTabla(string).getData();
							BufferedReader br = new BufferedReader(new FileReader(file));
							for(String line; (line = br.readLine()) != null; ) {
								String[] stringcitos = line.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");
								datos.add(stringcitos);
							}
							return datos;
						} else {
							throw new NoColumnFoundException();
						}
					} else {
						throw new SyntaxErrorException();
					}
				}
			} else {
				throw new SyntaxErrorException();
			}
		} else {
			throw new NoDataBaseSelectedException();
		}
		
	}

	public Integer executeUpgrade(Accion accion, String sql) throws NoDataBaseInExistance, IOException, ClassNotFoundException, SyntaxErrorException, DataBaseAlreadyExistsExceptions, NoDataBaseSelectedException, ArrayIndexOutOfBoundsException, NumberFormatException, TableAlreadyExistsException, BadTableRefenrece, NoTableInExistanceException, NoColumnFoundException, NoCompatibleTypesException, NotCorrectFormatTimeException, TypeNotSupportedException{
		
		sql = sql.toLowerCase();
		
		switch(accion){
			case USE:
				if( sql.endsWith(";")) {
					sql = sql.replaceAll(";", "");
					File file = new File("databases/" + sql + ".db");
					if(file.exists()){
						
						Path path = file.toPath();
						FileInputStream fin;
						fin = new FileInputStream(path.toString());
						ObjectInputStream ois = new ObjectInputStream(fin);
						databaseInUse = (DataBase) ois.readObject();
						return 1;
						
					} else {
						throw new NoDataBaseInExistance();
					}
				} else {
					throw new SyntaxErrorException();
				}
				
			case CREATE:
				if(sql.startsWith("database ") && sql.endsWith(";")) {
					sql = sql.replaceAll(";", "");
					sql = sql.replaceFirst("database ", "");
					return createDatabase(sql);
				} else if(sql.startsWith("table ") && sql.endsWith(");")) {
					sql = sql.replaceFirst("table ", "");
					return createTable(sql);
				} else {
					throw new SyntaxErrorException();
				}
			case INSERT:
				return insert(sql);
			case DELETE:
				return delete(sql);
			default:
				return 0;
		}
	}
	
	private int alter(String sql) {
	}

	private Integer delete(String sql) {
	}

	private Integer insert(String sql) {
	}

	public int createDatabase(String sql) throws FileNotFoundException, IOException, DataBaseAlreadyExistsExceptions, SyntaxErrorException{
		if(!sql.contains(" ")){
			
			File file = new File("databases/" + sql + ".db");
			
			if(!file.exists()){
				ObjectOutputStream out;
				File carpeta = new File("databases/" + sql);
				carpeta.mkdirs();
				File archivo = new File("databases/" + sql + ".db");
				archivo.getParentFile().mkdirs();
				out = new ObjectOutputStream(new FileOutputStream("databases/" + sql + ".db"));
				DataBase db = new DataBase(sql);
				out.writeObject(db);
				out.close();
				    
				return 1;  
			} else {
				throw new DataBaseAlreadyExistsExceptions();
			}
		} else {
			throw new SyntaxErrorException();
		}
	}
	
	public int createTable(String sql) throws NoDataBaseSelectedException, SyntaxErrorException, TableAlreadyExistsException, BadTableRefenrece, ArrayIndexOutOfBoundsException, NumberFormatException, NoTableInExistanceException, NoColumnFoundException, NoCompatibleTypesException, NotCorrectFormatTimeException, IOException, TypeNotSupportedException{
		if(databaseInUse != null){
			if(sql.startsWith("as ")){
				sql = sql.replaceFirst("as ", "");
				if(sql.contains(" (")){
					int index = sql.indexOf(" (");
					String substring = sql.substring(0, index);
					if(!substring.contains(" ")){
						if(!databaseInUse.tableExists(substring)){
							
							File file = new File("databases" + "/" + databaseInUse.getNombre() + "/" + substring + ".csv");
							Table table = new Table(sql.substring(0, index));
							
							String[] caracteristicasTabla = sql.split("\n");
							ArrayList<Column> columnas = new ArrayList<Column>();
							
							for (int i = 1; i < caracteristicasTabla.length - 1; i++) {
								columnas.add(createColumn(caracteristicasTabla[i]));
							}
							
							table.setColumna(columnas);
							table.setData(databaseInUse.getNombre(), file);
							table.setFile(file);
							databaseInUse.addTable(table);
							return 1;
							
						} else {
							throw new TableAlreadyExistsException();
						}
					} else {
						throw new SyntaxErrorException();
					}
				} else {
					throw new SyntaxErrorException();
				}
			} else {
				if(sql.contains(" (")){
					int index = sql.indexOf(" (");
					String substring = sql.substring(0, index);
					if(!substring.contains(" ")){
						if(databaseInUse.tableExists(substring)){
							File file = new File("databases" + "/" + databaseInUse.getNombre() + "/" + substring + ".csv");
							file.mkdir();
							Table table = new Table(sql.substring(0, index));
							table.setData(databaseInUse.getNombre(), file);
							String[] caracteristicasTabla = sql.split("\n");
							ArrayList<Column> columnas = new ArrayList<Column>();
							
							for (int i = 1; i < caracteristicasTabla.length - 1; i++) {
								columnas.add(createColumn(caracteristicasTabla[i]));
							}
							
							table.setColumna(columnas);
							
							databaseInUse.addTable(table);
							return 1;
						} else {
							throw new TableAlreadyExistsException();
						}
					} else {
						throw new SyntaxErrorException();
					}
				} else {
					throw new SyntaxErrorException();
				}
			}
		} else {
			throw new NoDataBaseSelectedException();
		}
	}

	private Column createColumn(String string) throws SyntaxErrorException, BadTableRefenrece, NoTableInExistanceException, NoColumnFoundException, NoCompatibleTypesException, ArrayIndexOutOfBoundsException, NumberFormatException, NotCorrectFormatTimeException {
		String[] conf = string.split(" ");
		if(conf.length > 2){
			Column columna = new Column<>(conf[0]);
			
			switch (conf[1]) {
				case "string":
					columna.setType(Tipo.STRING);
					break;
				case "integer":
					columna.setType(Tipo.INTEGER);
					break;
				case "double":
					columna.setType(Tipo.DOUBLE);
					break;
				case "date":
					columna.setType(Tipo.DATE);
					break;
				case "time":
					columna.setType(Tipo.TIME);
					break;
				case "byte":
					columna.setType(Tipo.BYTE);
					break;
				default:
					throw new SyntaxErrorException();
			}
			boolean ends = false;
			for (int i = 2; i < conf.length; i++) {
				switch (conf[i]) {
					case "not_null":
						if(columna.isRestriccionInExistance(Restriccion.NULL)){
							throw new SyntaxErrorException();
						} else {
							columna.addRestriccion(Restriccion.NOT_NULL);
						}
						break;
					case "null":
						if(columna.isRestriccionInExistance(Restriccion.NOT_NULL)){
							throw new SyntaxErrorException();
						} else {
							columna.addRestriccion(Restriccion.NULL);
						}
						break;
					case "unique":
						if(columna.isRestriccionInExistance(Restriccion.DEFAULT)){
							throw new SyntaxErrorException();
						} else {
							columna.addRestriccion(Restriccion.UNIQUE);
						}
						break;
					case "primary_key":
						if(i > 3){
							throw new SyntaxErrorException();
						}
						columna.addRestriccion(Restriccion.PRIMARY_KEY);
						ends = true;
						break;
					case "foreign_key":
						if(i > 3){
							throw new SyntaxErrorException();
						}
						columna.addRestriccion(Restriccion.FOREIGN_KEY);
						checkForForeign(columna, conf[i+1]);
						
						ends = true;
						break;
					case "default":
						if(columna.isRestriccionInExistance(Restriccion.UNIQUE)){
							throw new SyntaxErrorException();
						} else {
							columna.addRestriccion(Restriccion.DEFAULT);
							setDefault(columna, conf[i+1]);
						}
						break;
					default:
						throw new SyntaxErrorException();
				}
				if(ends){
					break;
				}
			}
			
			return columna;
		} else {
			return null;
		}
	}

	private void setDefault(Column columna, String string) throws SyntaxErrorException, NotCorrectFormatTimeException, NumberFormatException {
		String subCadena = string.replace("(", "");
		subCadena = string.replace(")", "");
		if(!(subCadena.contains(" "))){
			Tipo tipo = columna.getType();
			Data data = null;
			switch(tipo){
				case BYTE:
					data = new Data<Byte>(new Byte(subCadena));
					break;
				case DOUBLE:
					data = new Data<Double>(new Double(subCadena));
					break;
				case DATE:
					data = new Data<LocalDate>(LocalDate.parse(subCadena));
					break;
				case INTEGER:
					data = new Data<Integer>(new Integer(subCadena));
					break;
				case STRING:
					data = new Data<String>(subCadena);
					break;
				case TIME:
					data = new Data<Time>(new Time(subCadena));
					break;
			}
			columna.setValorDefault(data);
			
		} else{
			throw new SyntaxErrorException();
		}
	}

	private void checkForForeign(Column columna, String string) throws SyntaxErrorException, NoTableInExistanceException, NoColumnFoundException, NoCompatibleTypesException {
		String[] data = string.split("(");
		if(data.length < 2){
			Table tabla = databaseInUse.getTabla(data[0]);
			Column columnaForanea = tabla.getColumn(data[1].replace(")", ""));
			if(!(columnaForanea.getType() == columna.getType())){
				throw new NoCompatibleTypesException();
			} else {
				columna.setTablaForanea(tabla);
				columna.setColumnaForanea(columnaForanea);
			}
		} else {
			throw new SyntaxErrorException();
		}
	}

	public DataBase getDatabaseInUse() {
		return databaseInUse;
	}

	public void setDatabaseInUse(DataBase databaseInUse) {
		this.databaseInUse = databaseInUse;
	}	
}
