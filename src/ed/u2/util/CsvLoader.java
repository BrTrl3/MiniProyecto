package ed.u2.util;

import ed.u2.model.Cita;
import ed.u2.model.Insumo;
import ed.u2.model.Paciente;
import ed.u2.model.NodoPaciente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    // Cambia esto a ";" si tus archivos usan punto y coma como separador
    private static final String SEPARATOR = ";";

    /**
     * Carga archivos de Citas (citas_100.csv o citas_100_casi_ordenadas.csv)
     * Columnas: id, apellido, fechaHora. Devuelve un Arreglo (Requisito del proyecto)
     */
    public static Cita[] cargarCitas(String rutaArchivo) {
        List<Cita> listaTemporal = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Descartar encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARATOR);
                if (datos.length >= 3) {
                    Cita c = new Cita(datos[0].trim(), datos[1].trim(), datos[2].trim());
                    listaTemporal.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println("Error I/O al leer citas: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error de formato en fecha/datos de Cita. Verifique el formato: " + e.getMessage());
        }

        return listaTemporal.toArray(new Cita[0]);
    }

    /**
     * Carga archivo de Inventario (inventario_500_inverso.csv)
     * Columnas: id, insumo, stock. Devuelve un Arreglo (Requisito del proyecto)
     */
    public static Insumo[] cargarInventario(String rutaArchivo) {
        List<Insumo> listaTemporal = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Descartar encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARATOR);
                if (datos.length >= 3) {
                    // El stock es un entero
                    int stock = Integer.parseInt(datos[2].trim());
                    Insumo insumo = new Insumo(datos[0].trim(), datos[1].trim(), stock);
                    listaTemporal.add(insumo);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer inventario o parsear stock: " + e.getMessage());
        }

        return listaTemporal.toArray(new Insumo[0]);
    }

    /**
     * Carga archivo de Pacientes (pacientes_500.csv)
     * Columnas: id, apellido, prioridad. Devuelve la CABEZA (head) de la Lista Enlazada Simple (SLL).
     */
    public static NodoPaciente cargarPacientesSLL(String rutaArchivo) {
        NodoPaciente cabeza = null;
        NodoPaciente cola = null;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Descartar encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARATOR);
                if (datos.length >= 3) {
                    int prioridad = Integer.parseInt(datos[2].trim());
                    Paciente p = new Paciente(datos[0].trim(), datos[1].trim(), prioridad);
                    NodoPaciente nuevoNodo = new NodoPaciente(p);

                    // Lógica de inserción al final de la SLL
                    if (cabeza == null) {
                        cabeza = nuevoNodo;
                    } else {
                        cola.setSiguiente(nuevoNodo);
                    }
                    cola = nuevoNodo;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer pacientes o parsear prioridad: " + e.getMessage());
        }

        return cabeza;
    }
}
