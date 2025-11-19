package com.probjj.probjj.dao;

import com.probjj.probjj.entity.RecomendacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendacionRepository extends JpaRepository<RecomendacionEntity, Long> {
    List<RecomendacionEntity> findByUserName(String userName);
}
