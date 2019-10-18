package com.github.lucbui.util;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import java.util.ArrayList;
import java.util.List;

public class ProcessorUtils {
    public static List<Range> getRangesForWords(int lineNumber, String line) {
        List<Range> range = new ArrayList<>();
        line = StringUtils.stripEnd(line, null);
        int lastBoundary = 0;
        int cur = 0;
        while(Character.isWhitespace(line.charAt(cur++))){
            lastBoundary++;
        }
        for(; cur < line.length(); cur++) {
            if(Character.isWhitespace(line.charAt(cur))){
                range.add(new Range(new Position(lineNumber, lastBoundary), new Position(lineNumber, cur)));
                lastBoundary = cur + 1;
            }
        }
        range.add(new Range(new Position(lineNumber, lastBoundary), new Position(lineNumber, line.length())));
        return range;
    }
}
