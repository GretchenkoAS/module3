package com.epam.esm.dto;

public class QueryDto {
    private String tagName;
    private String contains;
    private String sortByName;
    private String sortByDate;

    public QueryDto() {
    }

    public QueryDto(String tagName, String contains, String sortByName, String sortByDate) {
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
}
