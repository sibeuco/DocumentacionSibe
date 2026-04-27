package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTipoIndicadorManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTipoIndicadorManejador;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.SEIS;
import static co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosTipoIndicadorFabrica.crearTiposIndicador;

@Component
@Order(SEIS)
@RequiredArgsConstructor
public class TipoIndicadorDataLoader extends DataLoader {
    private final HayDatosTipoIndicadorManejador hayDatosTipoIndicadorManejador;
    private final GuardarTipoIndicadorManejador guardarTipoIndicadorManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosTipoIndicadorManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        crearTiposIndicador().forEach(this.guardarTipoIndicadorManejador::ejecutar);
    }
}