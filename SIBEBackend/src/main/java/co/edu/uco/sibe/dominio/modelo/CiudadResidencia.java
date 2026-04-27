package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class CiudadResidencia {
    private UUID identificador;
    private String descripcion;

    private CiudadResidencia(UUID identificador, String descripcion) {
        this.identificador = identificador;
        this.descripcion = descripcion;
    }

    public static CiudadResidencia construir(UUID identificador, String descripcion) {
        return new CiudadResidencia(
                identificador,
                obtenerTextoPorDefecto(descripcion)
        );
    }

    public static CiudadResidencia construir() {
        return new CiudadResidencia(
                obtenerValorDefecto(),
                VACIO
        );
    }
}