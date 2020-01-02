package com.n26.transactions.util;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * Defines API used by ObjectMapper to serialize custom's BigDecimal reference types into JSON.
 * 
 * @author hrodriguez
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    /**
     * Performs a custom serialization process to get a correct format of KPI's.
     *
     * @param value the value
     * @param gen the gen
     * @param serializers the serializers
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP)
            .toString());
    }

}