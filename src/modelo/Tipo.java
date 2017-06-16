package modelo;

public enum Tipo {
	STRING(1),
	INTEGER(2),
	DOUBLE(3),
	DATE(4),
	TIME(5),
	BYTE(6);
	
	Tipo(int value){
		this.value = value;
	}
	
	private final int value;

	public int getValue() {
		return value;
	}
}
