package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PeticionRecuperacionClaveDTO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PeticionRecuperacionClaveDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PeticionRecuperacionClaveEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class PeticionRecuperacionClaveMapeador {
    private final PeticionRecuperacionClaveDAO peticionRecuperacionClaveDAO;

    public PeticionRecuperacionClaveEntidad construirEntidad(String codigo, String correo, LocalDateTime fecha) {
        return new PeticionRecuperacionClaveEntidad(
                generar(uuid -> !esNulo(peticionRecuperacionClaveDAO.findById(uuid).orElse(null))),
                codigo,
                correo,
                fecha
        );
    }

    public void actualizarEntidad(PeticionRecuperacionClaveEntidad entidad, String codigo, LocalDateTime fecha) {
        entidad.setCodigo(codigo);
        entidad.setFecha(fecha);
    }

    public PeticionRecuperacionClaveDTO construirDTO(PeticionRecuperacionClaveEntidad entidad) {
        return new PeticionRecuperacionClaveDTO(entidad.getIdentificador().toString(), entidad.getCorreo(), entidad.getCodigo(), entidad.getFecha().toString());
    }
}