package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.time.LocalDate;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilFecha.obtenerFechaDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilFecha.obtenerValorFechaPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.obtenerNumeroPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Estudiante extends Interno {
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String estadoCivil;
    private String correoPersonal;
    private String correoInstitucional;
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

    private Estudiante(
            UUID identificador,
            String nombreCompleto,
            String numeroIdentificacion,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String estadoCivil,
            String correoPersonal,
            String correoInstitucional,
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
        super(identificador, nombreCompleto, numeroIdentificacion, ciudadResidencia, idCarnet, sexo);
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.estadoCivil = estadoCivil;
        this.correoPersonal = correoPersonal;
        this.correoInstitucional = correoInstitucional;
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

    public static Estudiante construir(
            UUID identificador,
            String nombreCompleto,
            String numeroIdentificacion,
            CiudadResidencia ciudadResidencia,
            String idCarnet,
            String sexo,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String estadoCivil,
            String correoPersonal,
            String correoInstitucional,
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
        return new Estudiante(
                identificador,
                obtenerTextoPorDefecto(nombreCompleto),
                obtenerTextoPorDefecto(numeroIdentificacion),
                obtenerObjetoPorDefecto(ciudadResidencia, CiudadResidencia.construir()),
                obtenerTextoPorDefecto(idCarnet),
                obtenerTextoPorDefecto(sexo),
                obtenerValorFechaPorDefecto(fechaNacimiento),
                obtenerTextoPorDefecto(nacionalidad),
                obtenerTextoPorDefecto(estadoCivil),
                obtenerTextoPorDefecto(correoPersonal),
                obtenerTextoPorDefecto(correoInstitucional),
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

    public static Estudiante construir() {
        return new Estudiante(
                obtenerValorDefecto(),
                VACIO,
                VACIO,
                CiudadResidencia.construir(),
                VACIO,
                VACIO,
                obtenerFechaDefecto(),
                VACIO,
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