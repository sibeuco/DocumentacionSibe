import { TestBed } from '@angular/core/testing';
import { StateService } from './state.service';
import { StateProps } from '../model/state.enum';

describe('StateService', () => {
  let service: StateService;

  beforeEach(() => {
    sessionStorage.clear();
    TestBed.configureTestingModule({ providers: [StateService] });
    service = TestBed.inject(StateService);
  });

  afterEach(() => sessionStorage.clear());

  it('should be created', () => expect(service).toBeTruthy());

  it('should update and get state', () => {
    service.updateState(StateProps.USER_SESSION, { correo: 'test@test.com' });
    expect(service.getState(StateProps.USER_SESSION)).toEqual({ correo: 'test@test.com' });
  });

  it('should delete a property from state', () => {
    service.updateState(StateProps.USER_SESSION, { correo: 'test@test.com' });
    service.deleteProperty(StateProps.USER_SESSION);
    expect(service.getState(StateProps.USER_SESSION)).toBeUndefined();
  });

  it('should emit values via select observable', (done) => {
    service.updateState(StateProps.USER_SESSION, { correo: 'val1' });
    service.select(StateProps.USER_SESSION).subscribe(val => {
      if (val && (val as any).correo === 'val1') done();
    });
  });

  it('should return undefined for unset properties', () => {
    expect(service.getState(StateProps.USER_SESSION)).toBeUndefined();
  });

  it('should rehydrate session from sessionStorage on creation', () => {
    const payload = btoa(JSON.stringify({
      email: 'rehydrate@test.com',
      identificador: 'user-123',
      authorities: 'ROLE_USER,ROLE_ADMIN',
      rol: 'ADMIN',
      direccionId: 'dir-1',
      areaId: 'area-1',
      subareaId: 'sub-1'
    }));
    sessionStorage.setItem('Authorization', 'header.' + payload + '.sig');

    // Recreate the service to trigger constructor
    const freshService = new StateService();
    const session = freshService.getState(StateProps.USER_SESSION);
    expect(session).toBeTruthy();
    expect(session.correo).toBe('rehydrate@test.com');
    expect(session.logged).toBeTrue();
    expect(session.authorities).toEqual(['ROLE_USER', 'ROLE_ADMIN']);
  });

  it('should handle corrupted token during rehydration', () => {
    sessionStorage.setItem('Authorization', 'not-a-valid-token');
    const freshService = new StateService();
    expect(freshService.getState(StateProps.USER_SESSION)).toBeUndefined();
    expect(sessionStorage.getItem('Authorization')).toBeNull();
  });

  it('should not rehydrate when no token in storage', () => {
    sessionStorage.removeItem('Authorization');
    const freshService = new StateService();
    expect(freshService.getState(StateProps.USER_SESSION)).toBeUndefined();
  });
});
