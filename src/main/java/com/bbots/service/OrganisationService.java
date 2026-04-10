package com.bbots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.model.Organisation;
import com.bbots.repository.OrganisationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganisationService {

    @Autowired
    private OrganisationRepository repository;

    public List<Organisation> getAllOrganisations() {
        return repository.findAll();
    }

    public Map<String, Object> getPaginatedOrganisations(int page, int size) {
        int limit = size;
        int offset = page * size;

        List<Organisation> organisations = repository.findAll(limit, offset);
        long totalElements = repository.count();

        Map<String, Object> response = new HashMap<>();
        response.put("content", organisations);
        response.put("totalElements", totalElements);

        return response;
    }

    public Organisation getOrganisationById(Long orgcode) {
        return repository.findById(orgcode);
    }

    public void createOrganisation(Organisation organisation) {
        // Handle logo upload if needed before saving
        // if (organisation.getLogo() != null && organisation.getLogo().startsWith("data:")) {
        //    String ftpPath = uploadBase64ToFtp(organisation.getLogo());
        //    organisation.setLogo(ftpPath);
        // }
        repository.save(organisation);
    }

    public void updateOrganisation(Organisation organisation) {
        repository.update(organisation);
    }

    public void deleteOrganisation(Long orgcode) {
        repository.delete(orgcode);
    }

    private String uploadBase64ToFtp(String base64) {
        // Placeholder for FTP upload logic
        // 1. Decode base64 
        // 2. Connect to FTP
        // 3. Upload file
        // 4. Return the path
        return "/ftp/logos/org_" + System.currentTimeMillis() + ".png";
    }
}
