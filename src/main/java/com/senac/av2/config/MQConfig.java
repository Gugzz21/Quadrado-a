package com.senac.av2.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    // --- CONEXÃO (Lê do application.properties automaticamente) ---

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // --- OBRIGATÓRIO: CONVERSOR JSON ---
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    // --- OBRIGATÓRIO: TEMPLATE USANDO JSON ---
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // AQUI ESTÁ A CORREÇÃO: Força o uso do conversor JSON
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    // --- ESTRUTURA DA FILA ---

    private DirectExchange createDirectExchange(){
        return new DirectExchange("area-quadrado");
    }

    @Bean
    public CommandLineRunner inicializar(AmqpAdmin amqpAdmin) {
        return args -> {
            try {
                // Cria a fila com o MESMO NOME da App 2
                Queue queue = new Queue("provac2GustavoDiniz", true, false, false);
                DirectExchange directExchange = createDirectExchange();
                Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE,
                        directExchange.getName(), queue.getName(), null);

                amqpAdmin.declareQueue(queue);
                amqpAdmin.declareExchange(directExchange);
                amqpAdmin.declareBinding(binding);
                System.out.println("App 1: Fila configurada!");
            } catch (Exception e) {
                System.out.println("App 1: Erro ao configurar fila (ignorando)...");
            }
        };
    }
}
