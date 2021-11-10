package com.example.capitol.application;

import com.example.capitol.controller.CapitolUserController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestingWebApplicationTests {

    @Autowired
    private CapitolUserController controller;
    @Test
    public void contextLoads() throws Exception{
        assertThat(controller).isNotNull();
    }

}