

public class TestStrings {

	public static void main(String[] args) {
		//String string = "nice \nnice";
		//System.out.println(string);
		//String cadena = "cadena try(intercadena)";
		//System.out.println(cadena.substring(0, cadena.indexOf("(")));
		//String nice = "nice nic ni";
		//String[] cadenas = nice.split(" ");
		//System.out.println(cadenas[0]);
		/*String cadena = "nice asshat&";
		String[] subCadena = cadena.split("&");
		
		try{
			evaluar(subCadena[1]);
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println("la cagaste");
		}*/
		String regexTest = "\"nice done bro,nice\", good one";
		String[] sub = regexTest.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");
		for (String string : sub) {
			System.out.println(string);
		}
		///(,)(?=(?:[^"]|"[^"]*")*$)/
		String regex2Test = "\"\"nice bro\"\" ,\"\"'nice ass,nice ass'\"\",'98,67'";
		String[] su2b = regex2Test.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");
	}

	private static void evaluar(String cadena) {
		if(cadena == null){
			System.out.println("is null");
		} else {
			System.out.println("not null");
		}
	}
}
