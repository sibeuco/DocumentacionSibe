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
public class ActividadDetalladaDTO {
    private String identificador;
    private String nombre;
    private String objetivo;
    private String semestre;
    private String fechaCreacion;
    private IndicadorDTO indicador;
    private String colaborador;
    private List<EjecucionActividadDetalladaDTO> fechasProgramadas;
}