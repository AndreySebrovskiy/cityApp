import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { City } from '../../model/City';
import {CityService} from "../../_service/city/city.service";


@Component({
  selector: 'app-city-details',
  templateUrl: './city-details.component.html',
  styleUrls: ['./city-details.component.scss']
})
export class CityDetailsComponent implements OnInit {

  city: City | undefined;

  constructor(private route: ActivatedRoute, private cityService: CityService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    // @ts-ignore
    this.cityService.getCity(id.toString()).subscribe(city => {
      this.city = city;
    });
  }

}
