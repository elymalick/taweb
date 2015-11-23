package googleplaces;

import googleplaces.CityInfo.adr_comp;
import googleplaces.CityInfo.component;

import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Statement;

import JenaUtils.DumpString;
import JenaUtils.ModelFactoryPlaces;

public class City {
	private String next_page_token;
	private List<Entity> results;
	private String status;
	private CityInfo infos;
	private String Identifier;

	public void setIdentifier(String identifier) {
		Identifier = identifier;
	}

	public String getIdentifier() {
		return Identifier;
	}

	public String getNext_page_token() {
		return this.next_page_token;
	}

	public void setNext_page_token(String next_page_token) {
		this.next_page_token = next_page_token;
	}

	public List<Entity> getResults() {
		return this.results;
	}

	public void setResults(List<Entity> results) {
		this.results = results;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDetails(CityInfo inf) {
		this.infos = inf;
	}

	public CityInfo getDetails() {
		return this.infos;
	}

	public void Append(City other) {
		for (Entity ent : other.getResults()) {
			results.add(ent);
		}

		next_page_token = other.getNext_page_token();
	}

	@Override
	public String toString() {
		return DumpString.dumpString(this);
	}

	/**
	 * Convert To Jena
	 */
	public void toIndividual() {

		component comp = infos.getResults().get(0);
		if (comp == null) {
			System.out.println("No Result for Ville");
			return;
		}

		ModelFactoryPlaces model = ModelFactoryPlaces.getMPlaces();

		Individual cityI = model.getCity().createIndividual(
				model.getNs_city() + Identifier);

		Iterator<Statement> stmt = model.getCity().listProperties();

		while (stmt.hasNext()) {
			Statement s = stmt.next();

			adr_comp address = comp.getAddress_components().get(0);

			if (s.getPredicate().getLocalName().equals("name")) {
				if (address != null)
					cityI.addProperty(s.getPredicate(), comp
							.getAddress_components().get(0).getLong_name());
			} else if (s.getPredicate().getLocalName()
					.equals("formatted_addres")) {
				cityI.addProperty(s.getPredicate(), comp.getFormatted_address());
			} else if (s.getPredicate().getLocalName().equals("location")) {
				cityI.addProperty(s.getPredicate(), comp.getGeometry()
						.getLocation().toIndividualCity(Identifier));
			}
		}
		
		for (Entity e : results) {
			e.toIndividual(cityI);
		}
	}
}
