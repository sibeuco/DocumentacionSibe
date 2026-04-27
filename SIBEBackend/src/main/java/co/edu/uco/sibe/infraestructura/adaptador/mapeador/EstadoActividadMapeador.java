package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.EstadoActividadDTO;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstadoActividadEntidad;
import org.springframework.stereotype.Component;

@Component
public class EstadoActividadMapeador {

    public EstadoActividadEntidad construirEntidad(EstadoActividad estadoActividad) {
        return new EstadoActividadEntidad(
                estadoActividad.getIdentificador(),
                estadoActividad.getNombre()
        );
    }

    public EstadoActividad construirModelo(EstadoActividadEntidad estadoActividadEntidad) {
        return EstadoActividad.construir(
                estadoActividadEntidad.getIdentificador(),
                estadoActividadEntidad.getNombre()
        );
    }

    public EstadoActividadDTO construirDTO(EstadoActividadEntidad estadoActividad) {
        return new EstadoActividadDTO(
                estadoActividad.getIdentificador().toString(),
                estadoActividad.getNombre()
        );
    }
}