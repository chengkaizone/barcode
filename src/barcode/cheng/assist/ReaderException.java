package barcode.cheng.assist;

public abstract class ReaderException extends Exception {

	public ReaderException() {
		// do nothing
	}

	public final Throwable fillInStackTrace() {
		return null;
	}

}