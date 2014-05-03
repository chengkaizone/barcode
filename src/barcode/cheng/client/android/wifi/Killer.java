package barcode.cheng.client.android.wifi;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import barcode.cheng.client.android.demo.R;

/**
 * Close the parent after a delay.
 * 
 * @author Vikram Aggarwal
 */
public final class Killer implements Runnable {

	// Three full seconds
	private static final long DELAY_MS = 3 * 1000L;

	private final Activity parent;

	public Killer(Activity parent) {
		this.parent = parent;
	}

	public void launchIntent(Intent intent) {
		if (intent != null) {
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			try {
				parent.startActivity(intent);
			} catch (ActivityNotFoundException e) {
				AlertDialog.Builder builder = new AlertDialog.Builder(parent);
				builder.setTitle(R.string.app_name);
				builder.setMessage(R.string.msg_intent_failed);
				builder.setPositiveButton(R.string.button_ok, null);
				builder.show();
			}
		}
	}

	public void run() {
		final Handler handler = new Handler();
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						// This will kill the parent, a bad idea.
						// parent.finish();
						// This will start the browser, a better idea
						launchIntent(new Intent(Intent.ACTION_VIEW, Uri
								.parse("http://www.google.com/")));
					}
				});
			}
		}, DELAY_MS);
	}
}
