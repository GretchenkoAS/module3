package com.epam.esm.validator;

import com.epam.esm.dto.QueryDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QueryValidator {
    private static final String TAG_NAME = "tagName";
    private static final String CONTAINS = "contains";
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_DATE = "sortByDate";
    private static final Pattern TAG_NAME_REGEX = Pattern.compile("[\\w|\\s|,]{3,30}");
    private static final Pattern CONTAINS_REGEX = Pattern.compile("[\\w|\\s|,]{1,80}");
    private static final Pattern SORT_REGEX = Pattern.compile("ASC|DESC");

    public void validate(QueryDto queryDto) {
        Matcher matcher;
        if (queryDto.getTagName() != null) {
            matcher = TAG_NAME_REGEX.matcher(queryDto.getTagName());
            if (!matcher.matches()) {
                throw new AppException(ErrorCode.TAG_NAME_INVALID, TAG_NAME, queryDto.getTagName());
            }
        }
        if (queryDto.getContains() != null) {
            matcher = CONTAINS_REGEX.matcher(queryDto.getContains());
            if (!matcher.matches()) {
                throw new AppException(ErrorCode.QUERY_CONTAINS_INVALID, CONTAINS, queryDto.getContains());
            }
        }
        if (queryDto.getSortByName() != null) {
            matcher = SORT_REGEX.matcher(queryDto.getSortByName());
            if (!matcher.matches()) {
                throw new AppException(ErrorCode.QUERY_SORT_BY_NAME_INVALID, SORT_BY_NAME, queryDto.getSortByName());
            }
        }
        if (queryDto.getSortByDate() != null) {
            matcher = SORT_REGEX.matcher(queryDto.getSortByDate());
            if (!matcher.matches()) {
                throw new AppException(ErrorCode.QUERY_SORT_BY_DATE_INVALID, SORT_BY_NAME, queryDto.getSortByDate());
            }
        }
    }
}
