package cl.marco.screenmatch.repository;

import cl.marco.screenmatch.model.Categoria;
import cl.marco.screenmatch.model.Episodio;
import cl.marco.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria genero);

    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :temporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadasYEvaluacion(int temporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombre%\n")
    List<Episodio> episodiosPorNombre(String nombre);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.fechaLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();
}




