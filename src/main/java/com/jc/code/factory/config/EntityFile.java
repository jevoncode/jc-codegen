package com.jc.code.factory.config;

import java.util.ArrayList;
import java.util.List;

public class EntityFile extends CodeFile{

    private String fileName;

    private List<EntityField> entityFieldList = new ArrayList<>();

    public void addField(EntityField field) {
        entityFieldList.add(field);
    }

    public List<EntityField> getEntityFieldList() {
        return entityFieldList;
    }

    public void setEntityFieldList(List<EntityField> entityFieldList) {
        this.entityFieldList = entityFieldList;
    }

    public String getFileName() {
        if(fileName==null)
            fileName = getClassSimpleName()+".java";
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
