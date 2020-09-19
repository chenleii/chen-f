package com.chen.f.core.configuration.errorhandle;

import com.chen.f.core.api.exception.ApiException;
import com.chen.f.core.api.response.error.basic.BasicErrorResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author chen
 * @since 2020/9/19 19:04.
 */
@SpringBootTest
@EnableChenFErrorHandle
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class, SecurityAutoConfiguration.class})
@Import({TestController.class})
class ChenFErrorHandleTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void exceptionHandleTest() throws Exception {
        mvc.perform(
                post("/apiException")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(is(BasicErrorResponses.serverException().getErrorCode())))
                .andExpect(jsonPath("$.errorMsg").value(is(BasicErrorResponses.serverException().getErrorMsg())))
                .andExpect(jsonPath("$.error").value(is(BasicErrorResponses.serverException().getError())));
    }
}

@RestController
class TestController {

    @RequestMapping("/apiException")
    public Object apiException() {
        throw new ApiException(BasicErrorResponses.serverException());
    }
}