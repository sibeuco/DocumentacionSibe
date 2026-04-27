package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class CentroCostos {
    private UUID identificador;
    private String codigo;
    private String descripcion;

    private CentroCostos(UUID identificador, String codigo, String descripcion) {
        this.identificador = identificador;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public static CentroCostos construir(UUID identificador, String codigo, String descripcion) {
        return new CentroCostos(
                identificador,
                obtenerTextoPorDefecto(codigo),
                obtenerTextoPorDefecto(descripcion)
        );
    }

    public static CentroCostos construir() {
        return new CentroCostos(
                obtenerValorDefecto(),
                VACIO,
                VACIO
        );
    }
}