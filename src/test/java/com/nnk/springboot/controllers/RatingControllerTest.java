package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RatingRepository ratingRepository;

    // Requete sans connexion
    @Test
    @WithMockUser(authorities = "USER")
    public void homeWithUserTest() throws Exception {
        mockMvc.perform(get("/rating/list")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void homeWithAdminTest() throws Exception {
        mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void addWithUserTest() throws Exception {
        mockMvc.perform(get("/rating/add")).andExpect(status().isForbidden());
    }

    // Requete Admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addWithAdminTest() throws Exception {
        mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    public void validateRatingAdminTest() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "MoodysRating")
                .param("sandPRating", "SandPrading")
                .param("fitchRating", "FitchRating")
                .param("orderNumber", "10")
                .with(csrf()) // Contournement attaque
        ).andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser(authorities = "ADMIN") // User ne peut pas insérer
    @Test
    public void validateRatingAdminWithErrorTest() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "MoodysRating")
                .param("sandPRating", "SandPrading")
                .param("fitchRating", "FitchRating")
                .param("orderNumber", "10a")
                .with(csrf()) // Contournement attaque
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void validateRatingtUserTest() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "MoodysRating")
                .param("sandPRating", "SandPrading")
                .param("fitchRating", "FitchRating")
                .param("orderNumber", "10")
                .with(csrf()) // Contournement attaque
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void showUpdateRatingWithAdminTest() throws Exception {
        Rating rating = ratingRepository.save(new Rating("MoodysRating", "SandPRating", "FitchRating", 10));

        mockMvc.perform(get("/rating/update/" + rating.getId()))
                .andExpect(model().attribute("rating", Matchers.hasProperty("moodysRating", Matchers.equalTo("MoodysRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("sandPRating", Matchers.equalTo("SandPRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("fitchRating", Matchers.equalTo("FitchRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("orderNumber", Matchers.equalTo(10))));
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void showUpdateRatingWithUserTest() throws Exception {
        Rating rating = ratingRepository.save(new Rating("MoodysRating", "SandPRating", "FitchRating", 10));

        this.mockMvc.perform(get("/rating/update/" + rating.getId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateRatingAdminTest() throws Exception {
        Rating rating = ratingRepository.save(new Rating("MoodysRating", "SandPRating", "FitchRating", 10));
        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("moodysRating", "MoodysRating")
                .param("sandPRating", "SandPrading")
                .param("fitchRating", "FitchRating")
                .param("orderNumber", "10")
                .with(csrf())
        ).andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateRatingAdminHasErrorTest() throws Exception {
        Rating rating = ratingRepository.save(new Rating("MoodysRating", "SandPRating", "FitchRating", 10));
        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("moodysRating", "MoodysRating")
                .param("sandPRating", "SandPrading")
                .param("fitchRating", "FitchRating")
                .param("orderNumber", "10a")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void deleteRatingAdminTest() throws Exception {
        Rating rating = ratingRepository.save(new Rating("MoodysRating", "SandPRating", "FitchRating", 10));

        this.mockMvc.perform(get("/rating/delete/" + rating.getId())).andExpect(status().isFound()).andReturn();
    }
}
