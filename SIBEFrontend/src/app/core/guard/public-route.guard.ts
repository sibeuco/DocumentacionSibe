import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const publicRouteGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = sessionStorage.getItem('Authorization');

  // Si no hay token, permitir acceso a rutas públicas
  if (!token) {
    return true;
  }

  // Si hay token, verificar si está vencido
  if (token && tokenExpired(token)) {
    sessionStorage.removeItem('Authorization');
    return true; // Permitir acceso después de limpiar token vencido
  }

  // Si hay token válido, redirigir al home (usuario ya loggeado)
  router.navigate(['/home']);
  return false;
};

function tokenExpired(token: string): boolean {
  try {
    const expiry = JSON.parse(atob(token.split('.')[1])).exp;
    return Math.floor(Date.now() / 1000) >= expiry;
  } catch {
    // Si falla la decodificación, lo tratamos como expirado
    return true;
  }
}
