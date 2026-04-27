package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.EmpleadoFabrica;
import co.edu.uco.sibe.aplicacion.puerto.servicio.ProcesadorArchivoEmpleadoServicio;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.puerto.comando.EmpleadoRepositorioComando;
import co.edu.uco.sibe.dominio.usecase.comando.CargarMasivamenteEmpleadosUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CargarMasivamenteEmpleadosManejador implements ManejadorComandoRespuesta<MultipartFile, ComandoRespuesta<List<UUID>>> {
    private final ProcesadorArchivoEmpleadoServicio procesadorArchivoEmpleadoServicio;
    private final EmpleadoFabrica empleadoFabrica;
    private final CargarMasivamenteEmpleadosUseCase cargarMasivamenteEmpleadosUseCase;
    private final EmpleadoRepositorioComando empleadoRepositorioComando;

    @Override
    @Transactional
    public ComandoRespuesta<List<UUID>> ejecutar(MultipartFile comando) {
        var identificadores = new ArrayList<UUID>();
        var empleadosComando = procesadorArchivoEmpleadoServicio.procesar(comando);

        empleadoRepositorioComando.desactivarTodos();

        empleadosComando.forEach(empleadoComando -> {
            var empleado = empleadoFabrica.construir(empleadoComando);

            identificadores.add(this.cargarMasivamenteEmpleadosUseCase.ejecutar(empleado));
        });

        return new ComandoRespuesta<>(identificadores);
    }
}