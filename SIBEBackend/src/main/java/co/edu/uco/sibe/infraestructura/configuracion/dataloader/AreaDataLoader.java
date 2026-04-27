package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarAreaManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosAreaManejador;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.infraestructura.adaptador.dao.SubareaDAO;
import co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosAreaFabrica;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.OCHO;

@Component
@Order(OCHO)
@RequiredArgsConstructor
public class AreaDataLoader extends DataLoader {
    private final HayDatosAreaManejador hayDatosAreaManejador;
    private final GuardarAreaManejador guardarAreaManejador;
    private final SubareaDAO subareaDAO;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosAreaManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        var subareas = this.subareaDAO.findAll().stream()
                .map(e -> Subarea.construir(e.getIdentificador(), e.getNombre(), new ArrayList<>()))
                .toList();

        this.guardarAreaManejador.ejecutar(DatosAreaFabrica.crearBienestar(subareas));
        this.guardarAreaManejador.ejecutar(DatosAreaFabrica.crearEvangelizacion());
        this.guardarAreaManejador.ejecutar(DatosAreaFabrica.crearHogarJuvenil());
        this.guardarAreaManejador.ejecutar(DatosAreaFabrica.crearServicioAtencionUsuario());
    }
}