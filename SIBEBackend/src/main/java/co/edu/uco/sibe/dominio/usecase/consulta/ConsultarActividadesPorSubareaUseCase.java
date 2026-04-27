package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.SUBAREA_NO_ENCONTRADA_CON_ID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarActividadesPorSubareaUseCase {
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarActividadesPorSubareaUseCase(ActividadRepositorioConsulta actividadRepositorioConsulta,
            SubareaRepositorioConsulta subareaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.actividadRepositorioConsulta = actividadRepositorioConsulta;
        this.subareaRepositorioConsulta = subareaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public List<ActividadDTO> ejecutar(String identificadorSubarea) {
        var id = UtilUUID.textoAUUID(identificadorSubarea);
        autorizacionServicio.validarAccesoASubarea(id);
        var subarea = validarSiExisteSubarea(id, identificadorSubarea);
        return actividadRepositorioConsulta.consultarPorSubarea(subarea);
    }

    public Page<ActividadDTO> ejecutar(String identificadorSubarea, int pagina, int tamano) {
        var id = UtilUUID.textoAUUID(identificadorSubarea);
        autorizacionServicio.validarAccesoASubarea(id);
        validarSiExisteSubarea(id, identificadorSubarea);
        return actividadRepositorioConsulta.consultarPorSubareaPaginado(id, pagina, tamano);
    }

    private Subarea validarSiExisteSubarea(UUID id, String idComando) {
        var subarea = subareaRepositorioConsulta.consultarPorIdentificador(id);
        if (esNulo(subarea)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(SUBAREA_NO_ENCONTRADA_CON_ID, idComando));
        }
        return subarea;
    }
}