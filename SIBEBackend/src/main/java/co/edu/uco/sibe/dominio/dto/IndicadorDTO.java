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
public class IndicadorDTO {
    private String identificador;
    private String nombre;
    private TipoIndicadorDTO tipoIndicador;
    private TemporalidadDTO temporalidad;
    private ProyectoDTO proyecto;
    private List<PublicoInteresDTO> publicosInteres;
}