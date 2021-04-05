package com.example.openapi.students;

import com.example.openapi.model.Student;
import com.example.openapi.students.controller.StudentsApiController;
import com.example.openapi.students.repository.StudentsRepository;
import com.example.openapi.students.service.StudentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;

@WebMvcTest
@AutoConfigureMockMvc
class StudentsApplicationTests {

    @MockBean
    private StudentsApiController studentsApiController;

    @MockBean
    StudentsService studentsService;
    @Autowired
    private MockMvc mockMvc;

    /* Test that context is loaded */
    @Test
    void contextLoads() {
        assertThat(studentsApiController).isNotNull();
    }

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student();
        student.setStudentid(1);
        student.setName("Test");
        student.setAge(22);
        when(studentsApiController.findStudentById(1))
                .thenReturn(new ResponseEntity<>(student, HttpStatus.OK));
        this.mockMvc.perform(get("/students/1")).
                    andDo(print()).
                    andExpect(status().isOk()).
                    andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3)).
                    andExpect(MockMvcResultMatchers.jsonPath("$.studentid").value(1));
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setStudentid(1);
        student.setName("Test");
        student.setAge(22);
        when(studentsApiController.createStudent(any(Student.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/students").
                        content(writeAsJsonString(student)).
                        contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    /**
     * Utility method to create JSON string
     * @param obj Object to convert to json
     * @return String
     */
    static String writeAsJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
