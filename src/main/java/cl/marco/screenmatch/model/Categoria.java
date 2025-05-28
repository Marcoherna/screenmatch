package cl.marco.screenmatch.model;

public enum Categoria {
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    CRIMEN("Crime", "Crimen"),
    DRAMA("Drama", "Drama");

    private String categoriaOmdb;
    private String categoriaEspanol;

    Categoria (String categoriaOmdb, String categoriaEspanol) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static  Categoria fromString(String texto) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(texto)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No existe categoría para " + texto);
    }

    public static  Categoria fromEspanol(String texto) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspanol.equalsIgnoreCase(texto)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No existe categoría para " + texto);
    }
}
