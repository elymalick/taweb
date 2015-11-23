package MainLauncher;

import java.net.URLEncoder;

import googleplaces.City;
import googleplaces.Entity;
import JenaUtils.Endpoint;
import JenaUtils.ModelFactoryPlaces;
import JenaUtils.SDBUtils;
import apiutils.GooglePlaceCaller;



public class Main {
	
	public static void main(String[] args) {
		//String pr="PREFIX P : <http://localhost:9000/techweb/entity#>";
		Endpoint ep = new Endpoint();
		String query ="PREFIX p: <http://localhost:9000/techweb/entity#>"
				+ " select ?s  ?o where {?s p:name ?o }";
		
		String querys ="PREFIX p: <http://dbpedia.org/resource/>"
				+ " select ?oeuvre  where {?oeuvre <http://dbpedia.org/ontology/author p:Victor_Hugo }";
		
		ep.sparqlQuery(query);
		//CreateJenaModel();
		//GetFromWebByType("Juvignac", "food");
		
	}
//
	public static void CreateJenaModel() {
		ModelFactoryPlaces model = ModelFactoryPlaces.getMPlaces();
	}

	public static void GetFromWeb(String city) {
		GooglePlaceCaller x = new GooglePlaceCaller(10000);
		City r =  x.villeEntitiesFromWeb(city);
		r.toIndividual();
		ModelFactoryPlaces.getMPlaces().toConsole();
	}
//	
	public static void GetFromWebByType(String city, String type) {
		GooglePlaceCaller x = new GooglePlaceCaller(10000);
		City r =  x.villeEntitiesFromWebByTypes(city, type);
		r.toIndividual();
		for(Entity ent : r.getResults()){
			System.out.println(ent.getName());
		}
		ModelFactoryPlaces.getMPlaces().toConsole();
	}
}