package co.edu.uco.sibe.dominio.transversal.excepcion;

import java.io.Serial;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.UNO_LONG;

public class AuthorizationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = UNO_LONG;

    public AuthorizationException(String message) {
        super(message);
    }
}