import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { ExternalParticipantComponent } from './external-participant.component';

describe('ExternalParticipantComponent', () => {
  let component: ExternalParticipantComponent;
  let fixture: ComponentFixture<ExternalParticipantComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [ExternalParticipantComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ExternalParticipantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with empty participant fields', () => {
    expect(component.participante.documento).toBe('');
    expect(component.participante.nombreCompleto).toBe('');
  });

  it('should emit participanteAgregado with correct data on agregarParticipante', () => {
    spyOn(component.participanteAgregado, 'emit');

    component.participante = { documento: '12345', nombreCompleto: 'Juan Pérez' };
    component.agregarParticipante();

    expect(component.participanteAgregado.emit).toHaveBeenCalledWith({
      documento: '12345',
      nombreCompleto: 'Juan Pérez'
    });
  });

  it('should clear form after adding participant', () => {
    component.participante = { documento: '12345', nombreCompleto: 'Juan Pérez' };
    component.agregarParticipante();

    expect(component.participante.documento).toBe('');
    expect(component.participante.nombreCompleto).toBe('');
  });

  it('should reset fields on limpiarFormulario', () => {
    component.participante = { documento: '99999', nombreCompleto: 'Maria' };
    component.limpiarFormulario();

    expect(component.participante.documento).toBe('');
    expect(component.participante.nombreCompleto).toBe('');
  });

  it('should emit even with empty fields (no client-side validation in component)', () => {
    spyOn(component.participanteAgregado, 'emit');
    component.agregarParticipante();

    expect(component.participanteAgregado.emit).toHaveBeenCalledWith({
      documento: '',
      nombreCompleto: ''
    });
  });
});
