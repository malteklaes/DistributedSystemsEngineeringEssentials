package Task4_server;

import Task4_util.IProtocol;

public class RemoteObject implements IProtocol {

	@Override
	public String hello(String name) {
		return ("Hello, " + name + "!");
	}

	@Override
	public String goodbye(String name) {
		return (name + ", I'm afraid this is a farewell.");
	}

}
