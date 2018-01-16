package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class is flagged as a @RestController, meaning it’s ready for use by Spring MVC to handle web requests.
 *
 * When invoked from a browser or using curl on the command line, the method returns pure text.
 * That’s because @RestController combines @Controller and @ResponseBody, two annotations that results in web requests returning data rather than a view.
 *
 * @author cstan
 * @since 07.01.2018
 */
@RestController
public class HelloController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  /**
   * #RequestMapping maps / to the index() method.
   */
  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

  /**
   * The @ResponseBody annotation tells Spring MVC not to render a model into a view, but rather to write the returned object into the response body. It does this by using one of Spring’s message converters. Because Jackson 2 is in the classpath, this means that MappingJackson2HttpMessageConverter will handle the conversion of Greeting to JSON if the request’s Accept header specifies that JSON should be returned.
   *
   * @param name the name parameter from the call
   * @return the response to the call
   */
  @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
  public @ResponseBody
  Greeting sayHello(
      @RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
    return new Greeting(counter.getAndIncrement(), String.format(template, name));
  }
}
