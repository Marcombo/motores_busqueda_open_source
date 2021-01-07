package com.movies.spring.data.es;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import com.movies.spring.data.es.config.Config;
import com.movies.spring.data.es.model.Movie;
import com.movies.spring.data.es.repository.MoviesRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ElasticSearchTestRepository {

    @Autowired
    private MoviesRepository moviesRepository;

    @Test
    public void getAllMovies() throws Exception {
        
    	long nTotal= moviesRepository.count();
		if (nTotal==0){
			System.out.println("No hay ninguna película en la base de datos");
		}else {
			moviesRepository.findAll().forEach(System.out::println);
		}
        
		assertNotNull(moviesRepository.findAll());
    }
    
    @Test
    public void saveAndUpdateMovie() throws Exception {
        
    	Movie movie = new Movie();
    	movie.setId("1000011");
    	movie.setTitle("Title movie new 2021");
    	movie.setYear("2021");
    	movie.setTopics(asList("Action","Animation"));
    	moviesRepository.save(movie);
    	
    	assertNotNull(movie.getId());
    	
    	final Optional<Movie> movieById = moviesRepository.findById("1000011");
    	
    	final Movie movie2 = movieById.get();
    	
    	movie2.setTitle("New title for movie with id 1000011");
    	moviesRepository.save(movie2);
    	
    	final String newTitle = "New title for movie with id 1000011";
        
        assertEquals(newTitle, moviesRepository.findById(movie2.getId())
                .get()
                .getTitle());
    	
    	
    }
    
    @Test
    public void getMovieByTitle() throws Exception {
        
    	final List<Movie> moviesByTitle = moviesRepository.findByTitle("Title movie");
    	assertNotNull(moviesByTitle);
    	
    	final Movie movie = moviesByTitle.get(0);
        final String newTitle = "TITLE MOVIE";
        
        assertEquals(newTitle, moviesRepository.findById(movie.getId())
                .get()
                .getTitle().toUpperCase());

    }
    
    @Test
    public void getMovieById() throws Exception {
        
    	final Optional<Movie> movieById = moviesRepository.findById("1000011");
    	assertNotNull(movieById);

    }
    
    @Test
    public void deleteMovie() {
    	
    	final Optional<Movie> movieById = moviesRepository.findById("1000011");
    	final Movie movie = movieById.get();
    	final long count = moviesRepository.count();
        moviesRepository.delete(movie);
        assertEquals(count - 1, moviesRepository.count());
    }
    
}
