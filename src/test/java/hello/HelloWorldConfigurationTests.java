package hello;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Some more basic integration tests
 *
 * @author cstan
 * @since 07.01.2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class HelloWorldConfigurationTests {

  @LocalServerPort
  private int port;

  @Value("${local.management.port}")
  private int mgt;

  @Autowired
  private TestRestTemplate template;

  @Test
  public void shouldReturn200WhenSendingRequestToController() {
    ResponseEntity<Map> entity = this.template
        .getForEntity("http://localhost:" + this.port + "/hello-world", Map.class);

    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void shouldReturn200WhenSendingRequestToManagementEndpoint() {
    ResponseEntity<Map> entity = this.template
        .getForEntity("http://localhost:" + this.mgt + "/info", Map.class);

    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void methodShouldReturnNameParamWithCall() {
    List<String> names = new ArrayList<>();
    names.add("asd");
    names.add("Catalin");
    names.add("Catalin    ");

    for (String name : names) {
      ResponseEntity<Map> entity = this.template
          .getForEntity("http://localhost:" + this.port + "/hello-world?name=" + name, Map.class);

      then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
      then(entity.getBody().get("content")).isEqualTo("Hello, " + name + "!");
    }
  }

  @Test
  public void methodShouldReturnDefaultName() {
      ResponseEntity<Map> entity = this.template
          .getForEntity("http://localhost:" + this.port + "/hello-world?name=", Map.class);

      then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
      then(entity.getBody().get("content")).isEqualTo("Hello, Stranger!");
  }
}
