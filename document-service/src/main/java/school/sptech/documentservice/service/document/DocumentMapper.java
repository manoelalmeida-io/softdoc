package school.sptech.documentservice.service.document;

import org.springframework.stereotype.Component;
import school.sptech.documentservice.domain.document.Document;
import school.sptech.documentservice.service.document.dto.DocumentRequestDto;
import school.sptech.documentservice.service.document.dto.DocumentResponseDto;

@Component
public class DocumentMapper {
  
  public Document toDomain(DocumentRequestDto dto) {

    if (dto == null) {
      return null;
    }

    Document domain = new Document();
    domain.setTitle(dto.getTitle());
    domain.setDescription(dto.getDescription());
    domain.setContent(dto.getContent());

    return domain;
  }

  public DocumentResponseDto toResponseDto(Document domain) {
    
    if (domain == null) {
      return null;
    }

    DocumentResponseDto dto = new DocumentResponseDto();
    dto.setId(domain.getId());
    dto.setTitle(domain.getTitle());
    dto.setDescription(domain.getDescription());
    dto.setContent(domain.getContent());
    dto.setCreatedDate(domain.getCreatedDate());
    dto.setLastUpdatedDate(domain.getLastUpdatedDate());

    return dto;
  }
}
