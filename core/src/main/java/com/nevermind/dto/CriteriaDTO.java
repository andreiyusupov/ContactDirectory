package com.nevermind.dto;

import com.nevermind.criteria.Criterion;

import java.util.List;

public class CriteriaDTO {
    private List<Criterion> criteria;

    public List<Criterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criterion> criteria) {
        this.criteria = criteria;
    }
}
