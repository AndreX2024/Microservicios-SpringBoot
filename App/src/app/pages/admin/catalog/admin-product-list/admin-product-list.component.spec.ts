import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProductListComponent } from './admin-product-list.component';

describe('ProductListComponent', () => {
  let component: AdminProductListComponent;
  let fixture: ComponentFixture<AdminProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminProductListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
