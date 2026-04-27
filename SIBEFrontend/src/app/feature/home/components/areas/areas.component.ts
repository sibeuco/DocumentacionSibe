import { AfterViewInit, Component } from '@angular/core';
import Swiper from 'swiper';
import { Navigation, Pagination } from 'swiper/modules';

@Component({
  selector: 'app-areas',
  templateUrl: './areas.component.html',
  styleUrls: ['./areas.component.scss']
})
export class AreasComponent implements AfterViewInit {

  areas = [
    { titulo: 'Bienestar', imagen: 'assets/images/bienestar.webp', link: '/home/area-bienestar' },
    { titulo: 'Evangelización', imagen: 'assets/images/evangelizacion.jpg', link: '/home/area-evangelizacion' },
    { titulo: 'Hogar Santa María', imagen: 'assets/images/hogar.webp', link: '/home/area-hogar-santa-maria' },
    { titulo: 'Servicio al Usuario', imagen: 'assets/images/servicio.webp', link: '/home/area-servicio-atencion' }
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
