package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaDTO {
    private String nombre;
    private String tipoArea;
    private Long cantidadParticipantes;
    private Long cantidadAsistencias;
}