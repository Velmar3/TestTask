package TestTask.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateArticle() {
        Article article = new Article();
        Article createdArticle = new Article();
        when(articleRepository.save(any(Article.class))).thenReturn(createdArticle);

        Article result = articleService.createArticle(article);

        assertEquals(createdArticle, result);
    }

    @Test
    public void testListArticles() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Article> articles = new PageImpl<>(Collections.emptyList());
        when(articleRepository.findAll(any(Pageable.class))).thenReturn(articles);

        Page<Article> result = articleService.listArticles(pageable);

        assertEquals(articles, result);
    }

    @Test
    public void testGetArticleStatistics() {
        List<Article[]> statistics = Collections.emptyList();
        when(articleRepository.findArticleStatistics(any(LocalDate.class), any(LocalDate.class))).thenReturn(statistics);

        List<Article[]> result = articleService.getArticleStatistics();

        assertEquals(statistics, result);
    }
}
