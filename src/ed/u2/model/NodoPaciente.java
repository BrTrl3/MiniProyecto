package ed.u2.model;

public class NodoPaciente {
    private Paciente dato;
    private NodoPaciente siguiente;

    public NodoPaciente(Paciente dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    // Getters y Setters
    public Paciente getDato() { return dato; }
    public NodoPaciente getSiguiente() { return siguiente; }

    public void setDato(Paciente dato) { this.dato = dato; }
    public void setSiguiente(NodoPaciente siguiente) { this.siguiente = siguiente; }
}