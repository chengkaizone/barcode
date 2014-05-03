package barcode.cheng.client.android.demo;

import java.util.ArrayList;
import java.util.List;

import barcode.cheng.client.android.share.LoadPackagesAsyncTask;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.ListView;

public final class AppPickerActivity extends ListActivity {

	private final List<String[]> labelsPackages = new ArrayList<String[]>();

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (labelsPackages.isEmpty()) {
			new LoadPackagesAsyncTask(this).execute(labelsPackages);
		}
		// otherwise use last copy we loaded -- apps don't change much, and it
		// takes
		// forever to load for some reason
	}

	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {
		if (position >= 0 && position < labelsPackages.size()) {
			String url = "market://search?q=pname:"
					+ labelsPackages.get(position)[1];
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			intent.putExtra(Browser.BookmarkColumns.URL, url);
			setResult(RESULT_OK, intent);
		} else {
			setResult(RESULT_CANCELED);
		}
		finish();
	}

}
