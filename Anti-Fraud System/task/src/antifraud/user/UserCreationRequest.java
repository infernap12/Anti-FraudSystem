package antifraud.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.io.Serializable;

public record UserCreationRequest(
        @JsonProperty(required = true)
        String name,
        @JsonProperty(required = true)
        String username,
        @JsonProperty(required = true)
        String password
) implements Serializable {
}
