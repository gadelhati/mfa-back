package com.auth.mfa.controller;

import com.auth.mfa.persistence.model.Privilege;
import com.auth.mfa.persistence.payload.request.DTORequestPrivilege;
import com.auth.mfa.persistence.payload.response.DTOResponsePrivilege;
import com.auth.mfa.service.ServicePrivilege;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.UUID;

@RestController @RequestMapping("/privilege") @RequiredArgsConstructor
public class ControllerPrivilege implements ControllerInterfaceLegacy<DTOResponsePrivilege, DTORequestPrivilege> {

    private final ServicePrivilege servicePrivilege;

    @PostMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<DTOResponsePrivilege> create(@RequestBody @Valid DTORequestPrivilege created){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role").toUriString());
        return ResponseEntity.created(uri).body(servicePrivilege.create(created));
    }
    @GetMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<Page<DTOResponsePrivilege>> retrieve(@RequestParam(name="value", defaultValue = "", required = false) String value, Pageable pageable) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<Privilege> role = Privilege.class;
        return ResponseEntity.ok().body(servicePrivilege.retrieve(pageable, value, role));
    }
    @PutMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<DTOResponsePrivilege> update(@RequestBody @Valid DTORequestPrivilege updated){
        return ResponseEntity.accepted().body(servicePrivilege.update(updated.getId(), updated));
    }
    @DeleteMapping("/{id}") @Override @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<DTOResponsePrivilege> delete(@PathVariable("id") UUID id){
        return ResponseEntity.accepted().body(servicePrivilege.delete(id));
    }
    @DeleteMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<HttpStatus> delete(){
        try {
            servicePrivilege.delete();
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }
}