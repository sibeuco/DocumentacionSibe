package co.edu.uco.sibe.aplicacion.transversal.manejador;

import org.springframework.transaction.annotation.Transactional;

public interface ManejadorRespuesta<R> {
    @Transactional
    R ejecutar();
}