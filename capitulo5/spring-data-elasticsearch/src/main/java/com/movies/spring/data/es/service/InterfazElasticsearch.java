package com.movies.spring.data.es.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movies.spring.data.es.model.Movie;
import com.movies.spring.data.es.repository.MoviesRepository;


@Service
public class InterfazElasticsearch {
	
	@Autowired
	private MoviesRepository repositorio;

	public void guardarMovie(Movie movie){
		repositorio.save(movie);
	}

	public void obtenerMovies() {
		long nTotal= repositorio.count();
		if (nTotal==0){
			System.out.println("No hay ninguna movie en la base de datos");
		}else {
			repositorio.findAll().forEach(System.out::println);
		}
	}


	public void borrarMovie(Movie movie) {
		repositorio.delete(movie);
	}
	
	public Movie buscarPorTitulo(String titulo) {
		List<Movie> lista = repositorio.findByTitle(titulo);
		if (lista.size()>0){
			return lista.get(0);
		}else {
			return null;
		}
	}
	
	public List<Movie> buscarPorYear(String year) {
		List<Movie> lista = repositorio.findByYear(year);
		return lista;
	}

	public Movie buscarPorId(String id) {
		Optional<Movie> movie = repositorio.findById(id);
		
		if (movie.isPresent()){
			return movie.get();
		}else{
			return null;
		}

	}
}
