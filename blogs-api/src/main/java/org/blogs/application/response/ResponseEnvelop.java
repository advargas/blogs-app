package org.blogs.application.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseEnvelop<T> {

    private boolean success = true;

    private T response;

    private List<String> errors = new ArrayList<>();

    public ResponseEnvelop() {
    }

    public ResponseEnvelop(T result) {
        this.response = result;
    }

    public ResponseEnvelop(boolean success, T result) {
        super();
        this.success = success;
        this.response = result;
    }

    public void addError(String error) {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        this.errors.add(error);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}