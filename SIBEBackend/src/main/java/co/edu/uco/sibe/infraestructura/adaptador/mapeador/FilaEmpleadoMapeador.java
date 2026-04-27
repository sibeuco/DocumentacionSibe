package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.aplicacion.comando.DatosEmpleadoComando;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import java.util.Map;
import static co.edu.uco.sibe.dominio.transversal.constante.ArchivoConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.MAPEADOR_EMPLEADO_COMPONENTE;


@Component(MAPEADOR_EMPLEADO_COMPONENTE)
public class FilaEmpleadoMapeador extends FilaExcelBaseMapeador<DatosEmpleadoComando> {
    @Override
    public DatosEmpleadoComando mapearFila(Row fila, Map<String, Integer> mapaColumnas) {
        DatosEmpleadoComando empleado = new DatosEmpleadoComando();

        empleado.setNombre(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_NOMBRE)));
        empleado.setGenero(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_GENERO)));
        empleado.setIdentificacion(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_IDENTIFICACION)));
        empleado.setIdCarnet(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_ID_CARNET)));
        empleado.setCodigoClasificacion(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_COD_CLASIFICACION_7)));
        empleado.setGrupoRelacionLaboral(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_GRUPO_RELACION_LABORAL)));
        empleado.setCodigoCentroCostos(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_COD_CENTRO_COSTOS)));
        empleado.setCentroCostos(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_CENTRO_COSTOS_N1)));
        empleado.setCodigoCiudadResidencia(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_COD_CIUDAD_RESIDENCIA)));
        empleado.setCiudadDeResidencia(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_EMPLEADO_CIUDAD_RESIDENCIA)));

        return empleado;
    }
}