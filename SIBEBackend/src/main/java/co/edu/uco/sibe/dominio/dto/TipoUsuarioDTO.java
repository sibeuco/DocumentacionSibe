package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuarioDTO {
    private String identificador;
    private String codigo;
    private String nombre;
    private boolean crear;
    private boolean modificar;
    private boolean eliminar;
    private boolean consultar;
}