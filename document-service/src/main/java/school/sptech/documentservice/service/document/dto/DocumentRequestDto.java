package school.sptech.documentservice.service.document.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DocumentRequestDto {
  
  @NotBlank
  private String title;

  private String description;

  private String content;
}
