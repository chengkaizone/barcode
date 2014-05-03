package barcode.cheng.client.android.wifi;

import barcode.cheng.client.android.demo.WifiActivity.NetworkType;

/**
 * Everything we could get from the barcode is to be here
 * 
 * @author Vikram Aggarwal
 */
public final class NetworkSetting {

	/** The ancillary network setting from the barcode */
	private final NetworkType networkType;
	/** The password this ssid has */
	private final String password;
	/** The ssid we read from the barcode */
	private final String ssid;

	/**
	 * Create a new NetworkSetting object.
	 * 
	 * @param ssid
	 *            : The SSID
	 * @param password
	 *            : Password for the setting, blank if unsecured network
	 * @param networkType
	 *            : WPA for WPA/WPA2, or WEP for WEP or unsecured
	 */
	public NetworkSetting(String ssid, String password, NetworkType networkType) {
		this.ssid = ssid;
		this.password = password;
		this.networkType = networkType;
	}

	public NetworkType getNetworkType() {
		return networkType;
	}

	public String getPassword() {
		return password;
	}

	public String getSsid() {
		return ssid;
	}

}