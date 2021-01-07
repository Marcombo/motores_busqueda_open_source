package apache_solr;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;	

public class ConexionSolr {

	public static void main(String[] args) {
		String urlString = "http://localhost:8983/solr/techproducts";
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		
		solr.setParser(new XMLResponseParser());
		
		//Crear objetos documentos
		SolrInputDocument documento1 = new SolrInputDocument();
		documento1.addField("id", "1");
		documento1.addField("name", "Documento 1");
		documento1.addField("description", "Descripcion Documento 1");
		documento1.addField("value", 100);
		 
		SolrInputDocument documento2 = new SolrInputDocument();
		documento2.addField("id", "2");
		documento2.addField("name", "Documento 2");
		documento2.addField("description", "Descripcion Documento 2");
		documento2.addField("value", 200);

		
		//añadir los documentos al índice y comitear los documentos para posteriormente realizar búsquedas
		try {
			
			//Añadir documentos a la colección
			Collection<SolrInputDocument> documentos = new ArrayList<SolrInputDocument>();
			documentos.add(documento1);
			documentos.add(documento2);
			
			solr.addBean(new DocumentoBean("3", "Documento 3", 300));
			solr.commit();
			
			solr.add(documentos);
			solr.commit();
			
		} catch (SolrServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//consulta todos los documentos ordenados por id de forma ascendente
		try {
			
			SolrQuery query = new SolrQuery();
			query.setQuery( "*:*" );
			query.addSort("id", SolrQuery.ORDER.asc);
			
			//Ejecutar la consulta y procesar los resultados
			QueryResponse response;
			response = solr.query(query);
			SolrDocumentList docs = response.getResults();
			System.out.println("Número documentos: "+docs.getNumFound());
			ListIterator<SolrDocument> iterador=docs.listIterator();
			System.out.println("Iterador documentos: "+iterador);
			while (iterador.hasNext()) {
				System.out.println(iterador.next()); 
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//borrar documentos
		/*try {
			solr.deleteById("1");
			solr.commit();
			SolrQuery query = new SolrQuery();
			query.set("q", "id:1");
			QueryResponse response = solr.query(query);
			SolrDocumentList docList = response.getResults();
			assertEquals(docList.getNumFound(), 0);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			solr.deleteByQuery("name:Documento 1");
			solr.commit();
			SolrQuery query = new SolrQuery();
			query.set("q", "id:1");
			QueryResponse response = solr.query(query);
			SolrDocumentList docList = response.getResults();
			assertEquals(docList.getNumFound(), 0);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		

	}

}
