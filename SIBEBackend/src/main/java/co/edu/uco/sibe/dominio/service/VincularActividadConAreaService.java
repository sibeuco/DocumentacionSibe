package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.puerto.comando.AreaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.DireccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.SubareaRepositorioComando;
import java.util.UUID;

public class VincularActividadConAreaService {
    private final SubareaRepositorioComando subareaRepositorioComando;
    private final AreaRepositorioComando areaRepositorioComando;
    private final DireccionRepositorioComando direccionRepositorioComando;

    public VincularActividadConAreaService(SubareaRepositorioComando subareaRepositorioComando, AreaRepositorioComando areaRepositorioComando, DireccionRepositorioComando direccionRepositorioComando) {
        this.subareaRepositorioComando = subareaRepositorioComando;
        this.areaRepositorioComando = areaRepositorioComando;
        this.direccionRepositorioComando = direccionRepositorioComando;
    }

    public UUID ejecutar(Actividad actividad, UUID area, TipoArea tipoArea) {
        switch (tipoArea) {
            case DIRECCION -> {
                return direccionRepositorioComando.guardarActividad(actividad, area);
            }
            case AREA -> {
                return areaRepositorioComando.guardarActividad(actividad, area);
            }
            case SUBAREA -> {
                return subareaRepositorioComando.guardarActividad(actividad, area);
            }
            default -> {
                return null;
            }
        }

    }
}