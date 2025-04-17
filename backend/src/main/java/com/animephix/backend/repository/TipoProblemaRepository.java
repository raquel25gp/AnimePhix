package com.animephix.backend.repository;

import com.animephix.backend.model.TipoProblema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProblemaRepository extends JpaRepository<TipoProblema, Long> {
}
