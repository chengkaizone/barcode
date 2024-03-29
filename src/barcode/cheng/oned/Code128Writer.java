package barcode.cheng.oned;

import java.util.Hashtable;

import barcode.cheng.assist.BarcodeFormat;
import barcode.cheng.assist.WriterException;
import barcode.cheng.common.BitMatrix;

/**
 * This object renders a CODE128 code as a {@link BitMatrix}.
 * 
 * @author erik.barbara@gmail.com (Erik Barbara)
 */
public final class Code128Writer extends UPCEANWriter {

	public BitMatrix encode(String contents, BarcodeFormat format, int width,
			int height, Hashtable hints) throws WriterException {
		if (format != BarcodeFormat.CODE_128) {
			throw new IllegalArgumentException(
					"Can only encode CODE_128, but got " + format);
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

		// Determine which code we should use (C or B)
		boolean useCodeC = true;
		for (int i = 0; i < length; i++) {
			char c = contents.charAt(i);
			if (c < '0' || c > '9') {
				useCodeC = false;
				break;
			}
		}

		int codeWidth = 11 + 11 + 13; // start plus check plus stop character
		byte[] result;
		int pos;
		int check;

		if (useCodeC) {
			// Optionnaly add "0" to have pairs
			if (length % 2 != 0) {
				contents = '0' + contents;
				length++;
			}
			// get total code width for this barcode
			for (int i = 0; i < length; i += 2) {
				int[] patterns = Code128Reader.CODE_PATTERNS[Integer
						.parseInt(contents.substring(i, i + 2))];
				for (int j = 0; j < patterns.length; j++) {
					codeWidth += patterns[j];
				}
			}
			result = new byte[codeWidth];
			pos = appendPattern(result, 0, Code128Reader.CODE_PATTERNS[105], 1);
			check = 105;
			// append next character to bytematrix
			for (int i = 0; i < length; i += 2) {
				int pair = Integer.parseInt(contents.substring(i, i + 2));
				check += pair * (i / 2 + 1);
				pos += appendPattern(result, pos,
						Code128Reader.CODE_PATTERNS[pair], 1);
			}
		} else {
			// get total code width for this barcode
			for (int i = 0; i < length; i++) {
				int[] patterns = Code128Reader.CODE_PATTERNS[contents.charAt(i) - ' '];
				for (int j = 0; j < patterns.length; j++) {
					codeWidth += patterns[j];
				}
			}
			result = new byte[codeWidth];
			pos = appendPattern(result, 0, Code128Reader.CODE_PATTERNS[104], 1);
			check = 104;
			// append next character to bytematrix
			for (int i = 0; i < length; i++) {
				check += (contents.charAt(i) - ' ') * (i + 1);
				pos += appendPattern(result, pos,
						Code128Reader.CODE_PATTERNS[contents.charAt(i) - ' '],
						1);
			}
		}

		// compute checksum and append it along with end character and quiet
		// space
		check %= 103;
		pos += appendPattern(result, pos, Code128Reader.CODE_PATTERNS[check], 1);
		pos += appendPattern(result, pos, Code128Reader.CODE_PATTERNS[106], 1);

		return result;
	}

}
