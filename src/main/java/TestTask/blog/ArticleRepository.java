package TestTask.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    long countByPublishDate(LocalDate date);

    @Query("SELECT a FROM Article a WHERE a.publishDate BETWEEN :startDate AND :endDate")
    List<Article[]> findArticleStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
