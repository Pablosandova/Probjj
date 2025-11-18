package com.probjj.probjj.dao;

import com.probjj.probjj.entity.ActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadEntity, Long> {
    List<ActividadEntity> findByUserName(String userName);
}
