package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Interno extends Miembro {
    private CiudadResidencia ciudadResidencia;
    private String idCarnet;
    private String sexo;

    protected Interno(UUID identificador, String nombreCompleto, String numeroIdentificacion, CiudadResidencia ciudadResidencia, String idCarnet, String sexo) {
        super(identificador, nombreCompleto, numeroIdentificacion);
        this.ciudadResidencia = ciudadResidencia;
        this.idCarnet = idCarnet;
        this.sexo = sexo;
    }

    public static Interno construir(UUID identificador, String nombreCompleto, String numeroIdentificacion, CiudadResidencia ciudadResidencia, String idCarnet, String sexo) {
        return new Interno(
                identificador,
                obtenerTextoPorDefecto(nombreCompleto),
                obtenerTextoPorDefecto(numeroIdentificacion),
                obtenerObjetoPorDefecto(ciudadResidencia, CiudadResidencia.construir()),
                obtenerTextoPorDefecto(idCarnet),
                obtenerTextoPorDefecto(sexo)
        );
    }

    public static Interno construir() {
        return new Interno(
                obtenerValorDefecto(),
                VACIO,
                VACIO,
                CiudadResidencia.construir(),
                VACIO,
                VACIO
        );
    }
}