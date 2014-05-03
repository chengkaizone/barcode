package barcode.cheng.client.android.demo;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import barcode.cheng.client.android.LocaleManager;
import barcode.cheng.client.android.book.SearchBookContentsResult;

public final class BrowseBookListener implements
		AdapterView.OnItemClickListener {

	private final SearchBookContentsActivity activity;
	private final List<SearchBookContentsResult> items;

	public BrowseBookListener(SearchBookContentsActivity activity,
			List<SearchBookContentsResult> items) {
		this.activity = activity;
		this.items = items;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// HACK(jbreiden) I have no idea where the heck our pageId off by one
		// error is coming from. I should not have to put in this position - 1
		// kludge.
		String pageId = items.get(position - 1).getPageId();
		String query = SearchBookContentsResult.getQuery();
		if (activity.getISBN().startsWith("http://google.com/books?id=")
				&& (pageId.length() > 0)) {
			String uri = activity.getISBN();
			int equals = uri.indexOf('=');
			String volumeId = uri.substring(equals + 1);
			String readBookURI = "http://books.google."
					+ LocaleManager.getBookSearchCountryTLD() + "/books?id="
					+ volumeId + "&pg=" + pageId + "&vq=" + query;
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(readBookURI));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			activity.startActivity(intent);
		}
	}
}
