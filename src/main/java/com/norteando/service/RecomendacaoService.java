package com.norteando.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.norteando.model.Atracao;
import com.norteando.model.Hospedagem;
import com.norteando.model.Restaurante;
import com.norteando.repository.AtracaoRepository;
import com.norteando.repository.HospedagemRepository;
import com.norteando.repository.RestauranteRepository;

@Service
public class RecomendacaoService {
    
    private static final int RECOMENDACOES_MAXIMAS = 3;
    private static final int RAIO_TERRA_KM = 6371;
    
    @Autowired
    private AtracaoRepository atracaoRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private HospedagemRepository hospedagemRepository;

    // Método principal para buscar recomendações por atração
    @Cacheable(value = "recomendacoesPorAtracao", key = "{#idAtracao, #raio}")
    public Map<String, Object> buscarRecomendacoesProximasPorAtracao(Long idAtracao, double raio) {
        // Busca a atração de referência
        Optional<Atracao> atracaoOptional = atracaoRepository.findById(idAtracao);
        
        if (!atracaoOptional.isPresent()) {
            return criarRespostaVazia();
        }
        
        Atracao atracaoReferencia = atracaoOptional.get();
        double latitude = atracaoReferencia.getLatitude();
        double longitude = atracaoReferencia.getLongitude();
        
        // Busca as recomendações próximas
        Map<String, Object> recomendacoes = new HashMap<>();
        
        recomendacoes.put("atracaoReferencia", criarMapAtracao(atracaoReferencia));
        recomendacoes.put("atracoesProximas", buscarAtracoesProximasComDistancia(latitude, longitude, raio, idAtracao));
        recomendacoes.put("restaurantesProximos", buscarRestaurantesProximosComDistancia(latitude, longitude, raio));
        recomendacoes.put("hospedagensProximas", buscarHospedagensProximasComDistancia(latitude, longitude, raio));
        
        return recomendacoes;
    }

    // Cálculo da distância entre 2 localidades usando a Fórmula de Haversine
    public double calcularDistancia(double latitudeOrigem, double longitudeOrigem, 
                                   double latitudeDestino, double longitudeDestino) {
        
        double diferencaLatitude = Math.toRadians(latitudeDestino - latitudeOrigem);
        double diferencaLongitude = Math.toRadians(longitudeDestino - longitudeOrigem);
        
        double a = Math.sin(diferencaLatitude / 2) * Math.sin(diferencaLatitude / 2) +
                   Math.cos(Math.toRadians(latitudeOrigem)) * Math.cos(Math.toRadians(latitudeDestino)) *
                   Math.sin(diferencaLongitude / 2) * Math.sin(diferencaLongitude / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAIO_TERRA_KM * c;
    }
    
    // Métodos auxiliares para buscar cada tipo de recomendação
    private List<Map<String, Object>> buscarAtracoesProximasComDistancia(
            double latitude, double longitude, double raio, Long idAtracaoAtual) {
        return atracaoRepository.findAll().stream()
            .filter(atracao -> !atracao.getId().equals(idAtracaoAtual))
            .map(atracao -> {
                double distancia = calcularDistancia(latitude, longitude, 
                                                  atracao.getLatitude(), atracao.getLongitude());
                return criarMapAtracaoComDistancia(atracao, distancia);
            })
            .filter(atracaoMap -> (double) atracaoMap.get("distancia") <= raio)
            .sorted(Comparator.comparingDouble(a -> (double) ((Map<String, Object>) a).get("distancia")))
            .limit(RECOMENDACOES_MAXIMAS)
            .collect(Collectors.toList());
    }
    
    private List<Map<String, Object>> buscarRestaurantesProximosComDistancia(
            double latitude, double longitude, double raio) {
        return restauranteRepository.findAll().stream()
            .map(restaurante -> {
                double distancia = calcularDistancia(latitude, longitude, 
                                                  restaurante.getLatitude(), restaurante.getLongitude());
                return criarMapRestauranteComDistancia(restaurante, distancia);
            })
            .filter(restauranteMap -> (double) restauranteMap.get("distancia") <= raio)
            .sorted(Comparator.comparingDouble(r -> (double) ((Map<String, Object>) r).get("distancia")))
            .limit(RECOMENDACOES_MAXIMAS)
            .collect(Collectors.toList());
    }
    
    private List<Map<String, Object>> buscarHospedagensProximasComDistancia(
            double latitude, double longitude, double raio) {
        return hospedagemRepository.findAll().stream()
            .map(hospedagem -> {
                double distancia = calcularDistancia(latitude, longitude, 
                                                  hospedagem.getLatitude(), hospedagem.getLongitude());
                return criarMapHospedagemComDistancia(hospedagem, distancia);
            })
            .filter(hospedagemMap -> (double) hospedagemMap.get("distancia") <= raio)
            .sorted(Comparator.comparingDouble(h -> (double) ((Map<String, Object>) h).get("distancia")))
            .limit(RECOMENDACOES_MAXIMAS)
            .collect(Collectors.toList());
    }
    
    // Métodos auxiliares para criar mapas de resposta
    private Map<String, Object> criarMapAtracao(Atracao atracao) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", atracao.getId());
        map.put("nome", atracao.getNome());
        map.put("latitude", atracao.getLatitude());
        map.put("longitude", atracao.getLongitude());
        // Adicione outros campos conforme necessário
        return map;
    }
    
    private Map<String, Object> criarMapAtracaoComDistancia(Atracao atracao, double distancia) {
        Map<String, Object> map = criarMapAtracao(atracao);
        map.put("distancia", distancia);
        return map;
    }
    
    private Map<String, Object> criarMapRestauranteComDistancia(Restaurante restaurante, double distancia) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", restaurante.getId());
        map.put("nome", restaurante.getNome());
        map.put("latitude", restaurante.getLatitude());
        map.put("longitude", restaurante.getLongitude());
        map.put("distancia", distancia);
        // Adicione outros campos conforme necessário
        return map;
    }
    
    private Map<String, Object> criarMapHospedagemComDistancia(Hospedagem hospedagem, double distancia) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", hospedagem.getId());
        map.put("nome", hospedagem.getNome());
        map.put("latitude", hospedagem.getLatitude());
        map.put("longitude", hospedagem.getLongitude());
        map.put("distancia", distancia);
        // Adicione outros campos conforme necessário
        return map;
    }
    
    private Map<String, Object> criarRespostaVazia() {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("atracaoReferencia", null);
        resposta.put("atracoesProximas", List.of());
        resposta.put("restaurantesProximos", List.of());
        resposta.put("hospedagensProximas", List.of());
        return resposta;
    }
}