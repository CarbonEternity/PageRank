package com.popova.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BufferizedPageCreator {

    @Getter
    private Map<String, Page> bufferedPages = new HashMap<>();

    public Page createPage(String fullUrl, String urlWithoutParametersAndTrailingSlash) {
        Page page = bufferedPages.get(urlWithoutParametersAndTrailingSlash);
        if (page == null) {
            page = new Page(fullUrl, urlWithoutParametersAndTrailingSlash);
            bufferedPages.put(urlWithoutParametersAndTrailingSlash, page);
        }
        return page;
    }
}
