import { Component, OnInit } from '@angular/core';
import { MenuItem } from './core/model/menu-item.model';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent{
  public menuItems : MenuItem[] = [
    { url: '/home', nombre: 'Home' },
    { url: '/login', nombre: 'Cerrar sesión' },
  ]
}
