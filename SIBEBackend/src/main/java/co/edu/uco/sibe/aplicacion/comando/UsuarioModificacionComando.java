package co.edu.uco.sibe.aplicacion.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModificacionComando {
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String correo;
    private String tipoUsuario;
    private AreaComando area;
}