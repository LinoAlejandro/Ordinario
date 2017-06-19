import java.io.IOException;
import java.util.ArrayList;

import controlador.DataBaseController;
import modelo.Accion;
import modelo.Column;
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
import modelo.exceptions.SyntaxErrorException;
import modelo.exceptions.TableAlreadyExistsException;
import modelo.exceptions.TypeNotSupportedException;

public class DBTest {

	public static void main(String[] args) throws DbInExistanceException, ClassNotFoundException, NoDataBaseInExistance, IOException, SyntaxErrorException, DataBaseAlreadyExistsExceptions, NoDataBaseSelectedException, ArrayIndexOutOfBoundsException, NumberFormatException, TableAlreadyExistsException, BadTableRefenrece, NoTableInExistanceException, NoColumnFoundException, NoCompatibleTypesException, NotCorrectFormatTimeException, TypeNotSupportedException {
		DataBaseController controller = new DataBaseController();
		//int total = controller.executeUpgrade(Accion.CREATE, "database sample;"); 
		
		int total = controller.executeUpgrade(Accion.USE, "sample;");
		
		total = controller.executeUpgrade(Accion.CREATE, "table as Alumno (\n" + 
															"id integer not_null\n" +
															"dato2 string not_null\n" +
															"dato3 string not_null\n" +
															"dato4 integer not_null\n" +
															"dato5 double not_null\n" +
															"dato6 double not_null\n" +
															"dato7 double not_null\n" +
															"dato8 string not_null\n" +
															"dato9 string not_null\n" +
															"dato10 double not_null\n" +
															");");
		System.out.println(total);
		//int total = controller.executeUpgrade(Accion.USE, "school;");
		//System.out.println(total);		
		//total = controller.executeUpgrade(Accion.CREATE, "table alumnos()");
		//System.out.println(total);
	}
}
