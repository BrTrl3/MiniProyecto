package ed.u2.sorting;

import java.util.Arrays;

public class Ordenador {
    // --- Atributos para la medición (Requisito del Proyecto) ---
    private long comparaciones;
    private long swaps; // También se usa para contar moves en Inserción

    // Constructor
    public Ordenador() {
        // Inicializar contadores cada vez que se crea un nuevo objeto o antes de cada corrida
        resetContadores();
    }

    /**
     * Resetea las métricas antes de ejecutar un nuevo algoritmo.
     */
    public void resetContadores() {
        this.comparaciones = 0;
        this.swaps = 0;
    }

    // --- Getters de las métricas ---
    public long getComparaciones() {
        return comparaciones;
    }

    public long getSwaps() {
        return swaps;
    }

    // ------------------------------------------------------------------
    //                         ALGORITMOS
    // ------------------------------------------------------------------

    /**
     * Algoritmo de Ordenación por BURBUJA (Bubble Sort)
     * Mejor caso: O(n) si está casi ordenado (con bandera). Peor caso: O(n^2).
     * @param arr Arreglo a ordenar.
     */
    public <T extends Comparable<T>> void bubbleSort(T[] arr) {
        resetContadores();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                // Contamos la comparación
                comparaciones++;
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    // Intercambio (Swap)
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            // Optimización: si no hubo swaps, está ordenado
            if (!swapped) break;
        }
    }

    /**
     * Algoritmo de Ordenación por SELECCIÓN (Selection Sort)
     * Siempre O(n^2) en comparaciones. Mínimos swaps.
     * @param arr Arreglo a ordenar.
     */
    public <T extends Comparable<T>> void selectionSort(T[] arr) {
        resetContadores();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            // Buscar el elemento mínimo en el resto del arreglo
            for (int j = i + 1; j < n; j++) {
                // Contamos la comparación
                comparaciones++;
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            // Intercambiar el mínimo encontrado con el elemento actual (posición i)
            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
    }

    /**
     * Algoritmo de Ordenación por INSERCIÓN (Insertion Sort)
     * Mejor caso: O(n) (casi ordenado). Peor caso: O(n^2) (inverso).
     * @param arr Arreglo a ordenar.
     */
    public <T extends Comparable<T>> void insertionSort(T[] arr) {
        resetContadores();
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            T key = arr[i];
            int j = i - 1;

            // Mover elementos mayores que la llave hacia adelante
            // Bucle 'while' realiza comparaciones y movimientos ('moves')
            while (j >= 0) {
                comparaciones++; // Contamos la comparación
                if (arr[j].compareTo(key) > 0) {
                    arr[j + 1] = arr[j]; // Movimiento (Move)
                    swaps++; // Usamos 'swaps' para contar movimientos/moves en Inserción
                    j = j - 1;
                } else {
                    // Ya encontró su lugar
                    break;
                }
            }
            arr[j + 1] = key; // Inserción final
        }
    }

    // ------------------------------------------------------------------
    //                         UTILIDADES
    // ------------------------------------------------------------------

    /**
     * Método auxiliar para intercambiar dos elementos en el arreglo.
     * Incrementa el contador de swaps.
     */
    private <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        this.swaps++; // Contamos el intercambio
    }
}