package com.example.studentapi.teacher.repository;

import com.example.studentapi.standard.entity.Standard;
import com.example.studentapi.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findByStandard(Standard standard);
}
