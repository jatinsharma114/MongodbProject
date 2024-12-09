package com.test.mongodbTest.ClubControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.mongodbTest.Controller.ClubController;
import com.test.mongodbTest.Model.Club;
import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Service.CLubRepositoryCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CLubRepositoryCustom userService;

    @InjectMocks
    private ClubController clubController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clubController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllClubs_NoContent() throws Exception {
        when(userService.findAllPartnerContainers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/user/getAll"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).findAllPartnerContainers();
    }

    @Test
    public void testGetAllClubs_Success() throws Exception {
        Club club = Club.builder()
                .partners(Collections.emptyList())
                .otherField("TestField")
                .createdOn("2024-11-18")
                .build();
        when(userService.findAllPartnerContainers()).thenReturn(Collections.singletonList(club));

        mockMvc.perform(get("/api/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].otherField").value("TestField"));

        verify(userService, times(1)).findAllPartnerContainers();
    }

    @Test
    public void testAddClub() throws Exception {
        Club club = Club.builder()
                .partners(Collections.emptyList())
                .otherField("New Club")
                .build();
        Club savedClub = Club.builder()
                .partners(Collections.emptyList())
                .otherField("New Club")
                .createdOn("2024-11-18")
                .build();
        when(userService.addNewPartner(any(Club.class))).thenReturn(savedClub);

        mockMvc.perform(post("/api/user/addnew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(club)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.otherField").value("New Club"))
                .andExpect(jsonPath("$.createdOn").value("2024-11-18"));

        verify(userService, times(1)).addNewPartner(any(Club.class));
    }

    @Test
    public void testGetPartnerById_NotFound() throws Exception {
        when(userService.findPartnerById(1)).thenReturn(null);

        mockMvc.perform(get("/api/user/getByID/1"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findPartnerById(1);
    }

    @Test
    public void testGetPartnerById_Success() throws Exception {
        Partner partner = Partner.builder()
                .id(1)
                .name("John Doe")
                .details("Details about John")
                .age(30)
                .updatedOn("2024-11-18")
                .build();
        when(userService.findPartnerById(1)).thenReturn(partner);

        mockMvc.perform(get("/api/user/getByID/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30));

        verify(userService, times(1)).findPartnerById(1);
    }

    @Test
    public void testGetPartnersByName_NoContent() throws Exception {
        when(userService.getPartnersByName("John")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/user/partners/name")
                        .param("name", "John"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).getPartnersByName("John");
    }

    @Test
    public void testGetPartnersByName_Success() throws Exception {
        Partner partner = Partner.builder()
                .id(1)
                .name("John Doe")
                .details("Details about John")
                .age(30)
                .build();
        when(userService.getPartnersByName("John")).thenReturn(Collections.singletonList(partner));

        mockMvc.perform(get("/api/user/partners/name")
                        .param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(30));

        verify(userService, times(1)).getPartnersByName("John");
    }
}
