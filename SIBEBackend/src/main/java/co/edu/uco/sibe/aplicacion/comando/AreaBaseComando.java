package co.edu.uco.sibe.aplicacion.comando;

import co.edu.uco.sibe.dominio.modelo.Subarea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaBaseComando {
    private String nombre;
    private List<Subarea> subareas;
}