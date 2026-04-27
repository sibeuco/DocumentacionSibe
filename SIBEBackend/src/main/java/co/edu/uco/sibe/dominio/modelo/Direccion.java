package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class Direccion {
    private UUID identificador;
    private String nombre;
    private List<Area> areas;
    private List<Actividad> actividades;

    private Direccion(UUID identificador, String nombre, List<Area> areas, List<Actividad> actividades) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.areas = areas;
        this.actividades = actividades;
    }

    public static Direccion construir(UUID identificador, String nombre, List<Area> areas, List<Actividad> actividades) {
        return new Direccion(
                identificador,
                obtenerTextoPorDefecto(nombre),
                obtenerObjetoPorDefecto(areas, new ArrayList<>()),
                obtenerObjetoPorDefecto(actividades, new ArrayList<>())
        );
    }

    public static Direccion construir() {
        return new Direccion(
                obtenerValorDefecto(),
                VACIO,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}