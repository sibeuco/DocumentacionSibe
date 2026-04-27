package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import java.util.UUID;

public interface CentroCostosRepositorioConsulta {
    CentroCostos consultarPorIdentificador(UUID identificador);
    CentroCostos consultarPorCodigo(String codigo);
}