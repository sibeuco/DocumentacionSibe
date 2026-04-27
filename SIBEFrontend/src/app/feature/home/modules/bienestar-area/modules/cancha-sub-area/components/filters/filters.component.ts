import { Component, EventEmitter, Output } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent {
  @Output() filtersChanged = new EventEmitter<FiltersRequestWithoutArea>();

  onFilterApplied(filters: FiltersRequestWithoutArea): void {
    this.filtersChanged.emit(filters);
  }
}
