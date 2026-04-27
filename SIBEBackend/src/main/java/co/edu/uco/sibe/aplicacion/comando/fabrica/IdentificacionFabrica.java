package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.IdentificacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class IdentificacionFabrica {
    private final TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;
    private final IdentificacionRepositorioConsulta identificacionRepositorioConsulta;

    public Identificacion construir(String numeroIdentificacion, String identificadorTipoIdentificacion) {
        var identificadorIdentificacion = generar(uuid -> !esNulo(identificacionRepositorioConsulta.consultarPorIdentificador(uuid)));
        var tipoIdentificacion = tipoIdentificacionRepositorioConsulta.consultarPorIdentificador(UtilUUID.textoAUUID(identificadorTipoIdentificacion));

        return Identificacion.construir(identificadorIdentificacion, numeroIdentificacion, tipoIdentificacion);
    }

    public Identificacion construirActualizar(String numeroIdentificacion, String identificadorTipoIdentificacion, UUID identificacionIdentificador) {
        var tipoIdentificacion = tipoIdentificacionRepositorioConsulta.consultarPorIdentificador(UtilUUID.textoAUUID(identificadorTipoIdentificacion));

        return Identificacion.construir(identificacionIdentificador, numeroIdentificacion, tipoIdentificacion);
    }
}