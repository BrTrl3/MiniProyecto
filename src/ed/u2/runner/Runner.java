package ed.u2.runner;

import ed.u2.util.CsvLoader;
import ed.u2.model.Cita;
import ed.u2.model.Insumo;
import ed.u2.model.NodoPaciente;
import ed.u2.model.Paciente;
import ed.u2.sorting.Ordenador;
import ed.u2.search.BinarySearch;
import ed.u2.search.SequentialSearch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Runner {

    private static final String RUTA_CITAS_NORMAL = "src/data/citas_100.csv";
    private static final String RUTA_CITAS_CASI = "src/data/citas_100_casi_ordenadas.csv";
    private static final String RUTA_INVENTARIO = "src/data/inventario_500_inverso.csv";
    private static final String RUTA_PACIENTES = "src/data/pacientes_500.csv";

    /**
     * Clase interna simple para almacenar las métricas de una sola corrida.
     */
    private static class RunMetrics {
        final long comparaciones;
        final long swaps;
        final long tiempoNano;

        public RunMetrics(long c, long s, long t) {
            this.comparaciones = c;
            this.swaps = s;
            this.tiempoNano = t;
        }

        public long getComparaciones() { return comparaciones; }
        public long getSwaps() { return swaps; }
        public long getTiempoNano() { return tiempoNano; }
    }


    public static void main(String[] args) {

        System.out.println("----- MINI-PROYECTO U2 ED -----");

        // 1. CARGA DE DATOS
        Cita[] citasNormal = CsvLoader.cargarCitas(RUTA_CITAS_NORMAL);
        Cita[] citasCasiOrdenadas = CsvLoader.cargarCitas(RUTA_CITAS_CASI);
        Insumo[] inventario = CsvLoader.cargarInventario(RUTA_INVENTARIO);
        NodoPaciente headPacientes = CsvLoader.cargarPacientesSLL(RUTA_PACIENTES);

        // El arreglo de citas debe ORDENARSE ONCE para las búsquedas binarias.
        Ordenador ordenadorBase = new Ordenador();
        ordenadorBase.bubbleSort(citasNormal);

        System.out.printf(
                "Datos cargados: Citas Normal (%d), Citas Casi (%d), Inventario (%d), Pacientes (SLL OK)\n",
                citasNormal.length, citasCasiOrdenadas.length, inventario.length
        );

        // =====================================================
        // TABLA A – ORDENACIÓN
        // =====================================================
        System.out.println("\n=== TABLA A: ORDENACIÓN ===");
        ejecutarTablaOrdenacion(citasCasiOrdenadas, inventario);

        // =====================================================
        // TABLA B – BÚSQUEDA
        // =====================================================
        System.out.println("\n=== TABLA B: BÚSQUEDA ===");
        ejecutarBusquedaPacientes(headPacientes);
        ejecutarBusquedaArreglos(citasNormal, inventario);

        System.out.println("\n--- FIN DE LA EJECUCIÓN ---");
    }


    // --- NUEVO MÉTODO PARA MOSTRAR EL MENÚ ---
    private static void mostrarMenu() {
        System.out.println("\n------------------------------------------");
        System.out.println(" MINI-PROYECTO – ESTRUCTURA DE DATOS U2");
        System.out.println("------------------------------------------");
        System.out.println("1. Tabla A – Ordenación");
        System.out.println("2. Tabla B – Búsqueda");
        System.out.println("3. Ejecutar todo");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }


    // ---MÉTODO PARA ENCAPSULAR LA IMPRESIÓN DE LA TABLA A ---
    private static void ejecutarTablaOrdenacion(Cita[] citasCasiOrdenadas, Insumo[] inventario) {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("A) Resultados de ordenacion (Mediana de 10 Corridas)");
        System.out.println("---------------------------------------------------------");
        System.out.printf("| %-20s | %-15s | %-15s | %-15s | %-15s |\n",
                "Dataset (n)", "Algoritmo", "Comparaciones", "Swaps", "Tiempo (ns)");
        System.out.println("---------------------------------------------------------");

        // Ejecutar los experimentos
        ejecutarExperimentosOrdenacion(citasCasiOrdenadas, "Citas (100, Casi Ordenado)");
        ejecutarExperimentosOrdenacion(inventario, "Inventario (500, Inverso)");
    }

    /**
     * Helper: Ejecuta las 10 corridas, calcula la mediana y muestra los resultados.
     */
    private static <T extends Comparable<T>> void ejecutarExperimentosOrdenacion(T[] arrOriginal, String datasetNombre) {
        // Ejecutamos y mostramos la mediana para cada uno de los 3 algoritmos.

        // Burbuja
        List<RunMetrics> resultsBubble = realizarCorridas(arrOriginal, "bubbleSort");
        mostrarMediana(datasetNombre, "BubbleSort", resultsBubble);

        // Selección
        List<RunMetrics> resultsSelection = realizarCorridas(arrOriginal, "selectionSort");
        mostrarMediana(datasetNombre, "SelectionSort", resultsSelection);

        // Inserción
        List<RunMetrics> resultsInsertion = realizarCorridas(arrOriginal, "insertionSort");
        mostrarMediana(datasetNombre, "InsertionSort", resultsInsertion);
    }

    /**
     * Realiza las 10 corridas de un algoritmo usando la instancia de Ordenador
     * y devuelve los resultados (tiempo, comp, swap).
     */
    private static <T extends Comparable<T>> List<RunMetrics> realizarCorridas(T[] arrOriginal, String metodo) {
        List<RunMetrics> resultados = new ArrayList<>();
        Ordenador ordenador = new Ordenador(); // Usamos una nueva instancia para cada algoritmo

        for (int i = 0; i < 10; i++) {
            // CLONAR EL ARREGLO es VITAL para que cada corrida comience con los datos originales
            T[] arrClone = Arrays.copyOf(arrOriginal, arrOriginal.length);

            ordenador.resetContadores(); // Limpiar métricas de la ejecución anterior

            long startTime = System.nanoTime();

            // Ejecutar el método correspondiente
            switch (metodo) {
                case "bubbleSort":
                    ordenador.bubbleSort(arrClone);
                    break;
                case "selectionSort":
                    ordenador.selectionSort(arrClone);
                    break;
                case "insertionSort":
                    ordenador.insertionSort(arrClone);
                    break;
                default:
                    throw new IllegalArgumentException("Método de ordenación no soportado");
            }

            long endTime = System.nanoTime();
            long tiempo = endTime - startTime;

            // Recoger las métricas después de la ejecución
            RunMetrics result = new RunMetrics(ordenador.getComparaciones(), ordenador.getSwaps(), tiempo);
            resultados.add(result);
        }
        return resultados;
    }

    /**
     * Calcula la mediana del tiempo, comparaciones y swaps de las 7 mejores corridas
     * e imprime la fila de la tabla.
     */
    private static void mostrarMediana(String dataset, String algoritmo, List<RunMetrics> results) {
        // 1. Ordenar la lista por TIEMPO (para descartar las 3 corridas más lentas)
        Collections.sort(results, (r1, r2) -> Long.compare(r1.getTiempoNano(), r2.getTiempoNano()));

        // 2. Tomar solo las 7 corridas más rápidas (índices 0 a 6)
        List<RunMetrics> best7 = results.subList(0, 7);

        // 3. Obtener las listas de métricas de las 7 mejores
        List<Long> tiempos = new ArrayList<>();
        List<Long> comps = new ArrayList<>();
        List<Long> swaps = new ArrayList<>();

        for (RunMetrics r : best7) {
            tiempos.add(r.getTiempoNano());
            comps.add(r.getComparaciones());
            swaps.add(r.getSwaps());
        }

        // 4. Ordenar las métricas individualmente para encontrar la mediana
        Collections.sort(tiempos);
        Collections.sort(comps);
        Collections.sort(swaps);

        // La mediana es el valor en la posición central (índice 3 de los 7 elementos)
        long medianTime = tiempos.get(3);
        long medianComps = comps.get(3);
        long medianSwaps = swaps.get(3);

        String notas = "";
        if (dataset.contains("Casi")) {
            notas = algoritmo.equals("InsertionSort") ? "Mejor caso ≈ O(n) - Insertion gana" : "Mayor número de swaps";
        } else { // Inverso
            notas = algoritmo.equals("SelectionSort") ? "Comparaciones constantes ≈ n(n-1)/2" : "Penalización cuadrática en swaps";
        }

        // 5. Imprimir la fila de la tabla
        System.out.printf("| %-20s | %-15s | %-15d | %-15d | %-15d |\n",
                dataset, algoritmo, medianComps, medianSwaps, medianTime);
    }

    // -------------------------------------------------------------------
    // MÉTODOS DE BÚSQUEDA
    // -------------------------------------------------------------------

    private static void imprimirTablaBusqueda(String coleccion, String clave, String metodo, String salida) {
        // Usamos printf para alinear las columnas
        System.out.printf("| %-18s | %-25s | %-15s | %s\n",
                coleccion, clave, metodo, salida);
    }

    private static void ejecutarBusquedaPacientes(NodoPaciente head) {
        System.out.println("\n--- Búsqueda en SLL y Arreglos ---");

        // Encabezado de la Tabla B
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-18s | %-25s | %-15s | %s\n", "Colección", "Clave/Predicado", "Método", "Salida");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");

        String apellidoBuscado = "Jaramillo";
        int prioridadBuscada = 1;

        // --- BÚSQUEDA EN SLL PACIENTES ---

        // 1. Primera coincidencia
        NodoPaciente first = SequentialSearch.findFirstPacienteByApellido(head, apellidoBuscado);
        String outFirst = first != null ? first.getDato().toString() : "No encontrado";
        imprimirTablaBusqueda("SLL Pacientes", "Apellido '" + apellidoBuscado + "' (1er)", "Secuencial", outFirst);

        // 2. Última coincidencia
        NodoPaciente last = SequentialSearch.findLastPacienteByApellido(head, apellidoBuscado);
        String outLast = last != null ? last.getDato().toString() : "No encontrado";
        imprimirTablaBusqueda("SLL Pacientes", "Apellido '" + apellidoBuscado + "' (Últ)", "Secuencial", outLast);

        // 3. FindAll (Prioridad == 1)
        List<Paciente> prioridadAlta = SequentialSearch.findAllPacientesByPrioridad(head, prioridadBuscada);
        String outFindAll = "Total: " + prioridadAlta.size() + ". Ej: " + (prioridadAlta.isEmpty() ? "N/A" : prioridadAlta.get(0).getApellido());
        imprimirTablaBusqueda("SLL Pacientes", "Prioridad == " + prioridadBuscada, "Secuencial (FindAll)", outFindAll);
    }

    private static void ejecutarBusquedaArreglos(Cita[] citasOrdenadas, Insumo[] inventarioOrdenado) {
        // La ejecución de Búsqueda de arreglos debe ir DESPUÉS de ejecutarBusquedaPacientes
        // para que la tabla no se rompa (se adjunta aquí, pero se llama al final del main)

        Ordenador ordenadorInv = new Ordenador();
        ordenadorInv.selectionSort(inventarioOrdenado);  // O insertion, pero selection mantiene comparaciones fijas
        // --- BÚSQUEDA EN ARREGLOS ---

        // 1. Búsqueda Lineal Centinela (Inventario)
        Insumo claveCentinela = new Insumo("", "", 50);
        int idxCentinela = SequentialSearch.linearSearchCentinela(inventarioOrdenado, claveCentinela);
        String outCentinela = idxCentinela != -1 ? "Índice " + idxCentinela + " (Insumo: " + inventarioOrdenado[idxCentinela].getStock() + ")" : "No encontrado";
        imprimirTablaBusqueda("Arreglo Inventario", "Stock: 50", "Centinela", outCentinela);

        // 2. Búsqueda Binaria (Citas ordenadas)
        Cita claveBinaria = new Cita("", "", "2025-03-14T11:30");
        int idxBinaria = BinarySearch.find(citasOrdenadas, claveBinaria);
        String outBinaria = idxBinaria != -1 ? "Índice " + idxBinaria + " (Cita: " + citasOrdenadas[idxBinaria].getFechaHora().toString() + ")" : "No encontrado";
        //String outBinaria = idxBinaria != -1? "Índice " + idxBinaria + " (no garantizado por duplicados)": "No encontrado";
        imprimirTablaBusqueda("Arreglo Citas", "Fecha exacta", "Binaria", outBinaria);

        // 3. Lower Bound (Inventario - Rangos)
        Insumo claveLowerBound = new Insumo("N/A", "Clave", 200);
        int idxLower = BinarySearch.lowerBound(inventarioOrdenado, claveLowerBound);
        String outLower = idxLower < inventarioOrdenado.length ? "Índice " + idxLower + " (Stock: " + inventarioOrdenado[idxLower].getStock() + ")" : "Índice fuera de límites";
        imprimirTablaBusqueda("Arreglo Inventario", "Stock >= 200 (Lower Bound)", "Binaria", outLower);

        // Cierre de tabla
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }
}