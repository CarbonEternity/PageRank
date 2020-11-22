package com.popova.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class Page {

    @Setter
    private int id = -1;
    private final String fullUrl;
    private final String urlWithoutSchemaAndParameters;
    private final Set<Page> refersTo;
    private final Set<Page> referredBy;

    public Page(String fullUrl, String urlWithoutSchemaAndParameters) {
        this.fullUrl = fullUrl;
        this.urlWithoutSchemaAndParameters = urlWithoutSchemaAndParameters;
        this.refersTo = new HashSet<>();
        this.referredBy = new HashSet<>();
    }

    public static void linkPages(Page parentPage, Set<Page> childPages) {
//        Parent refers to child
        parentPage.refersTo.addAll(childPages);

//        Child refers to parent
        childPages.forEach(childPage -> childPage.referredBy.add(parentPage));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return urlWithoutSchemaAndParameters.equals(page.urlWithoutSchemaAndParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlWithoutSchemaAndParameters);
    }
}
