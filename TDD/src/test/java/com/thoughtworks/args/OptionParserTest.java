package com.thoughtworks.args;

import com.thoughtworks.args.exceptions.IllegalValueException;
import com.thoughtworks.args.exceptions.InsufficientArgumentsException;
import com.thoughtworks.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OptionParserTest {

    @Nested
    class UnaryOptionParser {
        @Test// Sad Path
        public void should_not_accept_extra_argument_for_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
                    OptionParsers.unary(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p")));

            assertEquals("p", e.getOption());
        }

        @ParameterizedTest// Sad Path
        @ValueSource(strings = {"-p -l", "-p"})
        public void should_not_accept_insufficient_argument_for_single_valued_option(String arguments) {
            InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () ->
                    OptionParsers.unary(0, Integer::parseInt).parse(asList(arguments.split(" ")), option("p")));

            assertEquals("p", e.getOption());
        }

        @Test// default value
        public void should_set_default_value_to_0_for_int_option() {
            Function<String, Object> whatever = (it) -> null;
            Object defaultValue = new Object();

            assertSame(defaultValue, OptionParsers.unary(defaultValue, whatever).parse(asList(), option("p")));
        }

        @Test// Happy path
        public void should_parse_value_if_flag_present() {
            Function<String, Object> parser = mock(Function.class);

            OptionParsers.unary(Mockito.any(), parser).parse(asList("-p", "8080"), option("p"));

            verify(parser).apply("8080");
        }
    }

    @Nested
    class BoolOptionParser {
        @Test// Sad Path
        public void should_not_accept_extra_argument_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
                    OptionParsers.bool().parse(asList("-l", "t"), option("l"))
            );

            assertEquals("l", e.getOption());
        }

        @Test// Default value
        public void should_set_default_value_to_false_if_option_not_present() {
            // setup
            OptionParser<Boolean> parser = OptionParsers.bool();
            // before
            Boolean result;
            // exercise
            result = parser.parse(asList(), option("l"));
            // verify
            assertFalse(result);
            // teardown
        }

        @Test// Happy Path
        public void should_set_value_to_true_if_option_present() {
            assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
        }

        static Option option(String value) {
            return new Option() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return Option.class;
                }

                @Override
                public String value() {
                    return value;
                }
            };
        }
    }

    @Nested
    class ListOptionParser {
        @Test
        public void should_parse_list_value() {
            Function parser = mock(Function.class);

            OptionParsers.list(Object[]::new, parser).parse(asList("-g", "this", "is"), option("g"));

            InOrder order = inOrder(parser, parser);

            order.verify(parser).apply("this");
            order.verify(parser).apply("is");
        }

        @Test
        public void should_not_treat_negative_int_as_flag() {
            assertArrayEquals(new Integer[]{-1, -2}, OptionParsers.list(Integer[]::new, Integer::parseInt).parse(asList("-g", "-1", "-2"), option("g")));
        }

        @Test
        public void should_use_empty_array_as_default_value() {
            String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(asList(), option("g"));

            assertEquals(0, value.length);
        }

        @Test
        public void should_throw_exception_if_value_parser_cant_parse_value() {
            Function<String, String> parser = (it) -> {
                throw new RuntimeException();
            };
            IllegalValueException e = assertThrows(IllegalValueException.class, () -> OptionParsers.list(String[]::new, parser)
                    .parse(asList("-g", "this", "is"), option("g")));
            assertEquals("g", e.getOption());
            assertEquals("this", e.getValue());
        }
    }

    static Option option(String value) {
        return new Option() {
            @Override
            public String value() {
                return value;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }
        };
    }
}
