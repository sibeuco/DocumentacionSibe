package co.edu.uco.sibe.infraestructura.error;

import co.edu.uco.sibe.dominio.transversal.excepcion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.concurrent.ConcurrentHashMap;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.OCURRIO_UN_ERROR;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@ControllerAdvice
public class ManejadorError extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger(ManejadorError.class);
    private static final ConcurrentHashMap<String, Integer> CODIGOS_ESTADO = new ConcurrentHashMap<>();

    public ManejadorError() {
        CODIGOS_ESTADO.put(ValorObligatorioExcepcion.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(LongitudExcepcion.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(PatronExcepcion.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(ValorDuplicadoExcepcion.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(ValorInvalidoExcepcion.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(NullPointerException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(TecnicoExcepcion.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        CODIGOS_ESTADO.put(UnsupportedOperationException.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        CODIGOS_ESTADO.put(AuthorizationException.class.getSimpleName(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> handleAllExceptions(Exception exception) {
        ResponseEntity<Error> resultado;

        String excepcionNombre = exception.getClass().getSimpleName();
        String mensaje = exception.getMessage();
        Integer codigo = CODIGOS_ESTADO.get(excepcionNombre);

        if (!esNulo(codigo)) {
            Error error = new Error(excepcionNombre, mensaje);
            resultado = new ResponseEntity<>(error, HttpStatus.valueOf(codigo));
        } else {
            LOGGER_ERROR.error(excepcionNombre, exception);
            Error error = new Error(excepcionNombre, OCURRIO_UN_ERROR);
            resultado = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return resultado;
    }
}