package barcode.cheng.oned.rss.expanded.decoders;

import barcode.cheng.common.BitArray;

/**
 * @author Pablo Orduña, University of Deusto (pablo.orduna@deusto.es)
 */
final class AI01320xDecoder extends AI013x0xDecoder {

	AI01320xDecoder(BitArray information) {
		super(information);
	}

	protected void addWeightCode(StringBuffer buf, int weight) {
		if (weight < 10000) {
			buf.append("(3202)");
		} else {
			buf.append("(3203)");
		}
	}

	protected int checkWeight(int weight) {
		if (weight < 10000) {
			return weight;
		}
		return weight - 10000;
	}

}