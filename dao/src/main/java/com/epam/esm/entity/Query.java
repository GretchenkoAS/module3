package com.epam.esm.entity;

import java.util.ArrayList;
import java.util.List;

public class Query {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificates gs";
    private static final String SQL_SELECT_BY_TAG = " JOIN tags_gift_certificates tg ON gs.id = tg.gift_certificate_id JOIN tags t ON t.id = tg.tag_id WHERE t.name = ?";
    private static final String SQL_QUERY_CONTAIN = " gs.name LIKE concat ('%', ?, '%') OR gs.description LIKE concat ('%', ?, '%')";
    private static final String SQL_QUERY_CONTAINS_WITHOUT_TAG = " WHERE";
    private static final String SQL_QUERY_CONTAINS_WITH_TAG = " AND";
    private static final String SQL_QUERY_ORDER = " ORDER BY";
    private static final String SQL_ORDER_DESC = " DESC";
    private static final String SQL_QUERY_ORDER_BY_DATE = " gs.create_date";
    private static final String SQL_QUERY_ORDER_BY_NAME = " gs.name";
    private static final String COMA_SIGN = ",";
    private static final String DESC = "DESC";
    private String tagName;
    private String contains;
    private String sortByName;
    private String sortByDate;
    private List<String> params = new ArrayList<>();
    private StringBuilder SqlQuery = new StringBuilder(SELECT_ALL_GIFT_CERTIFICATES);

    public Query() {
    }

    public Query(String tagName, String contains, String sortByName, String sortByDate) {
        this.tagName = tagName;
        this.contains = contains;
        this.sortByName = sortByName;
        this.sortByDate = sortByDate;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getSortByDate() {
        return sortByDate;
    }

    public void setSortByDate(String sortByDate) {
        this.sortByDate = sortByDate;
    }

    public String buildSqlQuery() {
        buildSelectByTag();
        buildSelectContains();
        buildSelectSortByName();
        buildSelectSortByDate();
        return SqlQuery.toString();
    }

    public Object[] getQueryParams() {
        return params.toArray();
    }

    private void buildSelectByTag() {
        if (tagName != null) {
            SqlQuery.append(SQL_SELECT_BY_TAG);
            params.add(tagName);
        }
    }

    private void buildSelectContains() {
        String sql = (tagName != null) ? SQL_QUERY_CONTAINS_WITH_TAG : SQL_QUERY_CONTAINS_WITHOUT_TAG;
        if (contains != null) {
            SqlQuery.append(sql).append(SQL_QUERY_CONTAIN);
            params.add(contains);
            params.add(contains);
        }
    }

    private void buildSelectSortByName() {
        if (sortByName != null) {
            SqlQuery.append(SQL_QUERY_ORDER);
            SqlQuery.append(SQL_QUERY_ORDER_BY_NAME);
            if (sortByName.equals(DESC)) {
                SqlQuery.append(SQL_ORDER_DESC);
            }
        }
    }

    private void buildSelectSortByDate() {
        if (sortByDate != null) {
            if (sortByName == null) {
                SqlQuery.append(SQL_QUERY_ORDER);
            } else {
                SqlQuery.append(COMA_SIGN);
            }
            SqlQuery.append(SQL_QUERY_ORDER_BY_DATE);
            if (sortByDate.equals(DESC)) {
                SqlQuery.append(SQL_ORDER_DESC);
            }
        }
    }
}
