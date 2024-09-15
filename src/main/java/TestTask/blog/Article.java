package TestTask.blog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotBlank(message = "Content is mandatory")
    private String content;

    @NotNull(message = "Publish date is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate publishDate;
}
