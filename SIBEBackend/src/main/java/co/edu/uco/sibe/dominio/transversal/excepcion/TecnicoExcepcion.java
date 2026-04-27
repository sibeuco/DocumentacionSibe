package co.edu.uco.sibe.dominio.transversal.excepcion;

import java.io.Serial;

public class TecnicoExcepcion extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public TecnicoExcepcion(String message) {
        super(message);
    }
}