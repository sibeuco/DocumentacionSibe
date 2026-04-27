import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-gimnasio-sub-area',
  templateUrl: './gimnasio-sub-area.component.html',
  styleUrls: ['./gimnasio-sub-area.component.scss']
})
export class GimnasioSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
