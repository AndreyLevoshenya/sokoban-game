package utils;

import logic.Level;

import java.io.Serial;
import java.io.Serializable;

public record Config(Level level) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}
