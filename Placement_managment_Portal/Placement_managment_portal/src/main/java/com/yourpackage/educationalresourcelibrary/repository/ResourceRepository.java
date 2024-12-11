package com.yourpackage.educationalresourcelibrary.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yourpackage.educationalresourcelibrary.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
