package com.github.lucbui.server.parse.processors;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.util.ListToOptionalCollector;

import java.util.*;
import java.util.stream.Collectors;

public enum DirectiveProcessors {
    DEFINE("#define",null, 2);

    private final String key;
    private final Processor processor;
    private final int parameterCount;

    DirectiveProcessors(String key, Processor processor, int parameterCount) {
        this.key = key;
        this.processor = processor;
        this.parameterCount = parameterCount;
    }

    public Processor getProcessor() {
        return processor;
    }

    public static Optional<DirectiveProcessors> getProcessor(CommandLine commandLine){
        return Arrays.stream(values())
                .filter(p -> p.key.equals(commandLine.getCommand()))
                .filter(p -> p.parameterCount == commandLine.getParameters().length)
                .collect(new ListToOptionalCollector<>());
    }
}
