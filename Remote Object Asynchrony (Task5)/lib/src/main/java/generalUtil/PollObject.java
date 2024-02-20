package generalUtil;

public class PollObject {

	private String result;
	private boolean resultAvailable;

	public PollObject() {
		this.result = null;
		this.resultAvailable = false;
	}

	public boolean isResultAvailable() {
		return resultAvailable;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
		resultAvailable = true;
	}

}
