package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTipoIdentificacionManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTipoIdentificacionManejador;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.DOS;
import static co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosTipoIdentificacionFabrica.*;

@Component
@Order(DOS)
@RequiredArgsConstructor
public class TipoIdentificacionDataLoader extends DataLoader {
    private final HayDatosTipoIdentificacionManejador hayDatosTipoIdentificacionManejador;
    private final GuardarTipoIdentificacionManejador guardarTipoIdentificacionManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosTipoIdentificacionManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        this.guardarTipoIdentificacionManejador.ejecutar(crearCedulaCiudadania());
        this.guardarTipoIdentificacionManejador.ejecutar(crearTarjetaIdentidad());
        this.guardarTipoIdentificacionManejador.ejecutar(crearCedulaExtranjeria());
    }
}