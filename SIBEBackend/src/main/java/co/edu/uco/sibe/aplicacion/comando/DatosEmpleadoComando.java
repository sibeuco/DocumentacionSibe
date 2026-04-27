package co.edu.uco.sibe.aplicacion.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatosEmpleadoComando {
    private String nombre;
    private String genero;
    private String identificacion;
    private String idCarnet;
    private String codigoClasificacion;
    private String grupoRelacionLaboral;
    private String codigoCentroCostos;
    private String centroCostos;
    private String codigoCiudadResidencia;
    private String ciudadDeResidencia;
}