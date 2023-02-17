package com.xy.fedex.admin;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@SpringBootTest
public class BaseTest {

    @Test
    void contextLoads() {
    }

}
