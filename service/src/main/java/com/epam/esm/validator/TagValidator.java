package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TagValidator {
    private static final String NAME = "name";
    private static final Pattern NAME_REGEX = Pattern.compile("[\\w|\\s|,]{3,80}");

    public void validate(TagDto tagDto) {
        if(tagDto.getName() == null) {
            throw new AppException(ErrorCode.TAG_NAME_IS_NULL);
        }
        Matcher matcher = NAME_REGEX.matcher(tagDto.getName());
        if (!matcher.matches()) {
            throw new AppException(ErrorCode.TAG_NAME_INVALID, NAME, tagDto.getName());
        }
    }
}
