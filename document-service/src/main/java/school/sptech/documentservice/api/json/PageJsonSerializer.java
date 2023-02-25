package school.sptech.documentservice.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

  @Override
  public void serialize(
      Page<?> page,
      JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    
    jsonGenerator.writeStartObject();

    jsonGenerator.writeObjectField("content", page.getContent());
    jsonGenerator.writeBooleanField("first", page.isFirst());
    jsonGenerator.writeBooleanField("last", page.isLast());
    jsonGenerator.writeNumberField("totalPages", page.getTotalPages());
    jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
    jsonGenerator.writeNumberField("numberOfElements", page.getNumberOfElements());
    jsonGenerator.writeNumberField("size", page.getSize());
    jsonGenerator.writeNumberField("number", page.getNumber());

    jsonGenerator.writeEndObject();
  }
}
