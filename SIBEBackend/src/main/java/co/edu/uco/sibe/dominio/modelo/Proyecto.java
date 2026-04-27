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
public class Proyecto {
    private UUID identificador;
    private String numeroProyecto;
    private String nombre;
    private String objetivo;
    private List<Accion> acciones;

    public Proyecto(UUID identificador, String numeroProyecto, String nombre, String objetivo, List<Accion> acciones) {
        this.identificador = identificador;
        this.numeroProyecto = numeroProyecto;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.acciones = acciones;
    }

    public static Proyecto construir(UUID identificador, String numeroProyecto, String nombre, String objetivo, List<Accion> acciones) {
        return new Proyecto(
                identificador,
                obtenerTextoPorDefecto(numeroProyecto),
                obtenerTextoPorDefecto(nombre),
                obtenerTextoPorDefecto(objetivo),
                obtenerObjetoPorDefecto(acciones, new ArrayList<>())
        );
    }

    public static Proyecto construir() {
        return new Proyecto(
                obtenerValorDefecto(),
                VACIO,
                VACIO,
                VACIO,
                new ArrayList<>()
        );
    }
}