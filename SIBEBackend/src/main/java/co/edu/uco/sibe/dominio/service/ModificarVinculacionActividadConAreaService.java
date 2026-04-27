package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.puerto.comando.AreaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.DireccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.SubareaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ModificarVinculacionActividadConAreaService {

    private final SubareaRepositorioComando subareaRepositorioComando;
    private final AreaRepositorioComando areaRepositorioComando;
    private final DireccionRepositorioComando direccionRepositorioComando;
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;
    private final AreaRepositorioConsulta areaRepositorioConsulta;
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;
    private final VincularActividadConAreaService vincularActividadConAreaService;

    public ModificarVinculacionActividadConAreaService(
            SubareaRepositorioComando subareaRepositorioComando,
            AreaRepositorioComando areaRepositorioComando,
            DireccionRepositorioComando direccionRepositorioComando,
            SubareaRepositorioConsulta subareaRepositorioConsulta,
            AreaRepositorioConsulta areaRepositorioConsulta,
            DireccionRepositorioConsulta direccionRepositorioConsulta,
            VincularActividadConAreaService vincularActividadConAreaService) {
        this.subareaRepositorioComando = subareaRepositorioComando;
        this.areaRepositorioComando = areaRepositorioComando;
        this.direccionRepositorioComando = direccionRepositorioComando;
        this.subareaRepositorioConsulta = subareaRepositorioConsulta;
        this.areaRepositorioConsulta = areaRepositorioConsulta;
        this.direccionRepositorioConsulta = direccionRepositorioConsulta;
        this.vincularActividadConAreaService = vincularActividadConAreaService;
    }

    public UUID ejecutar(Actividad actividad, UUID nuevaAreaId, TipoArea nuevoTipoArea) {
        desvincularActividadExistente(actividad);

        return this.vincularActividadConAreaService.ejecutar(actividad, nuevaAreaId, nuevoTipoArea);
    }

    private void desvincularActividadExistente(Actividad actividad) {
        var direccion = direccionRepositorioConsulta.consultarPorActividad(actividad);

        if (!esNulo(direccion)) {
            direccionRepositorioComando.eliminarActividad(actividad, direccion);

            return;
        }

        var area = areaRepositorioConsulta.consultarPorActividad(actividad);
        if (!esNulo(area)) {
            areaRepositorioComando.eliminarActividad(actividad, area);

            return;
        }

        var subarea = subareaRepositorioConsulta.consultarPorActividad(actividad);

        if (!esNulo(subarea)) {
            subareaRepositorioComando.eliminarActividad(actividad, subarea);
        }
    }
}