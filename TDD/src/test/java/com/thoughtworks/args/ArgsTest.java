package com.thoughtworks.args;

import com.thoughtworks.args.exceptions.IllegalOptionException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.creation.MockSettingsImpl;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgsTest {

    @Test
    public void should_parse_multi_options() {
        // SUT Arg.parse
        // before
        MultiOptions options;

        // exercise
        options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");

        // verify
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());

        // teardown
    }

    // setup
    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    @Test
    public void should_throw_illegal_option_exception_if_annotation_not_present() {
        // before
        IllegalOptionException e;

        e = /*verify*/assertThrows(IllegalOptionException.class, () -> /*execrise*/Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        // verify
        assertEquals("port", e.getParameter());
        // teardown
    }

    // setup
    static record OptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {
    }
    // sad path:
    //  - bool -l t / -t t f
    //  - int -p/ -p 8080 8081
    //  - string -d/ -d /usr/logs /usr/vars
    // default value
    //  - bool: false
    //  - int: 0
    // - string ""

    // -g this is a list -d 1 2 -3 5
    @Test
    public void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");

        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new Integer[]{1, 2, -3, 5}, options.decimals());
    }

    // setup
    static record ListOptions(@Option("g") String[] group, @Option("d") Integer[] decimals) {
    }

    @Test
    public void should_parse_options_if_option_parser_provided() {
        OptionParser<Boolean> boolParser = mock(OptionParser.class);
        OptionParser<Integer> intParser = mock(OptionParser.class);
        OptionParser<String> stringParser = mock(OptionParser.class);

        when(boolParser.parse(any(), any())).thenReturn(true);
        when(intParser.parse(any(), any())).thenReturn(1000);
        when(stringParser.parse(any(), any())).thenReturn("parsed");

        Args<MultiOptions> args = new Args<>(MultiOptions.class, Map.of(boolean.class, boolParser, int.class, intParser, String.class, stringParser));

        MultiOptions options = args.parse("-l", "-p", "8080", "-d", "/usr/logs");

        assertTrue(true);
        assertEquals(1000, options.port());
        assertEquals("parsed", options.directory());
    }
}
