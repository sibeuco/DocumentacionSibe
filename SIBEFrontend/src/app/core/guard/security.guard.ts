import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

const ADMINISTRADOR_DIRECCION = 'ADMINISTRADOR_DIRECCION';
const ADMINISTRADOR_AREA = 'ADMINISTRADOR_AREA';

const ROLE_ROUTES: Record<string, string[]> = {
  '/gestionar-direccion': [ADMINISTRADOR_DIRECCION],
  '/gestionar-usuarios': [ADMINISTRADOR_DIRECCION],
  '/gestionar-indicadores': [ADMINISTRADOR_DIRECCION],
};

export const securityGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = sessionStorage.getItem('Authorization');
  const currentUrl = state.url;

  // Rutas públicas que no requieren autenticación
  const publicRoutes = ['/login', '/recuperar-contrasena'];
  const isPublicRoute = publicRoutes.some(route => currentUrl === route || currentUrl.startsWith(route));

  // 🔹 Si no hay token
  if (!token) {
    if (isPublicRoute) {
      return true;
    }
    router.navigate(['/login']);
    return false;
  }

  // 🔹 Si hay token pero está vencido
  if (token && tokenExpired(token)) {
    sessionStorage.removeItem('Authorization');
    if (isPublicRoute) {
      return true;
    }
    router.navigate(['/login']);
    return false;
  }

  // 🔹 Si hay token válido y el usuario intenta ir a una ruta pública
  if (token && !tokenExpired(token) && isPublicRoute) {
    router.navigate(['/home']);
    return false;
  }

  // 🔹 Validar acceso por rol a rutas restringidas
  const rolUsuario = getRolFromToken(token);
  const basePath = '/' + currentUrl.split('/').filter(Boolean)[0];
  const allowedRoles = ROLE_ROUTES[basePath];

  if (allowedRoles && !allowedRoles.includes(rolUsuario)) {
    router.navigate(['/home']);
    return false;
  }

  // 🔹 Si hay token válido y está accediendo a una ruta protegida permitida
  return true;
};

function tokenExpired(token: string): boolean {
  try {
    const expiry = JSON.parse(atob(token.split('.')[1])).exp;
    return Math.floor(Date.now() / 1000) >= expiry;
  } catch {
    return true;
  }
}

function getRolFromToken(token: string): string {
  try {
    return JSON.parse(atob(token.split('.')[1])).rol || '';
  } catch {
    return '';
  }
}
