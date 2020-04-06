package com.epam.lab.exception;

import java.util.Objects;

public class ExceptionInfo {

    private final String url;
    private final String message;

    public ExceptionInfo(final String path, final String exceptionDescription) {
        this.url = path;
        this.message = exceptionDescription;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExceptionInfo that = (ExceptionInfo) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, message);
    }

    @Override
    public String toString() {
        return "ExceptionInfo{" +
                "url='" + url + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
