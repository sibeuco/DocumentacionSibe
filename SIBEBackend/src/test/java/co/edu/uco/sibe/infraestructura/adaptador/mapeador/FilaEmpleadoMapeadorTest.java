package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.aplicacion.comando.DatosEmpleadoComando;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static co.edu.uco.sibe.dominio.transversal.constante.ArchivoConstante.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilaEmpleadoMapeadorTest {

    private FilaEmpleadoMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new FilaEmpleadoMapeador();
    }

    @Test
    void deberiaMapearFilaCorrectamente() {
        Row fila = mock(Row.class);
        Map<String, Integer> mapaColumnas = new HashMap<>();

        mapaColumnas.put(COLUMNA_EMPLEADO_NOMBRE, 0);
        mapaColumnas.put(COLUMNA_EMPLEADO_GENERO, 1);
        mapaColumnas.put(COLUMNA_EMPLEADO_IDENTIFICACION, 2);
        mapaColumnas.put(COLUMNA_EMPLEADO_ID_CARNET, 3);
        mapaColumnas.put(COLUMNA_EMPLEADO_COD_CLASIFICACION_7, 4);
        mapaColumnas.put(COLUMNA_EMPLEADO_GRUPO_RELACION_LABORAL, 5);
        mapaColumnas.put(COLUMNA_EMPLEADO_COD_CENTRO_COSTOS, 6);
        mapaColumnas.put(COLUMNA_EMPLEADO_CENTRO_COSTOS_N1, 7);
        mapaColumnas.put(COLUMNA_EMPLEADO_COD_CIUDAD_RESIDENCIA, 8);
        mapaColumnas.put(COLUMNA_EMPLEADO_CIUDAD_RESIDENCIA, 9);

        for (int i = 0; i <= 9; i++) {
            Cell cell = mock(Cell.class);
            when(cell.getCellType()).thenReturn(CellType.STRING);
            when(cell.getStringCellValue()).thenReturn("valor" + i);
            when(fila.getCell(i)).thenReturn(cell);
        }

        DatosEmpleadoComando resultado = mapeador.mapearFila(fila, mapaColumnas);

        assertNotNull(resultado);
        assertEquals("valor0", resultado.getNombre());
        assertEquals("valor1", resultado.getGenero());
        assertEquals("valor2", resultado.getIdentificacion());
        assertEquals("valor3", resultado.getIdCarnet());
    }

    @Test
    void deberiaMapearFilaConCeldasNulas() {
        Row fila = mock(Row.class);
        Map<String, Integer> mapaColumnas = new HashMap<>();
        mapaColumnas.put(COLUMNA_EMPLEADO_NOMBRE, 0);
        mapaColumnas.put(COLUMNA_EMPLEADO_GENERO, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_IDENTIFICACION, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_ID_CARNET, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_COD_CLASIFICACION_7, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_GRUPO_RELACION_LABORAL, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_COD_CENTRO_COSTOS, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_CENTRO_COSTOS_N1, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_COD_CIUDAD_RESIDENCIA, null);
        mapaColumnas.put(COLUMNA_EMPLEADO_CIUDAD_RESIDENCIA, null);

        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("Nombre Empleado");
        when(fila.getCell(0)).thenReturn(cell);

        DatosEmpleadoComando resultado = mapeador.mapearFila(fila, mapaColumnas);

        assertNotNull(resultado);
        assertEquals("Nombre Empleado", resultado.getNombre());
    }
}
