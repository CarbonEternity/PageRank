package com.popova.util;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;


public final class UrlUtils {

    private UrlUtils(){

    }

    public static String getRootUrl(String rawUrl) {
        String scheme = rawUrl.startsWith("https://")
                ? "https://"
                : "http://";

        String rootUrl = replaceHttpToEmpty(rawUrl);
        rootUrl = trimParametersAndLastSlash(rootUrl);
        rootUrl = rootUrl.split("/", 2)[0];
        return scheme + rootUrl;
    }

    private static String replaceHttpToEmpty(String rawUrl) {
        return rawUrl.replaceAll("http://|https://", "");
    }

    public static String trimParametersAndLastSlash(String rawUrl) {
        String url = rawUrl.split("#", 2)[0];
        url = url.split("\\?", 2)[0];
        url = StringUtils.stripEnd(url, "/");
        return url;
    }

    public static String getAbsoluteUrl(String baseUrlString, String urlString) {
        try {
            if (urlString == null || urlString.trim().length() == 0) {
                urlString = "";
            }
            URL baseUrl = new URL(baseUrlString);
            URL url = new URL(baseUrl, urlString);

            urlString = url.toString().replaceAll("\\\\+", "/");
            url = new URL(urlString);
            String uri = url.getPath();
            String uriString = uri.replaceAll("/+", "/");
            urlString = url.toString().replaceAll(uri, uriString);
            int index = urlString.indexOf("/..");
            if (index < 0) {
                return urlString;
            }
            String urlStringLeft = urlString.substring(0, index) + "/";
            String urlStringRight = urlString.substring(index + 1);
            return getAbsoluteUrl(urlStringLeft, urlStringRight);

        } catch (MalformedURLException e) {
            return "";
        }
    }

    public static boolean isChildrenRootUrlSimilarToGeneralRootUrl(String rawUrl, String rootUrl) {
        boolean startWith = rawUrl.startsWith(rootUrl);
        if (startWith) {
            if (rawUrl.length() <= rootUrl.length()) {
                return true;
            }

            String delimiter = rawUrl.substring(rootUrl.length(), rootUrl.length() + 1);
            return delimiter.equals("/") || delimiter.equals("#") || delimiter.equals("?");
        }
        return false;
    }
}
