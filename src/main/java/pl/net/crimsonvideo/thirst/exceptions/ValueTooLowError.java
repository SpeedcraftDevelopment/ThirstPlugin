package pl.net.crimsonvideo.thirst.exceptions;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.stream.IntStream;

public class ValueTooLowError extends Error{
    @Serial
    private static final long serialVersionUID = 3578516434783311757L;
    private final String message;

    public ValueTooLowError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public int length() {
        return getMessage().length();
    }

    public boolean isEmpty() {
        return getMessage().isEmpty();
    }

    public byte[] getBytes() {
        return getMessage().getBytes();
    }

    @Override
    public boolean equals(Object anObject) {
        return getMessage().equals(anObject);
    }

    public int compareTo(@NotNull String anotherString) {
        return getMessage().compareTo(anotherString);
    }

    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }

    public String toString() {
        return getMessage().toString();
    }

    public IntStream codePoints() {
        return getMessage().codePoints();
    }

    public String intern() {
        return getMessage().intern();
    }
}
