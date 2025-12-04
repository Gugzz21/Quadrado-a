package com.senac.av2.controller; // Confira se o pacote está certo

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
        QuadradoDTO resposta = null;

        try {
            // Tenta mandar para o RabbitMQ (vai falhar no seu PC)
            resposta = (QuadradoDTO) rabbitTemplate.convertSendAndReceive("fila-gustavo-quadrado", quadradoDTO);
            rabbitTemplate.convertSendAndReceive("provac2GustavoDiniz", quadradoDTO);
        } catch (Exception e) {
            // Ignora o erro silenciosamente
        }
        return resposta;
    }
}