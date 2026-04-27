package co.edu.uco.sibe.aplicacion.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatosEstudianteComando {
    private String nombreCompleto;
    private String numeroIdentificacion;
    private String idLecturaCarnetUniversitario;
    private String fechaNacimiento;
    private String sexo;
    private String nacionalidad;
    private String estadoCivil;
    private String direccion;
    private String municipioResidencia;
    private String telefono;
    private String celular;
    private String correoPersonal;
    private String correoInstitucional;
    private String programaAcademico;
    private String facultad;
    private String annoIngreso;
    private String semestreActual;
    private String creditosAprobados;
    private String promedioGeneral;
    private String estadoAcademico;
    private String modalidad;
    private String tiempoLlegadaUniversidad;
    private String medioDeTransporte;
}