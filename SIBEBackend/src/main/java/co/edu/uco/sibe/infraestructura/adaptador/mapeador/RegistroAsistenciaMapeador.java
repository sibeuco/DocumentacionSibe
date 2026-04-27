package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.RegistroAsistenciaEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistroAsistenciaMapeador {

    private final EjecucionActividadMapeador ejecucionActividadMapeador;
    private final ParticipanteMapeador participanteMapeador;

    public RegistroAsistenciaEntidad construirEntidad(RegistroAsistencia dominio) {
        return new RegistroAsistenciaEntidad(
                dominio.getIdentificador(),
                ejecucionActividadMapeador.construirEntidad(dominio.getEjecucionActividad()),
                participanteMapeador.construirEntidad(dominio.getParticipante())
        );
    }

    public RegistroAsistencia construirModelo(RegistroAsistenciaEntidad entidad) {
        return RegistroAsistencia.construir(
                entidad.getIdentificador(),
                ejecucionActividadMapeador.construirModelo(entidad.getEjecucionActividad()),
                participanteMapeador.construirModelo(entidad.getParticipante())
        );
    }
}