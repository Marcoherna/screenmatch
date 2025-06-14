package cl.marco.screenmatch.principal;

import cl.marco.screenmatch.model.*;
import cl.marco.screenmatch.repository.SerieRepository;
import cl.marco.screenmatch.service.ConsumoAPI;
import cl.marco.screenmatch.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);

    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String URL_BASE = "http://www.omdbapi.com/?t=";

    private  final String API_KEY = "&apikey=9f665f92";

    private ConvierteDatos conversor = new ConvierteDatos();

    private List<Serie> series;

    private SerieRepository repositorio;

    private Optional<Serie> serieBuscada;


    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1- Buscar Serie
                    2- Buscar Episodio
                    3- Mostrar series buscadas
                    4- Buscar serie por titulo
                    5- Top 5 series con mejor evaluación
                    6- Mostrar series por categoría
                    7- Mostrar series por temporadas y evaluación
                    8- Mostrar episodios por nombre
                    9- Mostrar top 5 episodios de una serie
                    0- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodio();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    mostrarSeriesPorTemporadasYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorNombre();
                    break;
                case 9:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando programa...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosSerie buscarSerie() {
        System.out.println("Ingrese el nombre de la serie: ");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(
                URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodio() {
        mostrarSeriesBuscadas();
        System.out.println("Escriba el nombre de la serie: ");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieEncontrada = serie.get();

            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(
                        URL_BASE + serieEncontrada.getTitulo()
                                .replace(" ", "+") + "&Season=" + i + API_KEY);
                DatosTemporadas datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }

    }

    private void buscarSerieWeb(){
        DatosSerie datos = buscarSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {

        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    public void buscarSeriePorTitulo() {
        System.out.println("Ingrese el titulo de la serie: ");
        String nombreSerie = teclado.nextLine();

        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: " + serieBuscada.get().getTitulo());
        } else {
            System.out.println("No se encontró ninguna serie con ese título.");
        }

    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s ->
                System.out.println("Serie: " + s.getTitulo() + " - " + "Evaluación: " + s.getEvaluacion()));

    }
    private void buscarSeriesPorCategoria() {
        System.out.println("Ingrese la categoría de la serie: ");
        String genero = teclado.nextLine();

        var categoria = Categoria.fromEspanol(genero);

        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);

        System.out.println("Series de la categoría " + genero + ":");
        seriesPorCategoria.forEach(System.out::println);

    }

    private void mostrarSeriesPorTemporadasYEvaluacion() {
        System.out.println("Ingrese la cantidad de temporadas: ");
        int temporadas = teclado.nextInt();
        System.out.println("Ingrese la evaluación: ");
        Double evaluacion = teclado.nextDouble();

        List<Serie> seriesPorTemporadasYEvaluacion = repositorio.seriesPorTemporadasYEvaluacion(temporadas, evaluacion);

        System.out.println("Series con " + temporadas + " temporadas o menos y una evaluación mayor a " + evaluacion + ":");
        seriesPorTemporadasYEvaluacion.forEach(System.out::println);

    }

    private void buscarEpisodiosPorNombre() {
        System.out.println("Ingrese el nombre del episodio: ");
        String nombre = teclado.nextLine();

        List<Episodio> episodios = repositorio.episodiosPorNombre(nombre);

        episodios.forEach(e ->
                System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluacion: %s",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodeo(), e.getEvaluacion()));
    }

    private void buscarTop5Episodios() {
        buscarSeriePorTitulo();

        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluacion: %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
        }
    }


}
