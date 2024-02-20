package Task4_exceptions;

public class InvalidMethodName extends GenericRemoteException {

	public InvalidMethodName(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
