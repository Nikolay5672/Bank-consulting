package com.summerpractice.bankconsulting.repository;

import com.summerpractice.bankconsulting.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Services, Integer> {

}
