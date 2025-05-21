import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConversionService {
  private readonly API_URL = 'https://v6.exchangerate-api.com/v6/756f7e24d0c4df33be04d21c/latest/COP';

  constructor(private http: HttpClient) { }

  convertCOPToUSD(amountInCOP: number): Observable<number> {
    return this.http.get<any>(this.API_URL).pipe(
      map(response => {
        console.log('Respuesta de la API de conversión:', response); // 👈 Diagnóstico

        if (!response || !response.conversion_rates || !response.conversion_rates.USD) {
          throw new Error('Tasa de cambio no encontrada');
        }

        const rate = response.conversion_rates.USD;
        return amountInCOP * rate;
      })
    );
  }
}