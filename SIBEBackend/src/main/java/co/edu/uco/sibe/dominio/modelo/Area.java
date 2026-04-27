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
public class Area {
    private UUID identificador;
    private String nombre;
    private List<Subarea> subareas;
    private List<Actividad> actividades;

    private Area(UUID identificador, String nombre, List<Subarea> subareas, List<Actividad> actividades) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.subareas = subareas;
        this.actividades = actividades;
    }

    public static Area construir(UUID identificador, String nombre, List<Subarea> subareas, List<Actividad> actividades) {
        return new Area(
                identificador,
                obtenerTextoPorDefecto(nombre),
                obtenerObjetoPorDefecto(subareas, new ArrayList<>()),
                obtenerObjetoPorDefecto(actividades, new ArrayList<>())
        );
    }

    public static Area construir() {
        return new Area(
                obtenerValorDefecto(),
                VACIO,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}