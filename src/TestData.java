import modelo.Data;
import modelo.exceptions.NotCorrectFormatTimeException;
import modelo.exceptions.NotCorrectTypeException;
import modelo.types.Time;

public class TestData {

	public static void main(String[] args) throws NotCorrectFormatTimeException, NotCorrectTypeException {
		Data data1 = new Data<Time>(new Time("02:05:50"));
		Data data2 = new Data<Time>(new Time("02:05:50"));
	}
}
