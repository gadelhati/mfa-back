package com.auth.mfa.controller;

import com.auth.mfa.MfaApplication;
import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.persistence.payload.request.DTORequestUserPassword;
import com.auth.mfa.persistence.payload.request.DTORequestUserTOTP;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import com.auth.mfa.service.ServiceUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/user") @RequiredArgsConstructor
public class ControllerUser {

    private final ServiceUser serviceUser;
    private final static Logger LOGGER = LoggerFactory.getLogger(MfaApplication.class);

    @PostMapping("")
    public @ResponseBody DTOResponseUser create(@Valid @RequestBody DTORequestUser dtoRequestUser) {
        LOGGER.info("An INFO Message "+dtoRequestUser.getUsername()+" CREATE AN USER");
        return serviceUser.create(dtoRequestUser);
    }
    @GetMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<Page<DTOResponseUser>> retrieve(@RequestParam(name="value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceUser.retrieve(pageable, value, User.class));
    }
    @PutMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseUser> update(@RequestBody @Valid DTORequestUser updated){
        return ResponseEntity.accepted().body(serviceUser.update(updated.getId(), updated));
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseUser> delete(@PathVariable("id") UUID id){
        return ResponseEntity.accepted().body(serviceUser.delete(id));
    }
//    @DeleteMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
//    public ResponseEntity<HttpStatus> delete(){
//        try {
//            serviceUser.delete();
//            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
//        }
//    }
    @PutMapping("/changePassword")
    public ResponseEntity<DTOResponseUser> changePassword(@RequestBody @Valid DTORequestUserPassword updated){
        try {
            return new ResponseEntity<>(serviceUser.changePassword(updated), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/totp") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public String resetTOTP(@Valid @RequestBody DTORequestUserTOTP userTOTP) {
        return serviceUser.resetTOTP(userTOTP.getUsername());
    }
}