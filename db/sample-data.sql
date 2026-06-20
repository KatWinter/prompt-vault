USE promptvault;


INSERT INTO promptvault.prompt_vault_users (created_at, id, updated_at, email, first_name, last_name, password_hash,
                                            username, role, status)
VALUES ('2026-06-20 17:06:45.038779', 1, '2026-06-20 17:06:45.038990', 'admin@promptvault.me', 'Admin', 'User',
        '$2a$10$kxJtS64BGNxul7hLoN1LreuD4EY2JwKsJ77vzrmFWsGcq9KwEeGBC', 'admin', 'admin', 'enabled');
INSERT INTO promptvault.prompt_vault_users (created_at, id, updated_at, email, first_name, last_name, password_hash,
                                            username, role, status)
VALUES ('2026-06-20 17:07:05.144028', 2, '2026-06-20 17:07:54.986209', 'user@promptvault.me', 'Regular', 'User',
        '$2a$10$ZcAGPciDxQ0zusJW0amrceGSSMTGBQclklJl2.o3dQjcRHOw.EsR.', 'user', 'user', 'enabled');


INSERT INTO promptvault.policy_keywords (created_at, id, updated_at, text)
VALUES ('2026-06-20 17:08:48.301861', 1, '2026-06-20 17:08:48.301901', 'Api Key');
INSERT INTO promptvault.policy_keywords (created_at, id, updated_at, text)
VALUES ('2026-06-20 17:08:52.587549', 2, '2026-06-20 17:08:52.587608', 'password');

INSERT INTO promptvault.prompt_categories (created_at, id, updated_at, name)
VALUES ('2026-06-20 17:08:08.997791', 1, '2026-06-20 17:08:08.997807', 'Category 1');
INSERT INTO promptvault.prompt_categories (created_at, id, updated_at, name)
VALUES ('2026-06-20 17:08:13.249575', 2, '2026-06-20 17:08:13.249606', 'Second Category');
INSERT INTO promptvault.prompt_categories (created_at, id, updated_at, name)
VALUES ('2026-06-20 17:08:22.333678', 3, '2026-06-20 17:08:22.333709', 'A 3rd Category');
INSERT INTO promptvault.prompt_categories (created_at, id, updated_at, name)
VALUES ('2026-06-20 17:08:24.333678', 4, '2026-06-20 17:08:24.333709', 'Not Used Category');


INSERT INTO promptvault.prompts (created_at, fk_account_id, fk_category_id, id, updated_at, prompt_text, title,
                                 visibility)
VALUES ('2026-06-20 17:09:10.652098', 1, 1, 1, '2026-06-20 17:09:10.652127',
        'This is my first prompt I\'m creating. It has nothing wrong.', 'My First Prompt', 'personal');
INSERT INTO promptvault.prompts (created_at, fk_account_id, fk_category_id, id, updated_at, prompt_text, title,
                                 visibility)
VALUES ('2026-06-20 17:09:47.184164', 1, 2, 2, '2026-06-20 17:09:47.184186',
        'Here is some text. It contains the flagged word \'passWORD\'', 'Shared Prompt', 'shared');
INSERT INTO promptvault.prompts (created_at, fk_account_id, fk_category_id, id, updated_at, prompt_text, title,
                                 visibility)
VALUES ('2026-06-20 17:10:17.331784', 1, 3, 3, '2026-06-20 17:10:17.331824',
        'It\'s my private one with nothing wrong too!', 'A 3rd Prompt', 'personal');
INSERT INTO promptvault.prompts (created_at, fk_account_id, fk_category_id, id, updated_at, prompt_text, title,
                                 visibility)
VALUES ('2026-06-20 17:11:04.283713', 2, 2, 4, '2026-06-20 17:11:04.283731',
        'This one has the flagged word \'api key\'', 'Regular User Private prompt!', 'personal');
INSERT INTO promptvault.prompts (created_at, fk_account_id, fk_category_id, id, updated_at, prompt_text, title,
                                 visibility)
VALUES ('2026-06-20 17:11:17.736233', 2, 3, 5, '2026-06-20 17:11:17.736260', 'Nothing to see here!',
        'Regular User Shared', 'shared');

INSERT INTO promptvault.flagged_prompts (created_at, fk_prompt_id, id, updated_at)
VALUES ('2026-06-20 17:09:47.192844', 2, 1, '2026-06-20 17:09:47.192864');
INSERT INTO promptvault.flagged_prompts (created_at, fk_prompt_id, id, updated_at)
VALUES ('2026-06-20 17:11:04.290890', 4, 2, '2026-06-20 17:11:04.290904');

INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (1, 2);
INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (2, 1);

INSERT INTO promptvault.prompt_history_items (created_at, fk_account_id, id, updated_at, prompt_text, response_text)
VALUES ('2026-06-20 17:10:19.014818', 1, 1, '2026-06-20 17:10:19.014840',
        'It\'s my private one with nothing wrong too!',
        'AI Service responded with \'TODO\' at 2026-06-20T18:10:19.011530 for prompt \'It\'s my private one with nothing wrong too!\'');
INSERT INTO promptvault.prompt_history_items (created_at, fk_account_id, id, updated_at, prompt_text, response_text)
VALUES ('2026-06-20 17:10:27.263649', 1, 2, '2026-06-20 17:10:27.263659',
        'Here is some text. It contains the flagged word \'passWORD\'',
        'AI Service responded with \'TODO\' at 2026-06-20T18:10:27.263088 for prompt \'Here is some text. It contains the flagged word \'passWORD\'\'');
INSERT INTO promptvault.prompt_history_items (created_at, fk_account_id, id, updated_at, prompt_text, response_text)
VALUES ('2026-06-20 17:10:38.247273', 2, 3, '2026-06-20 17:10:38.247289',
        'Here is some text. It contains the flagged word \'passWORD\'',
        'AI Service responded with \'TODO\' at 2026-06-20T18:10:38.246373 for prompt \'Here is some text. It contains the flagged word \'passWORD\'\'');
