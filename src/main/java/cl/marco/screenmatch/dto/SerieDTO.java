package cl.marco.screenmatch.dto;

import cl.marco.screenmatch.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        Categoria genero,
        String actores,
        String imagen,
        String sinopsis
) {
}
