package barcode.cheng.datamatrix.decoder;

import barcode.cheng.assist.ChecksumException;
import barcode.cheng.assist.FormatException;
import barcode.cheng.common.BitMatrix;
import barcode.cheng.common.DecoderResult;
import barcode.cheng.common.reedsolomon.GenericGF;
import barcode.cheng.common.reedsolomon.ReedSolomonDecoder;
import barcode.cheng.common.reedsolomon.ReedSolomonException;

/**
 * <p>
 * The main class which implements Data Matrix Code decoding -- as opposed to
 * locating and extracting the Data Matrix Code from an image.
 * </p>
 * 
 * @author bbrown@google.com (Brian Brown)
 */
public final class DataDecoder {

	private final ReedSolomonDecoder rsDecoder;

	public DataDecoder() {
		rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);
	}

	/**
	 * <p>
	 * Convenience method that can decode a Data Matrix Code represented as a 2D
	 * array of booleans. "true" is taken to mean a black module.
	 * </p>
	 * 
	 * @param image
	 *            booleans representing white/black Data Matrix Code modules
	 * @return text and bytes encoded within the Data Matrix Code
	 * @throws FormatException
	 *             if the Data Matrix Code cannot be decoded
	 * @throws ChecksumException
	 *             if error correction fails
	 */
	public DecoderResult decode(boolean[][] image) throws FormatException,
			ChecksumException {
		int dimension = image.length;
		BitMatrix bits = new BitMatrix(dimension);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (image[i][j]) {
					bits.set(j, i);
				}
			}
		}
		return decode(bits);
	}

	/**
	 * <p>
	 * Decodes a Data Matrix Code represented as a {@link BitMatrix}. A 1 or
	 * "true" is taken to mean a black module.
	 * </p>
	 * 
	 * @param bits
	 *            booleans representing white/black Data Matrix Code modules
	 * @return text and bytes encoded within the Data Matrix Code
	 * @throws FormatException
	 *             if the Data Matrix Code cannot be decoded
	 * @throws ChecksumException
	 *             if error correction fails
	 */
	public DecoderResult decode(BitMatrix bits) throws FormatException,
			ChecksumException {

		// Construct a parser and read version, error-correction level
		BitMatrixParser parser = new BitMatrixParser(bits);
		Version version = parser.getVersion();

		// Read codewords
		byte[] codewords = parser.readCodewords();
		// Separate into data blocks
		DataBlock[] dataBlocks = DataBlock.getDataBlocks(codewords, version);

		int dataBlocksCount = dataBlocks.length;

		// Count total number of data bytes
		int totalBytes = 0;
		for (int i = 0; i < dataBlocksCount; i++) {
			totalBytes += dataBlocks[i].getNumDataCodewords();
		}
		byte[] resultBytes = new byte[totalBytes];

		// Error-correct and copy data blocks together into a stream of bytes
		for (int j = 0; j < dataBlocksCount; j++) {
			DataBlock dataBlock = dataBlocks[j];
			byte[] codewordBytes = dataBlock.getCodewords();
			int numDataCodewords = dataBlock.getNumDataCodewords();
			correctErrors(codewordBytes, numDataCodewords);
			for (int i = 0; i < numDataCodewords; i++) {
				// De-interlace data blocks.
				resultBytes[i * dataBlocksCount + j] = codewordBytes[i];
			}
		}

		// Decode the contents of that stream of bytes
		return DecodedBitStreamParser.decode(resultBytes);
	}

	/**
	 * <p>
	 * Given data and error-correction codewords received, possibly corrupted by
	 * errors, attempts to correct the errors in-place using Reed-Solomon error
	 * correction.
	 * </p>
	 * 
	 * @param codewordBytes
	 *            data and error correction codewords
	 * @param numDataCodewords
	 *            number of codewords that are data bytes
	 * @throws ChecksumException
	 *             if error correction fails
	 */
	private void correctErrors(byte[] codewordBytes, int numDataCodewords)
			throws ChecksumException {
		int numCodewords = codewordBytes.length;
		// First read into an array of ints
		int[] codewordsInts = new int[numCodewords];
		for (int i = 0; i < numCodewords; i++) {
			codewordsInts[i] = codewordBytes[i] & 0xFF;
		}
		int numECCodewords = codewordBytes.length - numDataCodewords;
		try {
			rsDecoder.decode(codewordsInts, numECCodewords);
		} catch (ReedSolomonException rse) {
			throw ChecksumException.getChecksumInstance();
		}
		// Copy back into array of bytes -- only need to worry about the bytes
		// that were data
		// We don't care about errors in the error-correction codewords
		for (int i = 0; i < numDataCodewords; i++) {
			codewordBytes[i] = (byte) codewordsInts[i];
		}
	}

}
