import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-extension-sub-area',
  templateUrl: './extension-sub-area.component.html',
  styleUrls: ['./extension-sub-area.component.scss']
})
export class ExtensionSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
