package edu.billing.controller.base;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


public class ControllerUnitBase {

    protected String URL_BASE = "/api/account";
    protected MockMvc mockMvc;

    /**
     * CORS Headers
     * @throws Exception in MockMvc.perform
     */
    protected void test_cors_headers() throws Exception {
        mockMvc.perform(get(URL_BASE))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                .andExpect(header().string("Access-Control-Max-Age", "3600"));
    }
}