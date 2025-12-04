package com.senac.av2.controller;

import com.senac.av2.dto.QuadradoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quadrado")
public class QuadradoController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/calcular-area")
    public QuadradoDTO calcularArea(@RequestBody QuadradoDTO quadradoDTO) {
        System.out.println("Enviando lado: " + quadradoDTO.getLado());

        // Envia para a fila e espera a resposta (RPC)
        QuadradoDTO resposta = (QuadradoDTO) rabbitTemplate.convertSendAndReceive("fila-gustavo-quadrado", quadradoDTO);

        return resposta != null ? resposta : new QuadradoDTO(0);
    }
}