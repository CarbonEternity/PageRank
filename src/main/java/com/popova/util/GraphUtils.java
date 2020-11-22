package com.popova.util;

import com.popova.dto.graph.GraphData;
import com.popova.dto.graph.GraphEdge;
import com.popova.dto.graph.GraphNode;
import com.popova.entity.Page;

import java.util.HashSet;
import java.util.Set;

public final class GraphUtils {

    private GraphUtils() {
    }

    public static GraphData createGraph(Set<Page> pages) {
        Set<GraphNode> graphNodes = new HashSet<>();
        for (Page page : pages) {
            graphNodes.add(new GraphNode(
                    page.getId(),
                    getPageNameFromUrl(page.getUrlWithoutSchemaAndParameters()),
                    page.getUrlWithoutSchemaAndParameters()
            ));
        }

        Set<GraphEdge> graphEdges = new HashSet<>();
        for (Page page : pages) {
            for (Page innerPages : page.getRefersTo()) {
                graphEdges.add(new GraphEdge(page.getId(), innerPages.getId()));
            }
        }

        return new GraphData(graphNodes, graphEdges);
    }

    private static String getPageNameFromUrl(String rawUrl) {
        String pageName = UrlUtils.trimParametersAndLastSlash(rawUrl);
        pageName = pageName.substring(pageName.lastIndexOf("/"));
        pageName = pageName.substring(Math.min(pageName.length(), 1), Math.min(pageName.length(), 7));
        return pageName;
    }
}
