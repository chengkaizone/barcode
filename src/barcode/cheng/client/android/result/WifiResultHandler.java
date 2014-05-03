package barcode.cheng.client.android.result;

import android.app.Activity;
import barcode.cheng.client.android.demo.R;
import barcode.cheng.result.ParsedResult;
import barcode.cheng.result.WifiParsedResult;

/**
 * Handles address book entries.
 * 
 * @author viki@google.com (Vikram Aggarwal)
 */
public final class WifiResultHandler extends ResultHandler {

	private final Activity parent;

	public WifiResultHandler(Activity activity, ParsedResult result) {
		super(activity, result);
		parent = activity;
	}

	@Override
	public int getButtonCount() {
		// We just need one button, and that is to configure the wireless. This
		// could change in the future.
		return 1;
	}

	@Override
	public int getButtonText(int index) {
		if (index == 0) {
			return R.string.button_wifi;
		}
		throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public void handleButtonPress(int index) {
		// Get the underlying wifi config
		WifiParsedResult wifiResult = (WifiParsedResult) getResult();
		if (index == 0) {
			wifiConnect(wifiResult);
		}
	}

	// Display the name of the network and the network type to the user.
	@Override
	public CharSequence getDisplayContents() {
		WifiParsedResult wifiResult = (WifiParsedResult) getResult();
		StringBuffer contents = new StringBuffer(50);
		String wifiLabel = parent.getString(R.string.wifi_ssid_label);
		ParsedResult.maybeAppend(wifiLabel + '\n' + wifiResult.getSsid(),
				contents);
		String typeLabel = parent.getString(R.string.wifi_type_label);
		ParsedResult.maybeAppend(
				typeLabel + '\n' + wifiResult.getNetworkEncryption(), contents);
		return contents.toString();
	}

	@Override
	public int getDisplayTitle() {
		return R.string.result_wifi;
	}
}