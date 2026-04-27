import { AfterViewInit, Component } from '@angular/core';
import Swiper from 'swiper';
import { Navigation, Pagination } from 'swiper/modules';

@Component({
  selector: 'app-sub-areas',
  templateUrl: './sub-areas.component.html',
  styleUrls: ['./sub-areas.component.scss']
})
export class SubAreasComponent implements AfterViewInit{

  areas = [
    { titulo: 'Deportes', imagen: 'assets/images/Instructora-ensenando-a-mujer-como-alzar-pesas-UCO.webp', link: '/home/area-bienestar/sub-area-deportes' },
    { titulo: 'Cancha Sintética', imagen: 'assets/images/Hombre-al-frente-de-cancha-de-futbol-UCO.webp', link: '/home/area-bienestar/sub-area-cancha-sintetica' },
    { titulo: 'Extensión Cultural', imagen: 'assets/images/Profesor-ensenando-a-tocar-guitarra-a-estudiante-UCO.webp', link: '/home/area-bienestar/sub-area-extension-cultural' },
    { titulo: 'Banda Sinfónica', imagen: 'assets/images/sinfonica.webp', link: '/home/area-bienestar/sub-area-banda-sinfonica' },
    { titulo: 'Gimnasio', imagen: 'assets/images/Instructora-ensenando-a-mujer-como-alzar-pesas-UCO.webp', link: '/home/area-bienestar/sub-area-gimnasio' },
    { titulo: 'Unidad de Salud', imagen: 'assets/images/Enfermeras-en-reunion-UCO.webp', link: '/home/area-bienestar/sub-area-unidad-de-salud' },
    { titulo: 'Acompañamiento Psicosocial', imagen: 'assets/images/Dos-mujeres-viendo-pantalla-UCO.webp', link: '/home/area-bienestar/sub-area-acompanamiento-psicosocial' },
    { titulo: 'Trabajo Social', imagen: 'assets/images/Cuatros-personas-en-reunion-frente-a-un-computador-UCO.webp', link: '/home/area-bienestar/sub-area-trabajo-social' }
  ];

  ngAfterViewInit() {
    new Swiper('.mySwiper', {
      modules: [Navigation, Pagination],
      loop: true,
      spaceBetween: 20,

      pagination: {
        el: '.swiper-pagination',
        clickable: true
      },

      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },

      breakpoints: {
        0:   { slidesPerView: 1 },
        576: { slidesPerView: 2 },
        768: { slidesPerView: 2 },
        992: { slidesPerView: 4 }
      }
    });
  }

}
