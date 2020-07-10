package com.nevermind.criteria;

import java.util.ArrayList;
import java.util.List;

public class CriterionParser {

    public static List<String[]> parseCriterion(List<Criterion> criteria) {
        List<String[]> criterionList = new ArrayList<>();
        for (Criterion criterion : criteria) {
            String field = criterion.getField();
            String value = criterion.getValue()[0];
            String[] parsedCriterion = switch (field) {
                case "first-name" -> new String[]{"contacts", "first_name", "=", "'" + value + "'"};
                case "middle-name" -> new String[]{"contacts", "middle_name", "=", "'" + value + "'"};
                case "last-name" -> new String[]{"contacts", "last_name", "=", "'" + value + "'"};
                case "date-of-birth-before" -> new String[]{"contacts", "date_of_birth", "<", "'" + value + "'"};
                case "date-of-birth-after" -> new String[]{"contacts", "date_of_birth", ">", "'" + value + "'"};
                case "gender" -> new String[]{"contacts", "gender", "=", "'" + value + "'"};
                case "marital-status" -> new String[]{"contacts", "marital_status", "=", "'" + value + "'"};
                case "citizenship" -> new String[]{"contacts", "citizenship", "=", "'" + value + "'"};
                case "country" -> new String[]{"addresses", "country", "=", "'" + value + "'"};
                case "city" -> new String[]{"addresses", "city", "=", "'" + value + "'"};
                case "street" -> new String[]{"addresses", "street", "=", "'" + value + "'"};
                case "house-number" -> new String[]{"addresses", "house_number", "=", "'" + value + "'"};
                case "apartment-number" -> new String[]{"addresses", "apartment_number", "=", "'" + value + "'"};
                case "postcode" -> new String[]{"addresses", "postcode", "=", "'" + value + "'"};
                default -> throw new IllegalStateException("Unexpected value: " + field);
            };
            criterionList.add(parsedCriterion);
        }
        return criterionList;
    }
}
