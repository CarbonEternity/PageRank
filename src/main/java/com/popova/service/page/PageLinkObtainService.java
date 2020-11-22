package com.popova.service.page;

import com.popova.entity.BufferizedPageCreator;
import com.popova.entity.Page;
import com.popova.service.url.UrlExtractorService;
import com.popova.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageLinkObtainService {

    private final PageContentLoaderService pageContentLoaderService;
    private final UrlExtractorService linksExtractorService;

    public Set<Page> getPages(String startUrl, int maxPages) {
        BufferizedPageCreator pageCreator = new BufferizedPageCreator();
        Set<Page> processedPages = new HashSet<>();
        Set<Page> pageForProcessing = new HashSet<>();

        String citeRootUrl = UrlUtils.getRootUrl(startUrl);
        Page startPage = pageCreator.createPage(
                startUrl,
                UrlUtils.trimParametersAndLastSlash(startUrl)
        );
        pageForProcessing.add(startPage);

        while (!pageForProcessing.isEmpty() && processedPages.size() < maxPages) {
            Iterator<Page> iterator = pageForProcessing.iterator();
            Page page = iterator.next();
            iterator.remove();
            processedPages.add(page);

            Set<Page> childrenPages = getChildrenPages(page, citeRootUrl, pageCreator);
            Page.linkPages(page, childrenPages);
            addPageForProcessing(childrenPages, pageForProcessing, processedPages);
        }

        return new HashSet<>(
                pageCreator.getBufferedPages().values()
        );
    }

    private Set<Page> getChildrenPages(Page page, String citeRootUrl, BufferizedPageCreator pageCreator) {
        String content = pageContentLoaderService.loadPageContent(page.getFullUrl());
        Set<String> childrenRawUrls = linksExtractorService.extractUrls(content);

        return childrenRawUrls.stream()
                .map(url -> UrlUtils.getAbsoluteUrl(page.getFullUrl(), url))
                .filter(url -> UrlUtils.isChildrenRootUrlSimilarToGeneralRootUrl(url, citeRootUrl))
                .map(url -> pageCreator.createPage(
                        url,
                        UrlUtils.trimParametersAndLastSlash(url)
                ))
                .collect(Collectors.toSet());
    }

    private void addPageForProcessing(Set<Page> childrenPages,
                                      Set<Page> pageForProcessing,
                                      Set<Page> processedPages) {
        for (Page page : childrenPages) {
            if (!processedPages.contains(page)) {
                pageForProcessing.add(page);
            }
        }
    }
}
