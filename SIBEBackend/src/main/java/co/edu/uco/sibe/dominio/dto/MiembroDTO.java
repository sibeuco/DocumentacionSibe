package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MiembroDTO {
    private String identificador;
    private String nombreCompleto;
    private String documentoIdentificacion;
    private String programaAcademico;
    private String tipoProgramaAcademico;
    private String correoInstitucional;
    private String tipo;
    private String tipoInterno;
}