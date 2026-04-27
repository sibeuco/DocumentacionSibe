package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarDireccionManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreaManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosDireccionManejador;
import co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosDireccionFabrica;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.NUEVE;

@Component
@Order(NUEVE)
@RequiredArgsConstructor
public class DireccionDataLoader extends DataLoader {
    private final HayDatosDireccionManejador hayDatosDireccionManejador;
    private final GuardarDireccionManejador guardarDireccionManejador;
    private final ConsultarAreaManejador consultarAreaManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosDireccionManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        var areas = this.consultarAreaManejador.ejecutar();

        this.guardarDireccionManejador.ejecutar(
                DatosDireccionFabrica.crearDireccionBienestar(areas)
        );
    }
}