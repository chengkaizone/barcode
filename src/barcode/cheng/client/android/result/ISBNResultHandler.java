package barcode.cheng.client.android.result;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import barcode.cheng.assist.Result;
import barcode.cheng.client.android.demo.R;
import barcode.cheng.result.ISBNParsedResult;
import barcode.cheng.result.ParsedResult;

/**
 * Handles books encoded by their ISBN values.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ISBNResultHandler extends ResultHandler {
	private static final int[] buttons = { R.string.button_product_search,
			R.string.button_book_search, R.string.button_search_book_contents,
			R.string.button_custom_product_search };

	public ISBNResultHandler(Activity activity, ParsedResult result,
			Result rawResult) {
		super(activity, result, rawResult);
		showGoogleShopperButton(new View.OnClickListener() {
			public void onClick(View view) {
				ISBNParsedResult isbnResult = (ISBNParsedResult) getResult();
				openGoogleShopper(isbnResult.getISBN());
			}
		});
	}

	@Override
	public int getButtonCount() {
		return hasCustomProductSearch() ? buttons.length : buttons.length - 1;
	}

	@Override
	public int getButtonText(int index) {
		return buttons[index];
	}

	@Override
	public void handleButtonPress(final int index) {
		showNotOurResults(index, new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				ISBNParsedResult isbnResult = (ISBNParsedResult) getResult();
				switch (index) {
				case 0:
					openProductSearch(isbnResult.getISBN());
					break;
				case 1:
					openBookSearch(isbnResult.getISBN());
					break;
				case 2:
					searchBookContents(isbnResult.getISBN());
					break;
				case 3:
					openURL(fillInCustomSearchURL(isbnResult.getISBN()));
					break;
				}
			}
		});
	}

	@Override
	public int getDisplayTitle() {
		return R.string.result_isbn;
	}
}
