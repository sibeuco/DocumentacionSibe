package co.edu.uco.sibe.dominio.transversal.excepcion;

import java.io.Serial;

public class ValorObligatorioExcepcion extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ValorObligatorioExcepcion(String message) {
        super(message);
    }
}