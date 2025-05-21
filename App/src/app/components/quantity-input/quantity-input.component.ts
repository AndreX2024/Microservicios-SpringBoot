import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-quantity-input',
  imports: [FormsModule],
  templateUrl: './quantity-input.component.html',
  styleUrl: './quantity-input.component.css'
})
export class QuantityInputComponent {
  @Input() quantity: number = 1;
  @Input() min: number = 1;
  @Input() max: number | null = null;

  @Output() quantityChange = new EventEmitter<number>();

  increase(): void {
    if (this.max === null || this.quantity < this.max) {
      this.quantity++;
      this.quantityChange.emit(this.quantity);
    }
  }

  decrease(): void {
    if (this.quantity > this.min) {
      this.quantity--;
      this.quantityChange.emit(this.quantity);
    }
  }

  onInputChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    let value = parseInt(input.value, 10);

    if (isNaN(value)) return;

    if (this.max !== null && value > this.max) value = this.max;
    if (value < this.min) value = this.min;

    this.quantity = value;
    this.quantityChange.emit(this.quantity);
  }
}
