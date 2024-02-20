package generalUtil;

public class RequestMessage<T> implements IMarshall {
	private final KnownMethods method;
	private final T requestData;

	public RequestMessage(KnownMethods method, T requestData) {
		super();
		this.method = method;
		this.requestData = requestData;
	}

	public T getRequestData() {
		return requestData;
	}

	public KnownMethods getMethodName() {
		return method;
	}
}
