package barcode.cheng.result;

import barcode.cheng.assist.Result;

/**
 * Parses a "geo:" URI result, which specifies a location on the surface of the
 * Earth as well as an optional altitude above the surface. See <a
 * href="http://tools.ietf.org/html/draft-mayrhofer-geo-uri-00">
 * http://tools.ietf.org/html/draft-mayrhofer-geo-uri-00</a>.
 * 
 * @author Sean Owen
 */
final class GeoResultParser extends ResultParser {

	private GeoResultParser() {
	}

	public static GeoParsedResult parse(Result result) {
		String rawText = result.getText();
		if (rawText == null
				|| (!rawText.startsWith("geo:") && !rawText.startsWith("GEO:"))) {
			return null;
		}
		// Drop geo, query portion
		int queryStart = rawText.indexOf('?', 4);
		String query;
		String geoURIWithoutQuery;
		if (queryStart < 0) {
			query = null;
			geoURIWithoutQuery = rawText.substring(4);
		} else {
			query = rawText.substring(queryStart + 1);
			geoURIWithoutQuery = rawText.substring(4, queryStart);
		}
		int latitudeEnd = geoURIWithoutQuery.indexOf(',');
		if (latitudeEnd < 0) {
			return null;
		}
		int longitudeEnd = geoURIWithoutQuery.indexOf(',', latitudeEnd + 1);
		double latitude, longitude, altitude;
		try {
			latitude = Double.parseDouble(geoURIWithoutQuery.substring(0,
					latitudeEnd));
			if (latitude > 90.0 || latitude < -90.0) {
				return null;
			}
			if (longitudeEnd < 0) {
				longitude = Double.parseDouble(geoURIWithoutQuery
						.substring(latitudeEnd + 1));
				altitude = 0.0;
			} else {
				longitude = Double.parseDouble(geoURIWithoutQuery.substring(
						latitudeEnd + 1, longitudeEnd));
				altitude = Double.parseDouble(geoURIWithoutQuery
						.substring(longitudeEnd + 1));
			}
			if (longitude > 180.0 || longitude < -180.0 || altitude < 0) {
				return null;
			}
		} catch (NumberFormatException nfe) {
			return null;
		}
		return new GeoParsedResult(latitude, longitude, altitude, query);
	}

}