package modelo;

public enum Restriccion {
	
	NOT_NULL (1),
	NULL (2),
	UNIQUE (3),
	PRIMARY_KEY(4),
	FOREIGN_KEY(5),
	DEFAULT(6),
	IDENTITY(7);
	
	private final int value;
	
	Restriccion(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
