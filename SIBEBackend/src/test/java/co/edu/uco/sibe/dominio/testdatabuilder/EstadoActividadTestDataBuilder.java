package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.PENDIENTE;

public class EstadoActividadTestDataBuilder {
    private UUID identificador = UUID.randomUUID();
    private String nombre = PENDIENTE;

    public EstadoActividadTestDataBuilder conIdentificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public EstadoActividadTestDataBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public EstadoActividad construir() {
        return EstadoActividad.construir(identificador, nombre);
    }
}
