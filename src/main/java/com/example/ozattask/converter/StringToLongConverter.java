package com.example.ozattask.converter;

import org.springframework.core.convert.converter.Converter;

public class StringToLongConverter implements Converter<String, Long> {

    public Long convert(String source) {
        if (source == null || source.isEmpty()) {
            return null; // Возвращаем null, если исходная строка пуста или равна null
        }

        try {
            return Long.parseLong(source); // Пытаемся преобразовать строку в Long
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Невозможно преобразовать строку в Long: " + source);
        }
    }

}