package ed.u2.model;

public class Paciente {
    private String id;
    private String apellido; // Clave para búsqueda secuencial (primera/última)
    private int prioridad;   // Clave para findAll (prioridad == 1)

    public Paciente(String id, String apellido, int prioridad) {
        this.id = id;
        this.apellido = apellido;
        this.prioridad = prioridad;
    }

    // Getters
    public String getId() { return id; }
    public String getApellido() { return apellido; }
    public int getPrioridad() { return prioridad; }

    @Override
    public String toString() {
        return String.format("Paciente{%s | Apellido: %s | Prio: %d}", id, apellido, prioridad);
    }
}
