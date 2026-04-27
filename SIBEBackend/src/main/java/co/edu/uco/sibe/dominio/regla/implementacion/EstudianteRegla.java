package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroMayorOIgual;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.*;

public final class EstudianteRegla implements Regla<Estudiante> {
    private static final EstudianteRegla INSTANCIA = new EstudianteRegla();

    private EstudianteRegla() {
        super();
    }

    public static EstudianteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_ESTUDIANTE_NULO);
    }

    @Override
    public void validarCampos(Estudiante modelo) {
        validarNombreCompleto(modelo.getNombreCompleto());
        validarNumeroIdentificacion(modelo.getNumeroIdentificacion());
        validarIdCarnet(modelo.getIdCarnet());
        validarSexo(modelo.getSexo());
        validarNacionalidad(modelo.getNacionalidad());
        validarEstadoCivil(modelo.getEstadoCivil());
        validarCorreoPersonal(modelo.getCorreoPersonal());
        validarCorreoInstitucional(modelo.getCorreoInstitucional());
        validarProgramaAcademico(modelo.getProgramaAcademico());
        validarFacultad(modelo.getFacultad());
        validarAnnoIngreso(modelo.getAnnoIngreso());
        validarSemestreActual(modelo.getSemestreActual());
        validarCreditosAprobados(modelo.getCreditosAprobados());
        validarPromedioGeneral(modelo.getPromedioGeneral());
        validarEstadoAcademico(modelo.getEstadoAcademico());
        validarModalidadEstudio(modelo.getModalidadEstudio());
        validarTiempoLlegadaUniversidad(modelo.getTiempoLlegadaUniversidad());
        validarMedioTransporte(modelo.getMedioTransporte());
    }

    private void validarNombreCompleto(String nombreCompleto) {
        validarObligatorio(nombreCompleto, NOMBRE_COMPLETO_MIEMBRO_OBLIGATORIO);
        validarTextoValido(nombreCompleto, NOMBRE_COMPLETO_MIEMBRO_INVALIDO);
        validarNumeroEntre(nombreCompleto.length(), 5, 100, LONGITUD_NOMBRE_COMPLETO_MIEMBRO_INVALIDA);
    }

    private void validarNumeroIdentificacion(String numeroIdentificacion) {
        validarObligatorio(numeroIdentificacion, NUMERO_IDENTIFICACION_MIEMBRO_OBLIGATORIO);
        validarNumeroIdentificacionValido(numeroIdentificacion, NUMERO_IDENTIFICACION_MIEMBRO_INVALIDO);
        validarNumeroEntre(numeroIdentificacion.length(), 6, 12, LONGITUD_NUMERO_IDENTIFICACION_MIEMBRO_INVALIDA);
    }

    private void validarIdCarnet(String idCarnet) {
        validarObligatorio(idCarnet, ID_CARNET_OBLIGATORIO);
        validarTextoAlfanumericoValido(idCarnet, ID_CARNET_INVALIDO);
        validarNumeroEntre(idCarnet.length(), 1, 20, LONGITUD_ID_CARNET_INVALIDA);
    }

    private void validarSexo(String sexo) {
        validarObligatorio(sexo, SEXO_OBLIGATORIO);
        validarTextoValido(sexo, SEXO_INVALIDO);
        validarNumeroEntre(sexo.length(), 1, 1, LONGITUD_SEXO_INVALIDA);
    }

    private void validarNacionalidad(String nacionalidad) {
        validarObligatorio(nacionalidad, NACIONALIDAD_ESTUDIANTE_OBLIGATORIA);
        validarTextoValido(nacionalidad, NACIONALIDAD_ESTUDIANTE_INVALIDA);
        validarNumeroEntre(nacionalidad.length(), 4, 50, LONGITUD_NACIONALIDAD_ESTUDIANTE_INVALIDA);
    }

    private void validarEstadoCivil(String estadoCivil) {
        validarObligatorio(estadoCivil, ESTADO_CIVIL_ESTUDIANTE_OBLIGATORIO);
        validarTextoValido(estadoCivil, ESTADO_CIVIL_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(estadoCivil.length(), 5, 15, LONGITUD_ESTADO_CIVIL_ESTUDIANTE_INVALIDA);
    }

    private void validarCorreoPersonal(String correoPersonal) {
        validarObligatorio(correoPersonal, CORREO_PERSONAL_ESTUDIANTE_OBLIGATORIO);
        validarCorreoValido(correoPersonal, CORREO_PERSONAL_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(correoPersonal.length(), 10, 100, LONGITUD_CORREO_PERSONAL_ESTUDIANTE_INVALIDA);
    }

    private void validarCorreoInstitucional(String correoInstitucional) {
        validarObligatorio(correoInstitucional, CORREO_INSTITUCIONAL_ESTUDIANTE_OBLIGATORIO);
        validarCorreoValido(correoInstitucional, CORREO_INSTITUCIONAL_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(correoInstitucional.length(), 10, 100, LONGITUD_CORREO_INSTITUCIONAL_ESTUDIANTE_INVALIDA);
    }

    private void validarProgramaAcademico(String programaAcademico) {
        validarObligatorio(programaAcademico, PROGRAMA_ACADEMICO_ESTUDIANTE_OBLIGATORIO);
        validarTextoAlfanumericoValido(programaAcademico, PROGRAMA_ACADEMICO_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(programaAcademico.length(), 5, 100, LONGITUD_PROGRAMA_ACADEMICO_ESTUDIANTE_INVALIDA);
    }

    private void validarFacultad(String facultad) {
        validarObligatorio(facultad, FACULTAD_ESTUDIANTE_OBLIGATORIA);
        validarTextoValido(facultad, FACULTAD_ESTUDIANTE_INVALIDA);
        validarNumeroEntre(facultad.length(), 5, 50, LONGITUD_FACULTAD_ESTUDIANTE_INVALIDA);
    }

    private void validarAnnoIngreso(int annoIngreso) {
        validarNumeroMayorOIgual(annoIngreso, 2000, ANNO_INGRESO_ESTUDIANTE_INVALIDO);
    }

    private void validarSemestreActual(String semestreActual) {
        validarObligatorio(semestreActual, SEMESTRE_ACTUAL_ESTUDIANTE_OBLIGATORIO);
        validarTextoAlfanumericoValido(semestreActual, SEMESTRE_ACTUAL_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(semestreActual.length(), 5, 5, LONGITUD_SEMESTRE_ACTUAL_ESTUDIANTE_INVALIDA);
    }

    private void validarCreditosAprobados(int creditosAprobados) {
        validarNumeroMayorOIgual(creditosAprobados, 0, CREDITOS_APROBADOS_ESTUDIANTE_INVALIDOS);
    }

    private void validarPromedioGeneral(float promedioGeneral) {
        validarNumeroMayorOIgual(promedioGeneral, 0.0, PROMEDIO_GENERAL_ESTUDIANTE_INVALIDO);
    }

    private void validarEstadoAcademico(String estadoAcademico) {
        validarObligatorio(estadoAcademico, ESTADO_ACADEMICO_ESTUDIANTE_OBLIGATORIO);
        validarTextoValido(estadoAcademico, ESTADO_ACADEMICO_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(estadoAcademico.length(), 6, 10, LONGITUD_ESTADO_ACADEMICO_ESTUDIANTE_INVALIDA);
    }

    private void validarModalidadEstudio(String modalidadEstudio) {
        validarObligatorio(modalidadEstudio, MODALIDAD_ESTUDIO_ESTUDIANTE_OBLIGATORIA);
        validarTextoValido(modalidadEstudio, MODALIDAD_ESTUDIO_ESTUDIANTE_INVALIDA);
        validarNumeroEntre(modalidadEstudio.length(), 5, 50, LONGITUD_MODALIDAD_ESTUDIO_ESTUDIANTE_INVALIDA);
    }

    private void validarTiempoLlegadaUniversidad(int tiempoLlegadaUniversidad) {
        validarNumeroMayorOIgual(tiempoLlegadaUniversidad, 1, TIEMPO_LLEGADA_ESTUDIANTE_INVALIDO);
    }

    private void validarMedioTransporte(String medioTransporte) {
        validarTextoValido(medioTransporte, MEDIO_TRANSPORTE_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(medioTransporte.length(), 0, 30, LONGITUD_MEDIO_TRANSPORTE_ESTUDIANTE_INVALIDA);
    }
}