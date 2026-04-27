package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmpleadoMapeador {
    private final InternoCiudadResidenciaMapeador internoCiudadResidenciaMapeador;
    private final EmpleadoRelacionLaboralMapeador empleadoRelacionLaboralMapeador;
    private final EmpleadoCentroCostosMapeador empleadoCentroCostosMapeador;

    public Empleado construirModelo(EmpleadoEntidad empleadoEntidad) {
        return Empleado.construir(
                empleadoEntidad.getIdentificador(),
                empleadoEntidad.getNombreCompleto(),
                empleadoEntidad.getNumeroIdentificacion(),
                this.internoCiudadResidenciaMapeador.construirModelo(empleadoEntidad.getCiudadResidencia()),
                empleadoEntidad.getIdCarnet(),
                empleadoEntidad.getSexo(),
                this.empleadoRelacionLaboralMapeador.construirModelo(empleadoEntidad.getRelacionLaboral()),
                this.empleadoCentroCostosMapeador.construirModelo(empleadoEntidad.getCentroCostos())
        );
    }

    public EmpleadoEntidad construirEntidad(Empleado empleado) {
        return new EmpleadoEntidad(
                empleado.getIdentificador(),
                empleado.getNombreCompleto(),
                empleado.getNumeroIdentificacion(),
                this.internoCiudadResidenciaMapeador.construirEntidad(empleado.getCiudadResidencia()),
                empleado.getIdCarnet(),
                empleado.getSexo(),
                this.empleadoRelacionLaboralMapeador.construirEntidad(empleado.getRelacionLaboral()),
                this.empleadoCentroCostosMapeador.construirEntidad(empleado.getCentroCostos())
        );
    }

    public void modificarEntidad(EmpleadoEntidad entidad, Empleado empleado) {
        entidad.setNombreCompleto(empleado.getNombreCompleto());
        entidad.setNumeroIdentificacion(empleado.getNumeroIdentificacion());
        this.internoCiudadResidenciaMapeador.modificarEntidad(entidad.getCiudadResidencia(), empleado.getCiudadResidencia());
        entidad.setIdCarnet(empleado.getIdCarnet());
        entidad.setSexo(empleado.getSexo());
        this.empleadoRelacionLaboralMapeador.modificarEntidad(entidad.getRelacionLaboral(), empleado.getRelacionLaboral());
        this.empleadoCentroCostosMapeador.modificarEntidad(entidad.getCentroCostos(), empleado.getCentroCostos());
    }
}