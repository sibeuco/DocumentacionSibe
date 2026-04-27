package co.edu.uco.sibe.dominio.transversal.excepcion;

import java.io.Serial;

public class PatronExcepcion extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public PatronExcepcion(String message) {
        super(message);
    }
}