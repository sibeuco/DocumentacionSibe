package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroEstadisticaDTO {
    private String mes;
    private Integer anno;
    private String semestre;
    private String programaAcademico;
    private String tipoProgramaAcademico;
    private String centroCostos;
    private String tipoParticipante;
    private String indicador;
    private String tipoArea;
    private UUID idArea;
}