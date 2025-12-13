package ed.u2.search;

import ed.u2.model.Paciente;
import ed.u2.model.NodoPaciente;

import java.util.ArrayList;
import java.util.List;

public class SequentialSearch {

    // -------------------------------------------------------------------
    // BUSQUEDA EN LISTA ENLAZADA SIMPLE (SLL - Pacientes)
    // -------------------------------------------------------------------

    /**
     * BUSQUEDA LINEAL: Encuentra la PRIMERA coincidencia por Apellido en la SLL de Pacientes.
     * @param head La cabeza de la SLL.
     * @param apellidoBuscado El apellido a buscar.
     * @return El NodoPaciente que coincide primero, o null si no se encuentra.
     */
    public static NodoPaciente findFirstPacienteByApellido(NodoPaciente head, String apellidoBuscado) {
        NodoPaciente actual = head;
        while (actual != null) {
            // Se usa equalsIgnoreCase para buscar sin distinguir mayúsculas/minúsculas
            if (actual.getDato().getApellido().equalsIgnoreCase(apellidoBuscado)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null; // No encontrado
    }

    /**
     * BUSQUEDA LINEAL: Encuentra la ÚLTIMA coincidencia por Apellido en la SLL de Pacientes.
     */
    public static NodoPaciente findLastPacienteByApellido(NodoPaciente head, String apellidoBuscado) {
        NodoPaciente actual = head;
        NodoPaciente ultimoEncontrado = null;
        while (actual != null) {
            if (actual.getDato().getApellido().equalsIgnoreCase(apellidoBuscado)) {
                ultimoEncontrado = actual; // Almacena la coincidencia y sigue buscando
            }
            actual = actual.getSiguiente();
        }
        return ultimoEncontrado;
    }

    /**
     * BUSQUEDA LINEAL FIND ALL: Encuentra todos los pacientes con una prioridad dada (ej: prioridad == 1).
     */
    public static List<Paciente> findAllPacientesByPrioridad(NodoPaciente head, int prioridadBuscada) {
        List<Paciente> resultados = new ArrayList<>();
        NodoPaciente actual = head;
        while (actual != null) {
            if (actual.getDato().getPrioridad() == prioridadBuscada) {
                resultados.add(actual.getDato());
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }

    // -------------------------------------------------------------------
    // BUSQUEDA EN ARREGLO (Centinela)
    // -------------------------------------------------------------------

    /**
     * BUSQUEDA LINEAL CENTINELA: Mejora la eficiencia del bucle eliminando la verificación del límite (i < n).
     * @param arr Arreglo a buscar (Cita[] o Insumo[]).
     * @param key El objeto Comparable a buscar.
     * @return El índice de la primera coincidencia, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int linearSearchCentinela(T[] arr, T key) {
        int n = arr.length;
        if (n == 0) return -1;

        T last = arr[n - 1]; // Guardar el último elemento original
        arr[n - 1] = key;    // Colocar el centinela

        int i = 0;
        // Bucle sin la condición i < n-1 (optimización)
        while (arr[i].compareTo(key) != 0) {
            i++;
        }

        arr[n - 1] = last; // Restaurar el último elemento

        // La clave se encontró:
        // 1. Si i < n-1: En el cuerpo del arreglo.
        // 2. Si i == n-1 y el valor restaurado coincide con la clave: En la última posición.
        if (i < n - 1 || arr[n - 1].compareTo(key) == 0) {
            return i;
        } else {
            return -1; // No encontrado
        }
    }
}
