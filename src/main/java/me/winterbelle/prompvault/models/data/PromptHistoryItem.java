package me.winterbelle.prompvault.models.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@Entity
@Table(name = "prompt_history_items")
public class PromptHistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private PromptVaultUser account;

    @NotBlank
    private String promptText;

    @NotBlank
    private String responseText;

    @NotNull
    private LocalDateTime requestTime;

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

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }
}
