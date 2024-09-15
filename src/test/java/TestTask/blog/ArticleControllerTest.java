package TestTask.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@Import(SecurityConfig.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private Article article;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        article = new Article();
        article.setId(1L);
        article.setTitle("Test Title");
        article.setAuthor("Test Author");
        article.setContent("Test Content");
        article.setPublishDate(LocalDate.now());
    }

    @Test
    @WithMockUser
    public void testCreateArticle() throws Exception {
        when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/articles")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Title\", \"author\": \"Test Author\", \"content\": \"Test Content\", \"publishDate\": \"2024-09-15\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(jsonPath("$.publishDate").value("2024-09-15"));
    }

    @Test
    @WithMockUser
    public void testListArticles() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Article> page = new PageImpl<>(Collections.singletonList(article), pageable, 1);

        when(articleService.listArticles(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("Test Title"))
                .andExpect(jsonPath("$.content[0].author").value("Test Author"))
                .andExpect(jsonPath("$.content[0].content").value("Test Content"))
                .andExpect(jsonPath("$.content[0].publishDate").value("2024-09-15"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetArticleStatistics() throws Exception {
        List<Article[]> statistics = Collections.singletonList(new Article[]{article});

        when(articleService.getArticleStatistics()).thenReturn(statistics);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/statistics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0].id").value(1L))
                .andExpect(jsonPath("$[0][0].title").value("Test Title"))
                .andExpect(jsonPath("$[0][0].author").value("Test Author"))
                .andExpect(jsonPath("$[0][0].content").value("Test Content"))
                .andExpect(jsonPath("$[0][0].publishDate").value("2024-09-15"));
    }
}
