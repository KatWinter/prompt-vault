package me.winterbelle.prompvault.models.data;

import jakarta.persistence.*;

@Entity
@Table(name = "prompt_categories")
public class PromptCategory extends AuditableEntity{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


