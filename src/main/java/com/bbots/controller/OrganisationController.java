package com.bbots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bbots.model.Organisation;
import com.bbots.service.OrganisationService;
import com.bbots.service.AuthorizationProcedureService;

import java.util.Map;

@RestController
@RequestMapping("/api/organisations")
@CrossOrigin(origins = "*")
public class OrganisationController {

    @Autowired
    private OrganisationService service;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    // GET all organisations with pagination
    @GetMapping
    public Map<String, Object> getAllOrganisations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getPaginatedOrganisations(page, size);
    }

    // GET single organisation by orgcode
    @GetMapping("/{orgcode}")
    public Organisation getOrganisation(@PathVariable Long orgcode) {
        return service.getOrganisationById(orgcode);
    }

    // POST - create new organisation (goes through authorization)
    @PostMapping
    public void createOrganisation(@RequestBody Organisation organisation) {
        // Use ORG-CRT as programId and ORG001 as tableName for the authorization procedure
        authProcedureService.processAuthorization(organisation.getOrgcode(), "ORG-CRT", "ORG001", organisation);
    }

    // PUT - update existing organisation
    @PutMapping
    public void updateOrganisation(@RequestBody Organisation organisation) {
        service.updateOrganisation(organisation);
    }

    // DELETE - delete organisation by orgcode
    @DeleteMapping("/{orgcode}")
    public void deleteOrganisation(@PathVariable Long orgcode) {
        service.deleteOrganisation(orgcode);
    }
}
