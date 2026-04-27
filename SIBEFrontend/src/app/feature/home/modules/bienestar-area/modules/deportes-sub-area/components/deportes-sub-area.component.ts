import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-deportes-sub-area',
  templateUrl: './deportes-sub-area.component.html',
  styleUrls: ['./deportes-sub-area.component.scss']
})
export class DeportesSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
