import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserNotificationService {
  private usuarioCreadoSubject = new Subject<any>();
  private usuarioActualizadoSubject = new Subject<any>();
  private usuarioEliminadoSubject = new Subject<any>();

  usuarioCreado$ = this.usuarioCreadoSubject.asObservable();
  usuarioActualizado$ = this.usuarioActualizadoSubject.asObservable();
  usuarioEliminado$ = this.usuarioEliminadoSubject.asObservable();

  notificarUsuarioCreado(usuario: any): void {
    this.usuarioCreadoSubject.next(usuario);
  }

  notificarUsuarioActualizado(usuario: any): void {
    this.usuarioActualizadoSubject.next(usuario);
  }

  notificarUsuarioEliminado(usuario: any): void {
    this.usuarioEliminadoSubject.next(usuario);
  }
}
