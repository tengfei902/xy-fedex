package com.xy.fedex.catalog;

import com.xy.fedex.rpc.context.UserContext;
import com.xy.fedex.rpc.context.UserContextHolder;
import com.xy.fedex.rpc.context.UserType;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseTest {

    @Before
    public void initUserContext() {
        UserContext userContext = new UserContext();
        userContext.setTenantId("10000");
        userContext.setIdentityId("5001");
        userContext.setUserType(UserType.USER);
        userContext.setProject("test_project");
        UserContextHolder.initUserContext(userContext);
    }
}
