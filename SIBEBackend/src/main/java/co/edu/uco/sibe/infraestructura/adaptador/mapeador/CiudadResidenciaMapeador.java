package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CiudadResidenciaEntidad;
import org.springframework.stereotype.Component;

@Component
public class CiudadResidenciaMapeador {
    public CiudadResidencia construirModelo(CiudadResidenciaEntidad ciudadResidencia) {
        return CiudadResidencia.construir(
                ciudadResidencia.getIdentificador(),
                ciudadResidencia.getDescripcion()
        );
    }

    public CiudadResidenciaEntidad construirEntidad(CiudadResidencia ciudadResidencia) {
        return new CiudadResidenciaEntidad(
                ciudadResidencia.getIdentificador(),
                ciudadResidencia.getDescripcion()
        );
    }
}