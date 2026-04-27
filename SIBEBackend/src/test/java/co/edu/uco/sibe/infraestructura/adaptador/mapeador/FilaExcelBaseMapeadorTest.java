package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static org.junit.jupiter.api.Assertions.*;

class FilaExcelBaseMapeadorTest {

    private FilaExcelBaseMapeador<String> mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new FilaExcelBaseMapeador<>() {
            @Override
            public String mapearFila(Row fila, Map<String, Integer> mapaColumnas) {
                return obtenerValorCelda(fila, mapaColumnas.get("COL"));
            }
        };
    }

    @Test
    void deberiaRetornarVacioCuandoIndiceEsNull() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Row fila = wb.createSheet().createRow(0);
            fila.createCell(0).setCellValue("dato");

            String resultado = mapeador.mapearFila(fila, Map.of());

            assertEquals(VACIO, resultado);
        }
    }

    @Test
    void deberiaRetornarVacioCuandoCeldaEsNull() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Row fila = wb.createSheet().createRow(0);

            String resultado = mapeador.mapearFila(fila, Map.of("COL", 5));

            assertEquals(VACIO, resultado);
        }
    }

    @Test
    void deberiaRetornarValorTexto() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Row fila = wb.createSheet().createRow(0);
            fila.createCell(0).setCellValue("  Hola Mundo  ");

            String resultado = mapeador.mapearFila(fila, Map.of("COL", 0));

            assertEquals("Hola Mundo", resultado);
        }
    }

    @Test
    void deberiaRetornarValorNumericoFormateado() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Row fila = wb.createSheet().createRow(0);
            Cell celda = fila.createCell(0);
            celda.setCellValue(12345.0);

            String resultado = mapeador.mapearFila(fila, Map.of("COL", 0));

            assertNotNull(resultado);
            assertFalse(resultado.isEmpty());
        }
    }

    @Test
    void deberiaRetornarFechaFormateadaCuandoCeldaEsFecha() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet();
            Row fila = sheet.createRow(0);
            Cell celda = fila.createCell(0);

            CellStyle dateStyle = wb.createCellStyle();
            CreationHelper helper = wb.getCreationHelper();
            dateStyle.setDataFormat(helper.createDataFormat().getFormat("m/d/yyyy"));
            celda.setCellStyle(dateStyle);
            celda.setCellValue(LocalDateTime.of(2026, 3, 15, 0, 0));

            String resultado = mapeador.mapearFila(fila, Map.of("COL", 0));

            assertEquals("3/15/2026", resultado);
        }
    }

    @Test
    void deberiaRetornarValorFormateadoCuandoCeldaEsTipoDefault() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Row fila = wb.createSheet().createRow(0);
            Cell celda = fila.createCell(0);
            celda.setCellValue(true);

            String resultado = mapeador.mapearFila(fila, Map.of("COL", 0));

            assertNotNull(resultado);
        }
    }
}
