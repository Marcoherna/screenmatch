package cl.marco.screenmatch;

import cl.marco.screenmatch.model.DatosEpisodio;
import cl.marco.screenmatch.model.DatosSerie;
import cl.marco.screenmatch.model.DatosTemporadas;
import cl.marco.screenmatch.principal.EjemploStreams;
import cl.marco.screenmatch.principal.Principal;
import cl.marco.screenmatch.repository.SerieRepository;
import cl.marco.screenmatch.service.ConsumoAPI;
import cl.marco.screenmatch.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication {


	public static void main(String[] args) {

		SpringApplication.run(ScreenmatchApplication.class, args);
	}



}