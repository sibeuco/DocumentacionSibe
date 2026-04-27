import { Component, ViewChild, OnInit } from '@angular/core';
import { UserResponse } from 'src/app/shared/model/user.model';
import { EditUserComponent } from './edit-user/edit-user.component';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { DepartmentResponse } from 'src/app/shared/model/department.model';
import { AreaResponse } from 'src/app/shared/model/area.model';
import { SubAreaResponse } from 'src/app/shared/model/subarea.model';

// Interfaz para el evento de usuario seleccionado para editar
export interface UsuarioSeleccionadoParaEditar {
  usuario: UserResponse;
  tipoComponente: 'department' | 'area';
}

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.scss']
})
export class ManageUsersComponent implements OnInit {
  @ViewChild(EditUserComponent) editUserComponent!: EditUserComponent;
  usuarioSeleccionado: UserResponse | null = null;
  tipoComponenteOrigen: 'department' | 'area' = 'department';

  // Propiedades para las listas
  listaDepartamentos: { identificador: string; nombre: string }[] = [];
  listaAreas: { identificador: string; nombre: string }[] = [];
  listaSubareas: { identificador: string; nombre: string }[] = [];

  constructor(
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  ngOnInit(): void {
    this.cargarDepartamentos();
    this.cargarAreasYSubareas();
  }

  cargarDepartamentos(): void {
    this.departmentService.consultarDirecciones().subscribe({
      next: (departamentos: DepartmentResponse[]) => {
        this.listaDepartamentos = departamentos.map(dept => ({
          identificador: dept.identificador,
          nombre: dept.nombre
        }));
      },
      error: (err) => {
        console.error('Error al cargar departamentos:', err);
      }
    });
  }

  cargarAreasYSubareas(): void {
    // Cargar áreas
    this.areaService.consultarAreas().subscribe({
      next: (areas: AreaResponse[]) => {
        this.listaAreas = areas.map(area => ({
          identificador: area.identificador,
          nombre: area.nombre
        }));
      },
      error: (err) => {
        console.error('Error al cargar áreas:', err);
      }
    });

    // Cargar subáreas
    this.subAreaService.consultarSubareas().subscribe({
      next: (subareas: SubAreaResponse[]) => {
        this.listaSubareas = subareas.map(subarea => ({
          identificador: subarea.identificador,
          nombre: subarea.nombre
        }));
      },
      error: (err) => {
        console.error('Error al cargar subáreas:', err);
      }
    });
  }

  onUsuarioSeleccionadoParaEditar(evento: UsuarioSeleccionadoParaEditar): void {
    console.log('Usuario seleccionado en manage-users:', evento); // Debug log
    this.usuarioSeleccionado = evento.usuario;
    this.tipoComponenteOrigen = evento.tipoComponente;
    
    // Abrir el modal programáticamente después de un pequeño delay
    setTimeout(() => {
      this.abrirModalEditar();
    }, 100);
  }

  abrirModalEditar(): void {
    if (this.editUserComponent) {
      this.editUserComponent.abrirModal();
    }
  }

  onUsuarioActualizado(response: any): void {
    console.log('Usuario actualizado en manage-users:', response); // Debug log
    // Limpiar la selección después de actualizar
    this.usuarioSeleccionado = null;
  }
}
