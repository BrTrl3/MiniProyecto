package ed.u2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cita implements Comparable<Cita> {
    private String id;
    private String pacienteNombre;
    private LocalDateTime fechaHora; // Clave para la ordenación

    // Constructor que acepta String para la fecha
    public Cita(String id, String apellido, String fechaHoraStr) {
        this.id = id;
        this.pacienteNombre = apellido;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        this.fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getPacienteNombre() { return pacienteNombre; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    // El SetFechaHora puede ser útil si se modifica la cita
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    // IMPLEMENTACIÓN DE COMPARABLE (Ordenación por fechaHora)
    @Override
    public int compareTo(Cita otra) {
        // Devuelve negativo si esta fecha es antes
        return this.fechaHora.compareTo(otra.fechaHora);
    }

    @Override
    public String toString() {
        return String.format("Cita{%s | %s | %s}", id, pacienteNombre, fechaHora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}