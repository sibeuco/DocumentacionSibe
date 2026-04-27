export const HTTP_ERRORES_CODIGO : {[key:string]:string} = {
  NO_HAY_INTERNET: 'Lo sentimos, no se detecta conexión a internet',
  PETICION_FALLIDA: 'Error inesperado en la petición',
  400: 'El servidor no puede procesar la petición por un error de sintaxis del cliente.',
  403: 'No tienes permisos para realizar esta acción. Tu rol no permite acceder a este recurso.',
  404: 'No se encuentra la petición.',
  405: 'Se ha hecho una petición con un recurso no soportado por ese recurso (GET, POST, PUT, DELETE).',
  500: 'Error inesperado en el servidor.',
  501: 'El servidor o no reconoce el método del la petición o carece de la capacidad para completarlo.',
  503: 'El servidor no esta disponible.',
  504: 'El tiempo de espera para la petición se ha excedido',
};
