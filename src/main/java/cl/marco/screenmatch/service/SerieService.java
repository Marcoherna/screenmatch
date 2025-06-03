package cl.marco.screenmatch.service;

import cl.marco.screenmatch.dto.EpisodioDTO;
import cl.marco.screenmatch.dto.SerieDTO;
import cl.marco.screenmatch.model.Categoria;
import cl.marco.screenmatch.model.Serie;
import cl.marco.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries() {
        return convierteDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public List<SerieDTO> convierteDatos(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(),
                        s.getGenero(), s.getActores(), s.getImagen(), s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public SerieDTO obternerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO( s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(),
                     s.getGenero(), s.getActores(), s.getImagen(), s.getSinopsis());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodeo()))
                    .collect(Collectors.toList());
        }
        return null;

    }

    public List<EpisodioDTO> obtenerTemporadaPorNumero(Long id, Integer numeroTemporada) {
        return repository.obtenerTemporadaPorNumero(id, numeroTemporada).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodeo()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String nombreGenero) {
        Categoria categoria = Categoria.fromEspanol(nombreGenero);
        return  convierteDatos(repository.findByGenero(categoria));
    }
}
