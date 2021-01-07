package com.movies.spring.data.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movies.spring.data.es.config.Config;
import com.movies.spring.data.es.model.Movie;
import com.movies.spring.data.es.service.InterfazElasticsearch;

import java.io.*;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class GestionMovies {

    final int OPCION_NUEVA_PELICULA = 1;
    final int OPCION_VER_TODAS = 2;
    final int OPCION_BUSCAR_POR_ID = 3;
    final int OPCION_BUSCAR_POR_TITULO = 4;
    final int OPCION_BUSCAR_POR_ANYO = 5;
    final int OPCION_MODIFICAR_POR_TITULO = 6;
    final int OPCION_BORRAR_POR_ID = 7;
    final int OPCION_SALIR = 8;

    final String MENSAJE_BUSCAR_POR_TITULO = "Título a buscar: ";
    final String MENSAJE_BUSCAR_POR_ID = "Introduzca el ID: ";
    final String MENSAJE_BUSCAR_ANYO = "Anyo: ";

    @Autowired
	private InterfazElasticsearch servicio;
    
    static final String TEXTO_CONTINUAR = "\n\n\t ==== Presione enter para continuar =====";
    
    @Test
    public void getAllMovies() throws Exception {
    	servicio.obtenerMovies();
    }
    

    @Test
    public void menuPrincipal() {

        MenusUsuario menusUsuario = new MenusUsuario();

        Movie movie, movieTMP;
        int opcion;
        String tituloBusqueda;
        String idBusqueda;
        String year;
        List<Movie> listaMovies;

        while (true) {
        	limpiarPantalla();
            opcion = menusUsuario.menuPrincipal();

            switch (opcion) {
                case OPCION_NUEVA_PELICULA:
                    movie = menusUsuario.introducirValoresPeliculaConsola();

                    movieTMP = servicio.buscarPorId(String.valueOf(movie.getId()));
                   
                    if ((movieTMP != null)) {
                        System.out.println("Ya existe una movie con el mismo id !\n\n");

                        menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    } else {
                    	servicio.guardarMovie(movie);
                    }

                    break;
                case OPCION_VER_TODAS:
                	servicio.obtenerMovies();
                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_BUSCAR_POR_ID:
                    idBusqueda = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_POR_ID);
                    movie = servicio.buscarPorId(idBusqueda);

                    if (movie == null) {
                        System.out.println("Movie no encontrada !\n\n");
                    } else {
                        System.out.println("Movie obtenida: " + movie);
                    }

                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_BUSCAR_POR_TITULO:
                    tituloBusqueda = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_POR_TITULO);
                    movie = servicio.buscarPorTitulo(tituloBusqueda);

                    if (movie == null) {
                        System.out.println("Movie no encontrada !\n\n");
                    } else {
                        System.out.println("Movie obtenida: " + movie);
                    }

                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_BUSCAR_POR_ANYO:
                    year = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_ANYO);
                    listaMovies = servicio.buscarPorYear(year);
                    if (listaMovies.size() == 0) {
                        System.out.println("Ninguna movie para el anyo buscado\n\n");
                    } else {
                        System.out.println("Lista de movies para el año buscado: ");
                        listaMovies.forEach(System.out::println);
                    }
                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_MODIFICAR_POR_TITULO:
                    tituloBusqueda = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_POR_TITULO);
                    Movie tmp = servicio.buscarPorTitulo(tituloBusqueda);
                    movie = menusUsuario.introducirMovieConValorPrevio(tmp);
                    servicio.guardarMovie(movie);

                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_BORRAR_POR_ID:
                    idBusqueda = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_POR_ID);
                    movie = servicio.buscarPorId(idBusqueda);
                    if (movie == null) {
                        System.out.println(" Movie no encontrada !\n\n");
                        menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    } else {
                    	servicio.borrarMovie(movie);
                    	movie = servicio.buscarPorId(idBusqueda);

                        if (movie == null) {
                            System.out.println("Movie eliminada correctamente\n\n");
                        } else {
                            System.out.println("Movie no se ha eliminado: " + movie);
                        }
                    }
                    break;
                case OPCION_SALIR:
                    break;
                default:
                    System.out.println("Opcion invalida!\n\n");
                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
            }
            if (opcion == OPCION_SALIR) {
                break;
            }
        }
    }


    static void limpiarPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
