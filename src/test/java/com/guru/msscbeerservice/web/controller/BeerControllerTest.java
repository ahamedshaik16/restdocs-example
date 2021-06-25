package com.guru.msscbeerservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guru.msscbeerservice.repositories.BeerRepository;
import com.guru.msscbeerservice.web.domain.Beer;
import com.guru.msscbeerservice.web.model.BeerDto;
import com.guru.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.guru.msscbeerservice.web.mappers")
class BeerControllerTest {

    @MockBean
    BeerRepository beerRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {

        given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .param("iscold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        requestParameters(
                                parameterWithName("iscold").description("Is Beer cold Query Param")
                        ),
                        responseFields(
                               fieldWithPath("id").description("Id of Beer"),
                                fieldWithPath("version").description("Version Number"),
                                fieldWithPath("createdDate").description("Created Date"),
                                fieldWithPath("lastModifiedDate").description("Last Modified Date"),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer"),
                                fieldWithPath("price").description("Price of Beer"),
                                fieldWithPath("quantityOnHand").description("Quantity On Hand")
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer",
                        requestFields(
                                fieldWithPath("id").description("Id of Beer").ignored(),
                                fieldWithPath("version").description("Version Number").ignored(),
                                fieldWithPath("createdDate").description("Created Date").ignored(),
                                fieldWithPath("lastModifiedDate").description("Last Modified Date").ignored(),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer").attributes(),
                                fieldWithPath("price").description("Price of Beer"),
                                fieldWithPath("quantityOnHand").description("Quantity On Hand").ignored()
                        )));
    }

    @Test
    void updateBeerById() throws Exception{
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());

    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("Jack")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.88"))
                .upc(34567844567L)
                .build();

    }
}