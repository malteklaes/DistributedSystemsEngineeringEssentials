package Task4_exceptions;

import java.io.IOException;

public class GenericRemoteException extends IOException {

	private static final long serialVersionUID = 1L;
	private final String errorName;

	/**
	 * describes the error with a name and a short description
	 * 
	 * @param errorName
	 * @param errorMessage
	 */
	public GenericRemoteException(String errorName, String errorMessage) {
		super(errorMessage);
		this.errorName = errorName;
	}

	public String getErrorName() {
		return errorName;
	}

}
