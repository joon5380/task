package com.task.taskproject.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @Author : 최대준
 * @ClassName : SzsProvider
 * @Description : Szs 특정 값 활용 객체
 * @Since : 2024. 06. 25.
 */
@PropertySource("classpath:szs.yml")
@Service
@Getter
public class SzsProvider {
    private final String scrapingUrl;
    private final String xApiKey;

    public SzsProvider(
        @Value("${scraping-url}") String scrapingUrl,
        @Value("${x-api-key}") String xApiKey
    ) {
        this.scrapingUrl = scrapingUrl;
        this.xApiKey = xApiKey;
    }
}
