package cl.marco.screenmatch.service;

import cl.marco.screenmatch.dto.SerieDTO;
import cl.marco.screenmatch.model.Serie;
import cl.marco.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .map(s -> new SerieDTO(s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(),
                        s.getGenero(), s.getActores(), s.getImagen(), s.getSinopsis()))
                .collect(Collectors.toList());
    }

}
