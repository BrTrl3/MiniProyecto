#  MiniProyecto U2 – Agenda e Inventario Inteligentes

**Asignatura:** Estructura de Datos  
**Unidad:** U2 – Ordenación y Búsqueda  
**Lenguaje:** Java (JDK 17/21)  
**Contexto:** Software de administración para el Hospital Veterinario de la UNL

---

##  Descripción general

Este miniproyecto implementa un **módulo inteligente de gestión de citas, pacientes e inventario**, cuyo objetivo principal es **aplicar, comparar y justificar algoritmos de ordenación y búsqueda** estudiados en la Unidad 2.

El sistema carga datos desde archivos CSV, los organiza utilizando distintos métodos de ordenación y permite realizar búsquedas eficientes según la estructura de datos empleada (arreglos o listas simplemente enlazadas).

---

##  Objetivos

* Aplicar **métodos de ordenación**: Burbuja, Selección e Inserción.
* Implementar **búsquedas secuenciales** (primera, última, findAll y centinela).
* Aplicar **búsqueda binaria** en arreglos previamente ordenados.
* Comparar el desempeño de los algoritmos según:

    * Tipo de estructura (arreglo vs SLL).
    * Estado de los datos (casi ordenados, inversos, con duplicados).
* Analizar **casos borde** y documentar decisiones técnicas.

---

##  Estructura del proyecto

```text
src/
 └── main/
     └── java/
         └── ed/
             └── u2/
                 ├── model/      # Clases de dominio (Cita, Paciente, Inventario)
                 ├── sorting/    # Algoritmos de ordenación instrumentados
                 ├── search/     # Algoritmos de búsqueda
                 └── demo/       # Runner / Main de ejecución
```

---

##  Funcionalidades implementadas

###  Agenda de citas (Arreglos)

* Carga de archivos:

    * `citas_100.csv`
    * `citas_100_casi_ordenadas.csv`
* Ordenación por **fecha y hora**:

    * Inserción (principal)
    * Comparación con Burbuja y Selección
* Búsquedas:

    * Búsqueda binaria exacta por `fechaHora`
    * Búsqueda por rangos usando `lowerBound` y `upperBound`

---

###  Pacientes (Lista Simplemente Enlazada – SLL)

* Estructura: `(id, apellido, prioridad)`
* Búsquedas secuenciales:

    * Primera ocurrencia por apellido
    * Última ocurrencia por apellido
    * `findAll` para pacientes con `prioridad == 1`

---

###  Inventario (Arreglos)

* Carga desde `inventario_500_inverso.csv`
* Ordenación por **stock**
* Búsqueda binaria para consultar cantidades específicas

---

##  Instrumentación y medición

Todos los algoritmos de ordenación están instrumentados para medir:

* Número de **comparaciones**
* Número de **swaps/movimientos**
* **Tiempo de ejecución** (System.nanoTime)

 Metodología de medición:

* 10 ejecuciones por caso
* Se descartan las primeras 3
* Se reporta la **mediana**

---

## Casos borde considerados

*  **Datos duplicados**:

    * La búsqueda binaria estándar no garantiza el índice exacto.
    * Se implementan límites (`lowerBound`, `upperBound`) cuando es necesario.

*  **Arreglos inversos**:

    * Inserción y Burbuja presentan peor rendimiento.
    * Selección mantiene comparaciones constantes.

*  **Datos casi ordenados**:

    * Inserción supera ampliamente a Burbuja y Selección.

*  **Precondición de búsqueda binaria**:

    * El arreglo debe estar previamente ordenado.
    * Se valida antes de ejecutar la búsqueda.

---

## ️ Ejecución del programa

1. Asegúrese de tener **JDK 17 o superior**.
2. Compile el proyecto.
3. Ejecute la clase `Main` o `Demo`.
4. El programa:

    * Carga los CSV
    * Ordena los datos
    * Ejecuta búsquedas
    * Muestra resultados mínimos por consola

---

##  Conclusión

<p> El desarrollo de este miniproyecto permitió comprobar de manera práctica que no existe un algoritmo de ordenación o búsqueda universalmente óptimo, sino que su eficiencia depende del tipo de estructura de datos utilizada y de las características del conjunto de información procesado; mediante la instrumentación y medición de comparaciones, movimientos y tiempo de ejecución, fue posible evidenciar cómo algoritmos como Inserción se benefician de datos casi ordenados, mientras que Selección mantiene un comportamiento estable ante escenarios adversos, reforzando así la importancia de analizar el contexto antes de tomar decisiones de implementación en sistemas reales. </p>
---
