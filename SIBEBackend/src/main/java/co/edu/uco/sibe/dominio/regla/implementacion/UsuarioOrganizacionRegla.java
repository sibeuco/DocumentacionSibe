package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.UsuarioOrganizacion;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.IDENTIFICADOR_USUARIO_ORGANIZACION_NULO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_HAY_CAMPOS_POR_VALIDAR;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;

public class UsuarioOrganizacionRegla implements Regla<UsuarioOrganizacion> {
    private static final UsuarioOrganizacionRegla INSTANCIA = new UsuarioOrganizacionRegla();

    private UsuarioOrganizacionRegla() {
        super();
    }

    public static UsuarioOrganizacionRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_USUARIO_ORGANIZACION_NULO);
    }

    @Override
    public void validarCampos(UsuarioOrganizacion modelo) {
        throw new IllegalStateException(NO_HAY_CAMPOS_POR_VALIDAR);
    }
}