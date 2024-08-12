package com.example.studentapi.student.specifications;
import com.example.studentapi.student.entity.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import com.example.studentapi.utils.DateUtils;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {
//    public static Specification<Student> createdOnBetween(Timestamp startDate, Timestamp endDate) {
//        return (Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
//            if (startDate == null || endDate == null) {
//                throw new IllegalArgumentException("Start date and end date must not be null");
//            }
//
//            return criteriaBuilder.between(root.get("createdOn"), startDate, endDate);
//        };
//    }
    public static Specification<Student> createdOnBetween(String startDateStr, String endDateStr,  String searchString,Integer standardId) {
        return (Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (startDateStr != null && !startDateStr.isEmpty() && endDateStr != null && !endDateStr.isEmpty()) {
                predicates.add(criteriaBuilder.between(root.get("createdOn"),
                        DateUtils.fromDateToTimestamp(startDateStr),
                        DateUtils.toDateToTimestamp(endDateStr)));
            }
            if(searchString!=null && !searchString.isEmpty()){
                String searchPattern = "%" + searchString.toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern);
                Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchPattern);
                Predicate addressPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), searchPattern);
                Predicate phoneNumberPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), searchPattern);
                Predicate schoolPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("school")), searchPattern);
                Predicate subjectsPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("subjects")), searchPattern);
                predicates.add(criteriaBuilder.or(namePredicate, emailPredicate, addressPredicate, phoneNumberPredicate, schoolPredicate, subjectsPredicate));
            }
            if (standardId != null) {
                predicates.add(criteriaBuilder.equal(root.get("standard").get("id"), standardId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
