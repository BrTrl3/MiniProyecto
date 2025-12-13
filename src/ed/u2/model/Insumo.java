package ed.u2.model;

public class Insumo implements Comparable<Insumo> {
    private String id;
    private String nombre; // Corresponde a la columna 'insumo'
    private int stock;     // Clave para ordenación y búsqueda binaria

    public Insumo(String id, String nombre, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getStock() { return stock; }

    // Setters (útil para modificar stock)
    public void setStock(int stock) { this.stock = stock; }

    // IMPLEMENTACIÓN DE COMPARABLE (Ordenación por stock)
    @Override
    public int compareTo(Insumo otro) {
        // Orden natural ascendente por cantidad de stock
        return Integer.compare(this.stock, otro.stock);
    }

    @Override
    public String toString() {
        return String.format("Insumo{%s | %s | Stock: %d}", id, nombre, stock);
    }
}
