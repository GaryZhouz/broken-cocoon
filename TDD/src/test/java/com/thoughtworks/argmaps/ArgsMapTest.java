package com.thoughtworks.argmaps;

import com.thoughtworks.args.Option;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgsMapTest {
    // option without value, -b
    @Test
    public void should_split_option_without_value() {
        Map<String, String[]> args = Args.toMap("-b");

        assertEquals(1, args.size());
        assertArrayEquals(new String[]{}, args.get("b"));
    }

    // option with value -p 8080
    @Test
    public void should_split_option_with_value() {
        Map<String, String[]> args = Args.toMap("-p", "8080");

        assertEquals(1, args.size());
        assertArrayEquals(new String[]{"8080"}, args.get("p"));
    }

    // option with values -g this is a list
    @Test
    public void should_split_option_with_values() {
        Map<String, String[]> args = Args.toMap("-g", "this", "is", "a", "list");

        assertEquals(1, args.size());
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, args.get("g"));
    }

    // multi options
    @Test
    public void should_split_args_to_map() {
        Map<String, String[]> args = Args.toMap("-b", "-p", "8080", "-d", "/usr/logs");

        assertEquals(3, args.size());
        assertArrayEquals(new String[]{}, args.get("b"));
        assertArrayEquals(new String[]{"/usr/logs"}, args.get("d"));
    }

    @Test
    public void should_split_args_list_to_map() {
        Map<String, String[]> args = Args.toMap("-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");

        assertEquals(2, args.size());
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, args.get("g"));
        assertArrayEquals(new String[]{"1", "2", "-3", "5"}, args.get("d"));
    }


    @Test
    public void should_parse_bool_option() {
        Function<String[], Map<String, String[]>> optionParser = mock(Function.class);

        when(optionParser.apply(new String[]{"-l"})).thenReturn(Map.of("l", new String[0]));

        Args<BoolOption> args = new Args<>(BoolOption.class, Map.of(boolean.class, ArgsMapTest::parseBool), optionParser);

        BoolOption option = args.parse("-l");

        assertTrue(option.logging);
    }

    static record BoolOption(@Option("l") boolean logging) {
    }

    @Test
    public void should_parse_int_option() {
        Function<String[], Map<String, String[]>> optionParser = mock(Function.class);

        when(optionParser.apply(new String[]{"-p", "8080"})).thenReturn(Map.of("p", new String[]{"8080"}));

        Args<IntOption> args = new Args<>(IntOption.class, Map.of(int.class, ArgsMapTest::parseInt), optionParser);

        IntOption option = args.parse("-p", "8080");

        assertEquals(8080, option.port);
    }

    static record IntOption(@Option("p") int port) {
    }

    private static boolean parseBool(String[] values) {
        checkSize(values, 0);
        return values != null;
    }

    private static int parseInt(String[] values) {
        checkSize(values, 1);
        return Integer.parseInt(values[0]);
    }

    private static void checkSize(String[] values, int size) {
        if (values != null && values.length != size) {
            throw new RuntimeException();
        }
    }
}
