package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import java.util.UUID;

public interface RelacionLaboralRepositorioConsulta {
    RelacionLaboral consultarPorIdentificador(UUID identificador);
    RelacionLaboral consultarPorCodigo(String codigo);
}