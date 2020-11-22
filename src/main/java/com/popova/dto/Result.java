package com.popova.dto;

import com.popova.dto.graph.GraphData;
import com.popova.dto.pagerank.PageRank;
import lombok.Data;

import java.util.List;

@Data
public class Result {

    private final GraphData graphData;
    private final List<PageRank> pageRanks;
}
