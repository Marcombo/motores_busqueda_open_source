package com.movies.elasticsearch;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.movies.spring.data.es.model.Movie;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * 
 * The following docker command can be used: docker run -d --name es762 -p
 * 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
 */
public class ElasticSearchManualTest {

    private RestHighLevelClient client = null;

    @Before
    public void setUp() throws UnknownHostException {

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .build();
        client = RestClients.create(clientConfiguration)
            .rest();
    }

   
   
    @Test
    public void getAllMovies() throws Exception {
        SearchRequest searchRequest = new SearchRequest("movies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = response.getHits()
            .getHits();
        List<Movie> results = Arrays.stream(searchHits)
            .map(hit -> JSON.parseObject(hit.getSourceAsString(), Movie.class))
            .collect(Collectors.toList());

        results.forEach(System.out::println);
    }

    @Test
    public void getMoviesByRangeYear() throws Exception {
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(QueryBuilders.rangeQuery("year")
            .from(2000)
            .to(2010));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] searchHits = response.getHits()
                .getHits();
            List<Movie> results = Arrays.stream(searchHits)
                .map(hit -> JSON.parseObject(hit.getSourceAsString(), Movie.class))
                .collect(Collectors.toList());
            
        results.forEach(System.out::println);

    }
    
    @Test
    public void getMovieByTitle() throws Exception {

        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(QueryBuilders.matchQuery("title", "Masterminds"));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] searchHits = response.getHits()
                .getHits();
            List<Movie> results = Arrays.stream(searchHits)
                .map(hit -> JSON.parseObject(hit.getSourceAsString(), Movie.class))
                .collect(Collectors.toList());
            
        results.forEach(System.out::println);
    }

    @Test
    public void indexNewMovie() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
            .field("title", "title movie")
            .field("year", "2020")
            .field("topics", asList("Action","Animation"))
            .endObject();

        IndexRequest indexRequest = new IndexRequest("movies");
        indexRequest.source(builder);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        assertEquals(Result.CREATED, response.getResult());
    }
}
