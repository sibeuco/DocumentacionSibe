package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.infraestructura.adaptador.mapeador.FilaExcelMapeador;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProcesadorExcelMotorTest {

    private ProcesadorExcelMotor procesador;

    @BeforeEach
    void setUp() {
        procesador = new ProcesadorExcelMotor();
    }

    @Test
    void deberiaLanzarExcepcionSiArchivoEstaVacio() {
        MultipartFile archivo = new MockMultipartFile("file", new byte[0]);

        assertThrows(IllegalArgumentException.class, () ->
                procesador.procesarArchivo(archivo, (fila, mapa) -> null));
    }

    @Test
    void deberiaLanzarExcepcionSiHojaSoloTieneEncabezadosVacios() throws IOException {
        byte[] contenido = crearExcelSinFilas();
        MultipartFile archivo = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", contenido);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                procesador.procesarArchivo(archivo, (fila, mapa) -> null));
        assertInstanceOf(IllegalStateException.class, ex.getCause());
    }

    @Test
    void deberiaProcesarArchivoConDatos() throws IOException {
        byte[] contenido = crearExcelConDatos();
        MultipartFile archivo = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", contenido);

        FilaExcelMapeador<String> mapeador = (fila, mapa) -> {
            int colIdx = mapa.getOrDefault("NOMBRE", 0);
            Cell cell = fila.getCell(colIdx);
            return cell != null ? cell.getStringCellValue() : "";
        };

        List<String> resultados = procesador.procesarArchivo(archivo, mapeador);

        assertEquals(2, resultados.size());
        assertEquals("Juan", resultados.get(0));
        assertEquals("Maria", resultados.get(1));
    }

    @Test
    void deberiaIgnorarFilasVacias() throws IOException {
        byte[] contenido = crearExcelConFilaVacia();
        MultipartFile archivo = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", contenido);

        FilaExcelMapeador<String> mapeador = (fila, mapa) -> {
            int colIdx = mapa.getOrDefault("NOMBRE", 0);
            Cell cell = fila.getCell(colIdx);
            return cell != null ? cell.getStringCellValue() : "";
        };

        List<String> resultados = procesador.procesarArchivo(archivo, mapeador);

        assertEquals(1, resultados.size());
    }

    private byte[] crearExcelSinFilas() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.createSheet("Hoja1");
            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    private byte[] crearExcelConDatos() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Hoja1");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("NOMBRE");
            header.createCell(1).setCellValue("EDAD");

            Row fila1 = sheet.createRow(1);
            fila1.createCell(0).setCellValue("Juan");
            fila1.createCell(1).setCellValue(25);

            Row fila2 = sheet.createRow(2);
            fila2.createCell(0).setCellValue("Maria");
            fila2.createCell(1).setCellValue(30);

            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    private byte[] crearExcelConFilaVacia() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Hoja1");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("NOMBRE");

            Row fila1 = sheet.createRow(1);
            fila1.createCell(0).setCellValue("Juan");

            sheet.createRow(2); // empty row

            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    @Test
    void deberiaLanzarRuntimeExceptionConIOException() {
        byte[] contenidoInvalido = "esto no es un xlsx".getBytes();
        MultipartFile archivo = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", contenidoInvalido);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                procesador.procesarArchivo(archivo, (fila, mapa) -> null));
        assertNotNull(ex.getMessage());
    }

    @Test
    void deberiaLanzarRuntimeExceptionConIOExceptionDelStream() throws IOException {
        MultipartFile archivo = org.mockito.Mockito.mock(MultipartFile.class);
        org.mockito.Mockito.when(archivo.isEmpty()).thenReturn(false);
        org.mockito.Mockito.when(archivo.getInputStream()).thenThrow(new IOException("Error de lectura"));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                procesador.procesarArchivo(archivo, (fila, mapa) -> null));
        assertInstanceOf(IOException.class, ex.getCause());
    }

    @Test
    void deberiaNormalizarEncabezadosConAcentos() throws IOException {
        byte[] contenido = crearExcelConEncabezadosAcentuados();
        MultipartFile archivo = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", contenido);

        FilaExcelMapeador<String> mapeador = (fila, mapa) -> {
            int colIdx = mapa.getOrDefault("CODIGO_CLASIFICACION_7", -1);
            Cell cell = fila.getCell(colIdx);
            return cell != null ? cell.getStringCellValue() : "";
        };

        List<String> resultados = procesador.procesarArchivo(archivo, mapeador);

        assertEquals(1, resultados.size());
        assertEquals("EDJ", resultados.get(0));
    }

    private byte[] crearExcelConEncabezadosAcentuados() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Hoja1");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("CÓDIGO_CLASIFICACIÓN_7");

            Row fila1 = sheet.createRow(1);
            fila1.createCell(0).setCellValue("EDJ");

            workbook.write(baos);
            return baos.toByteArray();
        }
    }
}
