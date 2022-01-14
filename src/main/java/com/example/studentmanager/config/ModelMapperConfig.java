package com.example.studentmanager.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                // map chặt chẽ
                // sử dụng strict thì thứ tự thuộc tính phải đúng
                // mọi từ trong thuộc tính nguồn phải khớp với toàn bộ
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
