package antifraud.auth.user;

import com.fasterxml.jackson.annotation.JsonProperty;

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
