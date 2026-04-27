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
public class Subarea {
    private UUID identificador;
    private String nombre;
    private List<Actividad> actividades;

    private Subarea(UUID identificador, String nombre, List<Actividad> actividades) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.actividades = actividades;
    }

    public static Subarea construir(UUID identificador, String nombre, List<Actividad> actividades) {
        return new Subarea(
                identificador,
                obtenerTextoPorDefecto(nombre),
                obtenerObjetoPorDefecto(actividades, new ArrayList<>())
        );
    }

    public static Subarea construir() {
        return new Subarea(
                obtenerValorDefecto(),
                VACIO,
                new ArrayList<>()
        );
    }
}