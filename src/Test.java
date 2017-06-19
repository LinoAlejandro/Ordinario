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
//35
//8.33
			abb.insertar(new Data(35.0), 0);
			abb.insertar(new Data(68.02), 1);
			abb.insertar(new Data(2.99), 2);
			abb.insertar(new Data(3.99), 3);
			abb.insertar(new Data(5.94), 4);
			abb.insertar(new Data(4.95), 5);
			
			
		} catch (TypeNotSupportedException e) {
			e.printStackTrace();
		}
		
		Nodo<Data> nodo = abb.getRoot();
		System.out.println();
		abb.inOrden();
		System.out.println();
		System.out.println((((Data<StringBuffer>) nodo.getIzquierda().getContenido()).getDoubleValue()));
		System.out.println(nodo.getContenido().getDoubleValue());
		System.out.println((((Data<StringBuffer>) nodo.getDerecha().getContenido()).getDoubleValue()));
	}
}
