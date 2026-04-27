import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-bienestar-area',
  templateUrl: './bienestar-area.component.html',
  styleUrls: ['./bienestar-area.component.scss']
})
export class BienestarAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
