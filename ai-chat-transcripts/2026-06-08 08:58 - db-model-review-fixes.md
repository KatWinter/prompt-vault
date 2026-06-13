Model: GPT-5.5-medium
Transcript: 2026-06-08 08:58 - db-model-review-fixes

# Chat Transcript

## User
Investigate src/main/java/me/winterbelle/prompvault/models/data and check if I'm doing anything wrong with my database models

## Assistant
I inspected the Spring Data JPA model package and related repository/application configuration.

Findings:

- High: `FlaggedPrompt.java` used `@JoinTable(name = "policy_keywords")`, which collided with the real `PolicyKeyword` entity table. Hibernate created `policy_keywords` as both entity table and many-to-many join table, producing columns like `flagged_prompt_id`, `policy_keyword_id`, and `text` in one table. Recommended separate join table: `flagged_prompt_policy_keywords`.
- High: `PromptVaultUser.java` mapped `promptList` with `@JoinColumn(name = "fk_prompt_id")`, while `Prompt.java` already had `fk_account_id`. Hibernate created both columns on `prompts`, including `fk_prompt_id` referencing users. Recommended `@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)`.
- Medium: `PromptHistoryItem.java` had a stray `‰` in the table name: `prompt_history_items‰`. Hibernate really created a quoted table with that character.
- Medium: Enum fields were persisted as ordinals in `Prompt.java` and `PromptVaultUser.java`. Recommended `@Enumerated(EnumType.STRING)`.
- Medium: `application.properties` had `spring.jpa.hibernate.ddl-auto = create`, which drops/recreates tables on startup. Recommended `update` for local prototyping or migrations/`validate` once schema matters.
- Low: `username` and `email` only had indexes, not uniqueness constraints. Recommended DB-level unique constraints.

Verification:

- `./mvnw test` passed after allowing Maven to use `~/.m2` and connect to local MySQL.
- Hibernate DDL confirmed the problems: it created `prompt_history_items‰`, reused `policy_keywords` as a join table, and added both `fk_account_id` and `fk_prompt_id` to `prompts`.

## User
Make those set of fixes for me

## Assistant
Implemented the database model fixes.

Changed:

- `PromptVaultUser.java`: added unique constraints for `username` and `email`.
- `PromptVaultUser.java`: persisted `Role` and `Status` as strings.
- `PromptVaultUser.java`: fixed `promptList` to use `mappedBy = "account"` instead of creating the wrong `fk_prompt_id`.
- `Prompt.java`: persisted `Visibility` as a string.
- `application.properties`: changed Hibernate DDL from `create` to `update` and removed the explicit MySQL dialect.

Note:

- The join table and history table typo were already corrected when reread before editing.
- A sandboxed `./mvnw test` run failed because it could not connect to local MySQL.
- Rerunning `./mvnw test` with local MySQL access passed: build success, 1 test passed.
- Hibernate generated the corrected schema: separate `flagged_prompt_policy_keywords` join table, no extra `fk_prompt_id` on `prompts`, string enum columns, and corrected `prompt_history_items`.

Caveat:

- `ddl-auto=update` will not clean up old bad tables or columns in an existing database. If the local DB still has the previous bad schema, drop it manually or write a migration.

## User
Export this chat transcript to a file in ai-chat-transcripts. Ensure the start includes the model used here (GPT-5.5-medium). The file should be start with the timestamp of the first message in iso 8601 format followed by an id for this transcript (YYYY-MM-DD HH:mm - {TRANSCRIPT_ID})

## Assistant
Created this transcript file:

`ai-chat-transcripts/2026-06-08 08:58 - db-model-review-fixes.md`
