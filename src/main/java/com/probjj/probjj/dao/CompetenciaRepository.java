package com.probjj.probjj.dao;

import com.probjj.probjj.entity.CompetenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompetenciaRepository extends JpaRepository<CompetenciaEntity, Long> {
    List<CompetenciaEntity> findByCategoria(String categoria);
}
