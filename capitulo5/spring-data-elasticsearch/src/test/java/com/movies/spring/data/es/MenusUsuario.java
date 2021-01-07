package com.movies.spring.data.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.movies.spring.data.es.model.Movie;

public class MenusUsuario {
    private static final String MENSAJE_TITLE = "Título película: ";
    private static final String MENSAJE_ID = "Id: ";
    private static final String MENSAJE_YEAR = "Anyo: ";
    private static final String MENSAJE_TOPICS = "Topics: ";
    private static final String MENSAJE_MAS_TOPICS = "¿Desea introducir más topics (s/n)? ";
    private static final String MENSAJE_SI_CONTINUAR = "s";

    private static final String MENSAJE_OPCION_MENU_PRINCIPAL="Opcion: ";
    public int menuPrincipal(){
        System.out.println("\tEscoja la opcion deseada: \n");
        System.out.println("1-Registrar una película");
        System.out.println("2-Visualizar lista de películas");
        System.out.println("3-Buscar por Id");
        System.out.println("4-Buscar por título");
        System.out.println("5-Buscar por anyo");
        System.out.println("6-Modificar por título");
        System.out.println("7-Borrar por identificador");
        System.out.println("8-Salir");
        System.out.println("\n");
        return obtenerEnteroDeConsola(MENSAJE_OPCION_MENU_PRINCIPAL);
    }
    
    public Movie introducirValoresPeliculaConsola() {
        String titulo = obtenerPalabraDeConsola(MENSAJE_TITLE);
        int id = obtenerEnteroDeConsola(MENSAJE_ID);
        String year = obtenerPalabraDeConsola(MENSAJE_YEAR);
        String respuesta;
        String topic;
        List<String> topics = new ArrayList<String>();
        while (true) {
            topic = obtenerPalabraDeConsola(MENSAJE_TOPICS);
            topics.add(topic);
            respuesta = obtenerPalabraDeConsola(MENSAJE_MAS_TOPICS);
            if (!respuesta.toLowerCase().startsWith(MENSAJE_SI_CONTINUAR)) {
                break;
            }
        }
        return new Movie(String.valueOf(id),titulo,year,topics);
    }


    public Movie introducirMovieConValorPrevio(Movie movie) {
    	System.out.println("Titulo actual: "+movie.getTitle());
    	System.out.println("Introduce el nuevo valor:\n");
        String titulo = obtenerPalabraDeConsola(MENSAJE_TITLE);
        System.out.println("Anyo actual: "+movie.getYear());
        System.out.println("Introduce el nuevo valor:\n");
        String year = obtenerPalabraDeConsola(MENSAJE_YEAR);
        System.out.println("Topics actual: "+movie.getTopics());
        System.out.println("Introduce el nuevo valor:\n");
        String respuesta;
        String topic;
        List<String> topics = new ArrayList<String>();
        while (true) {
            topic = obtenerPalabraDeConsola(MENSAJE_TOPICS);
            topics.add(topic);
            respuesta = obtenerPalabraDeConsola(MENSAJE_MAS_TOPICS);
            if (!respuesta.toLowerCase().startsWith(MENSAJE_SI_CONTINUAR)) {
                break;
            }
        }
        if(titulo.isEmpty()) {
        	titulo = movie.getTitle();
        }
        if(year.isEmpty()) {
        	year = movie.getYear();
        }
        if(topics.isEmpty()) {
        	topics = movie.getTopics();
        }
        return new Movie(movie.getId(), titulo, year, topics);
    }

    public String obtenerPalabraDeConsola(String mensajeEntrada) {

        System.out.print(mensajeEntrada);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String l = null;
        try {
            l = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;
    }

    public int obtenerEnteroDeConsola(String mensajeEntrada) {
        int i = 0;
        System.out.print(mensajeEntrada);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            i = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Formato no valido!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return i;
    }
}
