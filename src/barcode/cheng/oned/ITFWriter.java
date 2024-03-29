package barcode.cheng.oned;

import java.util.Hashtable;

import barcode.cheng.assist.BarcodeFormat;
import barcode.cheng.assist.WriterException;
import barcode.cheng.common.BitMatrix;

/**
 * This object renders a ITF code as a {@link BitMatrix}.
 * 
 * @author erik.barbara@gmail.com (Erik Barbara)
 */
public final class ITFWriter extends UPCEANWriter {

	public BitMatrix encode(String contents, BarcodeFormat format, int width,
			int height, Hashtable hints) throws WriterException {
		if (format != BarcodeFormat.ITF) {
			throw new IllegalArgumentException("Can only encode ITF, but got "
					+ format);
		}

		return super.encode(contents, format, width, height, hints);
	}

	public byte[] encode(String contents) {
		int length = contents.length();
		if (length > 80) {
			throw new IllegalArgumentException(
					"Requested contents should be less than 80 digits long, but got "
							+ length);
		}
		byte[] result = new byte[9 + 9 * length];
		int[] start = { 1, 1, 1, 1 };
		int pos = appendPattern(result, 0, start, 1);
		for (int i = 0; i < length; i += 2) {
			int one = Character.digit(contents.charAt(i), 10);
			int two = Character.digit(contents.charAt(i + 1), 10);
			int[] encoding = new int[18];
			for (int j = 0; j < 5; j++) {
				encoding[(j << 1)] = ITFReader.PATTERNS[one][j];
				encoding[(j << 1) + 1] = ITFReader.PATTERNS[two][j];
			}
			pos += appendPattern(result, pos, encoding, 1);
		}
		int[] end = { 3, 1, 1 };
		pos += appendPattern(result, pos, end, 1);

		return result;
	}

}