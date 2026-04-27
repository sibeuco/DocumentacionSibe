package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDTO {
    private String identificador;
    private String nombre;
    private String objetivo;
    private String semestre;
    private String rutaInsumos;
    private String fechaCreacion;
    private IndicadorDTO indicador;
    private String colaborador;
    private String nombreColaborador;
    private String creador;
}