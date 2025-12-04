package com.senac.av2.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding; // [cite: 16]
import org.springframework.amqp.core.DirectExchange; // [cite: 17]
import org.springframework.beans.factory.annotation.Autowired; // [cite: 19]
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct; // [cite: 21]


@Configuration
public class MQConfig {
    @Autowired // [cite: 24]
    private AmqpAdmin amqpAdmin; // [cite: 25]

    private Queue queue; // [cite: 26]

    // Método auxiliar para instanciar a fila
    private Queue queue(String queueName){ // [cite: 27]
        // durable: true, exclusive: false, autoDelete: false
        return new Queue(queueName, true, false, false); // [cite: 29]
    }

    // Método auxiliar para criar o Exchange
    private DirectExchange createDirectExchange(){ // [cite: 30]
        return new DirectExchange("area-quadrado"); // Adaptado de [cite: 31]
    }

    @PostConstruct // [cite: 34]
    private void Create(){ // [cite: 35]
        // Define o nome da fila conforme sua solicitação
        this.queue = new Queue("provac2GustavoDiniz"); // [cite: 36]

        // Cria o direct exchange
        DirectExchange directExchange = createDirectExchange(); // [cite: 38]

        // Cria o binding (ligação) entre fila e exchange
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE,
                directExchange.getName(), queue.getName(), null); // [cite: 40]

        // Declara tudo no servidor RabbitMQ
        amqpAdmin.declareQueue(queue); // [cite: 41]
        amqpAdmin.declareExchange(directExchange); // [cite: 42]
        amqpAdmin.declareBinding(binding); // [cite: 43]
    }
}