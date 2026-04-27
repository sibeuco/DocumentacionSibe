package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContextoUsuarioAutenticado {
    private UUID identificador;
    private String correo;
    private String rol;
    private UUID direccionId;
    private UUID areaId;
    private UUID subareaId;
}
