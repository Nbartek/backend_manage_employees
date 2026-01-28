package org.example.lab5;

import from_lab_2.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepo extends JpaRepository<Rate,Long> {
}
