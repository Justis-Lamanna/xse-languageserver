package com.github.lucbui.server;

import com.github.lucbui.server.util.Pair;
import com.github.lucbui.server.util.WithLineNumber;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class XseDocumentModel {

    private String text;
    private List<Line> lines;

    public XseDocumentModel(String text){
        this.text = text;
        compile();
    }

    /**
     * Compile everything because I am lazy
     */
    private void compile(){
        lines = getLines(text)
                .map(Line::new)
                .collect(Collectors.toList());
    }

    private static Stream<String> getLines(String text){
        BufferedReader reader = new BufferedReader(new StringReader(text));
        return reader.lines();
    }

    private Line get(int lineNumber){
        if(lineNumber >= lines.size()){
            Line newLine = new Line("");
            lines.add(newLine);
            return newLine;
        }
        return lines.get(lineNumber);
    }

    /**
     * Get the line given its line number
     * @param lineNumber The line number
     * @return The line
     */
    public Pair<Integer, Line> line(int lineNumber){
        return new Pair<>(lineNumber, get(lineNumber));
    }

    /**
     * Get multiple lines, given a range of line numbers
     * For safety's sake, if the start and end are swapped, it still returns the same range
     * @param startLineNumber Starting line number
     * @param endLineNumber Ending line number
     * @return A list of lines, in order, from startLinenumber to endLineNumber
     */
    public List<Pair<Integer, Line>> lines(int startLineNumber, int endLineNumber){
        if(startLineNumber > endLineNumber){
            return lines(endLineNumber, startLineNumber);
        } else if(startLineNumber == endLineNumber){
            return Collections.singletonList(line(startLineNumber));
        } else {
            return IntStream.rangeClosed(startLineNumber, endLineNumber)
                    .mapToObj(lineNumber -> new Pair<>(lineNumber, get(lineNumber)))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Get all lines in the document
     * @return
     */
    public List<Pair<Integer, Line>> lines(){
        return IntStream.range(0, lines.size())
                .mapToObj(lineNumber -> new Pair<>(lineNumber, get(lineNumber)))
                .collect(Collectors.toList());
    }

    public void applyChanges(List<TextDocumentContentChangeEvent> changes) {
        for(TextDocumentContentChangeEvent change : changes) {
            int startLine = change.getRange().getStart().getLine();
            int endLine = change.getRange().getEnd().getLine();
            int startChar = change.getRange().getStart().getCharacter();
            int endChar = change.getRange().getEnd().getCharacter();

            //beforeLines: Lines before the modification.
            //oldLines: Lines being modified
            //afterLines: Lines after the modification
            List<Line> beforeLines, oldLines, afterLines;
            beforeLines = IntStream.range(0, startLine)
                    .mapToObj(lines::get)
                    .collect(Collectors.toList());
            oldLines = IntStream.rangeClosed(startLine, endLine)
                    .mapToObj(this::get)
                    .collect(Collectors.toList());
            afterLines = IntStream.range(endLine + 1, lines.size())
                    .mapToObj(lines::get)
                    .collect(Collectors.toList());
            List<Line> newLines;
            String text = change.getText();

            if (text.isEmpty()) {
                //Deletions
                newLines = new ArrayList<>();

                if(oldLines.isEmpty()){
                    throw new RuntimeException("There's no way this really happened, right...?");
                } else {
                    String topExistingLine = oldLines.get(0).getLine();
                    String bottomExistingLine = oldLines.get(oldLines.size() - 1).getLine();
                    String preExistingLine = (startChar == 0 ? "" : topExistingLine.substring(0, startChar));
                    String postExistingLine = (endChar == bottomExistingLine.length() ? "" : bottomExistingLine.substring(endChar));
                    if(!preExistingLine.isEmpty() || !postExistingLine.isEmpty()) {
                        newLines.add(new Line(preExistingLine + postExistingLine));
                    }
                }
            } else {
                //Additions
                newLines = getLines(change.getText())
                        .map(Line::new)
                        .collect(Collectors.toList());
                if (text.endsWith("\n")) {
                    newLines.add(new Line(""));
                }

                String oldFirstLine = oldLines.get(0).getLine();
                String newFirstLine = newLines.get(0).getLine();
                String prefix = oldFirstLine.substring(0, startChar);
                newLines.get(0).setLine(prefix + newFirstLine);

                if (newLines.size() > 0) {
                    String oldLastLine = oldLines.get(oldLines.size() - 1).getLine();
                    String newLastLine = newLines.get(newLines.size() - 1).getLine();
                    String suffix = (endChar < oldLastLine.length()) ? oldLastLine.substring(endChar) : "";
                    newLines.get(newLines.size() - 1).setLine(newLastLine + suffix);
                }
            }
            //Combine everything together again
            lines = new ArrayList<>(beforeLines.size() + newLines.size() + afterLines.size());
            lines.addAll(beforeLines);
            lines.addAll(newLines);
            lines.addAll(afterLines);
        }
    }

    public class Line {
        private String line;

        private Line(String line){
            this.line = line;
        }

        public String getLine() {
            return line;
        }

        private void setLine(String line) {
            this.line = line;
        }
    }
}
