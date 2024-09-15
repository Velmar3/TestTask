package TestTask.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testCountByPublishDate() {
        LocalDate date = LocalDate.of(2024, 9, 15);
        Article article = new Article();
        article.setTitle("Test Title");
        article.setAuthor("Test Author");
        article.setContent("Test Content");
        article.setPublishDate(date);
        entityManager.persistAndFlush(article);

        long count = articleRepository.countByPublishDate(date);
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void testFindArticleStatistics() {
        LocalDate startDate = LocalDate.of(2024, 9, 1);
        LocalDate endDate = LocalDate.of(2024, 9, 30);
        Article article1 = new Article();
        article1.setTitle("Title 1");
        article1.setAuthor("Author 1");
        article1.setContent("Content 1");
        article1.setPublishDate(LocalDate.of(2024, 9, 10));
        entityManager.persistAndFlush(article1);

        Article article2 = new Article();
        article2.setTitle("Title 2");
        article2.setAuthor("Author 2");
        article2.setContent("Content 2");
        article2.setPublishDate(LocalDate.of(2024, 9, 14));
        entityManager.persistAndFlush(article2);

        List<Article[]> statistics = articleRepository.findArticleStatistics(startDate, endDate);
        assertThat(statistics).hasSize(2);
    }
}