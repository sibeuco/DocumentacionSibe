import { Component, EventEmitter, Output } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-area-filters',
  templateUrl: './area-filters.component.html',
  styleUrls: ['./area-filters.component.scss']
})
export class AreaFiltersComponent {
  @Output() filtersChanged = new EventEmitter<FiltersRequestWithoutArea>();

  onFilterApplied(filters: FiltersRequestWithoutArea): void {
    this.filtersChanged.emit(filters);
  }
}
