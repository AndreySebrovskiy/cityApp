package com.jdms.grad.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jdms.grad.cities.application.model.ObjectId;
import com.jdms.grad.cities.application.repository.CityRepository;
import com.jdms.grad.cities.application.service.CityService;
import com.jdms.grad.test.component.BaseComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class CityController_IT extends BaseComponentTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CityService cityService;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    @WithMockUser(roles = "admin")
    void shouldCreateCity() throws Exception {
        var mvcResult =
                mockMvc
                        .perform(
                                post("/api/cities")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(
                                                """
                                                        {
                                                          "id": "1",
                                                          "name": "Tokyo",
                                                          "imageUrl": "someurl"
                                                        }
                                                        """))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").exists())
                        .andReturn();

        var cityId =
                objectMapper
                        .readValue(mvcResult.getResponse().getContentAsString(), ObjectId.class)
                        .getId();
        transactionTemplate.executeWithoutResult(
                status -> {
                    var city = cityService.findOne(cityId);
                    assertThat(city.getId()).isEqualTo(cityId);
                    assertThat(city.getName()).isEqualTo("Tokyo");
                    assertThat(city.getImageUrl()).isEqualTo("someurl");
                });
    }

    @Test
    void shouldNotAllow_whenCreateCityWithNotProvidedRole() throws Exception {
        mockMvc
                .perform(
                        post("/api/cities")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  "id": "1",
                                                  "name": "Tokyo",
                                                  "imageUrl": "someurl"
                                                }
                                                """))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ALLOW_EDIT")
    @DatabaseSetup("CityController_updateCity.xml")
    void shouldUpdateCity() throws Exception {
        mockMvc
                .perform(
                        put("/api/cities/{id}", "dbff293a-de39-11ed-b5ea-0242ac120002")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  "name": "Tokyo",
                                                  "imageUrl": "someurl"
                                                }
                                                """))
                .andExpect(status().isNoContent())
                .andReturn();

        var city = cityService.findOne(UUID.fromString("dbff293a-de39-11ed-b5ea-0242ac120002"));
        assertThat(city.getName()).isEqualTo("Tokyo");
        assertThat(city.getImageUrl()).isEqualTo("someurl");
    }

    @Test
    @WithMockUser(roles = "admin")
    @DatabaseSetup("CityController_updateCity.xml")
    void shouldNotAllow_wheUpdateCity() throws Exception {
        mockMvc
                .perform(
                        put("/api/cities/{id}", "dbff293a-de39-11ed-b5ea-0242ac120002")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  "name": "Tokyo",
                                                  "imageUrl": "someurl"
                                                }
                                                """))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "user")
    @DatabaseSetup("CityController_Pagination.xml")
    void shouldReturnPage_wheFindCities() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/cities").param("size", "10").param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content()
                                .json(
                                        """
                                                {
                                                  "content": [
                                                    {
                                                      "id": "dbff293a-de39-11ed-b5ea-0242ac120002",
                                                      "externalId": 1,
                                                      "name": "London",
                                                      "imageUrl": "someurl"
                                                    },
                                                    {
                                                      "id": "dbff293a-de39-11ed-b5ea-0242ac120003",
                                                      "externalId": 2,
                                                      "name": "Prague",
                                                      "imageUrl": "someurl"
                                                    },
                                                    {
                                                      "id": "dbff293a-de39-11ed-b5ea-0242ac120004",
                                                      "externalId": 3,
                                                      "name": "Paris",
                                                      "imageUrl": "someurl"
                                                    },
                                                    {
                                                      "id": "dbff293a-de39-11ed-b5ea-0242ac120005",
                                                      "externalId": 4,
                                                      "name": "Berlin",
                                                      "imageUrl": "someurl"
                                                    }
                                                  ],
                                                  "pageable": {
                                                    "sort": {
                                                      "empty": true,
                                                      "sorted": false,
                                                      "unsorted": true
                                                    },
                                                    "offset": 0,
                                                    "pageSize": 10,
                                                    "pageNumber": 0,
                                                    "paged": true,
                                                    "unpaged": false
                                                  },
                                                  "last": true,
                                                  "totalPages": 1,
                                                  "totalElements": 4,
                                                  "size": 10,
                                                  "number": 0,
                                                  "sort": {
                                                    "empty": true,
                                                    "sorted": false,
                                                    "unsorted": true
                                                  },
                                                  "first": true,
                                                  "numberOfElements": 4,
                                                  "empty": false
                                                }
                                                """));
    }


    @Test
    @WithMockUser(roles = "user")
    @DatabaseSetup("CityController_Pagination.xml")
    void shouldReturnCity_wheFindCityByName() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/cities")
                                .param("size", "10")
                                .param("page", "0")
                                .param("name", "Paris"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content()
                                .json(
                                        """
                                             {
                                               "content": [
                                                 {
                                                   "id": "dbff293a-de39-11ed-b5ea-0242ac120004",
                                                   "externalId": 3,
                                                   "name": "Paris",
                                                   "imageUrl": "someurl"
                                                 }
                                               ],
                                               "pageable": {
                                                 "sort": {
                                                   "empty": true,
                                                   "sorted": false,
                                                   "unsorted": true
                                                 },
                                                 "offset": 0,
                                                 "pageNumber": 0,
                                                 "pageSize": 10,
                                                 "paged": true,
                                                 "unpaged": false
                                               },
                                               "last": true,
                                               "totalPages": 1,
                                               "totalElements": 1,
                                               "size": 10,
                                               "number": 0,
                                               "sort": {
                                                 "empty": true,
                                                 "sorted": false,
                                                 "unsorted": true
                                               },
                                               "first": true,
                                               "numberOfElements": 1,
                                               "empty": false
                                             }
                                                """));
    }

    @Test
    @WithMockUser(roles = "admin")
    @DatabaseSetup("CityController_GetCity.xml")
    void shouldCreateCity_whenGetById() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/cities/{id}", "dbff293a-de39-11ed-b5ea-0242ac120002")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        content()
                                .json(
                                        """
                                                {
                                                "id": "dbff293a-de39-11ed-b5ea-0242ac120002",
                                                "externalId": 1,
                                                "name": "London",
                                                "imageUrl":"someurl"
                                                }
                                                """
                                ))
                        .andReturn();
    }

    @Test
    @WithMockUser(roles = "user")
    @DatabaseSetup("CityController_DeleteCity.xml")
    void shouldDeleteCity() throws Exception {
        mockMvc
                .perform(delete("/api/cities/{id}", "dbff293a-de39-11ed-b5ea-0242ac120002"))
                .andExpect(status().isNoContent());

        assertThat(cityRepository.findById(UUID.fromString("dbff293a-de39-11ed-b5ea-0242ac120002"))).isEmpty();
    }

}
