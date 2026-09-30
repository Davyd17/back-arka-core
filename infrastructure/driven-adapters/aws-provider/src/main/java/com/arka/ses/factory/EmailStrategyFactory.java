package com.arka.ses.factory;

import com.arka.notification.dto.MessageSubject;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Resolves the {@link EmailContentStrategy} responsible for building the
 * email body/content for a given {@link MessageSubject}.
 * <p>
 * All {@link EmailContentStrategy} beans are auto-collected by Spring and
 * indexed by their supported subject at startup.
 */
@Component
public class EmailStrategyFactory {

    private final Map<MessageSubject, EmailContentStrategy>
            strategies = new EnumMap<>(MessageSubject.class);

    public EmailStrategyFactory(List<EmailContentStrategy> strategyList){
        for(EmailContentStrategy strategy : strategyList){
            this.strategies.put(strategy.getSupportedSubject(), strategy);
        }
    }

    /**
     * @param subject the email type to resolve a strategy for
     * @return the matching strategy
     * @throws IllegalArgumentException if no strategy is registered for the given subject
     */
    public EmailContentStrategy getEmailContentFor(MessageSubject subject){

        EmailContentStrategy strategy = strategies.get(subject);

        if(strategy == null)
            throw new IllegalArgumentException("No strategy configured for subject: " + subject);

        return strategy;
    }
}
