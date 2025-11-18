package com.probjj.probjj.dao;

import com.probjj.probjj.entity.NutritionDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NutritionDataRepository extends JpaRepository<NutritionDataEntity, Long> {
    List<NutritionDataEntity> findByUserName(String userName);
}
