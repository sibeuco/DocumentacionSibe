import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-unidad-sub-area',
  templateUrl: './unidad-sub-area.component.html',
  styleUrls: ['./unidad-sub-area.component.scss']
})
export class UnidadSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
