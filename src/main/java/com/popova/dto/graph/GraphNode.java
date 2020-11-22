package com.popova.dto.graph;

import lombok.Data;

import java.util.Objects;

@Data
public class GraphNode {

    private final int id;
    private final String label;
    private final String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return id == graphNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
