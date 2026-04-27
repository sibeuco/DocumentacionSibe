package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.puerto.consulta.EmpleadoRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EmpleadoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EmpleadoMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class EmpleadoRepositorioConsultaImplementacion implements EmpleadoRepositorioConsulta {
    private final EmpleadoDAO empleadoDAO;
    private final EmpleadoMapeador empleadoMapeador;

    @Override
    public Empleado consultarPorIdentificador(UUID identificador) {
        var entidad = this.empleadoDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.empleadoMapeador.construirModelo(entidad);
    }

    @Override
    public Empleado consultarPorIdentificacion(String identificacion) {
        var entidad = this.empleadoDAO.findFirstByNumeroIdentificacion(identificacion);

        if(esNulo(entidad)) {
            return null;
        }

        return this.empleadoMapeador.construirModelo(entidad);
    }
}