import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import {City} from "../../model/City";
import {CityService} from "../../_service/city/city.service";

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit {
  searchKey = new FormControl('');
  cities: City[] = [];
  totalRecords: number = 0;
  pageIndex = -1;
  pageSize = 5;
  constructor(private cityService: CityService) {}

  ngOnInit(): void {
    this.cityService.getCities('', this.pageIndex, this.pageSize);
  }

  searchByName() {
    this.pageIndex = 0;
    this.pageSize = 5;
    this.getCityPage(
      this.searchKey.value ?? '',
      this.pageIndex,
      this.pageSize
    );
  }

  getCityPage(searchKey: string,
              currentPage:number, pageSize:number) {
    this.cityService
      .getCities(searchKey,(currentPage + 1), pageSize)
      .subscribe((response) => {
        this.cities = response.content as City[];
        this.totalRecords = response.totalElements;
      });
  }

  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex ;
    this.pageSize = e.pageSize;
    this.getCityPage(
      this.searchKey.value ?? '',
      this.pageIndex,
      this.pageSize
    );
  }
}

