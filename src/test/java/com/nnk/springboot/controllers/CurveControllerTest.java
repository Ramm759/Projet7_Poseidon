package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class CurveControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CurvePointRepository curvePointRepository;

    // Requete sans connexion
    @Test
    @WithMockUser(authorities = "USER")
    public void homeWithUserTest() throws Exception {
        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void homeWithAdminTest() throws Exception {
        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void addWithUserTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addWithAdminTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    public void validateCurveAdminTest() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "10")
                .param("term", "20D")
                .param("value", "5D")
                .with(csrf()) // Contournement attaque
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    public void validateCurveAdminWithErrorTest() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "10")
                .param("term", "20D")
                .param("value", "aaa")
                .with(csrf()) // Contournement attaque
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void validateCurveUserTest() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "10")
                .param("term", "20D")
                .param("value", "5D")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void showUpdateCurveWithAdminTest() throws Exception {
        CurvePoint curve = curvePointRepository.save(new CurvePoint(10, 20D, 5D));

        mockMvc.perform(get("/curvePoint/update/" + curve.getId()))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("curveId", Matchers.equalTo(10))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("term", Matchers.equalTo(20D))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("value", Matchers.equalTo(5D))));

    }

    @WithMockUser(authorities = "USER")
    @Test
    public void showUpdateCurveWithUserTest() throws Exception {
        CurvePoint curve = curvePointRepository.save(new CurvePoint(10, 20D, 5D));

        this.mockMvc.perform(get("/curvePoint/update/" + curve.getCurveId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateCurveAdminTest() throws Exception {
        CurvePoint curve = curvePointRepository.save(new CurvePoint(10, 20D, 5D));
        this.mockMvc.perform(post("/curvePoint/update/" + curve.getCurveId())
                .param("curveId", "10")
                .param("term", "20D")
                .param("value", "5D")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateCurveAdminHasErrorTest() throws Exception {
        CurvePoint curve = curvePointRepository.save(new CurvePoint(10, 20D, 5D));
        this.mockMvc.perform(post("/curvePoint/update/" + curve.getCurveId())
                .param("curveId", "10")
                .param("term", "20D")
                .param("value", "5dddD")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void deleteCurveAdminTest() throws Exception {
        CurvePoint curve = curvePointRepository.save(new CurvePoint(10, 20D, 5D));

        this.mockMvc.perform(get("/curvePoint/delete/" + curve.getCurveId())).andExpect(status().isFound()).andReturn();
    }




}
