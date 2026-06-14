package me.winterbelle.prompvault.models.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "prompt_history_items")
public class PromptHistoryItem extends AuditableEntity {

    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private PromptVaultUser account;

    @NotBlank
    private String promptText;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String responseText;

    public PromptVaultUser getAccount() {
        return account;
    }

    public void setAccount(PromptVaultUser account) {
        this.account = account;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
