package co.edu.uco.sibe.aplicacion.transversal.manejador;

import org.springframework.transaction.annotation.Transactional;

public interface ManejadorComandoRespuesta<C, R> {
    @Transactional
    R ejecutar(C comando);
}