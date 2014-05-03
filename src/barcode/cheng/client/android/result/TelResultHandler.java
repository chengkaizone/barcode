package barcode.cheng.client.android.result;

import android.app.Activity;
import android.telephony.PhoneNumberUtils;
import barcode.cheng.client.android.demo.R;
import barcode.cheng.result.ParsedResult;
import barcode.cheng.result.TelParsedResult;

/**
 * Offers relevant actions for telephone numbers.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class TelResultHandler extends ResultHandler {
	private static final int[] buttons = { R.string.button_dial,
			R.string.button_add_contact };

	public TelResultHandler(Activity activity, ParsedResult result) {
		super(activity, result);
	}

	@Override
	public int getButtonCount() {
		return buttons.length;
	}

	@Override
	public int getButtonText(int index) {
		return buttons[index];
	}

	@Override
	public void handleButtonPress(int index) {
		TelParsedResult telResult = (TelParsedResult) getResult();
		switch (index) {
		case 0:
			dialPhoneFromUri(telResult.getTelURI());
			break;
		case 1:
			String[] numbers = new String[1];
			numbers[0] = telResult.getNumber();
			addContact(null, numbers, null, null, null, null, null);
			break;
		}
	}

	// Overriden so we can take advantage of Android's phone number hyphenation
	// routines.
	@Override
	public CharSequence getDisplayContents() {
		String contents = getResult().getDisplayResult();
		contents = contents.replace("\r", "");
		return PhoneNumberUtils.formatNumber(contents);
	}

	@Override
	public int getDisplayTitle() {
		return R.string.result_tel;
	}
}
