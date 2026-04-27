import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-banda-sub-area',
  templateUrl: './banda-sub-area.component.html',
  styleUrls: ['./banda-sub-area.component.scss']
})
export class BandaSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
