package barcode.cheng.client.android.book;

/**
 * The underlying data for a SBC result.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class SearchBookContentsResult {

	private static String query;

	private final String pageId;
	private final String pageNumber;
	private final String snippet;
	private final boolean validSnippet;

	public SearchBookContentsResult(String pageId, String pageNumber,
			String snippet, boolean validSnippet) {
		this.pageId = pageId;
		this.pageNumber = pageNumber;
		this.snippet = snippet;
		this.validSnippet = validSnippet;
	}

	public static void setQuery(String query) {
		SearchBookContentsResult.query = query;
	}

	public String getPageId() {
		return pageId;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public String getSnippet() {
		return snippet;
	}

	public boolean getValidSnippet() {
		return validSnippet;
	}

	public static String getQuery() {
		return query;
	}
}
