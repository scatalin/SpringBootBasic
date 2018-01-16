package hello;

/**
 * @author cstan
 * @since 07.01.2018
 */
public class Greeting {

  private final long id;
  private final String content;

  Greeting(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

}
