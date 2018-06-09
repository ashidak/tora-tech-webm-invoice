package invoice.invoice_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * The Class InvoiceApiConfing.
 */
@Configuration
public class InvoiceApiConfing {
    /**
     * Mapping jackson 2 http message converter.
     * RestControllerで返却するJSONフォーマットを設定.
     *
     * @return the mapping jackson 2 http message converter
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder //
                .json()
                .indentOutput(true)
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .build();
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    /**
     * Extend message converters.
     *
     * @param converters the converters
     */
    public void extendMessageConverters(
            final List<HttpMessageConverter<?>> converters) {
        converters.add(0, mappingJackson2HttpMessageConverter());
    }
}
