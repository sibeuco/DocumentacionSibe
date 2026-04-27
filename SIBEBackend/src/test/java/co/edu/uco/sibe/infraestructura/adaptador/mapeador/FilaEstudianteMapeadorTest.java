package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando;
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
class FilaEstudianteMapeadorTest {

    private FilaEstudianteMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new FilaEstudianteMapeador();
    }

    @Test
    void deberiaMapearFilaCorrectamente() {
        Row fila = mock(Row.class);
        Map<String, Integer> mapaColumnas = new HashMap<>();

        mapaColumnas.put(COLUMNA_ESTUDIANTE_NOMBRE_COMPLETO, 0);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_NUMERO_IDENTIFICACION, 1);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ID_CARNET, 2);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CORREO_INSTITUCIONAL, 3);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_FECHA_NACIMIENTO, 4);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_SEXO, 5);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_NACIONALIDAD, 6);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ESTADO_CIVIL, 7);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_DIRECCION, 8);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_MUNICIPIO_RESIDENCIA, 9);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_TELEFONO, 10);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CELULAR, 11);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CORREO_PERSONAL, 12);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_PROGRAMA_ACADEMICO, 13);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_FACULTAD, 14);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ANNO_INGRESO, 15);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_SEMESTRE_ACTUAL, 16);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CREDITOS_APROBADOS, 17);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_PROMEDIO_GENERAL, 18);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ESTADO_ACADEMICO, 19);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_MODALIDAD, 20);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_TIEMPO_LLEGADA, 21);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_MEDIO_TRANSPORTE, 22);

        for (int i = 0; i <= 22; i++) {
            Cell cell = mock(Cell.class);
            when(cell.getCellType()).thenReturn(CellType.STRING);
            when(cell.getStringCellValue()).thenReturn("valor" + i);
            when(fila.getCell(i)).thenReturn(cell);
        }

        DatosEstudianteComando resultado = mapeador.mapearFila(fila, mapaColumnas);

        assertNotNull(resultado);
        assertEquals("valor0", resultado.getNombreCompleto());
        assertEquals("valor1", resultado.getNumeroIdentificacion());
    }

    @Test
    void deberiaMapearFilaConCeldasNulas() {
        Row fila = mock(Row.class);
        Map<String, Integer> mapaColumnas = new HashMap<>();
        mapaColumnas.put(COLUMNA_ESTUDIANTE_NOMBRE_COMPLETO, 0);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_NUMERO_IDENTIFICACION, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ID_CARNET, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CORREO_INSTITUCIONAL, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_FECHA_NACIMIENTO, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_SEXO, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_NACIONALIDAD, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ESTADO_CIVIL, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_DIRECCION, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_MUNICIPIO_RESIDENCIA, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_TELEFONO, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CELULAR, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CORREO_PERSONAL, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_PROGRAMA_ACADEMICO, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_FACULTAD, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ANNO_INGRESO, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_SEMESTRE_ACTUAL, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_CREDITOS_APROBADOS, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_PROMEDIO_GENERAL, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_ESTADO_ACADEMICO, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_MODALIDAD, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_TIEMPO_LLEGADA, null);
        mapaColumnas.put(COLUMNA_ESTUDIANTE_MEDIO_TRANSPORTE, null);

        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("Nombre Test");
        when(fila.getCell(0)).thenReturn(cell);

        DatosEstudianteComando resultado = mapeador.mapearFila(fila, mapaColumnas);

        assertNotNull(resultado);
        assertEquals("Nombre Test", resultado.getNombreCompleto());
    }
}
