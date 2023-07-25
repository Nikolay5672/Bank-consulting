package com.summerpractice.bankconsulting.repository;

import com.summerpractice.bankconsulting.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Integer> {
}
