package co.edu.uco.sibe.aplicacion.transversal.manejador;

import org.springframework.transaction.annotation.Transactional;

public interface ManejadorComandoParametroRespuesta<C, P, R> {
    @Transactional
    R ejecutar(C comando, P parametro);
}