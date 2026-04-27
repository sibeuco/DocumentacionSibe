import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { ExcelReportService } from './excel-report.service';
import { DepartmentService } from './department.service';
import { AreaService } from './area.service';
import { SubAreaService } from './subarea.service';

describe('ExcelReportService', () => {
  let service: ExcelReportService;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  beforeEach(() => {
    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarPorNombre', 'consultarDetalle']);
    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarPorNombre', 'consultarDetalle']);
    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarPorNombre', 'consultarDetalle']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ExcelReportService,
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ]
    });
    service = TestBed.inject(ExcelReportService);
  });

  it('should be created', () => expect(service).toBeTruthy());

  describe('generarInformeDireccion', () => {
    it('should fetch department by name, get details, and generate Excel', (done) => {
      const mockDept = { identificador: 'd1', nombre: 'Dirección Test' };
      const mockDetail = {
        nombre: 'Dirección Test',
        actividades: [{
          identificador: 'a1', nombre: 'Act 1', objetivo: 'Obj', semestre: '2025-1',
          fechaCreacion: '2025-01-01', colaborador: 'Col', indicador: { nombre: 'Ind' },
          fechasProgramadas: []
        }],
        areas: []
      };

      mockDepartmentService.consultarPorNombre.and.returnValue(of(mockDept as any));
      mockDepartmentService.consultarDetalle.and.returnValue(of(mockDetail as any));

      service.generarInformeDireccion('Dirección Test').subscribe({
        next: () => {
          expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dirección Test');
          expect(mockDepartmentService.consultarDetalle).toHaveBeenCalledWith('d1');
          done();
        },
        error: done.fail
      });
    });

    it('should alert when there are no activities', () => {
      const mockDept = { identificador: 'd1', nombre: 'Empty' };
      const mockDetail = { nombre: 'Empty', actividades: [], areas: [] };

      mockDepartmentService.consultarPorNombre.and.returnValue(of(mockDept as any));
      mockDepartmentService.consultarDetalle.and.returnValue(of(mockDetail as any));
      spyOn(window, 'alert');

      service.generarInformeDireccion('Empty').subscribe();

      expect(window.alert).toHaveBeenCalledWith('No se encontraron actividades para exportar.');
    });
  });

  describe('generarInformeArea', () => {
    it('should fetch area by name, get details, and generate Excel', (done) => {
      const mockArea = { identificador: 'a1', nombre: 'Area Test' };
      const mockDetail = {
        nombre: 'Area Test',
        actividades: [{
          identificador: 'act1', nombre: 'Act', objetivo: 'Obj', semestre: '2025-1',
          fechaCreacion: '2025-01-01', colaborador: 'Col', indicador: { nombre: 'Ind' },
          fechasProgramadas: [{ fechaProgramada: '2025-03-01', estadoActividad: { nombre: 'Pendiente' }, participantes: [] }]
        }],
        subareas: []
      };

      mockAreaService.consultarPorNombre.and.returnValue(of(mockArea as any));
      mockAreaService.consultarDetalle.and.returnValue(of(mockDetail as any));

      service.generarInformeArea('Area Test').subscribe({
        next: () => {
          expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Area Test');
          done();
        },
        error: done.fail
      });
    });
  });

  describe('generarInformeSubarea', () => {
    it('should fetch subarea by name, get details, and generate Excel', (done) => {
      const mockSubarea = { identificador: 's1', nombre: 'Sub Test' };
      const mockDetail = {
        nombre: 'Sub Test',
        actividades: [{
          identificador: 'act1', nombre: 'Act', objetivo: 'Obj', semestre: '2025-1',
          fechaCreacion: '2025-01-01', colaborador: 'Col', indicador: { nombre: 'Ind' },
          fechasProgramadas: [{
            fechaProgramada: '2025-03-01',
            estadoActividad: { nombre: 'Finalizado' },
            participantes: [{ nombreCompleto: 'Juan', numeroIdentificacion: '123', tipo: 'Estudiante' }]
          }]
        }]
      };

      mockSubAreaService.consultarPorNombre.and.returnValue(of(mockSubarea as any));
      mockSubAreaService.consultarDetalle.and.returnValue(of(mockDetail as any));

      service.generarInformeSubarea('Sub Test').subscribe({
        next: () => {
          expect(mockSubAreaService.consultarPorNombre).toHaveBeenCalledWith('Sub Test');
          done();
        },
        error: done.fail
      });
    });
  });

  describe('generarInformeDireccion with areas and subareas', () => {
    it('should process activities from areas and subareas', (done) => {
      const mockDept = { identificador: 'd1', nombre: 'Dir' };
      const mockDetail = {
        nombre: 'Dir',
        actividades: [],
        areas: [{
          nombre: 'Area 1',
          actividades: [{
            identificador: 'a1', nombre: 'Act Area', fechasProgramadas: [{
              fechaProgramada: '2025-01-01', estadoActividad: { nombre: 'Pendiente' },
              participantes: [{ nombreCompleto: 'Ana', numeroIdentificacion: '456', tipo: 'Empleado', centroCostos: { codigo: 'CC1', descripcion: 'Centro' } }]
            }]
          }],
          subareas: [{
            nombre: 'Sub 1',
            actividades: [{
              identificador: 'sa1', nombre: 'Act Sub', fechasProgramadas: []
            }]
          }]
        }]
      };

      mockDepartmentService.consultarPorNombre.and.returnValue(of(mockDept as any));
      mockDepartmentService.consultarDetalle.and.returnValue(of(mockDetail as any));

      service.generarInformeDireccion('Dir').subscribe({
        next: () => {
          expect(mockDepartmentService.consultarDetalle).toHaveBeenCalledWith('d1');
          done();
        },
        error: done.fail
      });
    });
  });

  describe('generarInformeArea with subareas', () => {
    it('should process activities from subareas', (done) => {
      const mockArea = { identificador: 'a1', nombre: 'Area' };
      const mockDetail = {
        nombre: 'Area',
        actividades: [],
        subareas: [{
          nombre: 'Sub',
          actividades: [{
            identificador: 'sa1', nombre: 'Act', objetivo: 'Obj', fechasProgramadas: [{
              fechaProgramada: '2025-02-01', estadoActividad: { nombre: 'En Curso' },
              participantes: []
            }]
          }]
        }]
      };

      mockAreaService.consultarPorNombre.and.returnValue(of(mockArea as any));
      mockAreaService.consultarDetalle.and.returnValue(of(mockDetail as any));

      service.generarInformeArea('Area').subscribe({
        next: () => {
          expect(mockAreaService.consultarDetalle).toHaveBeenCalledWith('a1');
          done();
        },
        error: done.fail
      });
    });
  });
});
