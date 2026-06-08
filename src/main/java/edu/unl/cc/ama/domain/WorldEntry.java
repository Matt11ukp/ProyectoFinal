package edu.unl.cc.ama.domain;

public record WorldEntry(String kind, int col, int row, String extra) {

    public WorldEntry(String kind, int col, int row) {
        this(kind, col, row, "");
    }

    public boolean hasExtra() {
        return extra != null && !extra.isBlank();
    }
}
