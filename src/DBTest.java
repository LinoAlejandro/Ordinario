import java.util.ArrayList;

import controlador.DataBaseController;
import modelo.Column;
import modelo.Data;
import modelo.DataBase;
import modelo.Restriccion;
import modelo.Table;
import modelo.Tipo;
import modelo.exceptions.DbInExistanceException;

public class DBTest {

	public static void main(String[] args) throws DbInExistanceException {
		DataBaseController controller = new DataBaseController();
		controller.createDatabase("Escuela");
		
		
		/*****            Individual Test             *****/
		DataBase db = new DataBase("Escuela");
		
		Table alumnos = new Table("Alumno");
		
		Column nombre = new Column("nombre");
		nombre.setType(Tipo.STRING);
		nombre.addRestriccion(Restriccion.NOT_NULL);
		
		Column apellido = new Column("apellido");
		apellido.setType(Tipo.STRING);
		apellido.addRestriccion(Restriccion.NOT_NULL);
		
		Column promedio = new Column("promedio");
		promedio.setType(Tipo.DOUBLE);
		promedio.addRestriccion(Restriccion.DEFAULT);
		promedio.setDefaultValue(new Data(8.2));
		
		alumnos.addColumn(nombre);
		alumnos.addColumn(apellido);
		alumnos.addColumn(promedio);
		
		Table salon = new Table("salon");
		
		Column nombreSalon = new Column("nombre");
		nombreSalon.setType(Tipo.STRING);
		nombreSalon.addRestriccion(Restriccion.NOT_NULL);
		
		Column numero = new Column("numero");
		numero.setType(Tipo.INTEGER);
		numero.addRestriccion(Restriccion.NOT_NULL);
		
		salon.addColumn(nombreSalon);
		salon.addColumn(numero);
		
		controller.executeQuery("Create database school;");
	}
}
