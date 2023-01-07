package com.thrive.model.config;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheConfig {
    private Map<String, CacheNameConfig> mapConfig = Maps.newHashMap();

}
