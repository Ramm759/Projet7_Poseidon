package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class RuleNameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RuleNameRepository ruleNameRepository;

    // Requete sans connexion
    @Test
    @WithMockUser(authorities = "USER")
    public void homeWithUserTest() throws Exception {
        mockMvc.perform(get("/ruleName/list")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void homeWithAdminTest() throws Exception {
        mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void addWithUserTest() throws Exception {
        mockMvc.perform(get("/ruleName/add")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addWithAdminTest() throws Exception {
        mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    //String name, String description, String json, String template, String sqlStr, String sqlPart
    public void validateRuleNameAdminTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .param("name", "Name")
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "Sqlstr")
                .param("sqlPart", "Sqlpart")
                .with(csrf()) // Contournement attaque
        ).andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    public void validateRuleNameAdminWithErrorTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .param("name", "") // name not Null
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "Sqlstr")
                .param("sqlPart", "10")
                .with(csrf()) // Contournement attaque
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void validateRuleNameUserTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .param("name", "Name")
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "Sqlstr")
                .param("sqlPart", "10")
                .with(csrf()) // Contournement attaque
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void showUpdateRuleNameWithAdminTest() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("Name", "Description", "Json", "Template", "Sqlstr", "SqlPart"));

        mockMvc.perform(get("/ruleName/update/" + ruleName.getId()))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("name", Matchers.equalTo("Name"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("description", Matchers.equalTo("Description"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("json", Matchers.equalTo("Json"))));

    }

    @WithMockUser(authorities = "USER")
    @Test
    public void showUpdateRuleNameWithUserTest() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("Name", "Description", "Json", "Template", "Sqlstr", "SqlPart"));

        this.mockMvc.perform(get("/ruleName/update/" + ruleName.getId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateRuleNameAdminTest() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("Name", "Description", "Json", "Template", "Sqlstr", "SqlPart"));

        mockMvc.perform(post("/ruleName/validate")
                .param("name", "Name")
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "Sqlstr")
                .param("sqlPart", "10")
                .with(csrf()) // Contournement attaque
        ).andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateRuleNameAdminHasErrorTest() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("Name", "Description", "Json", "Template", "Sqlstr", "SqlPart"));

        this.mockMvc.perform(post("/ruleName/update/" + ruleName.getId())
                .param("name", "") // name est not Null
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "Sqlstr")
                .param("sqlPart", "10")
                .with(csrf()) // Contournement attaque
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void deleteRuleNameAdminTest() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("Name", "Description", "Json", "Template", "Sqlstr", "SqlPart"));


        this.mockMvc.perform(get("/ruleName/delete/" + ruleName.getId())).andExpect(status().isFound()).andReturn();
    }

}
