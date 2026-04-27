package co.edu.uco.sibe.dominio.transversal.excepcion;

import java.io.Serial;

public class ValorDuplicadoExcepcion extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ValorDuplicadoExcepcion(String message) {
        super(message);
    }
}