package com.springboot.cus.controller;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.springboot.cus.dto.CusCustomerDto;
import com.springboot.cus.dto.CusCustomerSaveRequestDto;
import com.springboot.cus.dto.CusCustomerUpdateRequestDto;
import com.springboot.cus.service.CusCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CusCustomerController {

    @Autowired
    private CusCustomerService cusCustomerService;

    @GetMapping
    public ResponseEntity findAll() {
        List<CusCustomerDto> customerDtoList = cusCustomerService.findAll();
        return new ResponseEntity(customerDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        CusCustomerDto customerDto = cusCustomerService.findById(id);
        return new ResponseEntity(customerDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {
        CusCustomerDto cusCustomerDto = cusCustomerService.save(cusCustomerSaveRequestDto);

        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).findById(cusCustomerDto.getId()));

        EntityModel entityModel = EntityModel.of(cusCustomerDto);
        entityModel.add(link.withRel("find-by-id"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return new ResponseEntity(mappingJacksonValue, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        cusCustomerService.delete(id);
        return ResponseEntity.ok(Void.TYPE);
    }

//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        cusCustomerService.delete(id);
//    }

    @PutMapping
    public ResponseEntity update(@RequestBody CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto) {
        CusCustomerDto cusCustomerDto = cusCustomerService.update(cusCustomerUpdateRequestDto);
        return new ResponseEntity(cusCustomerDto, HttpStatus.OK);
    }
}
