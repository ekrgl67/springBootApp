package com.springboot.acc.controller;

import com.springboot.acc.dto.*;
import com.springboot.acc.service.AccAccountActivityService;
import com.springboot.acc.service.AccAccountService;
import com.springboot.acc.service.AccMoneyTransferService;
import com.springboot.gen.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccAccountController {

    private final AccAccountService accAccountService;
    private final AccMoneyTransferService accMoneyTransferService;
    private final AccAccountActivityService accAccountActivityService;

    @GetMapping
    public ResponseEntity findAll() {
        List<AccAccountDto> accountDtoList = accAccountService.findAll();
        return ResponseEntity.ok(RestResponse.of(accountDtoList));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        AccAccountDto accAccountDto = accAccountService.findById(id);
        return  ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AccAccountSaveRequestDto accAccountSaveRequestDto) {
        AccAccountDto accAccountDto = accAccountService.save(accAccountSaveRequestDto);
        return ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @PatchMapping("/cancel{id}")
    public ResponseEntity cancel(@PathVariable Long id) {
        accAccountService.cancel(id);
        return ResponseEntity.ok(RestResponse.empty());
    }

    @PostMapping("/money-transfer")
    public ResponseEntity transferMoney(@RequestBody AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto) {
        AccMoneyTransferDto accMoneyTransferDto = accMoneyTransferService.transferMoney(accMoneyTransferSaveRequestDto);
        return ResponseEntity.ok(RestResponse.of(accMoneyTransferDto));
    }

    @PostMapping("/withDraw")
    public ResponseEntity withDraw(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto) {
        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.withDraw(accMoneyActivityRequestDto);
        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }

    @PostMapping("/depozit")
    public ResponseEntity depozit(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto) {
        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.depozit(accMoneyActivityRequestDto);
        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }
}
