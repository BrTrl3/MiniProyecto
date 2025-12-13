package ed.u2.search;

public class BinarySearch {

    /**
     * BUSQUEDA BINARIA ITERATIVA: Encuentra la primera ocurrencia de la clave en un arreglo ordenado.
     * @param arr El arreglo DEBE estar ordenado.
     * @param key El objeto Comparable a buscar.
     * @return El índice de la primera coincidencia, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int find(T[] arr, T key) {
        int low = 0;
        int high = arr.length - 1;
        int firstIndex = -1; // Almacenará la primera ocurrencia

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparacion = arr[mid].compareTo(key);

            if (comparacion == 0) {
                // Encontrado. Almacenamos el índice y seguimos buscando a la izquierda
                // para asegurar que encontramos la PRIMERA ocurrencia (Requisito bounds).
                firstIndex = mid;
                high = mid - 1;
            } else if (comparacion < 0) {
                // La clave es mayor
                low = mid + 1;
            } else {
                // La clave es menor
                high = mid - 1;
            }
        }
        return firstIndex;
    }

    /**
     * BINARY SEARCH BOUNDS (Lower Bound): Encuentra el índice del límite inferior.
     * Este es el índice del primer elemento MAYOR O IGUAL que la clave.
     * (Requerido para la discusión de duplicados y rangos).
     * @param arr El arreglo DEBE estar ordenado.
     * @param key El objeto Comparable que define el límite inferior del rango.
     * @return El índice del lower bound.
     */
    public static <T extends Comparable<T>> int lowerBound(T[] arr, T key) {
        int low = 0;
        int high = arr.length;
        int result = arr.length; // Si no se encuentra, devuelve arr.length (fuera de límites)

        while (low < high) {
            int mid = low + (high - low) / 2;

            // Si arr[mid] es mayor o igual a la clave, podría ser el límite.
            if (arr[mid].compareTo(key) >= 0) {
                result = mid; // Guardamos el potencial resultado
                high = mid;   // Buscamos a la izquierda (en la mitad inferior) para ver si hay uno más pequeño
            } else {
                // Si arr[mid] es menor, la clave está a la derecha
                low = mid + 1;
            }
        }
        return result;
    }
}
