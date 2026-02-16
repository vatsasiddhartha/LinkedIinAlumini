package com.siddhartha.Alumini_Searcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddhartha.Alumini_Searcher.Controller.AluminiController;
import com.siddhartha.Alumini_Searcher.Services.AluminiService;
import com.siddhartha.Alumini_Searcher.dto.RequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(AluminiController.class)
class AluminiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AluminiService aluminiService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSearchAlumni() throws Exception {

        RequestDto dto = new RequestDto();
        dto.setUniversity("IIT");

        Mockito.when(aluminiService.searchAlumini(dto))
                .thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.post("/alumni/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
