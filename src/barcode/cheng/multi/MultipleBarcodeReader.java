package barcode.cheng.multi;

import java.util.Hashtable;

import barcode.cheng.assist.BinaryBitmap;
import barcode.cheng.assist.NotFoundException;
import barcode.cheng.assist.Result;

/**
 * Implementation of this interface attempt to read several barcodes from one
 * image.
 * 
 * @see com.google.zxing.Reader
 * @author Sean Owen
 */
public interface MultipleBarcodeReader {

	public Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException;

	public Result[] decodeMultiple(BinaryBitmap image, Hashtable hints)
			throws NotFoundException;

}