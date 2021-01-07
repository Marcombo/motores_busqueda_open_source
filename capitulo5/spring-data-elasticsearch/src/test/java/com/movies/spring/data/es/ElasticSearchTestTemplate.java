package com.movies.spring.data.es;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.movies.spring.data.es.config.Config;
import com.movies.spring.data.es.model.Movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * 
 * The following docker command can be used: docker run -d --name es762 -p
 * 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class ElasticSearchTestTemplate {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Test
    public void getMovieByTitleWithNativeSearchQuery() {
        final Query searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "Title movie"))
            .build();
        final SearchHits<Movie> movies = elasticsearchTemplate.search(searchQuery, Movie.class);
        assertTrue(movies.getTotalHits()>0);
    }
    

    @Test
    public void getMovieByTitleWithNativeSearchQueryRegExp() {
        final Query searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", ".*movie.*"))
            .build();
        final SearchHits<Movie> movies = elasticsearchTemplate.search(searchQuery, Movie.class);
        assertTrue(movies.getTotalHits()>0);
    }
    

    @Test
    public void getMovieByTitleWithNativeSearchQueryFromIndex() {
        final String movieTitle = "Title movie new 2021";

        final Query searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", movieTitle).minimumShouldMatch("75%"))
            .build();
        final SearchHits<Movie> movies = elasticsearchTemplate.search(searchQuery, Movie.class);

        assertEquals(1, movies.getTotalHits());
        
    }
    
}
