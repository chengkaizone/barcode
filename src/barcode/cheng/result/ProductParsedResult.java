package barcode.cheng.result;

/**
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ProductParsedResult extends ParsedResult {

	private final String productID;
	private final String normalizedProductID;

	ProductParsedResult(String productID) {
		this(productID, productID);
	}

	ProductParsedResult(String productID, String normalizedProductID) {
		super(ParsedResultType.PRODUCT);
		this.productID = productID;
		this.normalizedProductID = normalizedProductID;
	}

	public String getProductID() {
		return productID;
	}

	public String getNormalizedProductID() {
		return normalizedProductID;
	}

	public String getDisplayResult() {
		return productID;
	}

}
