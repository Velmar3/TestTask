package TestTask.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    public void testAccessPublicEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAccessAdminEndpointWithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/statistics")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAccessAdminEndpointWithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stats")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }
}