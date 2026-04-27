import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { CoreService } from './core.service';

describe('CoreService', () => {
  let service: CoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(CoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
