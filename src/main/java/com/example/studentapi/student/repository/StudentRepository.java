package com.example.studentapi.student.repository;

import com.example.studentapi.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>
        ,JpaSpecificationExecutor<Student>
      {
    Optional<Student> findByEmail(String email);

    Optional<Student> findByPhoneNumber(String phoneNumber);

    List<Student> findBySubjects(String subjects);

    List<Student> findByStandardId(int standardId);

}
