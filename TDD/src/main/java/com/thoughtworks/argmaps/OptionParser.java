package com.thoughtworks.argmaps;

public interface OptionParser<T> {

    T parse(String[] values);

}
