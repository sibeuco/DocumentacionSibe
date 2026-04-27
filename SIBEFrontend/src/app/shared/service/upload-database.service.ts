import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EmployeeFileRequest } from '../model/employee.model';
import { StudentFileRequest } from '../model/student.model';

@Injectable({
  providedIn: 'root'
})
export class UploadDatabaseService {
  private readonly API_URL_BASE = `${environment.endpoint}/carga_masiva`;
  private readonly ENDPOINTS: Record<'empleados' | 'estudiantes', string> = {
    empleados: `${this.API_URL_BASE}/empleados`,
    estudiantes: `${this.API_URL_BASE}/estudiantes`
  };

  private readonly PARAM_NAME = 'archivo';

  constructor(private http: HttpClient) { }

  subirArchivoEmpleados(request: EmployeeFileRequest): Observable<any> {
    return this.subirArchivoConHeaders(request.archivo, 'empleados');
  }

  subirArchivoEstudiantes(request: StudentFileRequest): Observable<any> {
    return this.subirArchivoConHeaders(request.archivo, 'estudiantes');
  }

  private subirArchivoConHeaders(archivo: File, tipo: 'empleados' | 'estudiantes'): Observable<any> {
    const formData = this.crearFormData(archivo);

    const headers = this.crearHeadersAutorizacion();

    return this.http.post(this.ENDPOINTS[tipo], formData, { headers }).pipe(
      catchError(error => {
        if (error.status === 401 || error.status === 403) {
          // Si hay error de autenticación, probar sin headers
          return this.subirArchivoSinHeaders(archivo, tipo);
        }
        return throwError(() => error);
      })
    );
  }

  private subirArchivoSinHeaders(archivo: File, tipo: 'empleados' | 'estudiantes'): Observable<any> {
    const formData = this.crearFormData(archivo);
    return this.http.post(this.ENDPOINTS[tipo], formData).pipe(
      catchError(error => throwError(() => error))
    );
  }

  private crearFormData(archivo: File): FormData {
    const formData = new FormData();
    formData.append(this.PARAM_NAME, archivo);
    return formData;
  }

  private crearHeadersAutorizacion(): HttpHeaders {
    let headers = new HttpHeaders();
    const token = sessionStorage.getItem('Authorization') || localStorage.getItem('Authorization') || '';
    if (token) {
      const bearerToken = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
      headers = headers.set('Authorization', bearerToken);
    }
    return headers;
  }
}
