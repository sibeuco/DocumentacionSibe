package co.edu.uco.sibe.dominio.transversal.constante;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class SeguridadConstante {
    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String JWT_HEADER = "Authorization";
    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    public static final String JWT_TOKEN = "JWT Token";
    public static final String APP_NAME = "Login";
    public static final String EMAIL_PARAMETER = "email";
    public static final String ID_PARAMETER = "id";
    public static final String AUTHORITIES_PARAMETER = "authorities";
    public static final String LOCAL_FRONT_URL = "http://localhost:4200";

    public static final String ADMINISTRADOR_DIRECCION = "ADMINISTRADOR_DIRECCION";
    public static final String ADMINISTRADOR_AREA = "ADMINISTRADOR_AREA";
    public static final String COLABORADOR = "COLABORADOR";

    public static final String ADMIN_CREATE_AUTHORITY = "ADMINISTRADOR_DIRECCION_CREATE";
    public static final String ADMIN_GET_AUTHORITY = "ADMINISTRADOR_DIRECCION_READ";
    public static final String ADMIN_UPDATE_AUTHORITY = "ADMINISTRADOR_DIRECCION_UPDATE";
    public static final String ADMIN_DELETE_AUTHORITY = "ADMINISTRADOR_DIRECCION_DELETE";
    public static final String AREA_ADMIN_UPDATE_AUTHORITY = "ADMINISTRADOR_AREA_UPDATE";
    public static final String AREA_ADMIN_CREATE_AUTHORITY = "ADMINISTRADOR_AREA_CREATE";
    public static final String AREA_ADMIN_READ_AUTHORITY = "ADMINISTRADOR_AREA_READ";
    public static final String USER_GET_AUTHORITY = "COLABORADOR_READ";
    public static final String USER_UPDATE_AUTHORITY = "COLABORADOR_UPDATE";
    public static final String CREATE_AUTHORITY = "CREATE";
    public static final String READ_AUTHORITY = "READ";
    public static final String UPDATE_AUTHORITY = "UPDATE";
    public static final String DELETE_AUTHORITY = "DELETE";

    public static final String HAS_ADMIN_CREATE_AUTHORITY = "hasAuthority('" + ADMIN_CREATE_AUTHORITY + "')";
    public static final String HAS_AREA_ADMIN_CREATE_AUTHORITY = "hasAuthority('" + AREA_ADMIN_CREATE_AUTHORITY + "')";
    public static final String HAS_ADMIN_GET_AUTHORITY = "hasAuthority('" + ADMIN_GET_AUTHORITY + "')";
    public static final String HAS_AREA_ADMIN_GET_AUTHORITY = "hasAuthority('" + AREA_ADMIN_READ_AUTHORITY + "')";
    public static final String HAS_ADMIN_UPDATE_AUTHORITY = "hasAuthority('" + ADMIN_UPDATE_AUTHORITY + "')";
    public static final String HAS_AREA_ADMIN_UPDATE_AUTHORITY = "hasAuthority('" + AREA_ADMIN_UPDATE_AUTHORITY + "')";
    public static final String HAS_ADMIN_DELETE_AUTHORITY = "hasAuthority('" + ADMIN_DELETE_AUTHORITY + "')";
    public static final String HAS_USER_GET_AUTHORITY = "hasAuthority('" + USER_GET_AUTHORITY + "')";
    public static final String HAS_USER_UPDATE_AUTHORITY = "hasAuthority('" + USER_UPDATE_AUTHORITY + "')";
    public static final String HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY = HAS_USER_GET_AUTHORITY + " or "
            + HAS_ADMIN_GET_AUTHORITY + " or " + HAS_AREA_ADMIN_GET_AUTHORITY;
    public static final String HAS_USER_OR_ADMIN_UPDATE_AUTHORITY = HAS_USER_UPDATE_AUTHORITY + " or "
            + HAS_ADMIN_UPDATE_AUTHORITY;
    public static final String HAS_USER_OR_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY = HAS_USER_UPDATE_AUTHORITY + " or "
            + HAS_ADMIN_UPDATE_AUTHORITY + " or " + HAS_AREA_ADMIN_UPDATE_AUTHORITY;
    public static final String HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY = HAS_ADMIN_CREATE_AUTHORITY + " or "
            + HAS_AREA_ADMIN_CREATE_AUTHORITY;
    public static final String HAS_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY = HAS_ADMIN_UPDATE_AUTHORITY + " or "
            + HAS_AREA_ADMIN_UPDATE_AUTHORITY;

    public static final String USERNAME = "username";
    public static final String AUTHORITIES = "authorities";

    public static final String DIRECCION_ID_PARAMETER = "direccionId";
    public static final String AREA_ID_PARAMETER = "areaId";
    public static final String SUBAREA_ID_PARAMETER = "subareaId";
    public static final String ROL_PARAMETER = "rol";

    private SeguridadConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }
}