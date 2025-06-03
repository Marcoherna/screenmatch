//package cl.marco.screenmatch;
//
//import cl.marco.screenmatch.principal.Principal;
//import cl.marco.screenmatch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationConsola implements CommandLineRunner {
//	@Autowired
//	private SerieRepository repository;
//
//	public static void main(String[] args) {
//
//		SpringApplication.run(ScreenmatchApplicationConsola.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Principal principal = new Principal(repository);
//		principal.mostrarMenu();
////		EjemploStreams ejemploStreams = new EjemploStreams();
////		ejemploStreams.muestraEjemplo();
//	}
//}