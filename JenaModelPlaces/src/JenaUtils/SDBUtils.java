package JenaUtils;

import googleplaces.City;
import googleplaces.Entity;
import googleplaces.Location;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.StoreDesc;
import com.hp.hpl.jena.sdb.sql.JDBC;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.store.DatabaseType;
import com.hp.hpl.jena.sdb.store.DatasetStore;
import com.hp.hpl.jena.sdb.store.LayoutType;

public class SDBUtils {
	private static final String user = "root";
	private static final String psw = "elymalick";
	private static Store store = null;

	public static OntModel getModelSDB() {
		/****************************************************
		 * CONNEXION DE SDB A MySQL
		 ****************************************************/
		StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash, DatabaseType.MySQL);
		JDBC.loadDriverMySQL();
		String jdbcURL = "jdbc:mysql://localhost:3306/places_rdf";
		SDBConnection conn = null;
		try {
			conn = new SDBConnection(jdbcURL, user, psw);
			System.out.println("MODEL GETTING ...");
		} catch (Exception e) {
			System.out.println("CONNECTION FAILED !!");
			e.printStackTrace();
		}

		store = SDBFactory.connectStore(conn, storeDesc);
		Model model = SDBFactory.connectDefaultModel(store);
		OntModel mdb = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);

		return mdb;
	}

//	public void executeQuery(Query query) {
//		Dataset ds = DatasetStore.create(store);
//		QueryExecution qe = QueryExecutionFactory.create(query, ds);
//		try {
//			ResultSet rs = qe.execSelect();
//			ResultSetFormatter.out(rs);
//		} finally {
//			qe.close();
//		}
//	}


	public static void emptySDBModel() {
		StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash,
				DatabaseType.MySQL);
		JDBC.loadDriverMySQL();
		String jdbcURL = "jdbc:mysql://localhost:3306/places_rdf";
		SDBConnection conn = null;
		try {
			conn = new SDBConnection(jdbcURL, user, psw);
			System.out.println("DROPPING ALL ELEMENTS ...");
		} catch (Exception e) {
			System.out.println("CONNECTION FAILED !!");
			e.printStackTrace();
		}

		Store store = SDBFactory.connectStore(conn, storeDesc);

		store.getTableFormatter().truncate();

		conn.close();

	}

	/**
	 * Creation des tables
	 */
	public static void createSDBModel() {
		StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash, DatabaseType.MySQL);
		JDBC.loadDriverMySQL();
		String jdbcURL = "jdbc:mysql://localhost:3306/places_rdf";
		SDBConnection conn = null;
		
		try {
			conn = new SDBConnection(jdbcURL, user, psw);
			System.out.println("CREATE TABLES  ..");
		} catch (Exception e) {
			System.out.println("CONNECTION FAILED !!");
			e.printStackTrace();
		}

		Store store = SDBFactory.connectStore(conn, storeDesc);
		store.getTableFormatter().create();
		conn.close();
	}

}
