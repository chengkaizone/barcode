package barcode.cheng.result.optional;

import barcode.cheng.result.ParsedResult;
import barcode.cheng.result.ParsedResultType;

/**
 * @author Sean Owen
 */
public final class NDEFSmartPosterParsedResult extends ParsedResult {

	public static final int ACTION_UNSPECIFIED = -1;
	public static final int ACTION_DO = 0;
	public static final int ACTION_SAVE = 1;
	public static final int ACTION_OPEN = 2;

	private final String title;
	private final String uri;
	private final int action;

	NDEFSmartPosterParsedResult(int action, String uri, String title) {
		super(ParsedResultType.NDEF_SMART_POSTER);
		this.action = action;
		this.uri = uri;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getURI() {
		return uri;
	}

	public int getAction() {
		return action;
	}

	public String getDisplayResult() {
		if (title == null) {
			return uri;
		} else {
			return title + '\n' + uri;
		}
	}

}