package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoUsuarioEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioTipoUsuarioEntidad;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TipoUsuarioMapeador {
    public TipoUsuarioDTO construirDTO(TipoUsuarioEntidad tipoUsuario){
        return new TipoUsuarioDTO(tipoUsuario.getIdentificador().toString(), tipoUsuario.getCodigo(), tipoUsuario.getNombre(), tipoUsuario.isCrear(), tipoUsuario.isModificar(), tipoUsuario.isEliminar(), tipoUsuario.isConsultar());
    }

    public List<TipoUsuarioDTO> construirDTOs(List<TipoUsuarioEntidad> tiposUsuario){
        return tiposUsuario.stream().map(new TipoUsuarioMapeador()::construirDTO).toList();
    }

    public TipoUsuario construirModelo(TipoUsuarioEntidad entidad) {
        return TipoUsuario.construir(entidad.getIdentificador(), entidad.getCodigo(), entidad.getNombre(), entidad.isCrear(), entidad.isModificar(), entidad.isEliminar(), entidad.isConsultar());
    }

    public TipoUsuarioEntidad construirEntidad(TipoUsuario tipoUsuario) {
        return new TipoUsuarioEntidad(tipoUsuario.getIdentificador(), tipoUsuario.getCodigo(), tipoUsuario.getNombre(), tipoUsuario.isCrear(), tipoUsuario.isModificar(), tipoUsuario.isEliminar(), tipoUsuario.isConsultar());
    }

    public void modificarEntidad(UsuarioTipoUsuarioEntidad entidad, TipoUsuario modelo) {
        entidad.setTipoUsuario(construirEntidad(modelo));
    }
}