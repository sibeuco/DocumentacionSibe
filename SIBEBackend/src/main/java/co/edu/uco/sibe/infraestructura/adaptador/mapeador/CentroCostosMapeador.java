package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.CentroCostosDTO;
import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CentroCostosEntidad;
import org.springframework.stereotype.Component;

@Component
public class CentroCostosMapeador {
    public CentroCostos construirModelo(CentroCostosEntidad centroCostos) {
        return CentroCostos.construir(
                centroCostos.getIdentificador(),
                centroCostos.getCodigo(),
                centroCostos.getDescripcion()
        );
    }

    public CentroCostosEntidad construirEntidad(CentroCostos centroCostos) {
        return new CentroCostosEntidad(
                centroCostos.getIdentificador(),
                centroCostos.getCodigo(),
                centroCostos.getDescripcion()
        );
    }

    public CentroCostosDTO construirDTO(CentroCostosEntidad centroCostos) {
        return new CentroCostosDTO(
                centroCostos.getIdentificador().toString(),
                centroCostos.getCodigo(),
                centroCostos.getDescripcion()
        );
    }
}