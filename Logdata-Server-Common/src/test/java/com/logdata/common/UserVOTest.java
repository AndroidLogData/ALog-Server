package com.logdata.common;

import com.logdata.common.model.UserRoles;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.UserDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserVOTest {
    @MockBean
    private UserDataRepository userDataRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void UserModelTest() {
        ArrayList<UserRoles> userRoles = new ArrayList<UserRoles>();
        UserRoles userRoles1 = new UserRoles();
        userRoles1.setRno(1L);
        userRoles1.setRoleName("USER");
        userRoles.add(0, userRoles1);

        UserVO userVO1 = new UserVO();
        userVO1.setPassword("user");
        userVO1.setApiKey("key");
        userVO1.setUserID("user");
        userVO1.setRoles(userRoles);

        when(userDataRepository.findOne("1")).thenReturn(userVO1);
        UserVO result1 = userDataRepository.findOne("1");
        assertThat(result1.getUserID()).isEqualTo(userVO1.getUserID());
        assertThat(result1.getPassword()).isEqualTo(userVO1.getPassword());
        assertThat(result1.getApiKey()).isEqualTo(userVO1.getApiKey());
        assertThat(result1.getRoles().get(0).getRno()).isEqualTo(userVO1.getRoles().get(0).getRno());
        assertThat(result1.getRoles().get(0).getRoleName()).isEqualTo(userVO1.getRoles().get(0).getRoleName());

        UserVO userVO2 = new UserVO("user", "user", userRoles, "key");
        when(userDataRepository.findOne("1")).thenReturn(userVO2);
        UserVO result2 = userDataRepository.findOne("1");
        assertThat(result2.getUserID()).isEqualTo(userVO2.getUserID());
        assertThat(result2.getPassword()).isEqualTo(userVO2.getPassword());
    }
}
