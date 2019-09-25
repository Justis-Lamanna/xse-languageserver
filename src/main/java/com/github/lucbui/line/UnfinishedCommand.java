package com.github.lucbui.line;

public class UnfinishedCommand {
    private int actualNumberOfParams;
    private int expectedNumberOfParams;

    public int getActualNumberOfParams() {
        return actualNumberOfParams;
    }

    public void setActualNumberOfParams(int actualNumberOfParams) {
        this.actualNumberOfParams = actualNumberOfParams;
    }

    public int getExpectedNumberOfParams() {
        return expectedNumberOfParams;
    }

    public void setExpectedNumberOfParams(int expectedNumberOfParams) {
        this.expectedNumberOfParams = expectedNumberOfParams;
    }
}
