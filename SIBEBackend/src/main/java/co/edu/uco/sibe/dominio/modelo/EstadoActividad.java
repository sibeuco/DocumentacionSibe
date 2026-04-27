package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class EstadoActividad {
    private UUID identificador;
    private String nombre;

    private EstadoActividad(UUID identificador, String nombre) {
        this.identificador = identificador;
        this.nombre = nombre;
    }

    public static EstadoActividad construir(UUID identificador, String nombre) {
        return new EstadoActividad(
                identificador,
                obtenerTextoPorDefecto(nombre)
        );
    }

    public static EstadoActividad construir() {
        return new EstadoActividad(
                obtenerValorDefecto(),
                VACIO
        );
    }
}