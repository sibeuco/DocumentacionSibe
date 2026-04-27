package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteDTO {
    private String identificador;
    private String nombreCompleto;
    private String numeroIdentificacion;
    private RelacionLaboralDTO relacionLaboral;
    private CentroCostosDTO centroCostos;
    private String tipo;
    private String fechaNacimiento;
    private String nacionalidad;
    private String estadoCivil;
    private String correoPersonal;
    private String correoInstitucional;
    private String programaAcademico;
    private String tipoProgramaAcademico;
    private String facultad;
    private int annoIngreso;
    private String semestreActual;
    private int creditosAprobados;
    private float promedioGeneral;
    private String estadoAcademico;
    private String modalidadEstudio;
    private int tiempoLlegadaUniversidad;
    private String medioTransporte;
}