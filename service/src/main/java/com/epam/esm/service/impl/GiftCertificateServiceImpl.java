package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.QueryDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.mapper.impl.QueryMapper;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.QueryValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String ID = "id";
    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateMapper mapper;
    private final TagMapper tagMapper;
    private final QueryMapper queryMapper;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagValidator tagValidator;
    private final QueryValidator queryValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService,
                                      GiftCertificateMapper mapper, TagMapper tagMapper, QueryMapper queryMapper,
                                      GiftCertificateValidator giftCertificateValidator,
                                      TagValidator tagValidator, QueryValidator queryValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.mapper = mapper;
        this.tagMapper = tagMapper;
        this.queryMapper = queryMapper;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagValidator = tagValidator;
        this.queryValidator = queryValidator;
    }

    @Transactional
    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.validate(giftCertificateDto);
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        GiftCertificate giftCertificateInDb = giftCertificateDao.add(giftCertificate);
        return mapper.mapToDto(giftCertificateInDb);
    }

    @Transactional
    @Override
    public GiftCertificateDto find(Long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findOne(id);
        if (giftCertificateOptional.isEmpty()) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, ID, id);
        }
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        return mapper.mapToDto(giftCertificate);
    }

    @Transactional
    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        return mapper.mapToListDto(giftCertificates);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (giftCertificateDao.findOne(id).isEmpty()) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NO_CONTENT, ID, id);
        }
        giftCertificateDao.delete(id);
    }

    @Override
    public boolean exist(GiftCertificateDto giftCertificateDto, Long id) {
        Optional<GiftCertificate> certificateOptional = giftCertificateDao.findOne(id);
        if (certificateOptional.isEmpty()) {
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public GiftCertificateDto patch(GiftCertificateDto giftCertificateDto, Long id) {
        if (!exist(giftCertificateDto, id)) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, ID, id);
        }
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        GiftCertificate giftCertificateInDb = giftCertificateDao.findOne(id).get();
        giftCertificateDao.detach(giftCertificateInDb);
        if (giftCertificateDto.getName() != null) {
            giftCertificateInDb.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            giftCertificateInDb.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            giftCertificateInDb.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            giftCertificateInDb.setDuration(giftCertificateDto.getDuration());
        }
        giftCertificateValidator.validate(mapper.mapToDto(giftCertificateInDb));
        if (giftCertificateDto.getTags() != null) {
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                tagValidator.validate(tagDto);
            }
            giftCertificateInDb.setTags(giftCertificate.getTags());
        }
        giftCertificateInDb = giftCertificateDao.update(giftCertificateInDb);
        return mapper.mapToDto(giftCertificateInDb);
    }

    @Transactional
    @Override
    public List<GiftCertificateDto> findByQuery(QueryDto queryDto) {
        queryValidator.validate(queryDto);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByQuery(queryMapper.mapToEntity(queryDto));
        giftCertificates = giftCertificates.stream()
                .distinct().collect(Collectors.toList());
        for (GiftCertificate giftCertificate : giftCertificates) {
            Set<Tag> tags = giftCertificateDao.getTags(giftCertificate.getId());
            giftCertificate.setTags(tags);
        }
        return mapper.mapToListDto(giftCertificates);
    }

    @Transactional
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id) {
        if (!exist(giftCertificateDto, id)) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, ID, id);
        }
        giftCertificateValidator.validate(giftCertificateDto);
        giftCertificateDto.setId(id);
        GiftCertificate giftCertificateInDb = giftCertificateDao.update(mapper.mapToEntity(giftCertificateDto));
        return mapper.mapToDto(giftCertificateInDb);
    }

}
