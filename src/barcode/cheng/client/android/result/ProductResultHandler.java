package barcode.cheng.client.android.result;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import barcode.cheng.assist.Result;
import barcode.cheng.client.android.demo.R;
import barcode.cheng.result.ParsedResult;
import barcode.cheng.result.ProductParsedResult;

/**
 * Handles generic products which are not books.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ProductResultHandler extends ResultHandler {
	private static final int[] buttons = { R.string.button_product_search,
			R.string.button_web_search, R.string.button_custom_product_search };

	public ProductResultHandler(Activity activity, ParsedResult result,
			Result rawResult) {
		super(activity, result, rawResult);
		showGoogleShopperButton(new View.OnClickListener() {
			public void onClick(View view) {
				ProductParsedResult productResult = (ProductParsedResult) getResult();
				openGoogleShopper(productResult.getNormalizedProductID());
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
				ProductParsedResult productResult = (ProductParsedResult) getResult();
				switch (index) {
				case 0:
					openProductSearch(productResult.getNormalizedProductID());
					break;
				case 1:
					webSearch(productResult.getNormalizedProductID());
					break;
				case 2:
					openURL(fillInCustomSearchURL(productResult
							.getNormalizedProductID()));
					break;
				}
			}
		});
	}

	@Override
	public int getDisplayTitle() {
		return R.string.result_product;
	}
}
