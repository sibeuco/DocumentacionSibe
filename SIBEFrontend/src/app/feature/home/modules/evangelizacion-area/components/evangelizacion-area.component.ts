import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-evangelizacion-area',
  templateUrl: './evangelizacion-area.component.html',
  styleUrls: ['./evangelizacion-area.component.scss']
})
export class EvangelizacionAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
