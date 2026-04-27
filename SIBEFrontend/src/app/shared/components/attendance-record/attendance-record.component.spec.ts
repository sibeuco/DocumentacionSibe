import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { AttendanceRecordComponent } from './attendance-record.component';
import { UniversityMemberService } from '../../service/university-member.service';
import { ActivityService } from '../../service/activity.service';
import { NgxPaginationModule } from 'ngx-pagination';

describe('AttendanceRecordComponent', () => {
  let component: AttendanceRecordComponent;
  let fixture: ComponentFixture<AttendanceRecordComponent>;
  let mockUniversityMemberService: jasmine.SpyObj<UniversityMemberService>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;

  const mockMiembro = {
    identificador: 'mem-1',
    nombreCompleto: 'Juan Perez',
    documentoIdentificacion: '123456',
    programaAcademico: 'Ingenieria',
    correoInstitucional: 'juan@test.edu',
    tipo: 'Estudiante'
  };

  const STORAGE_KEY = 'selectedActivityInfo';

  beforeEach(() => {
    mockUniversityMemberService = jasmine.createSpyObj('UniversityMemberService', [
      'consultarPorCarnet', 'consultarPorIdentificacion'
    ]);
    mockActivityService = jasmine.createSpyObj('ActivityService', [
      'iniciarActividad', 'finalizarActividad', 'cancelarActividad',
      'consultarParticipantesPorEjecucion', 'consultarParticipantesPorEjecucionPaginado'
    ]);

    mockUniversityMemberService.consultarPorCarnet.and.returnValue(of(mockMiembro as any));
    mockUniversityMemberService.consultarPorIdentificacion.and.returnValue(of(mockMiembro as any));

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule, NgxPaginationModule],
      declarations: [AttendanceRecordComponent],
      providers: [
        { provide: UniversityMemberService, useValue: mockUniversityMemberService },
        { provide: ActivityService, useValue: mockActivityService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AttendanceRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    window.sessionStorage.removeItem('Authorization');
    window.sessionStorage.removeItem(STORAGE_KEY);
  });

  function setFakeJwt(userId: string): void {
    const payload = btoa(JSON.stringify({ identificador: userId, email: 'test@test.com', authorities: 'ROLE_USER' }));
    window.sessionStorage.setItem('Authorization', 'header.' + payload + '.sig');
  }

  function setStorageEjecucion(estado: string, ejId: string = 'ej-1', actId: string = 'act-1'): void {
    const data = {
      actividad: { identificador: actId, nombre: 'Test Act', colaborador: 'user-1', nombreColaborador: 'Colaborador' },
      ejecucion: { identificador: ejId, estadoActividad: { nombre: estado } }
    };
    window.sessionStorage.setItem(STORAGE_KEY, JSON.stringify(data));
  }

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should clear message on init', () => {
      expect(component.mensaje).toBe('');
    });
  });

  describe('ngOnChanges', () => {
    it('should call cargarEjecucionSeleccionada when actividad changes', () => {
      setStorageEjecucion('En curso');
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      expect(component.ejecucionSeleccionadaId).toBe('ej-1');
    });

    it('should call cargarEjecucionSeleccionada when idActividad changes', () => {
      setStorageEjecucion('Pendiente');
      component.ngOnChanges({ idActividad: { currentValue: 1, previousValue: 0, firstChange: false, isFirstChange: () => false } });
      expect(component.ejecucionSeleccionadaId).toBe('ej-1');
    });
  });

  describe('iniciarActividad', () => {
    it('should show error if no ejecucionSeleccionadaId', () => {
      component.ejecucionSeleccionadaId = null;
      component.iniciarActividad();
      expect(component.mensaje).toContain('No hay una');
      expect(component.tipoMensaje).toBe('error');
    });

    it('should not start if already starting', () => {
      (component as any).iniciandoActividad = true;
      component.iniciarActividad();
      expect(mockActivityService.iniciarActividad).not.toHaveBeenCalled();
    });

    it('should show permission error when user is not collaborator', () => {
      setFakeJwt('other-user');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      (component as any).ejecucionSeleccionada = { estadoActividad: { nombre: 'Pendiente' } };
      component.iniciarActividad();
      expect(component.mensaje).toContain('No tienes permisos');
      expect(component.tipoMensaje).toBe('error');
    });

    it('should call service and update state on success', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      mockActivityService.iniciarActividad.and.returnValue(of({ identificador: 'ej-1' }));

      component.iniciarActividad();

      expect(mockActivityService.iniciarActividad).toHaveBeenCalledWith('ej-1');
      expect(component.actividadIniciada).toBeTrue();
      expect(component.mensaje).toContain('iniciada');
    });

    it('should show error on service failure', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      mockActivityService.iniciarActividad.and.returnValue(throwError(() => new Error('fail')));

      component.iniciarActividad();

      expect(component.mensaje).toContain('No fue posible iniciar');
      expect(component.tipoMensaje).toBe('error');
    });
  });

  describe('buscarParticipante', () => {
    it('should warn when no search input provided', () => {
      component.rfidSearch = '';
      component.documentoSearch = '';
      component.buscarParticipante();
      expect(component.mensaje).toContain('ingrese un');
      expect(component.tipoMensaje).toBe('warning');
    });

    it('should search by carnet when rfidSearch is set', () => {
      component.rfidSearch = 'RFID123';
      component.documentoSearch = '';
      component.buscarParticipante();
      expect(mockUniversityMemberService.consultarPorCarnet).toHaveBeenCalledWith('RFID123');
    });

    it('should search by document when documentoSearch is set', () => {
      component.rfidSearch = '';
      component.documentoSearch = '123456';
      component.buscarParticipante();
      expect(mockUniversityMemberService.consultarPorIdentificacion).toHaveBeenCalledWith('123456');
    });

    it('should add member to list on successful search', () => {
      component.rfidSearch = 'RFID123';
      component.buscarParticipante();
      expect(component.miembrosAsistencia.length).toBe(1);
      expect(component.miembrosAsistencia[0].nombreCompleto).toBe('Juan Perez');
    });

    it('should not add duplicate participant', () => {
      component.rfidSearch = 'RFID123';
      component.buscarParticipante();
      expect(component.miembrosAsistencia.length).toBe(1);
      component.rfidSearch = 'RFID123';
      component.buscarParticipante();
      expect(component.miembrosAsistencia.length).toBe(1);
      expect(component.mensaje).toContain('registrado');
    });

    it('should handle null result from service', () => {
      mockUniversityMemberService.consultarPorCarnet.and.returnValue(of(null as any));
      component.rfidSearch = 'UNKNOWN';
      component.buscarParticipante();
      expect(component.miembrosAsistencia.length).toBe(0);
    });

    it('should open external participant modal on search error', () => {
      mockUniversityMemberService.consultarPorCarnet.and.returnValue(throwError(() => new Error('not found')));
      component.rfidSearch = 'BAD';
      component.buscarParticipante();
      expect(component.buscando).toBeFalse();
    });
  });

  describe('confirmarAgregarParticipanteExterno', () => {
    it('should call getElementById for confirm modal', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      component.confirmarAgregarParticipanteExterno();
      expect(document.getElementById).toHaveBeenCalledWith('confirmExternalParticipantModal');
    });
  });

  describe('cancelarAgregarParticipanteExterno', () => {
    it('should hide modal and show warning message', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      component.cancelarAgregarParticipanteExterno();
      expect(component.mensaje).toContain('No se encontró');
      expect(component.tipoMensaje).toBe('warning');
    });
  });

  describe('removerParticipante', () => {
    it('should remove participant at given index', () => {
      component.miembrosAsistencia = [mockMiembro as any];
      component.removerParticipante(0);
      expect(component.miembrosAsistencia.length).toBe(0);
      expect(component.mensaje).toContain('removido');
    });
  });

  describe('manejarParticipanteExterno', () => {
    it('should add external participant to list', () => {
      const datos = { nombreCompleto: 'Externo Test', documento: '999999' };
      component.manejarParticipanteExterno(datos);
      expect(component.miembrosAsistencia.length).toBe(1);
      expect(component.miembrosAsistencia[0].tipo).toBe('Externo');
      expect(component.mensaje).toContain('Participante externo');
    });
  });

  describe('finalizarActividad', () => {
    it('should warn if no participants', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.miembrosAsistencia = [];
      component.finalizarActividad();
      expect(component.mensaje).toContain('No hay participantes');
    });

    it('should show error if no ejecucionSeleccionadaId', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = null;
      component.actividad = { colaborador: 'user-1' } as any;
      component.finalizarActividad();
      expect(component.mensaje).toContain('No hay una');
    });

    it('should show error if ejecucionSeleccionadaId is empty after trim', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = '   ';
      component.actividad = { colaborador: 'user-1' } as any;
      component.finalizarActividad();
      expect(component.mensaje).toContain('identificador');
    });

    it('should show error if not collaborator', () => {
      setFakeJwt('other-user');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.miembrosAsistencia = [mockMiembro as any];
      component.finalizarActividad();
      expect(component.mensaje).toContain('No tienes permisos');
    });

    it('should call service and emit on success', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.miembrosAsistencia = [mockMiembro as any];
      mockActivityService.finalizarActividad.and.returnValue(of({ valor: 'ok' }));
      spyOn(component.actividadFinalizada, 'emit');

      component.finalizarActividad();

      expect(mockActivityService.finalizarActividad).toHaveBeenCalled();
      expect(component.actividadFinalizada.emit).toHaveBeenCalled();
      expect(component.mensaje).toContain('finalizada exitosamente');
    });

    it('should not finalize if already saving', () => {
      component.guardando = true;
      component.finalizarActividad();
      expect(mockActivityService.finalizarActividad).not.toHaveBeenCalled();
    });

    it('should show backend error message on failure', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.miembrosAsistencia = [mockMiembro as any];
      mockActivityService.finalizarActividad.and.returnValue(
        throwError(() => ({ error: { message: 'Duplicated participant' } }))
      );

      component.finalizarActividad();
      expect(component.mensaje).toBe('Duplicated participant');
    });

    it('should show generic error when no backend message', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.miembrosAsistencia = [mockMiembro as any];
      mockActivityService.finalizarActividad.and.returnValue(throwError(() => ({})));

      component.finalizarActividad();
      expect(component.mensaje).toContain('No fue posible finalizar');
    });

    it('should convert internal participant correctly', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.miembrosAsistencia = [mockMiembro as any];
      mockActivityService.finalizarActividad.and.returnValue(of({ valor: 'ok' }));

      component.finalizarActividad();

      const callArgs = mockActivityService.finalizarActividad.calls.mostRecent().args;
      expect(callArgs[1][0].identificador).toBe('mem-1');
    });

    it('should convert external participant with null identificador', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      const externo = { ...mockMiembro, identificador: 'externo-123', tipo: 'Externo' };
      component.miembrosAsistencia = [externo as any];
      mockActivityService.finalizarActividad.and.returnValue(of({ valor: 'ok' }));

      component.finalizarActividad();

      const callArgs = mockActivityService.finalizarActividad.calls.mostRecent().args;
      expect(callArgs[1][0].identificador).toBeNull();
    });
  });

  describe('cancelarActividad', () => {
    it('should show error if not collaborator', () => {
      setFakeJwt('other-user');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.cancelarActividad();
      expect(component.mensaje).toContain('No tienes permisos');
    });

    it('should show error if no ejecucionSeleccionadaId', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = null;
      component.actividad = { colaborador: 'user-1' } as any;
      component.cancelarActividad();
      expect(component.mensaje).toContain('No hay una');
    });

    it('should not cancel if already cancelling', () => {
      component.cancelandoActividad = true;
      component.cancelarActividad();
      expect(mockActivityService.cancelarActividad).not.toHaveBeenCalled();
    });

    it('should call service and emit on success', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      component.miembrosAsistencia = [mockMiembro as any];
      mockActivityService.cancelarActividad.and.returnValue(of({ identificador: 'ej-1' }));
      spyOn(component.actividadCancelada, 'emit');

      component.cancelarActividad();

      expect(mockActivityService.cancelarActividad).toHaveBeenCalledWith('ej-1');
      expect(component.actividadCancelada.emit).toHaveBeenCalled();
      expect(component.actividadIniciada).toBeFalse();
      expect(component.miembrosAsistencia.length).toBe(0);
    });

    it('should show error on service failure', () => {
      setFakeJwt('user-1');
      component.ejecucionSeleccionadaId = 'ej-1';
      component.actividad = { colaborador: 'user-1' } as any;
      mockActivityService.cancelarActividad.and.returnValue(throwError(() => new Error('fail')));

      component.cancelarActividad();
      expect(component.mensaje).toContain('No fue posible cancelar');
    });
  });

  describe('solicitarCancelacionActividad', () => {
    it('should try to show cancel modal', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      component.solicitarCancelacionActividad();
      expect(document.getElementById).toHaveBeenCalledWith('cancelActivityConfirmModal');
    });
  });

  describe('cerrarModalCancelacion', () => {
    it('should try to hide cancel modal', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      component.cerrarModalCancelacion();
      expect(document.getElementById).toHaveBeenCalledWith('cancelActivityConfirmModal');
    });
  });

  describe('esActividadFinalizada', () => {
    it('should return false by default', () => {
      expect(component.esActividadFinalizada()).toBeFalse();
    });

    it('should return true when ejecucionFinalizada is true', () => {
      component.ejecucionFinalizada = true;
      expect(component.esActividadFinalizada()).toBeTrue();
    });
  });

  describe('esUsuarioColaborador', () => {
    it('should return false when no actividad', () => {
      component.actividad = null;
      expect(component.esUsuarioColaborador()).toBeFalse();
    });

    it('should return false when no colaborador', () => {
      component.actividad = { nombre: 'Test' } as any;
      expect(component.esUsuarioColaborador()).toBeFalse();
    });

    it('should return false when no JWT token', () => {
      component.actividad = { colaborador: 'user-1' } as any;
      window.sessionStorage.removeItem('Authorization');
      expect(component.esUsuarioColaborador()).toBeFalse();
    });

    it('should return true when user matches collaborator', () => {
      setFakeJwt('user-1');
      component.actividad = { colaborador: 'user-1' } as any;
      expect(component.esUsuarioColaborador()).toBeTrue();
    });

    it('should return false when user does not match collaborator', () => {
      setFakeJwt('other-user');
      component.actividad = { colaborador: 'user-1' } as any;
      expect(component.esUsuarioColaborador()).toBeFalse();
    });

    it('should use actividadCargada when actividad is null', () => {
      setFakeJwt('user-1');
      component.actividad = null;
      (component as any).actividadCargada = { colaborador: 'user-1', nombreColaborador: 'Test' };
      expect(component.esUsuarioColaborador()).toBeTrue();
    });
  });

  describe('obtenerNombreColaborador', () => {
    it('should return name from actividad', () => {
      component.actividad = { nombreColaborador: 'Carlos' } as any;
      expect(component.obtenerNombreColaborador()).toBe('Carlos');
    });

    it('should return default when no actividad', () => {
      component.actividad = null;
      (component as any).actividadCargada = null;
      expect(component.obtenerNombreColaborador()).toBe('No disponible');
    });

    it('should return fallback when no nombreColaborador', () => {
      component.actividad = { nombre: 'Test' } as any;
      expect(component.obtenerNombreColaborador()).toBe('Sin colaborador asignado');
    });
  });

  describe('debeMostrarErrorPermisos', () => {
    it('should return false when no actividad', () => {
      component.actividad = null;
      (component as any).actividadCargada = null;
      expect(component.debeMostrarErrorPermisos()).toBeFalse();
    });

    it('should return false when no ejecucionSeleccionada', () => {
      component.actividad = { colaborador: 'user-1' } as any;
      (component as any).ejecucionSeleccionada = null;
      expect(component.debeMostrarErrorPermisos()).toBeFalse();
    });

    it('should return false when estado is FINALIZADO', () => {
      component.actividad = { colaborador: 'user-1' } as any;
      (component as any).ejecucionSeleccionada = { estadoActividad: { nombre: 'Finalizada' } };
      expect(component.debeMostrarErrorPermisos()).toBeFalse();
    });

    it('should return true when estado is PENDIENTE and user is not collaborator', () => {
      setFakeJwt('other-user');
      component.actividad = { colaborador: 'user-1' } as any;
      (component as any).ejecucionSeleccionada = { estadoActividad: { nombre: 'Pendiente' } };
      expect(component.debeMostrarErrorPermisos()).toBeTrue();
    });

    it('should return false when user is collaborator', () => {
      setFakeJwt('user-1');
      component.actividad = { colaborador: 'user-1' } as any;
      (component as any).ejecucionSeleccionada = { estadoActividad: { nombre: 'Pendiente' } };
      expect(component.debeMostrarErrorPermisos()).toBeFalse();
    });
  });

  describe('cargarEjecucionSeleccionada via sessionStorage', () => {
    it('should reset state when no storage data', () => {
      window.sessionStorage.removeItem(STORAGE_KEY);
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      expect(component.ejecucionSeleccionadaId).toBeNull();
      expect(component.actividadIniciada).toBeFalse();
    });

    it('should parse EN_CURSO estado and set actividadIniciada', () => {
      setStorageEjecucion('En curso');
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      expect(component.actividadIniciada).toBeTrue();
      expect(component.ejecucionFinalizada).toBeFalse();
    });

    it('should parse FINALIZADO estado and load participants', () => {
      setStorageEjecucion('Finalizada');
      mockActivityService.consultarParticipantesPorEjecucionPaginado.and.returnValue(of({
        content: [{ identificador: 'p1', nombreCompleto: 'Part 1', numeroIdentificacion: '111', tipo: 'Estudiante' }],
        totalElements: 1
      } as any));
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      expect(component.ejecucionFinalizada).toBeTrue();
      expect(mockActivityService.consultarParticipantesPorEjecucionPaginado).toHaveBeenCalled();
    });

    it('should clear miembros when ejecucion changes', () => {
      setStorageEjecucion('Pendiente', 'ej-old');
      component.ejecucionSeleccionadaId = 'ej-old';
      component.miembrosAsistencia = [mockMiembro as any];
      setStorageEjecucion('Pendiente', 'ej-new');
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      expect(component.miembrosAsistencia.length).toBe(0);
    });

    it('should handle corrupted JSON in storage', () => {
      window.sessionStorage.setItem(STORAGE_KEY, '{invalid-json');
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      expect(component.ejecucionSeleccionadaId).toBeNull();
    });

    it('should fallback actividad from storage when input actividad is null', () => {
      setStorageEjecucion('Pendiente');
      component.actividad = null;
      component.ngOnChanges({ idActividad: { currentValue: 1, previousValue: 0, firstChange: false, isFirstChange: () => false } });
      expect(component.actividad).toBeTruthy();
    });
  });

  describe('cargarParticipantesDeActividadFinalizada', () => {
    it('should restore previous participants on error', () => {
      component.ejecucionFinalizada = true;
      component.ejecucionSeleccionadaId = 'ej-1';
      component.miembrosAsistencia = [mockMiembro as any];
      mockActivityService.consultarParticipantesPorEjecucionPaginado.and.returnValue(throwError(() => new Error('fail')));

      (component as any).cargarParticipantesDeActividadFinalizada();

      expect(component.miembrosAsistencia.length).toBe(1);
      expect(component.mensaje).toContain('No fue posible obtener');
    });

    it('should map participant response to university member', () => {
      component.ejecucionFinalizada = true;
      component.ejecucionSeleccionadaId = 'ej-1';
      mockActivityService.consultarParticipantesPorEjecucionPaginado.and.returnValue(of({
        content: [
          {
            identificador: 'p1', nombreCompleto: 'Maria', numeroIdentificacion: '222',
            programaAcademico: 'Derecho', correoInstitucional: 'maria@test.edu', tipo: 'Estudiante'
          }
        ],
        totalElements: 1
      } as any));

      (component as any).cargarParticipantesDeActividadFinalizada();

      expect(component.miembrosAsistencia[0].nombreCompleto).toBe('Maria');
      expect(component.miembrosAsistencia[0].programaAcademico).toBe('Derecho');
      expect(component.totalElementos).toBe(1);
    });

    it('should not load when not finalizada', () => {
      component.ejecucionFinalizada = false;
      component.ejecucionSeleccionadaId = 'ej-1';
      (component as any).cargarParticipantesDeActividadFinalizada();
      expect(mockActivityService.consultarParticipantesPorEjecucionPaginado).not.toHaveBeenCalled();
    });
  });

  describe('onPageChange', () => {
    it('should update page and reload participants', () => {
      component.ejecucionFinalizada = true;
      component.ejecucionSeleccionadaId = 'ej-1';
      mockActivityService.consultarParticipantesPorEjecucionPaginado.and.returnValue(of({
        content: [], totalElements: 25
      } as any));
      component.onPageChange(3);
      expect(component.p).toBe(3);
      expect(mockActivityService.consultarParticipantesPorEjecucionPaginado).toHaveBeenCalledWith('ej-1', 2);
    });
  });

  describe('mostrarMensaje timeout', () => {
    it('should clear message after timeout', fakeAsync(() => {
      component.rfidSearch = '';
      component.documentoSearch = '';
      component.buscarParticipante();
      expect(component.mensaje).toContain('ingrese');
      tick(5000);
      expect(component.mensaje).toBe('');
    }));
  });

  describe('actualizarEstadoEjecucionLocal', () => {
    it('should update to EN_CURSO state', () => {
      setStorageEjecucion('Pendiente');
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      (component as any).actualizarEstadoEjecucionLocal('En curso');
      expect(component.actividadIniciada).toBeTrue();
      expect(component.ejecucionFinalizada).toBeFalse();
    });

    it('should update to PENDIENTE state and clear miembros', () => {
      setStorageEjecucion('En curso');
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      component.miembrosAsistencia = [mockMiembro as any];
      (component as any).actualizarEstadoEjecucionLocal('Pendiente');
      expect(component.actividadIniciada).toBeFalse();
      expect(component.miembrosAsistencia.length).toBe(0);
    });

    it('should update to FINALIZADO state and load participants', () => {
      setStorageEjecucion('En curso');
      component.ngOnChanges({ actividad: { currentValue: {}, previousValue: null, firstChange: false, isFirstChange: () => false } });
      mockActivityService.consultarParticipantesPorEjecucionPaginado.and.returnValue(of(
        { content: [], totalElements: 0 } as any
      ));
      (component as any).actualizarEstadoEjecucionLocal('Finalizada');
      expect(component.ejecucionFinalizada).toBeTrue();
      expect(mockActivityService.consultarParticipantesPorEjecucionPaginado).toHaveBeenCalled();
    });

    it('should handle when no storage available', () => {
      // No storage data
      window.sessionStorage.removeItem(STORAGE_KEY);
      (component as any).ejecucionSeleccionada = { estadoActividad: { nombre: 'Pendiente' } };
      (component as any).actualizarEstadoEjecucionLocal('En curso');
      expect(component.actividadIniciada).toBeTrue();
    });
  });

  describe('esEstadoPendienteOEnCurso edge cases', () => {
    it('should return false when no ejecucionSeleccionada', () => {
      (component as any).ejecucionSeleccionada = null;
      expect((component as any).esEstadoPendienteOEnCurso()).toBeFalse();
    });

    it('should return false when no estadoActividad', () => {
      (component as any).ejecucionSeleccionada = {};
      expect((component as any).esEstadoPendienteOEnCurso()).toBeFalse();
    });

    it('should return true for En curso estado', () => {
      (component as any).ejecucionSeleccionada = { estadoActividad: { nombre: 'En curso' } };
      expect((component as any).esEstadoPendienteOEnCurso()).toBeTrue();
    });
  });

  describe('obtenerIdentificadorUsuario edge cases', () => {
    it('should return null when no token', () => {
      window.sessionStorage.removeItem('Authorization');
      expect((component as any).obtenerIdentificadorUsuario()).toBeNull();
    });

    it('should return null for token without dots', () => {
      window.sessionStorage.setItem('Authorization', 'invalidtoken');
      expect((component as any).obtenerIdentificadorUsuario()).toBeNull();
      window.sessionStorage.removeItem('Authorization');
    });

    it('should return null for invalid base64 payload', () => {
      window.sessionStorage.setItem('Authorization', 'header.!!!invalid!!!.sig');
      expect((component as any).obtenerIdentificadorUsuario()).toBeNull();
      window.sessionStorage.removeItem('Authorization');
    });

    it('should return id field when identificador not present', () => {
      const payload = btoa(JSON.stringify({ id: 'alt-id' }));
      window.sessionStorage.setItem('Authorization', 'header.' + payload + '.sig');
      expect((component as any).obtenerIdentificadorUsuario()).toBe('alt-id');
      window.sessionStorage.removeItem('Authorization');
    });
  });

  describe('convertirAParticipantRequest edge cases', () => {
    it('should handle empty identificador as external', () => {
      const miembro = { identificador: '', nombreCompleto: 'Test', documentoIdentificacion: '123', tipo: '' } as any;
      const result = (component as any).convertirAParticipantRequest(miembro);
      expect(result.identificador).toBeNull();
    });

    it('should handle null tipo gracefully', () => {
      const miembro = { identificador: 'mem-1', nombreCompleto: 'Test', documentoIdentificacion: '123', tipo: null } as any;
      const result = (component as any).convertirAParticipantRequest(miembro);
      expect(result.identificador).toBe('mem-1');
    });
  });

  describe('cargarParticipantesDeActividadFinalizada edge cases', () => {
    it('should not load when no ejecucionSeleccionadaId', () => {
      component.ejecucionFinalizada = true;
      component.ejecucionSeleccionadaId = null;
      (component as any).cargarParticipantesDeActividadFinalizada();
      expect(mockActivityService.consultarParticipantesPorEjecucionPaginado).not.toHaveBeenCalled();
    });

    it('should not load when not finalized', () => {
      component.ejecucionFinalizada = false;
      component.ejecucionSeleccionadaId = 'ej-1';
      (component as any).cargarParticipantesDeActividadFinalizada();
      expect(mockActivityService.consultarParticipantesPorEjecucionPaginado).not.toHaveBeenCalled();
    });
  });

  describe('normalizarTexto', () => {
    it('should normalize accented text', () => {
      expect((component as any).normalizarTexto('Dirección')).toBe('direccion');
    });

    it('should trim whitespace', () => {
      expect((component as any).normalizarTexto('  test  ')).toBe('test');
    });

    it('should collapse multiple spaces', () => {
      expect((component as any).normalizarTexto('a   b')).toBe('a b');
    });
  });

  describe('obtenerMensajeError', () => {
    it('should return empty for null error', () => {
      expect((component as any).obtenerMensajeError(null)).toBe('');
    });

    it('should extract error.error.mensaje', () => {
      expect((component as any).obtenerMensajeError({ error: { mensaje: 'Test msg' } })).toBe('Test msg');
    });

    it('should extract error.error.detail', () => {
      expect((component as any).obtenerMensajeError({ error: { detail: 'Detail msg' } })).toBe('Detail msg');
    });

    it('should extract error.message', () => {
      expect((component as any).obtenerMensajeError({ message: 'Msg' })).toBe('Msg');
    });

    it('should return empty for non-string message', () => {
      expect((component as any).obtenerMensajeError({ error: { message: {} } })).toBe('');
    });
  });

  describe('modal operations with DOM element', () => {
    it('should open external participant modal when element found', () => {
      const mockModal = { show: jasmine.createSpy('show') };
      spyOn(bootstrap.Modal.prototype, 'show').and.callFake(function(this: any) { mockModal.show(); });
      const el = document.createElement('div');
      el.id = 'external-participant-modal';
      document.body.appendChild(el);
      spyOn(document, 'getElementById').and.returnValue(el);
      (component as any).abrirModalParticipanteExterno();
      document.body.removeChild(el);
    });

    it('should hide cancel confirmation modal when element found', () => {
      const mockModal = { hide: jasmine.createSpy('hide') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);
      component.cerrarModalCancelacion();
      expect(mockModal.hide).toHaveBeenCalled();
    });

    it('should show cancel confirmation modal when element found', () => {
      const mockModal = { show: jasmine.createSpy('show') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);
      component.solicitarCancelacionActividad();
      expect(mockModal.show).toHaveBeenCalled();
    });

    it('should hide confirm external participant modal when element found', () => {
      const mockModal = { hide: jasmine.createSpy('hide') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);
      component.cancelarAgregarParticipanteExterno();
      expect(mockModal.hide).toHaveBeenCalled();
    });
  });

  describe('storage removeItem in catch', () => {
    it('should remove storage key when cargarEjecucionSeleccionada catches error', () => {
      const badJson = '{invalid json';
      sessionStorage.setItem('selectedActivityInfo', badJson);
      (component as any).cargarEjecucionSeleccionada();
      expect(sessionStorage.getItem('selectedActivityInfo')).toBeNull();
    });
  });

  describe('esUsuarioColaborador edge cases', () => {
    it('should return false when no actividad and no actividadCargada', () => {
      component.actividad = null as any;
      (component as any).actividadCargada = null;
      expect(component.esUsuarioColaborador()).toBeFalse();
    });

    it('should return false when no colaborador in actividad', () => {
      component.actividad = { identificador: 'a1', colaborador: '' } as any;
      expect(component.esUsuarioColaborador()).toBeFalse();
    });

    it('should return false when obtenerIdentificadorUsuario returns null', () => {
      component.actividad = { identificador: 'a1', colaborador: 'col-1' } as any;
      sessionStorage.removeItem('Authorization');
      expect(component.esUsuarioColaborador()).toBeFalse();
    });
  });

  describe('obtenerIdentificadorUsuario', () => {
    it('should return null when no Authorization token', () => {
      sessionStorage.removeItem('Authorization');
      expect((component as any).obtenerIdentificadorUsuario()).toBeNull();
    });
  });
});
