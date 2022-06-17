package pl.net.crimsonvideo.thirst.exceptions;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.stream.IntStream;

public class ValueTooHighError extends Error {
    @Serial
    private static final long serialVersionUID = -4801576966237163502L;
    private final String message;
    public ValueTooHighError(String message) {
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

    public char charAt(int index) {
        return getMessage().charAt(index);
    }

    public int codePointAt(int index) {
        return getMessage().codePointAt(index);
    }

    public int codePointBefore(int index) {
        return getMessage().codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return getMessage().codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return getMessage().offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char @NotNull [] dst, int dstBegin) {
        getMessage().getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public byte[] getBytes(@NotNull String charsetName) throws UnsupportedEncodingException {
        return getMessage().getBytes(charsetName);
    }

    public byte[] getBytes(@NotNull Charset charset) {
        return getMessage().getBytes(charset);
    }

    public byte[] getBytes() {
        return getMessage().getBytes();
    }

    public boolean contentEquals(@NotNull StringBuffer sb) {
        return getMessage().contentEquals(sb);
    }

    public boolean contentEquals(@NotNull CharSequence cs) {
        return getMessage().contentEquals(cs);
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return getMessage().equalsIgnoreCase(anotherString);
    }

    public int compareTo(@NotNull String anotherString) {
        return getMessage().compareTo(anotherString);
    }

    public int compareToIgnoreCase(@NotNull String str) {
        return getMessage().compareToIgnoreCase(str);
    }

    public boolean regionMatches(int toffset, @NotNull String other, int ooffset, int len) {
        return getMessage().regionMatches(toffset, other, ooffset, len);
    }

    public boolean regionMatches(boolean ignoreCase, int toffset, @NotNull String other, int ooffset, int len) {
        return getMessage().regionMatches(ignoreCase, toffset, other, ooffset, len);
    }

    public boolean startsWith(@NotNull String prefix, int toffset) {
        return getMessage().startsWith(prefix, toffset);
    }

    public boolean startsWith(@NotNull String prefix) {
        return getMessage().startsWith(prefix);
    }

    public boolean endsWith(@NotNull String suffix) {
        return getMessage().endsWith(suffix);
    }

    public int indexOf(int ch) {
        return getMessage().indexOf(ch);
    }

    public int indexOf(int ch, int fromIndex) {
        return getMessage().indexOf(ch, fromIndex);
    }

    public int lastIndexOf(int ch) {
        return getMessage().lastIndexOf(ch);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return getMessage().lastIndexOf(ch, fromIndex);
    }

    public int indexOf(@NotNull String str) {
        return getMessage().indexOf(str);
    }

    public int indexOf(@NotNull String str, int fromIndex) {
        return getMessage().indexOf(str, fromIndex);
    }

    public int lastIndexOf(@NotNull String str) {
        return getMessage().lastIndexOf(str);
    }

    public int lastIndexOf(@NotNull String str, int fromIndex) {
        return getMessage().lastIndexOf(str, fromIndex);
    }

    public String substring(int beginIndex) {
        return getMessage().substring(beginIndex);
    }

    public String substring(int beginIndex, int endIndex) {
        return getMessage().substring(beginIndex, endIndex);
    }

    public CharSequence subSequence(int beginIndex, int endIndex) {
        return getMessage().subSequence(beginIndex, endIndex);
    }

    public String concat(@NotNull String str) {
        return getMessage().concat(str);
    }

    public String replace(char oldChar, char newChar) {
        return getMessage().replace(oldChar, newChar);
    }

    public boolean matches(@NotNull String regex) {
        return getMessage().matches(regex);
    }

    public boolean contains(@NotNull CharSequence s) {
        return getMessage().contains(s);
    }

    public String replaceFirst(@NotNull String regex, @NotNull String replacement) {
        return getMessage().replaceFirst(regex, replacement);
    }

    public String replaceAll(@NotNull String regex, @NotNull String replacement) {
        return getMessage().replaceAll(regex, replacement);
    }

    public String replace(@NotNull CharSequence target, @NotNull CharSequence replacement) {
        return getMessage().replace(target, replacement);
    }

    public String[] split(@NotNull String regex, int limit) {
        return getMessage().split(regex, limit);
    }

    public String[] split(@NotNull String regex) {
        return getMessage().split(regex);
    }

    public String toLowerCase(@NotNull Locale locale) {
        return getMessage().toLowerCase(locale);
    }

    public String toLowerCase() {
        return getMessage().toLowerCase();
    }

    public String toUpperCase(@NotNull Locale locale) {
        return getMessage().toUpperCase(locale);
    }

    public String toUpperCase() {
        return getMessage().toUpperCase();
    }

    public String trim() {
        return getMessage().trim();
    }

    public String toString() {
        return getMessage().toString();
    }

    public IntStream chars() {
        return getMessage().chars();
    }

    public IntStream codePoints() {
        return getMessage().codePoints();
    }

    public char[] toCharArray() {
        return getMessage().toCharArray();
    }

    public String intern() {
        return getMessage().intern();
    }
}
