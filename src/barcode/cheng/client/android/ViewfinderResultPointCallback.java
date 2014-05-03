package barcode.cheng.client.android;

import barcode.cheng.assist.ResultPoint;
import barcode.cheng.assist.ResultPointCallback;
import barcode.cheng.client.android.widget.ViewfinderView;

public final class ViewfinderResultPointCallback implements ResultPointCallback {

	private final ViewfinderView viewfinderView;

	public ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
		this.viewfinderView = viewfinderView;
	}

	public void foundPossibleResultPoint(ResultPoint point) {
		viewfinderView.addPossibleResultPoint(point);
	}

}
