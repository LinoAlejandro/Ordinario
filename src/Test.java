import modelo.Arbol;
import modelo.Data;
import modelo.Nodo;
import modelo.exceptions.NoSuchIdException;
import modelo.exceptions.NotCorrectFormatTimeException;
import modelo.exceptions.NotCorrectTypeException;
import modelo.exceptions.TypeNotSupportedException;
import modelo.types.Time;

public class Test {
	public static void main(String[] args) throws NotCorrectTypeException, NoSuchIdException {
		
		Arbol<Data> abb = new Arbol<>();
		
		try {
			
			abb.insertar(new Data(50));
			abb.insertar(new Data(25));
			abb.insertar(new Data(35));
			abb.insertar(new Data(60));
			abb.insertar(new Data(45));
			abb.insertar(new Data(70));
			abb.insertar(new Data(80));
			abb.insertar(new Data(90));
			
		} catch (TypeNotSupportedException e) {
			e.printStackTrace();
		}
		
		Nodo<Data> nodo = abb.getRoot();
		System.out.println();
		abb.inOrden();
		System.out.println();
		abb.eliminarNodo(new Data(50));
		abb.inOrden();
		System.out.println();
		System.out.println((((Data<StringBuffer>) nodo.getIzquierda().getContenido()).getIntegerValue()));
		System.out.println(nodo.getContenido().getIntegerValue());
		System.out.println((((Data<StringBuffer>) nodo.getDerecha().getContenido()).getIntegerValue()));
	}
}
