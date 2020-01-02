package com.n26.transactions.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.n26.transactions.util.BigDecimalSerializer;

/**
 * Class that declares bean definitions and overrides references to 
 * modules and utility classes used by spring. 
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * Extend message converters.
     *
     * @param converters the converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        SimpleModule module = new SimpleModule();
        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());

        converters.stream()
            .filter(MappingJackson2HttpMessageConverter.class::isInstance)
            .map(MappingJackson2HttpMessageConverter.class::cast)
            .map(AbstractJackson2HttpMessageConverter::getObjectMapper)
            .forEach(mapper -> mapper.registerModule(module));
    }

}
