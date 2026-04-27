package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.ContarUsuariosPorOrganizacionService;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.ORGANIZACION_NO_ENCONTRADA_CON_ID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class ContarUsuariosPorOrganizacionManejador implements ManejadorComandoRespuesta<String, ComandoRespuesta<Long>> {
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;
    private final AreaRepositorioConsulta areaRepositorioConsulta;
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;
    private final ContarUsuariosPorOrganizacionService contarUsuariosPorOrganizacionService;

    @Override
    public ComandoRespuesta<Long> ejecutar(String comando) {
        var identificador = UtilUUID.textoAUUID(comando);

        var direccion = direccionRepositorioConsulta.consultarPorIdentificador(identificador);

        if (!esNulo(direccion)) {
            return new ComandoRespuesta<>(contarUsuariosPorOrganizacionService.contarUsuariosPorDireccion(direccion));
        }

        var area = areaRepositorioConsulta.consultarPorIdentificador(identificador);

        if (!esNulo(area)) {
            return new ComandoRespuesta<>(contarUsuariosPorOrganizacionService.contarUsuariosPorArea(area));
        }

        var subarea = subareaRepositorioConsulta.consultarPorIdentificador(identificador);

        if (!esNulo(subarea)) {
            return new ComandoRespuesta<>(contarUsuariosPorOrganizacionService.contarUsuariosPorSubarea(subarea));
        }

        throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(ORGANIZACION_NO_ENCONTRADA_CON_ID, comando));
    }
}