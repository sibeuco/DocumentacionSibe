package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaMesDTO {
    private String mes;
    private Long cantidadParticipantes;
    private Long cantidadAsistencias;
}