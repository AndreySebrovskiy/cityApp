# CityListApp

works only page and search by name

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 15.2.6.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

# A CityListComponent to display the paginated list of cities.  path: http://localhost:4200/city-list. Works but without login
# A CityDetailsComponent to display the details of a selected city. -- not binded
# A LoginComponent to allow users to log in and authenticate themselves. http://localhost:4200/login

The cities array contains the list of cities to be displayed.
The searchTerm property contains the search term entered by the user.
The currentPage property contains the current page number.
The itemsPerPage property contains the number of items to be displayed per page.
The loadCities method loads the list of cities from the CityService using the search term, current page, and items per page properties.
The deleteCity method deletes a city from the list of cities and reloads the list.
The search method reloads the list of cities with the new search term.
The onPageChanged method is called when the user clicks on a new page number and updates the current page property and reloads the list of cities.

