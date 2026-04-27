package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubareaTestDataBuilder {
    private UUID identificador = UUID.randomUUID();
    private String nombre = "Sub-área Test";
    private List<Actividad> actividades = new ArrayList<>();

    public SubareaTestDataBuilder conIdentificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public SubareaTestDataBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public SubareaTestDataBuilder conActividades(List<Actividad> actividades) {
        this.actividades = actividades;
        return this;
    }

    public Subarea construir() {
        return Subarea.construir(identificador, nombre, actividades);
    }
}
