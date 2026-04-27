package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarObligatorio;

public final class TipoUsuarioRegla implements Regla<TipoUsuario> {
    private static final TipoUsuarioRegla INSTANCIA = new TipoUsuarioRegla();

    private TipoUsuarioRegla() {
        super();
    }

    public static TipoUsuarioRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_TIPO_USUARIO_NULO);
    }

    @Override
    public void validarCampos(TipoUsuario modelo) {
        validarNombre(modelo.getNombre());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_TIPO_USUARIO_VACIO);
        validarNumeroEntre(nombre.length(), 10, 30, LONGITUD_NOMBRE_TIPO_USUARIO);
    }
}