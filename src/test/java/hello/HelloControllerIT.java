package hello;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The embedded server is started up on a random port by virtue of the webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT and the actual port is discovered at runtime with the @LocalServerPort.
 *
 * @author cstan
 * @since 07.01.2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {

  @LocalServerPort
  private int port;

  private URL base;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Before
  public void setup() throws MalformedURLException {
    this.base = new URL("http://localhost:" + port + "/");
  }

  @Test
  public void getHello() {
    ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(base.toString(), String.class);
    assertThat(responseEntity.getBody(), equalTo("Greetings from Spring Boot!"));
  }

}