package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class TipoUsuario {
    private UUID identificador;
    private String codigo;
    private String nombre;
    private boolean crear;
    private boolean modificar;
    private boolean eliminar;
    private boolean consultar;

    private TipoUsuario(UUID identificador, String codigo, String nombre, boolean crear, boolean modificar, boolean eliminar, boolean consultar) {
        this.identificador = identificador;
        this.codigo = codigo;
        this.nombre = nombre;
        this.crear = crear;
        this.modificar = modificar;
        this.eliminar = eliminar;
        this.consultar = consultar;
    }

    public static TipoUsuario construir(UUID identificador, String codigo, String nombre, boolean crear, boolean modificar, boolean eliminar, boolean consultar) {
        return new TipoUsuario(
                identificador,
                obtenerTextoPorDefecto(codigo),
                obtenerTextoPorDefecto(nombre),
                crear,
                modificar,
                eliminar,
                consultar
        );
    }

    public static TipoUsuario construir() {
        return new TipoUsuario(
                obtenerValorDefecto(),
                VACIO,
                VACIO,
                false,
                false,
                false,
                false
        );
    }
}