package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {
    private String identificador;
    private String nombres;
    private String apellidos;
    private String correo;
    private IdentificacionDTO identificacion;
}