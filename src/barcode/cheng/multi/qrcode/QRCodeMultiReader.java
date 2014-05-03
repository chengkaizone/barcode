package barcode.cheng.multi.qrcode;

import java.util.Hashtable;
import java.util.Vector;

import barcode.cheng.assist.BarcodeFormat;
import barcode.cheng.assist.BinaryBitmap;
import barcode.cheng.assist.NotFoundException;
import barcode.cheng.assist.ReaderException;
import barcode.cheng.assist.Result;
import barcode.cheng.assist.ResultMetadataType;
import barcode.cheng.assist.ResultPoint;
import barcode.cheng.common.DecoderResult;
import barcode.cheng.common.DetectorResult;
import barcode.cheng.multi.MultipleBarcodeReader;
import barcode.cheng.multi.qrcode.detector.MultiDetector;
import barcode.cheng.qrcode.QRCodeReader;

/**
 * This implementation can detect and decode multiple QR Codes in an image.
 * 
 * @author Sean Owen
 * @author Hannes Erven
 */
public final class QRCodeMultiReader extends QRCodeReader implements
		MultipleBarcodeReader {

	private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];

	public Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException {
		return decodeMultiple(image, null);
	}

	public Result[] decodeMultiple(BinaryBitmap image, Hashtable hints)
			throws NotFoundException {
		Vector results = new Vector();
		DetectorResult[] detectorResult = new MultiDetector(
				image.getBlackMatrix()).detectMulti(hints);
		for (int i = 0; i < detectorResult.length; i++) {
			try {
				DecoderResult decoderResult = getDecoder().decode(
						detectorResult[i].getBits());
				ResultPoint[] points = detectorResult[i].getPoints();
				Result result = new Result(decoderResult.getText(),
						decoderResult.getRawBytes(), points,
						BarcodeFormat.QR_CODE);
				if (decoderResult.getByteSegments() != null) {
					result.putMetadata(ResultMetadataType.BYTE_SEGMENTS,
							decoderResult.getByteSegments());
				}
				if (decoderResult.getECLevel() != null) {
					result.putMetadata(
							ResultMetadataType.ERROR_CORRECTION_LEVEL,
							decoderResult.getECLevel().toString());
				}
				results.addElement(result);
			} catch (ReaderException re) {
				// ignore and continue
			}
		}
		if (results.isEmpty()) {
			return EMPTY_RESULT_ARRAY;
		} else {
			Result[] resultArray = new Result[results.size()];
			for (int i = 0; i < results.size(); i++) {
				resultArray[i] = (Result) results.elementAt(i);
			}
			return resultArray;
		}
	}

}
