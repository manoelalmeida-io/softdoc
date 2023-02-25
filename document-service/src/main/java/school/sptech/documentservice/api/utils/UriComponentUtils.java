package school.sptech.documentservice.api.utils;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UriComponentUtils {
  
  private UriComponentUtils() {}

  public static URI getLocationForCreatedResource(Long resource) {
    return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(resource).toUri();
  }
}
