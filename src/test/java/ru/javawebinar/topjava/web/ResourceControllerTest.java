package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void statusShouldBeOk() throws Exception {
        perform(get("/resources/css/style.css"))
                .andExpect(status().isOk());
    }

    @Test
    void contentTypeShouldBeCss() throws Exception {
        perform(get("/resources/css/style.css"))
                .andExpect(content().contentType("text/css;charset=UTF-8"));
    }
}
