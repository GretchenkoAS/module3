package com.epam.esm.dao.rowmapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(resultSet.getLong(ID));
        certificate.setName(resultSet.getString(NAME));
        certificate.setDescription(resultSet.getString(DESCRIPTION));
        certificate.setPrice(resultSet.getBigDecimal(PRICE));
        certificate.setDuration(resultSet.getInt(DURATION));
        certificate.setCreateDate((resultSet.getTimestamp(CREATE_DATE)).toLocalDateTime());
        certificate.setLastUpdateDate((resultSet.getTimestamp(LAST_UPDATE_DATE)).toLocalDateTime());
        return certificate;
    }
}
