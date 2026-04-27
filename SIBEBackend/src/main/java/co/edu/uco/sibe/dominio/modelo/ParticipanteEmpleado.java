package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class ParticipanteEmpleado extends ParticipanteInterno {
    private RelacionLaboral relacionLaboral;
    private CentroCostos centroCostos;

    private ParticipanteEmpleado(
            UUID identificador,
            Miembro miembro,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            RelacionLaboral relacionLaboral,
            CentroCostos centroCostos
    ) {
        super(identificador, miembro, ciudadResidencia, idCarnet, sexo);
        this.relacionLaboral = relacionLaboral;
        this.centroCostos = centroCostos;
    }

    public static ParticipanteEmpleado construir(
            UUID identificador,
            Miembro miembro,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            RelacionLaboral relacionLaboral,
            CentroCostos centroCostos
    ) {
        return new ParticipanteEmpleado(
                identificador,
                obtenerObjetoPorDefecto(miembro, Miembro.construir()),
                obtenerObjetoPorDefecto(ciudadResidencia, CiudadResidencia.construir()),
                obtenerTextoPorDefecto(idCarnet),
                obtenerTextoPorDefecto(sexo),
                obtenerObjetoPorDefecto(relacionLaboral, RelacionLaboral.construir()),
                obtenerObjetoPorDefecto(centroCostos, CentroCostos.construir())
        );
    }

    public static ParticipanteEmpleado construir() {
        return new ParticipanteEmpleado(
                obtenerValorDefecto(),
                Miembro.construir(),
                CiudadResidencia.construir(),
                VACIO,
                VACIO,
                RelacionLaboral.construir(),
                CentroCostos.construir()
        );
    }
}