package TestTask.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) {
        Article createdArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    @GetMapping
    public ResponseEntity<Page<Article>> listArticles(Pageable pageable) {
        Page<Article> articles = articleService.listArticles(pageable);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Article[]>> getArticleStatistics() {
        List<Article[]> statistics = articleService.getArticleStatistics();
        return ResponseEntity.ok(statistics);
    }
}
