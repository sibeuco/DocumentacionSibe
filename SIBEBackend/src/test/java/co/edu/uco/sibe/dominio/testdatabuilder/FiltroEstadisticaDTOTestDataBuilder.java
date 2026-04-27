package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;

import java.util.UUID;

public class FiltroEstadisticaDTOTestDataBuilder {
    private String mes = null;
    private Integer anno = null;
    private String semestre = null;
    private String programaAcademico = null;
    private String tipoProgramaAcademico = null;
    private String centroCostos = null;
    private String tipoParticipante = null;
    private String indicador = null;
    private String tipoArea = null;
    private UUID idArea = null;

    public FiltroEstadisticaDTOTestDataBuilder conMes(String mes) {
        this.mes = mes;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conAnno(Integer anno) {
        this.anno = anno;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conSemestre(String semestre) {
        this.semestre = semestre;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conTipoProgramaAcademico(String tipoProgramaAcademico) {
        this.tipoProgramaAcademico = tipoProgramaAcademico;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conCentroCostos(String centroCostos) {
        this.centroCostos = centroCostos;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conTipoParticipante(String tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conIndicador(String indicador) {
        this.indicador = indicador;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conTipoArea(String tipoArea) {
        this.tipoArea = tipoArea;
        return this;
    }

    public FiltroEstadisticaDTOTestDataBuilder conIdArea(UUID idArea) {
        this.idArea = idArea;
        return this;
    }

    public FiltroEstadisticaDTO construir() {
        return new FiltroEstadisticaDTO(mes, anno, semestre, programaAcademico, tipoProgramaAcademico,
                centroCostos, tipoParticipante, indicador, tipoArea, idArea);
    }
}
