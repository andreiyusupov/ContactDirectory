package com.nevermind.parser;

import com.nevermind.criteria.Criterion;
import com.nevermind.dto.CriteriaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CriteriaComposer {

    public static CriteriaDTO generateCriteriaDTO(Map<String, String[]> criteria) {
        CriteriaDTO criteriaDTO = new CriteriaDTO();
        List<Criterion> criteriaList = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : criteria.entrySet()) {
            if (entry.getKey().equals("current-page") || entry.getKey().equals("page-limit")) {
                continue;
            }
            Criterion criterion = new Criterion();
            criterion.setField(entry.getKey());
            criterion.setValue(entry.getValue());
            criteriaList.add(criterion);
        }
        criteriaDTO.setCriteria(criteriaList);
        return criteriaDTO;
    }
}
