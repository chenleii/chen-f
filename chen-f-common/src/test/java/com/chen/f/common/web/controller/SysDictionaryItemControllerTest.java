package com.chen.f.common.web.controller;

import com.chen.f.common.TestRedisConfiguration;
import com.chen.f.common.mapper.SysDictionaryItemMapper;
import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author chen
 * @since 2020/9/2 0:15.
 */
@SpringBootTest
@Import({TestRedisConfiguration.class})
@Transactional
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class SysDictionaryItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SysDictionaryItemMapper sysDictionaryItemMapper;

    @Test
    @WithMockUser(value = "chen")
    void getSysDictionaryItemList() throws Exception {
        final SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setId("1");
        sysDictionaryItem.setSysDictionaryId("");
        sysDictionaryItem.setCode("1");
        sysDictionaryItem.setName("");
        sysDictionaryItem.setKey("1");
        sysDictionaryItem.setValue("");
        sysDictionaryItem.setValueI18n("");
        sysDictionaryItem.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem.setColor("");
        sysDictionaryItem.setOrder(0);
        sysDictionaryItem.setRemark("");
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId("");
        sysDictionaryItem.setCreatedSysUserId("");
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem);
        
        mvc.perform(get("/chen/common/sys/dictionary/item/code/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].code").value(anything("1")));
    }

    @Test
    @WithMockUser(value = "chen")
    void getSysDictionaryItem() throws Exception {
        final SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setId("1");
        sysDictionaryItem.setSysDictionaryId("");
        sysDictionaryItem.setCode("1");
        sysDictionaryItem.setName("");
        sysDictionaryItem.setKey("1");
        sysDictionaryItem.setValue("");
        sysDictionaryItem.setValueI18n("");
        sysDictionaryItem.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem.setColor("");
        sysDictionaryItem.setOrder(0);
        sysDictionaryItem.setRemark("");
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId("");
        sysDictionaryItem.setCreatedSysUserId("");
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem);

        mvc.perform(get("/chen/common/sys/dictionary/item/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is("1")));
    }

    @Test
    @WithMockUser(value = "chen")
    void testGetSysDictionaryItem() throws Exception {

        final SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setId("1");
        sysDictionaryItem.setSysDictionaryId("");
        sysDictionaryItem.setCode("1");
        sysDictionaryItem.setName("");
        sysDictionaryItem.setKey("1");
        sysDictionaryItem.setValue("");
        sysDictionaryItem.setValueI18n("");
        sysDictionaryItem.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem.setColor("");
        sysDictionaryItem.setOrder(0);
        sysDictionaryItem.setRemark("");
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId("");
        sysDictionaryItem.setCreatedSysUserId("");
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem);

        mvc.perform(get("/chen/common/sys/dictionary/item/code/key/1/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(is("1")))
                .andExpect(jsonPath("$.key").value(is("1")));
    }

    @Test
    @WithMockUser(value = "chen")
    void getSysDictionaryItemByAlainTag() throws Exception {

        final SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setId("1");
        sysDictionaryItem.setSysDictionaryId("");
        sysDictionaryItem.setCode("1");
        sysDictionaryItem.setName("");
        sysDictionaryItem.setKey("1");
        sysDictionaryItem.setValue("");
        sysDictionaryItem.setValueI18n("");
        sysDictionaryItem.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem.setColor("");
        sysDictionaryItem.setOrder(0);
        sysDictionaryItem.setRemark("");
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId("");
        sysDictionaryItem.setCreatedSysUserId("");
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem);

        mvc.perform(get("/chen/common/sys/dictionary/item/alain/tag/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1").value(hasKey("text")))
                .andExpect(jsonPath("$.1").value(hasKey("color")));
    }

    @Test
    @WithMockUser(value = "chen")
    void getSysDictionaryItemByAlainSelect() throws Exception {

        final SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setId("1");
        sysDictionaryItem.setSysDictionaryId("");
        sysDictionaryItem.setCode("1");
        sysDictionaryItem.setName("");
        sysDictionaryItem.setKey("1");
        sysDictionaryItem.setValue("");
        sysDictionaryItem.setValueI18n("");
        sysDictionaryItem.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem.setColor("");
        sysDictionaryItem.setOrder(0);
        sysDictionaryItem.setRemark("");
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId("");
        sysDictionaryItem.setCreatedSysUserId("");
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem);

        mvc.perform(get("/chen/common/sys/dictionary/item/alain/select/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].value").value(anything("1")));
    }
}