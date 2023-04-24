import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {map, Observable} from "rxjs";
import {City, CityPage, CityResponse, CityResponsePage} from "../../model/City";
import {environment} from "../../../environments/environment";
import {AuthService} from "../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class CityService {
  private citiesUrl = environment.api + '/cities';

  constructor(private http: HttpClient,  private authService: AuthService) {
  }

  getCities(searchKey: string, page: number, size: number): Observable<CityPage> {
    const params = new HttpParams().set('page', page).set('size', size);
    const headers = new HttpHeaders().set('Authorization', 'Basic ' +
      window.btoa(localStorage.getItem('user') + ':' + localStorage.getItem('password')));
    return this.http.get<CityResponsePage>(this.citiesUrl, { params, headers }).pipe(
      map(response => {
        const cities: City[] = [];
        for (const cityResponse of response.content) {
          const city: City = {
            id: cityResponse.id,
            externalId: cityResponse.externalId,
            name: cityResponse.name,
            imageUrl: cityResponse.imageUrl
          };
          cities.push(city);
        }

        let totalElements = response.totalElements;
        let numberOfElements = response.numberOfElements;
        let totalPages = response.totalPages;
        let size = response.size;
        let number = response.number;

        return new class implements CityPage {
          content: City[] = cities;
          totalElements: number = totalElements;
          numberOfElements: number = numberOfElements;
          totalPages: number = totalPages;
          size: number = size;
          number: number= number;
        };
      })
    );
  }

  getCity(id: string): Observable<CityResponse> {
    const url = `${this.citiesUrl}/${id}`;
    return this.http.get<CityResponse>(url);
  }

  addCity(city: CityResponse): Observable<CityResponse> {
    return this.http.post<CityResponse>(this.citiesUrl, city);
  }

  updateCity(city: CityResponse): Observable<City> {
    const url = `${this.citiesUrl}/${city.id}`;
    return this.http.put<City>(url, city);
  }

  deleteCity(id: string) {
    const url = `${this.citiesUrl}/${id}`;
    this.http.delete<any>(url);
  }
}
