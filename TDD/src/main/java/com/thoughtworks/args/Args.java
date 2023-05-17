package com.thoughtworks.args;

import com.thoughtworks.args.exceptions.IllegalOptionException;
import com.thoughtworks.args.exceptions.UnsupportedOptionTypeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args<T> {

    private static final Map<Class<?>, OptionParser<?>> PARSERS = Map.of(
            boolean.class, OptionParsers.bool(),
            int.class, OptionParsers.unary(0, Integer::parseInt),
            String.class, OptionParsers.unary("", String::valueOf),
            String[].class, OptionParsers.list(String[]::new, String::valueOf),
            Integer[].class, OptionParsers.list(Integer[]::new, Integer::parseInt)
    );

    public static <T> T parse(Class<T> optionsClass, String... args) {
        return new Args<>(optionsClass, PARSERS).parse(args);
    }

    private final Class<T> optionClass;
    private final Map<Class<?>, OptionParser<?>> parsers;

    public Args(Class<T> optionClass, Map<Class<?>, OptionParser<?>> parsers) {
        this.optionClass = optionClass;
        this.parsers = parsers;
    }

    public T parse(String... args) {
        try {
            Constructor<?> constructor = optionClass.getDeclaredConstructors()[0];
            List<String> arguments = Arrays.asList(args);

            Object[] values = Arrays.stream(constructor.getParameters())
                    .map(it -> parseOption(it, arguments))
                    .toArray();

            return (T) constructor.newInstance(values);
        } catch (IllegalOptionException | UnsupportedOptionTypeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseOption(Parameter parameter, List<String> arguments) {
        if (!parameter.isAnnotationPresent(Option.class)) {
            throw new IllegalOptionException(parameter.getName());
        }
        Option option = parameter.getAnnotation(Option.class);
        if (!parsers.containsKey(parameter.getType())) {
            throw new UnsupportedOptionTypeException(option.value(), parameter.getType());
        }
        return parsers.get(parameter.getType()).parse(arguments, option);
    }

}
