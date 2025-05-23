import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductInventoryEditComponent } from './product-inventory-edit.component';

describe('ProductInventoryEditComponent', () => {
  let component: ProductInventoryEditComponent;
  let fixture: ComponentFixture<ProductInventoryEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductInventoryEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductInventoryEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
