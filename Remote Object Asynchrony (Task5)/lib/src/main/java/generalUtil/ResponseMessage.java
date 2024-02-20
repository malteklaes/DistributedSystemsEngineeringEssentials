package generalUtil;

public class ResponseMessage<T> implements IMarshall {

	private final T responseData;

	public ResponseMessage(T responseData) {
		this.responseData = responseData;
	}

	public T getResponseData() {
		return responseData;
	}
}