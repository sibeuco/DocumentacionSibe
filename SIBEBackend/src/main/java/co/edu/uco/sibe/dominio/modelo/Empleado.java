package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class Empleado extends Interno {
    private RelacionLaboral relacionLaboral;
    private CentroCostos centroCostos;

    private Empleado(
            UUID identificador,
            String nombreCompleto,
            String numeroIdentificacion,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            RelacionLaboral relacionLaboral,
            CentroCostos centroCostos
    ) {
        super(identificador, nombreCompleto, numeroIdentificacion, ciudadResidencia, idCarnet, sexo);
        this.relacionLaboral = relacionLaboral;
        this.centroCostos = centroCostos;
    }

    public static Empleado construir(
            UUID identificador,
            String nombreCompleto,
            String numeroIdentificacion,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            RelacionLaboral relacionLaboral,
            CentroCostos centroCostos
    ) {
        return new Empleado(
                identificador,
                obtenerTextoPorDefecto(nombreCompleto),
                obtenerTextoPorDefecto(numeroIdentificacion),
                obtenerObjetoPorDefecto(ciudadResidencia, CiudadResidencia.construir()),
                obtenerTextoPorDefecto(idCarnet),
                obtenerTextoPorDefecto(sexo),
                obtenerObjetoPorDefecto(relacionLaboral, RelacionLaboral.construir()),
                obtenerObjetoPorDefecto(centroCostos, CentroCostos.construir())
        );
    }

    public static Empleado construir() {
        return new Empleado(
                obtenerValorDefecto(),
                VACIO,
                VACIO,
                CiudadResidencia.construir(),
                VACIO,
                VACIO,
                RelacionLaboral.construir(),
                CentroCostos.construir()
        );
    }
}