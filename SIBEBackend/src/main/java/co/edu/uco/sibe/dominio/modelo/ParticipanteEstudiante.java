package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.obtenerNumeroPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class ParticipanteEstudiante extends ParticipanteInterno {
    private String estadoCivil;
    private String programaAcademico;
    private String facultad;
    private int annoIngreso;
    private String semestreActual;
    private int creditosAprobados;
    private float promedioGeneral;
    private String estadoAcademico;
    private String modalidadEstudio;
    private int tiempoLlegadaUniversidad;
    private String medioTransporte;

    private ParticipanteEstudiante(
            UUID identificador,
            Miembro miembro,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            String estadoCivil,
            String programaAcademico,
            String facultad,
            int annoIngreso,
            String semestreActual,
            int creditosAprobados,
            float promedioGeneral,
            String estadoAcademico,
            String modalidadEstudio,
            int tiempoLlegadaUniversidad,
            String medioTransporte
    ) {
        super(identificador, miembro, ciudadResidencia, idCarnet, sexo);
        this.estadoCivil = estadoCivil;
        this.programaAcademico = programaAcademico;
        this.facultad = facultad;
        this.annoIngreso = annoIngreso;
        this.semestreActual = semestreActual;
        this.creditosAprobados = creditosAprobados;
        this.promedioGeneral = promedioGeneral;
        this.estadoAcademico = estadoAcademico;
        this.modalidadEstudio = modalidadEstudio;
        this.tiempoLlegadaUniversidad = tiempoLlegadaUniversidad;
        this.medioTransporte = medioTransporte;
    }

    public static ParticipanteEstudiante construir(
            UUID identificador,
            Miembro miembro,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            String estadoCivil,
            String programaAcademico,
            String facultad,
            int annoIngreso,
            String semestreActual,
            int creditosAprobados,
            float promedioGeneral,
            String estadoAcademico,
            String modalidadEstudio,
            int tiempoLlegadaUniversidad,
            String medioTransporte
    ) {
        return new ParticipanteEstudiante(
                identificador,
                obtenerObjetoPorDefecto(miembro, Miembro.construir()),
                obtenerObjetoPorDefecto(ciudadResidencia, CiudadResidencia.construir()),
                obtenerTextoPorDefecto(idCarnet),
                obtenerTextoPorDefecto(sexo),
                obtenerTextoPorDefecto(estadoCivil),
                obtenerTextoPorDefecto(programaAcademico),
                obtenerTextoPorDefecto(facultad),
                obtenerNumeroPorDefecto(annoIngreso),
                obtenerTextoPorDefecto(semestreActual),
                obtenerNumeroPorDefecto(creditosAprobados),
                obtenerNumeroPorDefecto(promedioGeneral),
                obtenerTextoPorDefecto(estadoAcademico),
                obtenerTextoPorDefecto(modalidadEstudio),
                obtenerNumeroPorDefecto(tiempoLlegadaUniversidad),
                obtenerTextoPorDefecto(medioTransporte)
        );
    }

    public static ParticipanteEstudiante construir() {
        return new ParticipanteEstudiante(
                obtenerValorDefecto(),
                Miembro.construir(),
                CiudadResidencia.construir(),
                VACIO,
                VACIO,
                VACIO,
                VACIO,
                VACIO,
                CERO,
                VACIO,
                CERO,
                CERO,
                VACIO,
                VACIO,
                CERO,
                VACIO
        );
    }
}