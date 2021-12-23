//package com.artonhanger.manage.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class AccountControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    @WithAnonymousUser
//    public void index_anonymous_Annotation() throws Exception {
//        mockMvc.perform(get("/"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//    @Test
//    @WithUser
//    public void index_user_annotation() throws Exception {
//        mockMvc.perform(get("/"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void index_anonymous() throws Exception {
//        mockMvc.perform(get("/").with(anonymous()))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void index_user() throws Exception {
//        mockMvc.perform(get("/").with(user("test").roles("USER")))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void admin_user() throws Exception {
//        mockMvc.perform(get("/admin").with(user("test").roles("USER")))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//    }
//    @Test
//    public void admin_admin() throws Exception {
//        mockMvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//    @Test
//    public void login() throws Exception{
//        mockMvc.perform(formLogin().user("test@test.com").password("test"))
//                .andExpect(authenticated());
//    }
//
//}