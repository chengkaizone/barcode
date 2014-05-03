package barcode.cheng.result;

/**
 * A simple result type encapsulating a string that has no further
 * interpretation.
 * 
 * @author Sean Owen
 */
public final class TextParsedResult extends ParsedResult {

	private final String text;
	private final String language;

	public TextParsedResult(String text, String language) {
		super(ParsedResultType.TEXT);
		this.text = text;
		this.language = language;
	}

	public String getText() {
		return text;
	}

	public String getLanguage() {
		return language;
	}

	public String getDisplayResult() {
		return text;
	}

}
