package me.winterbelle.prompvault.models.data;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "flagged_prompts")
public class FlaggedPrompt extends AuditableEntity{

    @ManyToMany
    @JoinTable(name = "flagged_prompt_policy_keywords",
            joinColumns = @JoinColumn(name = "flagged_prompt_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_keyword_id"))
    private List<PolicyKeyword> keywords;

    @OneToOne
    @JoinColumn(name = "fk_prompt_id")
    private Prompt prompt;

    public List<PolicyKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<PolicyKeyword> keywords) {
        this.keywords = keywords;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }
}
