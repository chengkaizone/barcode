package barcode.cheng.result.optional;

import java.io.UnsupportedEncodingException;

import barcode.cheng.result.ResultParser;

/**
 * <p>
 * Superclass for classes encapsulating results in the NDEF format. See <a
 * href="http://www.nfc-forum.org/specs/">http://www.nfc-forum.org/specs/</a>.
 * </p>
 * 
 * <p>
 * This code supports a limited subset of NDEF messages, ones that are plausibly
 * useful in 2D barcode formats. This generally includes 1-record messages, no
 * chunking, "short record" syntax, no ID field.
 * </p>
 * 
 * @author Sean Owen
 */
abstract class AbstractNDEFResultParser extends ResultParser {

	static String bytesToString(byte[] bytes, int offset, int length,
			String encoding) {
		try {
			return new String(bytes, offset, length, encoding);
		} catch (UnsupportedEncodingException uee) {
			// This should only be used when 'encoding' is an encoding that must
			// necessarily
			// be supported by the JVM, like UTF-8
			throw new RuntimeException(
					"Platform does not support required encoding: " + uee);
		}
	}

}