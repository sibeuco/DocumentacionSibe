package co.edu.uco.sibe.infraestructura.error;

import co.edu.uco.sibe.dominio.transversal.excepcion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ManejadorErrorTest {

    private ManejadorError manejadorError;

    @BeforeEach
    void setUp() {
        manejadorError = new ManejadorError();
    }

    @Test
    void deberiaRetornarBadRequestParaValorObligatorioExcepcion() {
        var excepcion = new ValorObligatorioExcepcion("Campo obligatorio");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Campo obligatorio", resultado.getBody().mensaje());
        assertEquals("ValorObligatorioExcepcion", resultado.getBody().nombreExcepcion());
    }

    @Test
    void deberiaRetornarBadRequestParaLongitudExcepcion() {
        var excepcion = new LongitudExcepcion("Longitud invalida");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Longitud invalida", resultado.getBody().mensaje());
    }

    @Test
    void deberiaRetornarBadRequestParaPatronExcepcion() {
        var excepcion = new PatronExcepcion("Patron invalido");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
    }

    @Test
    void deberiaRetornarBadRequestParaValorDuplicadoExcepcion() {
        var excepcion = new ValorDuplicadoExcepcion("Valor duplicado");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
    }

    @Test
    void deberiaRetornarBadRequestParaValorInvalidoExcepcion() {
        var excepcion = new ValorInvalidoExcepcion("Valor invalido");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
    }

    @Test
    void deberiaRetornarBadRequestParaNullPointerException() {
        var excepcion = new NullPointerException("Nulo");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
    }

    @Test
    void deberiaRetornarInternalServerErrorParaTecnicoExcepcion() {
        var excepcion = new TecnicoExcepcion("Error tecnico");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());
    }

    @Test
    void deberiaRetornarForbiddenParaAuthorizationException() {
        var excepcion = new AuthorizationException("No autorizado");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.FORBIDDEN, resultado.getStatusCode());
        assertEquals("No autorizado", resultado.getBody().mensaje());
    }

    @Test
    void deberiaRetornarInternalServerErrorParaExcepcionDesconocida() {
        var excepcion = new RuntimeException("Error inesperado");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());
    }

    @Test
    void deberiaRetornarInternalServerErrorParaUnsupportedOperationException() {
        var excepcion = new UnsupportedOperationException("No soportado");

        ResponseEntity<Error> resultado = manejadorError.handleAllExceptions(excepcion);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());
    }
}
