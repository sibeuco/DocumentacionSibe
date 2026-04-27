package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.puerto.comando.MiembroRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.MiembroDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MiembroRepositorioComandoImplementacion implements MiembroRepositorioComando {
    private final MiembroDAO miembroDAO;

    @Override
    public void desactivarPorNumeroIdentificacion(String numeroIdentificacion) {
        this.miembroDAO.desactivarPorNumeroIdentificacion(numeroIdentificacion);
    }
}
