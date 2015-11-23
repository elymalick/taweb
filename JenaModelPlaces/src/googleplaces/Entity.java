package googleplaces;

import java.util.Iterator;
import java.util.List;

import JenaUtils.DumpString;
import JenaUtils.ModelFactoryPlaces;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class Entity implements Cloneable {
	protected Geometry geometry;
	protected String icon;
	protected String id;
	protected String name;
	protected String reference;
	protected List<String> types;
	protected String vicinity;

	public Geometry getGeometry() {
		return this.geometry;

	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public List<String> getTypes() {
		return this.types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getVicinity() {
		return this.vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	@Override
	public String toString() {
		return DumpString.dumpString(this);
	}

	/**
	 * Convert Entity to Individual JENA
	 * 
	 * @return
	 */
	public Individual toIndividual(Individual city) {

		ModelFactoryPlaces model = ModelFactoryPlaces.getMPlaces();

		Individual m = model.getEntity().createIndividual(
				model.getNs_entity() + id);

		Iterator<Statement> stmt = model.getEntity().listProperties();
		
		while (stmt.hasNext()) {
			Statement s = stmt.next();

			if (s.getPredicate().getLocalName().equals("name")) {
				m.addProperty(s.getPredicate(), name);
			} else if (s.getPredicate().getLocalName().equals("id")) {
				m.addProperty(s.getPredicate(), id);
			} else if (s.getPredicate().getLocalName().equals("address")) {
				m.addProperty(s.getPredicate(), vicinity);
			} else if (s.getPredicate().getLocalName().equals("city")) {
				m.addProperty(s.getPredicate(), city);
			} 
			
			/*
			 * A Modifier car elle n'est âs dans les ^properties
			 */
			else
				if (s.getPredicate().getLocalName().equals("aPourLocation")) {
				Individual loca = geometry.toIndividual(id);
				m.addProperty(s.getPredicate(), loca);
			}
		}

		for (String type : types) {
			OntClass ty = model.getClassByString(type);
			if (ty != null)
				ty.createIndividual(model.getNs_entity() +id);
		}

		return m;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static Entity fromIndivdual(Resource ressource) {
		return null;
	}
}
