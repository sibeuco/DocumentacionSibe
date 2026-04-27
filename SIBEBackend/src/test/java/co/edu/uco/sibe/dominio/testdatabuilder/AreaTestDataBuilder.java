package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AreaTestDataBuilder {
    private UUID identificador = UUID.randomUUID();
    private String nombre = "Área Test";
    private List<Subarea> subareas = new ArrayList<>();
    private List<Actividad> actividades = new ArrayList<>();

    public AreaTestDataBuilder conIdentificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public AreaTestDataBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public AreaTestDataBuilder conSubareas(List<Subarea> subareas) {
        this.subareas = subareas;
        return this;
    }

    public AreaTestDataBuilder conActividades(List<Actividad> actividades) {
        this.actividades = actividades;
        return this;
    }

    public Area construir() {
        return Area.construir(identificador, nombre, subareas, actividades);
    }
}
