package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import java.util.Map;
import static co.edu.uco.sibe.dominio.transversal.constante.ArchivoConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.MAPEADOR_ESTUDIANTE_COMPONENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.COMMA;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.PUNTO;

@Component(MAPEADOR_ESTUDIANTE_COMPONENTE)
public class FilaEstudianteMapeador extends FilaExcelBaseMapeador<DatosEstudianteComando> {
    @Override
    public DatosEstudianteComando mapearFila(Row fila, Map<String, Integer> mapaColumnas) {
        DatosEstudianteComando estudiante = new DatosEstudianteComando();

        estudiante.setNombreCompleto(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_NOMBRE_COMPLETO)));
        estudiante.setNumeroIdentificacion(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_NUMERO_IDENTIFICACION)));
        estudiante.setIdLecturaCarnetUniversitario(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_ID_CARNET)));
        estudiante.setCorreoInstitucional(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_CORREO_INSTITUCIONAL)));
        estudiante.setFechaNacimiento(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_FECHA_NACIMIENTO)));
        estudiante.setSexo(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_SEXO)));
        estudiante.setNacionalidad(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_NACIONALIDAD)));
        estudiante.setEstadoCivil(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_ESTADO_CIVIL)));
        estudiante.setDireccion(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_DIRECCION)));
        estudiante.setMunicipioResidencia(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_MUNICIPIO_RESIDENCIA)));
        estudiante.setTelefono(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_TELEFONO)));
        estudiante.setCelular(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_CELULAR)));
        estudiante.setCorreoPersonal(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_CORREO_PERSONAL)));
        estudiante.setProgramaAcademico(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_PROGRAMA_ACADEMICO)));
        estudiante.setFacultad(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_FACULTAD)));
        estudiante.setAnnoIngreso(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_ANNO_INGRESO)));
        estudiante.setSemestreActual(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_SEMESTRE_ACTUAL)));
        estudiante.setCreditosAprobados(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_CREDITOS_APROBADOS)));
        estudiante.setPromedioGeneral(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_PROMEDIO_GENERAL)).replace(COMMA, PUNTO));
        estudiante.setEstadoAcademico(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_ESTADO_ACADEMICO)));
        estudiante.setModalidad(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_MODALIDAD)));
        estudiante.setTiempoLlegadaUniversidad(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_TIEMPO_LLEGADA)));
        estudiante.setMedioDeTransporte(obtenerValorCelda(fila, mapaColumnas.get(COLUMNA_ESTUDIANTE_MEDIO_TRANSPORTE)));

        return estudiante;
    }
}