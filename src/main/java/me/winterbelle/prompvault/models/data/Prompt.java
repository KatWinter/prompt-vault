package me.winterbelle.prompvault.models.data;

import jakarta.persistence.*;
import me.winterbelle.prompvault.utils.enums.Visibility;

@Entity
@Table(name = "prompts")
public class Prompt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private PromptVaultUser account;
    @ManyToOne
    @JoinColumn(name = "fk_category_id")
    private PromptCategory category;
    private String promptText;
    private String title;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PromptVaultUser getAccount() {
        return account;
    }

    public void setAccount(PromptVaultUser account) {
        this.account = account;
    }

    public PromptCategory getCategory() {
        return category;
    }

    public void setCategory(PromptCategory category) {
        this.category = category;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
