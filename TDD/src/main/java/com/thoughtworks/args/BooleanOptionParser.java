package com.thoughtworks.args;

import java.util.List;

import static com.thoughtworks.args.SingleValueOptionParser.values;

class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return values(arguments, option, 0)
                .map(it -> true)
                .orElse(false);
    }
}
