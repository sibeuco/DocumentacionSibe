package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DireccionDetalladaDTO {
    private UUID identificador;
    private String nombre;
    private List<AreaDetalladaDTO> areas;
    private List<ActividadDetalladaDTO> actividades;
}