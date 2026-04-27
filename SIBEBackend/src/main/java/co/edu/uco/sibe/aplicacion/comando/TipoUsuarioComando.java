package co.edu.uco.sibe.aplicacion.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuarioComando {
    private String codigo;
    private String nombre;
    private boolean crear;
    private boolean modificar;
    private boolean eliminar;
    private boolean consultar;
}