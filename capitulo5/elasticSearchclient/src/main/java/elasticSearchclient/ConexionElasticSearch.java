package elasticSearchclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.client.core.MainResponse.Version;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ConexionElasticSearch {

	public static void main(String[] args) {
		
		RestHighLevelClient client = new RestHighLevelClient
				(RestClient.builder
						(new HttpHost("localhost", 9200, "http")));
		
		MainResponse response;
		try {
			response = client.info(RequestOptions.DEFAULT);
			
			String clusterName = response.getClusterName();
			String clusterUuid = response.getClusterUuid();
			String nodeName = response.getNodeName();
			Version version = response.getVersion();
			 
			System.out.println("Información del cluster: ");
			
			System.out.println("Nombre del cluster: "+ clusterName);
			System.out.println("Identificador del cluster: " + clusterUuid);
			System.out.println("Nombre de los nodos del cluster: " + nodeName);
			System.out.println("Versión de elasticsearch: " + version.getNumber());
			System.out.println("Versión de lucene: " + version.getLuceneVersion());
			 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//CreateIndexRequest request = new CreateIndexRequest("index_movies");
		
		/*request.settings(Settings.builder() 
			    .put("index.number_of_shards", 3)
			    .put("index.number_of_replicas", 2)
		);*/
		
		/*Map<String, Object> id_long = new HashMap<String, Object>();
		id_long.put("type", "long");
		Map<String, Object> text = new HashMap<String, Object>();
		text.put("type", "text");
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("id", id_long);
		properties.put("title", text);
		properties.put("year", text);
		properties.put("topics", text);
		
		Map<String, Object> mapping = new HashMap<String, Object>();
		mapping.put("properties", properties);
		request.mapping("movie",mapping);
		
		CreateIndexResponse createIndexResponse;
		try {
			createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
			boolean acknowledged = createIndexResponse.isAcknowledged();
			System.out.println("Indice creado: "+ acknowledged);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		try {
			
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("id", "1");
			jsonMap.put("title", "title movie");
			jsonMap.put("year", "2019");
			jsonMap.put("topics", new String[]{"Action","Animation"});
			IndexRequest indexRequest = new IndexRequest("index_movies")
			    .id("1").source(jsonMap);
			
			Map<String, Object> jsonMap2 = new HashMap<String, Object>();
			jsonMap2.put("id", "2");
			jsonMap2.put("title", "the movie");
			jsonMap2.put("year", "2020");
			jsonMap2.put("topics", new String[]{"Sci-Fi"});
			IndexRequest indexRequest2 = new IndexRequest("index_movies")
			    .id("2").source(jsonMap2);
			
			client.index(indexRequest,RequestOptions.DEFAULT);
			client.index(indexRequest2,RequestOptions.DEFAULT);
			
			BulkRequest bulkRequest = new BulkRequest();
			bulkRequest.add(indexRequest);
			bulkRequest.add(indexRequest2);
			BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
			System.out.println("BulkResponse: "+ bulkResponse);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			//Búsquedas
			
			//Obtener todos los documentos
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			
			//Petición sobre el índice de movies
			SearchRequest searchRequest = new SearchRequest("movies");
			searchRequest.source(searchSourceBuilder);
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			System.out.println("Número total de documentos: " +searchResponse.getHits().getTotalHits());
			
			
			//Respuesta
			for (SearchHit hit: searchResponse.getHits().getHits()){
				System.out.println("Documento con id " +hit.getId()+" "+ hit.getSourceAsString());
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		//Obtener los documentos que cumplan una determinada condición
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("title","Dreams"));
		 
		//Petición sobre el índice de movies
		SearchRequest searchRequest = new SearchRequest("movies");
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		
		System.out.println("Número total de documentos: " +searchResponse.getHits().getTotalHits());

		//Respuesta
		for (SearchHit hit: searchResponse.getHits().getHits()){
			System.out.println("Documento con id " +hit.getId()+" "+ hit.getSourceAsString());
		} 
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//Obtener los documentos que cumplan varias condiciones
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			
			MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("year", "2018");
			MatchPhraseQueryBuilder matchPhraseQueryBuilder2 = QueryBuilders.matchPhraseQuery("topics", "action");
		
			QueryBuilder queryBuilder = boolQuery.must(matchPhraseQueryBuilder).must(matchPhraseQueryBuilder2);

			searchSourceBuilder.query(queryBuilder);
			
			//Petición sobre el índice de movies
			SearchRequest searchRequest = new SearchRequest("movies");
			searchRequest.source(searchSourceBuilder);
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			System.out.println("Número total de documentos: " +searchResponse.getHits().getTotalHits());

			//Respuesta
			for (SearchHit hit: searchResponse.getHits().getHits()){
				System.out.println("Documento con id " +hit.getId()+" "+ hit.getSourceAsString());
			} 
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		

	}

}
