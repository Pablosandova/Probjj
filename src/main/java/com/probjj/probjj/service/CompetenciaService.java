package com.probjj.probjj.service;

import com.probjj.probjj.entity.CompetenciaEntity;
import com.probjj.probjj.dao.CompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompetenciaService {
    
    @Autowired
    private CompetenciaRepository competenciaRepository;

    public List<CompetenciaEntity> getAllCompetencias() {
        return competenciaRepository.findAll();
    }

    public Optional<CompetenciaEntity> getCompetenciaById(Long id) {
        return competenciaRepository.findById(id);
    }

    public List<CompetenciaEntity> getCompetenciaByCategoria(String categoria) {
        return competenciaRepository.findByCategoria(categoria);
    }

    public CompetenciaEntity createCompetencia(CompetenciaEntity competencia) {
        return competenciaRepository.save(competencia);
    }

    public CompetenciaEntity updateCompetencia(Long id, CompetenciaEntity competenciaDetails) {
        Optional<CompetenciaEntity> competencia = competenciaRepository.findById(id);
        if (competencia.isPresent()) {
            CompetenciaEntity existing = competencia.get();
            if (competenciaDetails.getNombre() != null) {
                existing.setNombre(competenciaDetails.getNombre());
            }
            if (competenciaDetails.getUbicacion() != null) {
                existing.setUbicacion(competenciaDetails.getUbicacion());
            }
            if (competenciaDetails.getCategoria() != null) {
                existing.setCategoria(competenciaDetails.getCategoria());
            }
            if (competenciaDetails.getFechaCompetencia() != null) {
                existing.setFechaCompetencia(competenciaDetails.getFechaCompetencia());
            }
            if (competenciaDetails.getResultados() != null) {
                existing.setResultados(competenciaDetails.getResultados());
            }
            if (competenciaDetails.getNotas() != null) {
                existing.setNotas(competenciaDetails.getNotas());
            }
            return competenciaRepository.save(existing);
        }
        return null;
    }

    public void deleteCompetencia(Long id) {
        competenciaRepository.deleteById(id);
    }
}
