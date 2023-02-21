package school.sptech.documentservice.api.controller;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.documentservice.api.utils.UriComponentUtils;
import school.sptech.documentservice.domain.document.Document;
import school.sptech.documentservice.domain.document.DocumentService;
import school.sptech.documentservice.service.document.DocumentMapper;
import school.sptech.documentservice.service.document.dto.DocumentRequestDto;
import school.sptech.documentservice.service.document.dto.DocumentResponseDto;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
  
  private final DocumentService documentService;
  private final DocumentMapper documentMapper;

  @GetMapping
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<Page<DocumentResponseDto>> listAll(Pageable pageable) {

    Page<Document> documents = this.documentService.list(pageable);
    return ResponseEntity.ok(documents.map(this.documentMapper::toResponseDto));
  }

  @GetMapping("{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<DocumentResponseDto> findOne(@PathVariable Long id) {

    Document document = this.documentService.findOne(id);
    return ResponseEntity.ok(this.documentMapper.toResponseDto(document));
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<DocumentResponseDto> create(
      @Valid @RequestBody DocumentRequestDto request) {

    Document document = this.documentService.create(this.documentMapper.toDomain(request));

    URI location = UriComponentUtils.getLocationForCreatedResource(document.getId());
    return ResponseEntity.created(location).body(this.documentMapper.toResponseDto(document));
  }

  @PutMapping("{id}")
  public ResponseEntity<DocumentResponseDto> update(
      @PathVariable Long id,
      @Valid @RequestBody DocumentRequestDto request) {

    Document document = this.documentService.update(id, this.documentMapper.toDomain(request));
    return ResponseEntity.ok(this.documentMapper.toResponseDto(document));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    
    this.documentService.delete(id);
    return ResponseEntity.ok().build();
  }
}
