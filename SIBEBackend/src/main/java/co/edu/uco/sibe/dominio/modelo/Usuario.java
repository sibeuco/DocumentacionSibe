package co.edu.uco.sibe.dominio.modelo;

import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class Usuario {
    private UUID identificador;
    private String correo;
    private String clave;
    private TipoUsuario tipoUsuario;
    private boolean estaActivo;

    private Usuario(UUID identificador, String correo, String clave, TipoUsuario tipoUsuario, boolean estaActivo) {
        this.identificador = identificador;
        this.correo = correo;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
        this.estaActivo = estaActivo;
    }

    public static Usuario construir(UUID identificador, String correo, String clave, TipoUsuario tipoUsuario, boolean estaActivo) {
        return new Usuario(
                identificador,
                obtenerTextoPorDefecto(correo),
                obtenerTextoPorDefecto(clave),
                obtenerObjetoPorDefecto(tipoUsuario, TipoUsuario.construir()),
                estaActivo
        );
    }

    public static Usuario construir() {
        return new Usuario(
                UtilUUID.obtenerValorDefecto(),
                VACIO,
                VACIO,
                TipoUsuario.construir(),
                false
        );
    }

    public void actualizarClave(String clave) {
        this.clave = clave;
    }
}