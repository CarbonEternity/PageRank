package com.popova.dto.graph;

import lombok.Data;

import java.util.Objects;

@Data
public class GraphEdge {

    private final int from;
    private final int to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphEdge graphEdge = (GraphEdge) o;
        return from == graphEdge.from &&
                to == graphEdge.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
