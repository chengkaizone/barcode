package barcode.cheng.client.android.book;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import barcode.cheng.client.android.demo.R;
import barcode.cheng.client.android.widget.SearchBookContentsListItem;

/**
 * Manufactures list items which represent SBC results.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class SearchBookContentsAdapter extends
		ArrayAdapter<SearchBookContentsResult> {

	public SearchBookContentsAdapter(Context context,
			List<SearchBookContentsResult> items) {
		super(context, R.layout.search_book_contents_list_item, 0, items);
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		SearchBookContentsListItem listItem;

		if (view == null) {
			LayoutInflater factory = LayoutInflater.from(getContext());
			listItem = (SearchBookContentsListItem) factory.inflate(
					R.layout.search_book_contents_list_item, viewGroup, false);
		} else {
			if (view instanceof SearchBookContentsListItem) {
				listItem = (SearchBookContentsListItem) view;
			} else {
				return view;
			}
		}

		SearchBookContentsResult result = getItem(position);
		listItem.set(result);
		return listItem;
	}
}
