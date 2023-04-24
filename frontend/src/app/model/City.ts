export interface City {
  id?: any;
  externalId?: string;
  name?: string;
  imageUrl?: string;
}

export interface CityPage {
  content: City[];
  totalElements: number;
  numberOfElements: number,
  totalPages: number,
  size: number,
  number: number
}


export interface CityResponse {
  id: any;
  externalId: string;
  name: string;
  imageUrl: string;

}

export interface CityResponsePage {
  content: CityResponse[];
  totalElements: number;
  numberOfElements: number,
  totalPages: number,
  size: number,
  number: number
}

export interface Filter {
  ids?: string[];
  namePart?: string;
}
