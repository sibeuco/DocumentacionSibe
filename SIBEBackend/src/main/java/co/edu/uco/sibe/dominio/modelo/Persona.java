package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Persona {
    private UUID identificador;
    private String nombres;
    private String apellidos;
    private String correo;
    private Identificacion identificacion;

    private Persona(UUID identificador, String nombres, String apellidos, String correo, Identificacion identificacion) {
        this.identificador = identificador;
        this.nombres = nombres;
        this.correo = correo;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
    }

    public static Persona construir(UUID identificador, String nombres, String apellidos, String correo, Identificacion identificacion) {
        return new Persona(
                identificador,
                obtenerTextoPorDefecto(nombres),
                obtenerTextoPorDefecto(apellidos),
                obtenerTextoPorDefecto(correo),
                obtenerObjetoPorDefecto(identificacion, Identificacion.construir())
        );
    }

    public static Persona construir() {
        return new Persona(
                obtenerValorDefecto(),
                VACIO,
                VACIO,
                VACIO,
                Identificacion.construir()
        );
    }
}