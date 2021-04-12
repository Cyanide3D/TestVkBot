package com.cyanide3d.lib.mylittleorm.query;

import java.util.List;

public interface SelectQueryExtractor {

    String extract(Class<?> clazz, List<Object> params);
    String extractAll(Class<?> clazz);

}
