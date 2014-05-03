package barcode.cheng.client.android.result;

import android.app.Activity;
import barcode.cheng.client.android.demo.R;
import barcode.cheng.result.ParsedResult;
import barcode.cheng.result.URIParsedResult;

/**
 * Offers appropriate actions for URLS.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class URIResultHandler extends ResultHandler {

	private static final int[] buttons = { R.string.button_open_browser,
			R.string.button_share_by_email, R.string.button_share_by_sms,
			R.string.button_search_book_contents, };

	public URIResultHandler(Activity activity, ParsedResult result) {
		super(activity, result);
	}

	@Override
	public int getButtonCount() {
		return isGoogleBooksURI() ? buttons.length : buttons.length - 1;
	}

	@Override
	public int getButtonText(int index) {
		return buttons[index];
	}

	@Override
	public void handleButtonPress(int index) {
		URIParsedResult uriResult = (URIParsedResult) getResult();
		String uri = uriResult.getURI();
		switch (index) {
		case 0:
			openURL(uri);
			break;
		case 1:
			shareByEmail(uri);
			break;
		case 2:
			shareBySMS(uri);
			break;
		case 3:
			searchBookContents(uri);
			break;
		}
	}

	@Override
	public int getDisplayTitle() {
		return R.string.result_uri;
	}

	private boolean isGoogleBooksURI() {
		return ((URIParsedResult) getResult()).getURI().startsWith(
				"http://google.com/books?id=");
	}

}
