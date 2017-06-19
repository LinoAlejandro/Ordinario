package modelo;

public enum Accion {
	USE(1),
	CREATE(2),
	ALTER(3),
	INSERT(4),
	DELETE(5),
	SELECT(6);
	
	private final int value;
	
	Accion(int value){
		this.value = value;
		
	}
	
	public int getValue() {
		return value;
	}
}
