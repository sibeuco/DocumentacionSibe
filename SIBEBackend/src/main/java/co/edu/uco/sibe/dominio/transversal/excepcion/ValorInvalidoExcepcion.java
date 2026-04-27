package co.edu.uco.sibe.dominio.transversal.excepcion;

import java.io.Serial;

public class ValorInvalidoExcepcion extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ValorInvalidoExcepcion(String message) {
        super(message);
    }
}