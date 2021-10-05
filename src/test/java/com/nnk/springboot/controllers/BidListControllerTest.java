package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class BidListControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BidListRepository bidListRepository;

    // Requete sans connexion
    @Test
    @WithMockUser(authorities = "USER")
    public void homeWithUserTest() throws Exception {
        mockMvc.perform(get("/bidList/list")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void homeWithAdminTest() throws Exception {
        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void addWithUserTest() throws Exception {
        mockMvc.perform(get("/bidList/add")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addWithAdminTest() throws Exception {
        mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
    }


    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    public void validateBidListAdminTest() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("Account", "BobAccount")
                .param("type", "livret")
                .param("bidQuantity", "5")
                .with(csrf()) // Contournement attaque
        ).andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    public void validateBidListAdminWithErrorTest() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("Account", "BobAccount")
                .param("type", "livret")
                .param("bidQuantity", "aaa")
                .with(csrf()) // Contournement attaque
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void validateBidListUserTest() throws Exception {
        this.mockMvc.perform(post("/bidList/validate")
                .param("Account", "BobAccount")
                .param("type", "livret")
                .param("bidQuantity", "5")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void showUpdateBidListWithAdminTest() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

        mockMvc.perform(get("/bidList/update/" + bid.getBidListId()))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("account", Matchers.equalTo("Account"))))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("type", Matchers.equalTo("Type"))))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("bidQuantity", Matchers.equalTo(10.0d))));

}

    @WithMockUser(authorities = "USER")
    @Test
    public void showUpdateBidListWithUserTest() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/bidList/update/" + bid.getBidListId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateBidListAdminTest() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));
        this.mockMvc.perform(post("/bidList/update/" + bid.getBidListId())
                .param("account", "nuAccount")
                .param("type", "nuType")
                .param("bidQuantity", "12.0")
                .with(csrf())
        ).andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateBidListAdminHasErrorTest() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));
        this.mockMvc.perform(post("/bidList/update/" + bid.getBidListId())
                .param("account", "nuAccount")
                .param("type", "nuType")
                .param("bidQuantity", "A.0")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void deleteBidListAdminTest() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/bidList/delete/" + bid.getBidListId())).andExpect(status().isFound()).andReturn();
    }






}