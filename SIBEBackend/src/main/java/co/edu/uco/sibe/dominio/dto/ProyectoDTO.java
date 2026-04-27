package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDTO {
    private String identificador;
    private String numeroProyecto;
    private String nombre;
    private String objetivo;
    private List<AccionDTO> acciones;
}