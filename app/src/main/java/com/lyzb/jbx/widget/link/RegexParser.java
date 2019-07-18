package com.lyzb.jbx.widget.link;

import android.util.Patterns;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/8 20:15
 */

public class RegexParser {
    static final String PHONE_PATTERN = Patterns.PHONE.pattern();
    static final String EMAIL_PATTERN = Patterns.EMAIL_ADDRESS.pattern();
    static final String HASHTAG_PATTERN = "(?:^|\\s|$)#[\\p{L}0-9_]*";
    static final String MENTION_PATTERN = "(?:^|\\s|$|[.])@[\\p{L}0-9_]*";
    static final String URL_PATTERN = "(^|[\\s.:;?\\-\\]<\\(])" +
            "((https|http?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#](\\(\\))?)" +
            "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])";
}
