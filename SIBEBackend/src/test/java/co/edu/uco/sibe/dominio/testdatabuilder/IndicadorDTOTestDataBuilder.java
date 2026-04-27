package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IndicadorDTOTestDataBuilder {
    private String identificador = UUID.randomUUID().toString();
    private String nombre = "Indicador Test";
    private TipoIndicadorDTO tipoIndicador = null;
    private TemporalidadDTO temporalidad = null;
    private ProyectoDTO proyecto = null;
    private List<PublicoInteresDTO> publicosInteres = new ArrayList<>();

    public IndicadorDTOTestDataBuilder conIdentificador(String identificador) {
        this.identificador = identificador;
        return this;
    }

    public IndicadorDTOTestDataBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public IndicadorDTOTestDataBuilder conTipoIndicador(TipoIndicadorDTO tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
        return this;
    }

    public IndicadorDTOTestDataBuilder conTemporalidad(TemporalidadDTO temporalidad) {
        this.temporalidad = temporalidad;
        return this;
    }

    public IndicadorDTOTestDataBuilder conProyecto(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public IndicadorDTOTestDataBuilder conPublicosInteres(List<PublicoInteresDTO> publicosInteres) {
        this.publicosInteres = publicosInteres;
        return this;
    }

    public IndicadorDTO construir() {
        return new IndicadorDTO(identificador, nombre, tipoIndicador, temporalidad, proyecto, publicosInteres);
    }
}
