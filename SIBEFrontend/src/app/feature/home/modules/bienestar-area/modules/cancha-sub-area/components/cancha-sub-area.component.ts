import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-cancha-sub-area',
  templateUrl: './cancha-sub-area.component.html',
  styleUrls: ['./cancha-sub-area.component.scss']
})
export class CanchaSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
