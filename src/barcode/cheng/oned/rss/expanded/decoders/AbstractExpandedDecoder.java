package barcode.cheng.oned.rss.expanded.decoders;

import barcode.cheng.assist.NotFoundException;
import barcode.cheng.common.BitArray;

/**
 * @author Pablo Orduña, University of Deusto (pablo.orduna@deusto.es)
 * @author Eduardo Castillejo, University of Deusto
 *         (eduardo.castillejo@deusto.es)
 */
public abstract class AbstractExpandedDecoder {

	protected final BitArray information;
	protected final GeneralAppIdDecoder generalDecoder;

	AbstractExpandedDecoder(BitArray information) {
		this.information = information;
		this.generalDecoder = new GeneralAppIdDecoder(information);
	}

	public abstract String parseInformation() throws NotFoundException;

	public static AbstractExpandedDecoder createDecoder(BitArray information) {
		if (information.get(1)) {
			return new AI01AndOtherAIs(information);
		} else if (!information.get(2)) {
			return new AnyAIDecoder(information);
		}

		int fourBitEncodationMethod = GeneralAppIdDecoder
				.extractNumericValueFromBitArray(information, 1, 4);

		switch (fourBitEncodationMethod) {
		case 4:
			return new AI013103decoder(information);
		case 5:
			return new AI01320xDecoder(information);
		}

		int fiveBitEncodationMethod = GeneralAppIdDecoder
				.extractNumericValueFromBitArray(information, 1, 5);
		switch (fiveBitEncodationMethod) {
		case 12:
			return new AI01392xDecoder(information);
		case 13:
			return new AI01393xDecoder(information);
		}

		int sevenBitEncodationMethod = GeneralAppIdDecoder
				.extractNumericValueFromBitArray(information, 1, 7);
		switch (sevenBitEncodationMethod) {
		case 56:
			return new AI013x0x1xDecoder(information, "310", "11");
		case 57:
			return new AI013x0x1xDecoder(information, "320", "11");
		case 58:
			return new AI013x0x1xDecoder(information, "310", "13");
		case 59:
			return new AI013x0x1xDecoder(information, "320", "13");
		case 60:
			return new AI013x0x1xDecoder(information, "310", "15");
		case 61:
			return new AI013x0x1xDecoder(information, "320", "15");
		case 62:
			return new AI013x0x1xDecoder(information, "310", "17");
		case 63:
			return new AI013x0x1xDecoder(information, "320", "17");
		}

		throw new IllegalStateException("unknown decoder: " + information);
	}

}
