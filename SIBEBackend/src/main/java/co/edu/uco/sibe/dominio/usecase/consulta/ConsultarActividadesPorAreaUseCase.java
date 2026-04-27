package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.AREA_NO_ENCONTRADA_CON_ID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarActividadesPorAreaUseCase {
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;
    private final AreaRepositorioConsulta areaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarActividadesPorAreaUseCase(ActividadRepositorioConsulta actividadRepositorioConsulta,
            AreaRepositorioConsulta areaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.actividadRepositorioConsulta = actividadRepositorioConsulta;
        this.areaRepositorioConsulta = areaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public List<ActividadDTO> ejecutar(String identificadorDireccion) {
        var id = UtilUUID.textoAUUID(identificadorDireccion);
        autorizacionServicio.validarAccesoAArea(id);
        var area = validarSiExisteArea(id, identificadorDireccion);

        return actividadRepositorioConsulta.consultarPorArea(area);
    }

    public Page<ActividadDTO> ejecutar(String identificadorArea, int pagina, int tamano) {
        var id = UtilUUID.textoAUUID(identificadorArea);
        autorizacionServicio.validarAccesoAArea(id);
        validarSiExisteArea(id, identificadorArea);
        return actividadRepositorioConsulta.consultarPorAreaPaginado(id, pagina, tamano);
    }

    private Area validarSiExisteArea(UUID id, String idComando) {
        var area = areaRepositorioConsulta.consultarPorIdentificador(id);

        if (esNulo(area)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(AREA_NO_ENCONTRADA_CON_ID, idComando));
        }

        return area;
    }
}