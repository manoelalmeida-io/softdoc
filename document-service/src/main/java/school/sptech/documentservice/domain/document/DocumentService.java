package school.sptech.documentservice.domain.document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentService {
  
  Page<Document> list(Pageable pageable);

  Document findOne(Long id);

  Document create(Document document);

  Document update(Long id, Document document);
  
  void delete(Long id);
}
