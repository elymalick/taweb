package googleplaces;

import java.util.List;

public class SearchResult {
	
	List<LocationResult> results; 
	
	public lass LocationResult{
		Geometry geometry;
		public Geometry getGeometry() {
			return geometry;
		}
	}
	
	public List<LocationResult> getResults() {
		return results;
	}

}
