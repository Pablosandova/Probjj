package com.probjj.probjj.service;

import com.probjj.probjj.entity.ActividadEntity;
import com.probjj.probjj.dao.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {
    
    @Autowired
    private ActividadRepository actividadRepository;

    public List<ActividadEntity> getAllActividades() {
        return actividadRepository.findAll();
    }

    public Optional<ActividadEntity> getActividadById(Long id) {
        return actividadRepository.findById(id);
    }

    public List<ActividadEntity> getActividadByUserName(String userName) {
        return actividadRepository.findByUserName(userName);
    }

    public ActividadEntity createActividad(ActividadEntity actividad) {
        return actividadRepository.save(actividad);
    }

    public ActividadEntity updateActividad(Long id, ActividadEntity actividadDetails) {
        Optional<ActividadEntity> actividad = actividadRepository.findById(id);
        if (actividad.isPresent()) {
            ActividadEntity existing = actividad.get();
            if (actividadDetails.getUserName() != null) {
                existing.setUserName(actividadDetails.getUserName());
            }
            if (actividadDetails.getTipoActividad() != null) {
                existing.setTipoActividad(actividadDetails.getTipoActividad());
            }
            if (actividadDetails.getDuracion() != null) {
                existing.setDuracion(actividadDetails.getDuracion());
            }
            if (actividadDetails.getIntensidad() != null) {
                existing.setIntensidad(actividadDetails.getIntensidad());
            }
            if (actividadDetails.getDescripcion() != null) {
                existing.setDescripcion(actividadDetails.getDescripcion());
            }
            if (actividadDetails.getCaloriasBurnadas() != null && actividadDetails.getCaloriasBurnadas() > 0) {
                existing.setCaloriasBurnadas(actividadDetails.getCaloriasBurnadas());
            }
            return actividadRepository.save(existing);
        }
        return null;
    }

    public void deleteActividad(Long id) {
        actividadRepository.deleteById(id);
    }
}
