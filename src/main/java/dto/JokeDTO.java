package dto;

import entities.Joke;
import java.util.Date;

/**
 *
 * @author Oliver
 */
public class JokeDTO {
    private final int id;
    private final Date created_at;
    private final String joke;
    private final String type;

    public JokeDTO(Joke joke) {
        this.id = joke.getId();
        this.created_at = joke.getCreated_at();
        this.joke = joke.getJoke();
        this.type = joke.getType();
    }

    public int getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getJoke() {
        return joke;
    }

    public String getType() {
        return type;
    }
}
