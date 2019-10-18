package com.github.lucbui.server.parse.processors;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.util.ListToOptionalCollector;
import com.github.lucbui.xse.XseLanguage;

import java.util.*;
import java.util.stream.Collectors;

public enum DirectiveProcessors {
    DEFINE("#define", new DefineProcessor(), 2);

    private final String key;
    private final Processor processor;
    private final int parameterCount;

    DirectiveProcessors(String key, Processor processor, int parameterCount) {
        this.key = key;
        this.processor = processor;
        this.parameterCount = parameterCount;
    }

    public Processor getProcessor() {
        XseLanguage.V1.getCommands().get(0).getParameters();
        return processor;
    }

    public static Optional<DirectiveProcessors> getProcessor(CommandLine commandLine){
        return Arrays.stream(values())
                .filter(p -> p.key.equals(commandLine.getCommand()))
                .filter(p -> p.parameterCount == commandLine.getParameters().length)
                .collect(new ListToOptionalCollector<>());
    }
}
