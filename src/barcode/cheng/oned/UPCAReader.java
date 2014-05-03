package barcode.cheng.oned;

import java.util.Hashtable;

import barcode.cheng.assist.BarcodeFormat;
import barcode.cheng.assist.BinaryBitmap;
import barcode.cheng.assist.ChecksumException;
import barcode.cheng.assist.FormatException;
import barcode.cheng.assist.NotFoundException;
import barcode.cheng.assist.Result;
import barcode.cheng.common.BitArray;

/**
 * <p>
 * Implements decoding of the UPC-A format.
 * </p>
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class UPCAReader extends UPCEANReader {

	private final UPCEANReader ean13Reader = new EAN13Reader();

	public Result decodeRow(int rowNumber, BitArray row, int[] startGuardRange,
			Hashtable hints) throws NotFoundException, FormatException,
			ChecksumException {
		return maybeReturnResult(ean13Reader.decodeRow(rowNumber, row,
				startGuardRange, hints));
	}

	public Result decodeRow(int rowNumber, BitArray row, Hashtable hints)
			throws NotFoundException, FormatException, ChecksumException {
		return maybeReturnResult(ean13Reader.decodeRow(rowNumber, row, hints));
	}

	public Result decode(BinaryBitmap image) throws NotFoundException,
			FormatException {
		return maybeReturnResult(ean13Reader.decode(image));
	}

	public Result decode(BinaryBitmap image, Hashtable hints)
			throws NotFoundException, FormatException {
		return maybeReturnResult(ean13Reader.decode(image, hints));
	}

	BarcodeFormat getBarcodeFormat() {
		return BarcodeFormat.UPC_A;
	}

	protected int decodeMiddle(BitArray row, int[] startRange,
			StringBuffer resultString) throws NotFoundException {
		return ean13Reader.decodeMiddle(row, startRange, resultString);
	}

	private static Result maybeReturnResult(Result result)
			throws FormatException {
		String text = result.getText();
		if (text.charAt(0) == '0') {
			return new Result(text.substring(1), null,
					result.getResultPoints(), BarcodeFormat.UPC_A);
		} else {
			throw FormatException.getFormatInstance();
		}
	}

}
