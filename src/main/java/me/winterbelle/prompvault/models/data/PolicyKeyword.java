package me.winterbelle.prompvault.models.data;

import jakarta.persistence.*;

@Entity
@Table(name = "policy_keywords")
public class PolicyKeyword extends AuditableEntity {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
