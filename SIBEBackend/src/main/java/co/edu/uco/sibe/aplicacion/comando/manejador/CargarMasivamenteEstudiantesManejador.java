package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.EstudianteFabrica;
import co.edu.uco.sibe.aplicacion.puerto.servicio.ProcesadorArchivoEstudianteServicio;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.puerto.comando.EstudianteRepositorioComando;
import co.edu.uco.sibe.dominio.usecase.comando.CargarMasivamenteEstudiantesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CargarMasivamenteEstudiantesManejador implements ManejadorComandoRespuesta<MultipartFile, ComandoRespuesta<List<UUID>>> {
    private final ProcesadorArchivoEstudianteServicio procesadorArchivoEstudianteServicio;
    private final EstudianteFabrica estudianteFabrica;
    private final CargarMasivamenteEstudiantesUseCase cargarMasivamenteEstudiantesUseCase;
    private final EstudianteRepositorioComando estudianteRepositorioComando;

    @Override
    @Transactional
    public ComandoRespuesta<List<UUID>> ejecutar(MultipartFile comando) {
        var identificadores = new ArrayList<UUID>();
        var estudiantesComando = procesadorArchivoEstudianteServicio.procesar(comando);

        estudianteRepositorioComando.desactivarTodos();

        estudiantesComando.forEach(estudianteComando -> {
            var estudiante = estudianteFabrica.construir(estudianteComando);

            identificadores.add(this.cargarMasivamenteEstudiantesUseCase.ejecutar(estudiante));
        });

        return new ComandoRespuesta<>(identificadores);
    }
}