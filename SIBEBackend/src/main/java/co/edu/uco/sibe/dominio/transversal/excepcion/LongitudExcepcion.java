package co.edu.uco.sibe.dominio.transversal.excepcion;

import java.io.Serial;

public class LongitudExcepcion extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public LongitudExcepcion(String message) {
        super(message);
    }
}