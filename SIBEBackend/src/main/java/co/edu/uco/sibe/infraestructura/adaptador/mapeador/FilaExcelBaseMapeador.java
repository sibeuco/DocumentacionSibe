package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.CellType;
import java.time.format.DateTimeFormatter;

import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;

public abstract class FilaExcelBaseMapeador<T> implements FilaExcelMapeador<T> {
    private final DataFormatter dataFormatter = new DataFormatter();
    private final DateTimeFormatter customDateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    protected String obtenerValorCelda(Row fila, Integer indice) {
        if (indice == null) {
            return VACIO;
        }

        Cell celda = fila.getCell(indice);

        if (celda == null) {
            return VACIO;
        }

        if (celda.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(celda)) {
                try {
                    var localDateTime = celda.getLocalDateTimeCellValue();
                    return localDateTime.format(customDateFormatter);
                } catch (Exception e) {
                    return dataFormatter.formatCellValue(celda).trim();
                }
            }

            return dataFormatter.formatCellValue(celda).trim();
        }

        if (celda.getCellType() == CellType.STRING) {
            return celda.getStringCellValue().trim();
        }

        return dataFormatter.formatCellValue(celda).trim();
    }
}