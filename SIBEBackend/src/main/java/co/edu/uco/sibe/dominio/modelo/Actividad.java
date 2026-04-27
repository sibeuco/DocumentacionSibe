package co.edu.uco.sibe.dominio.modelo;

import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilFecha.obtenerValorFechaPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilFecha.obtenerFechaDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import lombok.Getter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Actividad {
    private UUID identificador;
    private String nombre;
    private String objetivo;
    private String semestre;
    private String rutaInsumos;
    private LocalDate fechaCreacion;
    private Indicador indicador;
    private UUID colaborador;
    private UUID creador;

    private Actividad(
            UUID identificador,
            String nombre,
            String objetivo,
            String semestre,
            String rutaInsumos,
            LocalDate fechaCreacion,
            Indicador indicador,
            UUID colaborador,
            UUID creador) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.semestre = semestre;
        this.rutaInsumos = rutaInsumos;
        this.fechaCreacion = fechaCreacion;
        this.indicador = indicador;
        this.colaborador = colaborador;
        this.creador = creador;
    }

    public static Actividad construir(
            UUID identificador,
            String nombre,
            String objetivo,
            String semestre,
            String rutaInsumos,
            LocalDate fechaCreacion,
            Indicador indicador,
            UUID colaborador,
            UUID creador
    ) {
        return new Actividad(
                identificador,
                obtenerTextoPorDefecto(nombre),
                obtenerTextoPorDefecto(objetivo),
                obtenerTextoPorDefecto(semestre),
                obtenerTextoPorDefecto(rutaInsumos),
                obtenerValorFechaPorDefecto(fechaCreacion),
                obtenerObjetoPorDefecto(indicador,Indicador.construir()),
                colaborador,
                creador
        );
    }

    public static Actividad construir() {
        return new Actividad(
                obtenerValorDefecto(),
                VACIO,
                VACIO,
                VACIO,
                VACIO,
                obtenerFechaDefecto(),
                Indicador.construir(),
                obtenerValorDefecto(),
                obtenerValorDefecto()
        );
    }
}