import { Component, EventEmitter, Output } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-home-filters',
  templateUrl: './home-filters.component.html',
  styleUrls: ['./home-filters.component.scss']
})
export class HomeFiltersComponent {
  @Output() filtersChanged = new EventEmitter<FiltersRequestWithoutArea>();

  onFilterApplied(filters: FiltersRequestWithoutArea): void {
    this.filtersChanged.emit(filters);
  }
}
