package io.anggi.personalwebsite.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Load the full application context
@AutoConfigureMockMvc // Configure MockMvc for web layer testing
public class SecurityConfigurationTests {

    @Autowired
    private MockMvc mockMvc;

    private final String ALLOWED_ORIGIN = "https://anggi.io";
    private final String DISALLOWED_ORIGIN = "http://evil.com";
    private final String ANY_PATH = "/api/all"; // Use an existing GET endpoint that requires no auth

    @Test
    void whenOptionsRequestFromAllowedOrigin_thenReturnsCorsHeaders() throws Exception {
        mockMvc.perform(options(ANY_PATH) 
                        .header(HttpHeaders.ORIGIN, ALLOWED_ORIGIN)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
                        // Add this line to simulate requesting permission for a header
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "Content-Type")) 
                .andExpect(status().isOk()) 
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN))
                .andExpect(header().exists(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)) 
                // This assertion should now pass because we requested headers
                .andExpect(header().exists(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS)); 
    }

    @Test
    void whenGetRequestFromAllowedOrigin_thenReturnsCorsHeader() throws Exception {
        mockMvc.perform(get(ANY_PATH) // Simulate a GET request
                        .header(HttpHeaders.ORIGIN, ALLOWED_ORIGIN))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN)); // Crucial check
    }

    @Test
    void whenGetRequestFromDisallowedOrigin_thenNoCorsHeader() throws Exception {
        mockMvc.perform(get(ANY_PATH)
                        .header(HttpHeaders.ORIGIN, DISALLOWED_ORIGIN))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)); // Crucial: No CORS header for this origin
    }

    @Test
    void whenGetRequestWithNoOrigin_thenNoCorsHeader() throws Exception {
        // Simulate a request from a server or tool that doesn't add an Origin header
        mockMvc.perform(get(ANY_PATH))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }
}