package com.dinghao.entity.manage.role;

import com.dinghao.entity.BaseEntity;

public class Role extends BaseEntity {

	private static final long serialVersionUID = -2038815478973279489L;

	private String description;

    private Boolean isSystem;

    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}