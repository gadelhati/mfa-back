//package com.auth.mfa.controller;
//
//import com.auth.mfa.persistence.GenericAuditEntity;
//import com.auth.mfa.service.ServiceInterface;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//public abstract class ControllerGeneric<T extends GenericAuditEntity, I, O> {
//
//    private final ServiceInterface<T, I, O> serviceInterface;
//
//    @PostMapping("")
//    public ResponseEntity<O> create(@RequestBody @Valid I created){
//        String localPath = this.getClass().isAnnotationPresent(RequestMapping.class) ? this.getClass().getAnnotation(RequestMapping.class).value()[0] : "";
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(localPath).toUriString());
//        return ResponseEntity.created(uri).body(serviceInterface.create(created));
//    }
//    @GetMapping("")
//    public ResponseEntity<Page<O>> retrieve(@RequestParam(name = "value", defaultValue = "", required = false) String value, Pageable pageable){
//        return ResponseEntity.ok().body(serviceInterface.retrieve(pageable, value, T));
//    }
//    @PutMapping("")
//    public ResponseEntity<O> update(@RequestBody @Valid I updated){
//        return ResponseEntity.accepted().body(serviceInterface.update(updated.getId(), updated));
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<O> delete(@PathVariable UUID id){
//        return ResponseEntity.accepted().body(serviceInterface.delete(id));
//    }
//    @DeleteMapping("")
//    public ResponseEntity<HttpStatus> delete(){
//        try {
//            serviceInterface.delete();
//            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
//        }
//    }
//}