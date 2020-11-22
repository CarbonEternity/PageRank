package com.popova.controller;

import com.popova.dto.RequestDataInput;
import com.popova.dto.Result;
import com.popova.dto.graph.GraphData;
import com.popova.dto.pagerank.PageRank;
import com.popova.entity.Page;
import com.popova.service.page.PageLinkObtainService;
import com.popova.service.page.PageRankCalculatorService;
import com.popova.util.GraphUtils;
import com.popova.util.PageRankUtils;
import com.popova.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Validated
@RestController
@RequiredArgsConstructor
public class PageRankController {

    private final PageLinkObtainService pageLinkObtainService;
    private final PageRankCalculatorService pageRankCalculatorService;

    @PostMapping("/pageRank")
    public Result pageRankProcess(@Valid @RequestBody RequestDataInput requestDataInput) {
        Set<Page> pages = pageLinkObtainService.getPages(requestDataInput.getUrl(),
                                                                    requestDataInput.getMaxPages() == 0
                                                                    ? Integer.MAX_VALUE
                                                                    : requestDataInput.getMaxPages());
        PageUtils.removeSelfLinks(pages);
        PageUtils.numberPagesGraph(pages);

        GraphData graphData = GraphUtils.createGraph(pages);
        Map<Page, Double> pageRanksMap = pageRankCalculatorService.calculatePageRanks(pages, 0.5);
        List<PageRank> pageRanksToView = PageRankUtils.convertSortAndLimit(pageRanksMap, 10);

        return new Result(graphData, pageRanksToView);
    }
}
