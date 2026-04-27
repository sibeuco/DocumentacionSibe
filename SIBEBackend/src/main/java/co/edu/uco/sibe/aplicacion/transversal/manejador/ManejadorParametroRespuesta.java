package co.edu.uco.sibe.aplicacion.transversal.manejador;

import org.springframework.transaction.annotation.Transactional;

public interface ManejadorParametroRespuesta<P, R> {
    @Transactional
    R ejecutar(P parametro);
}