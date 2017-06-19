package modelo;

public enum Comparators {
	MAYORIGUAL(">="),
	MENORIGUAL(">="),
	MENOR("<"),
	MAYOR(">"),
	IGUAL("==");
	
	private final String value;
	
	Comparators(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
