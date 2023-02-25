package school.sptech.documentservice.service.document.dto;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class DocumentResponseDto {
  
  private Long id;
  private String title;
  private String description;
  private String content;
  private OffsetDateTime createdDate;
  private OffsetDateTime lastUpdatedDate;
}
