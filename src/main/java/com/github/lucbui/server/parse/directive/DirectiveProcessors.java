package com.github.lucbui.server.parse.directive;

import com.github.lucbui.line.CommandLine;

import java.util.*;
import java.util.stream.Collectors;

public enum DirectiveProcessors {
    DEFINE("#define",null, 2);

    private final String key;
    private final DirectiveProcessor processor;
    private final int parameterCount;

    DirectiveProcessors(String key, DirectiveProcessor processor, int parameterCount) {
        this.key = key;
        this.processor = processor;
        this.parameterCount = parameterCount;
    }

    public DirectiveProcessor getProcessor() {
        return processor;
    }

    public static List<DirectiveProcessors> getProcessor(CommandLine commandLine){
        return Arrays.stream(values())
                .filter(p -> p.key.equals(commandLine.getCommand()))
                .filter(p -> p.parameterCount == commandLine.getParameters().length)
                .collect(Collectors.toList());
    }
}
