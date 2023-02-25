package school.sptech.documentservice.service.document;

import java.time.Clock;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.documentservice.domain.document.Document;
import school.sptech.documentservice.domain.document.DocumentRepository;
import school.sptech.documentservice.domain.document.DocumentService;
import school.sptech.documentservice.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

  public static final String NOT_FOUND_MESSAGE = "Document not found";

  private final DocumentRepository documentRepository;
  private final Clock clock;

  @Override
  public Page<Document> list(Pageable pageable) {
    return this.documentRepository.findAll(pageable);
  }

  @Override
  public Document findOne(Long id) {
    return this.documentRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
  }

  @Override
  @Transactional
  public Document create(Document document) {
    document.setCreatedDate(OffsetDateTime.now(clock));
    document.setLastUpdatedDate(OffsetDateTime.now(clock));

    return this.documentRepository.save(document);
  }

  @Override
  @Transactional
  public Document update(Long id, Document document) {
    Document current = this.documentRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

    document.setId(id);
    document.setCreatedDate(current.getCreatedDate());
    document.setLastUpdatedDate(OffsetDateTime.now(clock));

    return this.documentRepository.save(document);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!this.documentRepository.existsById(id)) {
      throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
    }

    this.documentRepository.deleteById(id);
  }
}
