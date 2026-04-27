package co.edu.uco.sibe.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EjecucionActividadDTO {
    private String identificador;
    private String fechaProgramada;
    private String fechaRealizacion;
    private String horaInicio;
    private String horaFin;
    private EstadoActividadDTO estadoActividad;
}