package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;

public class ContextoUsuarioAutenticadoTestDataBuilder {
    private UUID identificador = UUID.randomUUID();
    private String correo = "usuario@test.com";
    private String rol = COLABORADOR;
    private UUID direccionId = UUID.randomUUID();
    private UUID areaId = UUID.randomUUID();
    private UUID subareaId = UUID.randomUUID();

    public ContextoUsuarioAutenticadoTestDataBuilder conIdentificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder conCorreo(String correo) {
        this.correo = correo;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder conRol(String rol) {
        this.rol = rol;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder conDireccionId(UUID direccionId) {
        this.direccionId = direccionId;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder conAreaId(UUID areaId) {
        this.areaId = areaId;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder conSubareaId(UUID subareaId) {
        this.subareaId = subareaId;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder comoAdministradorDireccion() {
        this.rol = ADMINISTRADOR_DIRECCION;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder comoAdministradorArea() {
        this.rol = ADMINISTRADOR_AREA;
        return this;
    }

    public ContextoUsuarioAutenticadoTestDataBuilder comoColaborador() {
        this.rol = COLABORADOR;
        return this;
    }

    public ContextoUsuarioAutenticado construir() {
        return new ContextoUsuarioAutenticado(identificador, correo, rol, direccionId, areaId, subareaId);
    }
}
