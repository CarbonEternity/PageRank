package com.popova.service.url;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UrlExtractorService {

    private static final String HREF_LINK_REGEX = "(?<=href=')[^']++(?=')|(?<=href=\")[^\"]++(?=\")";
    private static final Pattern HREF_LINK_PATTERN = Pattern.compile(HREF_LINK_REGEX, Pattern.CASE_INSENSITIVE);

    public Set<String> extractUrls(String content) {
        Matcher matcher = HREF_LINK_PATTERN.matcher(content);
        Set<String> resultUrls = new HashSet<>();

        while (matcher.find()) {
            resultUrls.add(matcher.group(0));
        }

        return resultUrls;
    }
}
