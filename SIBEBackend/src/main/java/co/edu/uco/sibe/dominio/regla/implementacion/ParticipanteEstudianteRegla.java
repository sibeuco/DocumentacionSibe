package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.ParticipanteEstudiante;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroMayorOIgual;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class ParticipanteEstudianteRegla implements Regla<ParticipanteEstudiante> {
    private static final ParticipanteEstudianteRegla INSTANCIA = new ParticipanteEstudianteRegla();

    private ParticipanteEstudianteRegla() {
        super();
    }

    public static ParticipanteEstudianteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_PARTICIPANTE_ESTUDIANTE_NULO);
    }

    @Override
    public void validarCampos(ParticipanteEstudiante modelo) {
        validarIdCarnet(modelo.getIdCarnet());
        validarSexo(modelo.getSexo());
        validarEstadoCivil(modelo.getEstadoCivil());
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

    private void validarIdCarnet(String idCarnet) {
        validarObligatorio(idCarnet, ID_CARNET_INTERNO_OBLIGATORIO);
        validarTextoAlfanumericoValido(idCarnet, ID_CARNET_INTERNO_INVALIDO);
        validarNumeroEntre(idCarnet.length(), 1, 20, LONGITUD_ID_CARNET_INTERNO_INVALIDA);
    }

    private void validarSexo(String sexo) {
        validarObligatorio(sexo, SEXO_PARTICIPANTE_INTERNO_OBLIGATORIO);
        validarTextoValido(sexo, SEXO_PARTICIPANTE_INTERNO_INVALIDO);
        validarNumeroEntre(sexo.length(), 1, 1, LONGITUD_SEXO_PARTICIPANTE_INTERNO_INVALIDA);
    }

    private void validarEstadoCivil(String estadoCivil) {
        validarObligatorio(estadoCivil, ESTADO_CIVIL_PARTICIPANTE_ESTUDIANTE_OBLIGATORIO);
        validarTextoValido(estadoCivil, ESTADO_CIVIL_PARTICIPANTE_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(estadoCivil.length(), 5, 15, LONGITUD_ESTADO_CIVIL_PARTICIPANTE_ESTUDIANTE_INVALIDA);
    }

    private void validarProgramaAcademico(String programaAcademico) {
        validarObligatorio(programaAcademico, PROGRAMA_PARTICIPANTE_ESTUDIANTE_OBLIGATORIO);
        validarTextoValido(programaAcademico, PROGRAMA_PARTICIPANTE_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(programaAcademico.length(), 5, 100, LONGITUD_PROGRAMA_PARTICIPANTE_ESTUDIANTE_INVALIDA);
    }

    private void validarFacultad(String facultad) {
        validarObligatorio(facultad, FACULTAD_PARTICPANTE_ESTUDIANTE_OBLIGATORIA);
        validarTextoValido(facultad, FACULTAD_PARTICIPANTE_ESTUDIANTE_INVALIDA);
        validarNumeroEntre(facultad.length(), 5, 50, LONGITUD_FACULTAD_PARTICIPANTE_ESTUDIANTE_INVALIDA);
    }

    private void validarAnnoIngreso(int annoIngreso) {
        validarNumeroMayorOIgual(annoIngreso, 2000, ANNO_INGRESO_PARTICIPANTE_ESTUDIANTE_INVALIDO);
    }

    private void validarSemestreActual(String semestreActual) {
        validarObligatorio(semestreActual, SEMESTRE_PARTICIPANTE_ESTUDIANTE_OBLIGATORIO);
        validarTextoValido(semestreActual, SEMESTRE_PARTICIPANTE_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(semestreActual.length(), 5, 5, LONGITUD_SEMESTRE_PARTICIPANTE_ESTUDIANTE_INVALIDA);
    }

    private void validarCreditosAprobados(int creditosAprobados) {
        validarNumeroMayorOIgual(creditosAprobados, 0, CREDITOS_APROBADOS_PARTICIPANTE_ESTUDIANTE_INVALIDOS);
    }

    private void validarPromedioGeneral(float promedioGeneral) {
        validarNumeroMayorOIgual(promedioGeneral, 0.0, PROMEDIO_GENERAL_PARTICIPANTE_ESTUDIANTE_INVALIDO);
    }

    private void validarEstadoAcademico(String estadoAcademico) {
        validarObligatorio(estadoAcademico, ESTADO_ACADEMICO_PARTICIPANTE_ESTUDIANTE_OBLIGATORIO);
        validarTextoValido(estadoAcademico, ESTADO_ACADEMICO_PARTICIPANTE_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(estadoAcademico.length(), 6, 10, LONGITUD_ESTADO_ACADEMICO_PARTICIPANTE_ESTUDIANTE_INVALIDA);
    }

    private void validarModalidadEstudio(String modalidadEstudio) {
        validarObligatorio(modalidadEstudio, MODALIDAD_ESTUDIO_PARTICIPANTE_ESTUDIANTE_OBLIGATORIA);
        validarTextoValido(modalidadEstudio, MODALIDAD_ESTUDIO_PARTICIPANTE_ESTUDIANTE_INVALIDA);
        validarNumeroEntre(modalidadEstudio.length(), 5, 50, LONGITUD_MODALIDAD_ESTUDIO_PARTICIPANTE_ESTUDIANTE_INVALIDA);
    }

    private void validarTiempoLlegadaUniversidad(int tiempoLlegadaUniversidad) {
        validarNumeroMayorOIgual(tiempoLlegadaUniversidad, 1, TIEMPO_LLEGADA_PARTICIPANTE_ESTUDIANTE_INVALIDO);
    }

    private void validarMedioTransporte(String medioTransporte) {
        validarTextoValido(medioTransporte, MEDIO_TRANSPORTE_PARTICIPANTE_ESTUDIANTE_INVALIDO);
        validarNumeroEntre(medioTransporte.length(), 5, 30, LONGITUD_MEDIO_TRANSPORTE_PARTICIPANTE_ESTUDIANTE_INVALIDA);
    }
}