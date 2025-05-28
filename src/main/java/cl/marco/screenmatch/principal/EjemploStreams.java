package cl.marco.screenmatch.principal;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList(
                "Andres",
                "Pepe",
                "Maria",
                "Paco",
                "Luis",
                "Juan",
                "Antonio",
                "Sandra",
                "Sofia",
                "Laura");
        nombres.stream()
                .sorted()
                .filter(n -> n.startsWith("L"))
                .map(m -> m.toUpperCase())
                .forEach(System.out::println);
    }
}
