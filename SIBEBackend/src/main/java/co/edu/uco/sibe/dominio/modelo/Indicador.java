package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Indicador {
    private UUID identificador;
    private String nombre;
    private TipoIndicador tipoIndicador;
    private Temporalidad temporalidad;
    private Proyecto proyecto;
    private List<PublicoInteres> publicosInteres;

    private Indicador(UUID identificador, String nombre, TipoIndicador tipoIndicador, Temporalidad temporalidad, Proyecto proyecto, List<PublicoInteres> publicosInteres) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.tipoIndicador = tipoIndicador;
        this.temporalidad = temporalidad;
        this.proyecto = proyecto;
        this.publicosInteres = publicosInteres;
    }

    public static Indicador construir(UUID identificador, String nombre, TipoIndicador tipoIndicador, Temporalidad temporalidad, Proyecto proyecto, List<PublicoInteres> publicosInteres) {
        return new Indicador(
                identificador,
                obtenerTextoPorDefecto(nombre),
                obtenerObjetoPorDefecto(tipoIndicador, TipoIndicador.construir()),
                obtenerObjetoPorDefecto(temporalidad, Temporalidad.construir()),
                obtenerObjetoPorDefecto(proyecto, Proyecto.construir()),
                obtenerObjetoPorDefecto(publicosInteres, new ArrayList<>())
        );
    }

    public static Indicador construir() {
        return new Indicador(
                obtenerValorDefecto(),
                VACIO,
                TipoIndicador.construir(),
                Temporalidad.construir(),
                Proyecto.construir(),
                new ArrayList<>()
        );
    }
}