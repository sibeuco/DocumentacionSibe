import { Component } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-trabajo-social-sub-area',
  templateUrl: './trabajo-social-sub-area.component.html',
  styleUrls: ['./trabajo-social-sub-area.component.scss']
})
export class TrabajoSocialSubAreaComponent {
  filtersRequest: FiltersRequestWithoutArea | null = null;

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }
}
