package TestTask.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Page<Article> listArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public List<Article[]> getArticleStatistics() {
        return (List<Article[]>) articleRepository.findArticleStatistics(LocalDate.now().minusDays(7), LocalDate.now());
    }
}