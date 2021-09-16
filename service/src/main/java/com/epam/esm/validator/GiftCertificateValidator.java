package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GiftCertificateValidator {
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String DESCRIPTION = "description";
    private static final Pattern NAME_REGEX = Pattern.compile("[\\w|\\s|,]{3,80}");
    private static final Pattern DESCRIPTION_REGEX = Pattern.compile("[\\w|\\s|,]{3,280}");
    private static final BigDecimal MIN_PRICE_VALUE = new BigDecimal(1.00);
    private static final BigDecimal MAX_PRICE_VALUE = new BigDecimal(100000L);
    private static final Integer MIN_DURATION_VALUE = 1;
    private static final Integer MAX_DURATION_VALUE = 5000;

    public void validate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() == null) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NAME_IS_NULL);
        }
        Matcher matcher = NAME_REGEX.matcher(giftCertificateDto.getName());
        if (!matcher.matches()) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NAME_INVALID, NAME, giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() == null) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_DESCRIPTION_IS_NULL);
        }
        matcher = DESCRIPTION_REGEX.matcher(giftCertificateDto.getDescription());
        if (!matcher.matches()) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_DESCRIPTION_INVALID, DESCRIPTION, giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() == null) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_PRICE_INVALID);
        }
        if (MIN_PRICE_VALUE.compareTo(giftCertificateDto.getPrice()) >= 0
                || MAX_PRICE_VALUE.compareTo(giftCertificateDto.getPrice()) < 0) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_PRICE_INVALID, PRICE, giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() == null) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_DURATION_IS_NULL);
        }
        if (MIN_DURATION_VALUE > giftCertificateDto.getDuration() || MAX_DURATION_VALUE < giftCertificateDto.getDuration()) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_DURATION_INVALID, DURATION, giftCertificateDto.getDuration());
        }
    }
}
