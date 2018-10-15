package me.hjc.finance.controller;

import me.hjc.finance.consts.RegConst;
import me.hjc.finance.service.IDividendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("dividend-info/v1")
@Validated
public class StockDividendRest {

    @Autowired
    IDividendService dividendService;

    @GetMapping("/dividend")
    public ResponseEntity<?> dividendsList(@Valid @Pattern(regexp = RegConst.CODE_REG) @RequestParam(value = "code") String code) {
        return dividendService.getDividendByCode(code).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
}
