package com.popova.util;

import com.popova.dto.pagerank.PageRank;
import com.popova.entity.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class PageRankUtils {

    private PageRankUtils() {
    }

    public static List<PageRank> convertSortAndLimit(Map<Page, Double> pageRank, int limit) {
        return pageRank.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(entry -> new PageRank(entry.getKey().getUrlWithoutSchemaAndParameters(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
