package com.karumi.screenshot;


public class Result<X,Y> {

    private final X value;
    private final Y error;

    public Result(X value, Y error) {
        this.value = value;
        this.error = error;
    }

    public X getValue() {
        return value;
    }

    public Y getError() {
        return error;
    }

    public boolean hasError(){
        return error != null;
    }
}
