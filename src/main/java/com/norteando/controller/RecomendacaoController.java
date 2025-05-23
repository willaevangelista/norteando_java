package com.norteando.controller;

import com.norteando.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping("/{idAtracao}")
    public ResponseEntity<Map<String, Object>> buscarRecomendacoesProximas(
            @PathVariable Long idAtracao,
            @RequestParam(defaultValue = "5") double raio) {
        
        Map<String, Object> recomendacoes = recomendacaoService
            .buscarRecomendacoesProximasPorAtracao(idAtracao, raio);
        
        return ResponseEntity.ok(recomendacoes);
    }
}