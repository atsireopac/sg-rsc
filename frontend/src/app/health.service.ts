import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HealthService {
  private readonly apiUrl = 'http://localhost:8080/api/health';

  constructor(private readonly http: HttpClient) {}

  getHealth(): Observable<string> {
    return this.http.get(this.apiUrl, { responseType: 'text' });
  }
}
