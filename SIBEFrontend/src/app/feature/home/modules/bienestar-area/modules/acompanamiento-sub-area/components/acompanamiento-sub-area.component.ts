import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-acompanamiento-sub-area',
  templateUrl: './acompanamiento-sub-area.component.html',
  styleUrls: ['./acompanamiento-sub-area.component.scss']
})
export class AcompanamientoSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
