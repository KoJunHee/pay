package me.jko.pay.api.handler;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class UniqueIdGenerator {
    private static final Integer ID_SIZE = 20;

    public String getUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_SIZE);
    }
}
