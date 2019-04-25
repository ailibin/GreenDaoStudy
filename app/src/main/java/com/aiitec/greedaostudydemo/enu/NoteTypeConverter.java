package com.aiitec.greedaostudydemo.enu;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @Author: ailibin
 * @Time: 2019/04/18
 * @Description: 测试实例
 * @Email: ailibin@qq.com
 */
public class NoteTypeConverter implements PropertyConverter<NoteType, String> {

    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}
