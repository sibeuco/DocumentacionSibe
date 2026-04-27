package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTipoUsuarioManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTipoUsuarioManejador;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.UNO;
import static co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosTipoUsuarioFabrica.*;

@Component
@Order(UNO)
@RequiredArgsConstructor
public class TipoUsuarioDataLoader extends DataLoader {
    private final HayDatosTipoUsuarioManejador hayDatosTipoUsuarioManejador;
    private final GuardarTipoUsuarioManejador guardarTipoUsuarioManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosTipoUsuarioManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        this.guardarTipoUsuarioManejador.ejecutar(crearAdministradorDireccion());
        this.guardarTipoUsuarioManejador.ejecutar(crearAdministradorArea());
        this.guardarTipoUsuarioManejador.ejecutar(crearColaborador());
    }
}