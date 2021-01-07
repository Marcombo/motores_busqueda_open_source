package com.movies.spring.data.es.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.movies.spring.data.es.model.Movie;

@Repository
public interface MoviesRepository extends ElasticsearchRepository<Movie,String> {
    List<Movie> findByTitle(String title);
    List<Movie> findByYear(String year);
    
}
