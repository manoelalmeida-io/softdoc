package school.sptech.documentservice.service.document;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import school.sptech.documentservice.domain.document.Document;
import school.sptech.documentservice.domain.document.DocumentRepository;
import school.sptech.documentservice.domain.document.DocumentService;
import school.sptech.documentservice.exception.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTests {

  @Mock
  private DocumentRepository documentRepository;
  
  @Mock
  private Clock clock;

  private DocumentService documentService;

  private static final ZonedDateTime NOW = ZonedDateTime.now(); 

  @BeforeEach
  void init() {
    this.documentService = new DocumentServiceImpl(documentRepository, clock);
  }

  @Test
  void listShouldReturnPageOfDocuments() {
    Pageable pageable = mock(Pageable.class);
    Page<Document> documents = new PageImpl<>(List.of(mock(Document.class)));

    when(documentRepository.findAll(pageable)).thenReturn(documents);

    Page<Document> response = this.documentService.list(pageable);

    assertEquals(documents, response);
  }

  @Test
  void findOneShouldReturnDocumentIfIdExists() {
    Long id = 1L;
    Document document = mock(Document.class);

    when(documentRepository.findById(id)).thenReturn(Optional.of(document));

    Document response = this.documentService.findOne(id);
    
    assertEquals(document, response);
  }

  @Test
  void findOneShouldThrowNotFoundIfIdNotExists() {
    Long id = 1L;

    when(documentRepository.findById(id)).thenReturn(Optional.ofNullable(null));
    
    assertThrows(EntityNotFoundException.class, () -> this.documentService.findOne(id));
  }

  @Test
  void createShouldSaveNewDocument() {
    Document document = new Document();

    when(documentRepository.save(document)).then(AdditionalAnswers.returnsFirstArg());

    when(clock.instant()).thenReturn(NOW.toInstant());
    when(clock.getZone()).thenReturn(NOW.getZone());

    Document response = this.documentService.create(document);

    assertEquals(document, response);
    assertEquals(NOW.toOffsetDateTime(), document.getCreatedDate());
    assertEquals(NOW.toOffsetDateTime(), document.getLastUpdatedDate());
  }

  @Test
  void updateShouldUpdateDocument() {
    Long id = 1L;

    Document updated = new Document();
    Document current = new Document();
    current.setCreatedDate(NOW.minusDays(1).toOffsetDateTime());

    when(documentRepository.save(updated)).then(AdditionalAnswers.returnsFirstArg());
    when(documentRepository.findById(id)).thenReturn(Optional.of(current));

    when(clock.instant()).thenReturn(NOW.toInstant());
    when(clock.getZone()).thenReturn(NOW.getZone());

    Document response = this.documentService.update(id, updated);

    assertEquals(updated, response);
    assertEquals(id, response.getId());
    assertEquals(NOW.minusDays(1).toOffsetDateTime(), response.getCreatedDate());
    assertEquals(NOW.toOffsetDateTime(), response.getLastUpdatedDate());
  }

  @Test
  void updateShouldThrowNotFoundIfIdNotExists() {
    Long id = 1L;
    Document updated = new Document();

    when(documentRepository.findById(id)).thenReturn(Optional.ofNullable(null));
    
    assertThrows(EntityNotFoundException.class, () -> this.documentService.update(id, updated));
  }

  @Test
  void deleteShouldDeleteDocument() {
    Long id = 1L;

    when(documentRepository.existsById(id)).thenReturn(true);

    this.documentService.delete(id);

    verify(documentRepository, times(1)).deleteById(id);
  }

  @Test
  void deleteShouldThrowNotFoundIfIdNotExists() {
    Long id = 1L;

    when(documentRepository.existsById(id)).thenReturn(false);
    
    assertThrows(EntityNotFoundException.class, () -> this.documentService.delete(id));
  }
}
