package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Indicador;
import java.time.LocalDate;
import java.util.UUID;

public class ActividadTestDataBuilder {
    private UUID identificador = UUID.randomUUID();
    private String nombre = "Actividad Test";
    private String objetivo = "Objetivo Test";
    private String semestre = "202601";
    private String rutaInsumos = "/insumos/test";
    private LocalDate fechaCreacion = LocalDate.now();
    private Indicador indicador = Indicador.construir();
    private UUID colaborador = UUID.randomUUID();
    private UUID creador = UUID.randomUUID();

    public ActividadTestDataBuilder conIdentificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public ActividadTestDataBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ActividadTestDataBuilder conSemestre(String semestre) {
        this.semestre = semestre;
        return this;
    }

    public ActividadTestDataBuilder conColaborador(UUID colaborador) {
        this.colaborador = colaborador;
        return this;
    }

    public ActividadTestDataBuilder conCreador(UUID creador) {
        this.creador = creador;
        return this;
    }

    public Actividad construir() {
        return Actividad.construir(identificador, nombre, objetivo, semestre, rutaInsumos, fechaCreacion, indicador,
                colaborador, creador);
    }
}
