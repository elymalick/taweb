package apiutils;


import googleplaces.City;
import googleplaces.CityInfo;
import googleplaces.Geometry;
import googleplaces.SearchResult;

import JenaUtils.GooglePlaceType;
import JenaUtils.ModelFactoryPlaces;

import com.google.gson.GsonBuilder;
import com.hp.hpl.jena.ontology.OntClass;

public class GooglePlaceCaller {

	int radius =5000;
	String serverurl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?sensor=false&radius=5000&location=";
	String key = "&key=AIzaSyD9_YnuHc2OkfKWfdVKMiMT2eDKqYdaNRQ";
	String type = "";
	
	String searchurl = "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=";
	String nextpage ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?sensor=false&pagetoken=";

	public GooglePlaceCaller() {}

	public GooglePlaceCaller(int radius) {this.radius = radius;	}
	
	public String locationFromcityName(String cityName) {
		String uri = searchurl + cityName;
		
		System.out.println(uri);
		
		
		//result contiens le contenu JSON
		String result = ApiCaller.cUrl(ApiCaller.getUrlFromString(uri));
		SearchResult r = new GsonBuilder().create().fromJson(result, SearchResult.class);
		
		
		
		
		if (r.getResults().size() != 1) {
			return null;
		} else {
			Geometry g = r.getResults().get(0).getGeometry();
			String ret = g.getLocation().getLat().toString() + ","
					+ g.getLocation().getLng().toString();
			return ret;
		}
	}
	
	/****************************************************************************************************
	 * RETOURNER LES DETAILS D'UNE VILLE
	 ****************************************************************************************************/
	private CityInfo getCityInfo(String cityName){
		String uri = searchurl + cityName;
		String result = ApiCaller.cUrl(ApiCaller.getUrlFromString(uri));
		
		CityInfo c = new GsonBuilder().create().fromJson(result, CityInfo.class);
		return c;
	}
	
	/****************************************************************************************************
	 * RECHERCHE DE TOUTES LES ENTITEES 
	 ****************************************************************************************************/
	public City villeEntitiesFromWeb(String cityName){
		String loc = locationFromcityName(cityName);
		
		if (loc != null) {
			String uri = serverurl + loc +type+ key;
			String result = ApiCaller.cUrl(ApiCaller.getUrlFromString(uri));
			City ret = new GsonBuilder().create().fromJson(result, City.class);
			
			ret.setIdentifier(loc);
			ret.setDetails(getCityInfo(cityName));
			System.out.println(uri);

			
			while (ret.getNext_page_token()!=null){
				
				uri = nextpage + ret.getNext_page_token() +key;
				System.out.println(uri);

				result = ApiCaller.cUrl(ApiCaller.getUrlFromString(uri));
				ret.Append(new GsonBuilder().create().fromJson(result, City.class));
			}
			return ret;

		} else {
			try {
				throw new Exception("Many or Not exist city");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	/****************************************************************************************************
	 * RECHERCHE PAR TYPE
	 ****************************************************************************************************/
	public City villeEntitiesFromWebByTypes(String cityName, String type){
		OntClass OC = ModelFactoryPlaces.getMPlaces().getClassByString(type);
		String result = OC.toString();
		return byType(cityName, type);
	}

	private City byType(String cityName, String typeName) {
		type = "&types="+typeName;
		return villeEntitiesFromWeb(cityName);
	}
	
}
