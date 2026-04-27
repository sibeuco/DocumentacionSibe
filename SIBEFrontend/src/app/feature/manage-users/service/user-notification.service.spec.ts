import { UserNotificationService } from './user-notification.service';

describe('UserNotificationService', () => {
  let service: UserNotificationService;

  beforeEach(() => {
    service = new UserNotificationService();
  });

  it('should be created', () => expect(service).toBeTruthy());

  it('should emit when user is created', (done) => {
    const usuario = { nombre: 'Test' };
    service.usuarioCreado$.subscribe(data => {
      expect(data).toEqual(usuario);
      done();
    });
    service.notificarUsuarioCreado(usuario);
  });

  it('should emit when user is updated', (done) => {
    const usuario = { nombre: 'Updated' };
    service.usuarioActualizado$.subscribe(data => {
      expect(data).toEqual(usuario);
      done();
    });
    service.notificarUsuarioActualizado(usuario);
  });

  it('should emit when user is deleted', (done) => {
    const usuario = { nombre: 'Deleted' };
    service.usuarioEliminado$.subscribe(data => {
      expect(data).toEqual(usuario);
      done();
    });
    service.notificarUsuarioEliminado(usuario);
  });
});
