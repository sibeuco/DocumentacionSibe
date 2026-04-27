import { Component, OnInit } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{
  filtersRequest: FiltersRequestWithoutArea | null = null;

  ngOnInit(): void {
  }

  onFiltersChanged(filters: FiltersRequestWithoutArea): void {
    this.filtersRequest = filters;
  }

}
