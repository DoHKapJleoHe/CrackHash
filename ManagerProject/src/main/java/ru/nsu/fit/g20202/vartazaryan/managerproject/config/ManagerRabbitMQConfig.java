package ru.nsu.fit.g20202.vartazaryan.managerproject.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerRabbitMQConfig
{
    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue managerQueue() {
        return new Queue("manager_queue");
    }

    @Bean
    public Queue workerQueue() {
        return new Queue("worker_queue");
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("exchange", true, false);
    }

    @Bean
    Binding workerBinding(@Qualifier("workerQueue") Queue workerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(workerQueue).to(exchange).with("worker_routing_key");
    }

    @Bean
    Binding managerBinding(@Qualifier("managerQueue") Queue managerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(managerQueue).to(exchange).with("manager_routing_key");
    }
}
