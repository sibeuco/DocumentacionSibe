import { HttpErrorResponse } from '@angular/common/http';
import { ManejadorError } from './manejador-error';
import { HTTP_ERRORES_CODIGO } from './http-codigo-error';

describe('ManejadorError', () => {
  let handler: ManejadorError;

  beforeEach(() => {
    handler = new ManejadorError();
    spyOn(window.console, 'error');
  });

  it('should be created', () => {
    expect(handler).toBeTruthy();
  });

  it('should log a string error to console', () => {
    handler.handleError('Something went wrong');
    expect(window.console.error).toHaveBeenCalled();
  });

  it('should log a generic Error to console', () => {
    handler.handleError(new Error('Generic error'));
    expect(window.console.error).toHaveBeenCalled();
  });

  it('should handle HttpErrorResponse when offline', () => {
    const originalOnLine = navigator.onLine;
    spyOnProperty(navigator, 'onLine').and.returnValue(false);

    const httpError = new HttpErrorResponse({ status: 0, statusText: 'Unknown Error' });

    handler.handleError(httpError as any);

    const callArgs = (window.console.error as jasmine.Spy).calls.mostRecent().args;
    const loggedResponse = callArgs[1];
    expect(loggedResponse.mensaje).toBe(HTTP_ERRORES_CODIGO['NO_HAY_INTERNET']);
  });

  it('should handle HttpErrorResponse with known status code and no mensaje in error body', () => {
    const httpError = new HttpErrorResponse({
      status: 404,
      statusText: 'Not Found',
      error: { someField: 'value' }
    });

    handler.handleError(httpError as any);

    const callArgs = (window.console.error as jasmine.Spy).calls.mostRecent().args;
    const loggedResponse = callArgs[1];
    expect(loggedResponse.mensaje).toBe(HTTP_ERRORES_CODIGO['PETICION_FALLIDA']);
  });

  it('should handle HttpErrorResponse with error body containing mensaje (passes through)', () => {
    const httpError = new HttpErrorResponse({
      status: 500,
      statusText: 'Internal Server Error',
      error: { mensaje: 'Custom backend error' }
    });

    handler.handleError(httpError as any);

    const callArgs = (window.console.error as jasmine.Spy).calls.mostRecent().args;
    const loggedResponse = callArgs[1];
    // When error.error has 'mensaje', the default handler returns the raw error
    expect(loggedResponse.mensaje).toBeTruthy();
  });

  it('should log error with date, path and message', () => {
    handler.handleError('test error');

    const callArgs = (window.console.error as jasmine.Spy).calls.mostRecent().args;
    expect(callArgs[0]).toContain('Error inesperado');
    const loggedResponse = callArgs[1];
    expect(loggedResponse.fecha).toBeDefined();
    expect(loggedResponse.path).toBe(window.location.href);
    expect(loggedResponse.mensaje).toBeDefined();
  });

  it('should handle HttpErrorResponse with unknown status code', () => {
    const httpError = new HttpErrorResponse({
      status: 999,
      statusText: 'Unknown',
      error: { someField: 'value' }
    });

    handler.handleError(httpError as any);

    const callArgs = (window.console.error as jasmine.Spy).calls.mostRecent().args;
    const loggedResponse = callArgs[1];
    // Unknown codes return undefined from HTTP_ERRORES_CODIGO
    expect(loggedResponse.mensaje).toBeUndefined();
  });
});
