package com.yourpackage.educationalresourcelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yourpackage.educationalresourcelibrary.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
