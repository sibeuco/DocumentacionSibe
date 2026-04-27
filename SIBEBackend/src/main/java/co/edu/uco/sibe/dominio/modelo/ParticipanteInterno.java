package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class ParticipanteInterno extends Participante {
    private CiudadResidencia ciudadResidencia;
    private String idCarnet;
    private String sexo;

    protected ParticipanteInterno(UUID identificador, Miembro miembro, CiudadResidencia ciudadResidencia, String idCarnet, String sexo) {
        super(identificador, miembro);
        this.ciudadResidencia = ciudadResidencia;
        this.idCarnet = idCarnet;
        this.sexo = sexo;
    }

    public static ParticipanteInterno construir(UUID identificador, Miembro miembro, CiudadResidencia ciudadResidencia, String idCarnet, String sexo) {
        return new ParticipanteInterno(
                identificador,
                obtenerObjetoPorDefecto(miembro, Miembro.construir()),
                obtenerObjetoPorDefecto(ciudadResidencia, CiudadResidencia.construir()),
                obtenerTextoPorDefecto(idCarnet),
                obtenerTextoPorDefecto(sexo)
        );
    }

    public static ParticipanteInterno construir() {
        return new ParticipanteInterno(
                obtenerValorDefecto(),
                Miembro.construir(),
                CiudadResidencia.construir(),
                VACIO,
                VACIO
        );
    }
}