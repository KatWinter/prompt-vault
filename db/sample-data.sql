USE promptvault;

INSERT INTO promptvault.prompt_vault_users (id, created_at, updated_at, email, first_name, last_name, password_hash,
                                            role, status, username)
VALUES (1, '2026-06-20 17:06:45.038779', '2026-06-20 17:06:45.038990', 'admin@promptvault.me', 'Admin', 'User',
        '$2a$10$kxJtS64BGNxul7hLoN1LreuD4EY2JwKsJ77vzrmFWsGcq9KwEeGBC', 'admin', 'enabled', 'admin');
INSERT INTO promptvault.prompt_vault_users (id, created_at, updated_at, email, first_name, last_name, password_hash,
                                            role, status, username)
VALUES (2, '2026-06-20 17:07:05.144028', '2026-06-20 17:07:54.986209', 'user@promptvault.me', 'Regular', 'User',
        '$2a$10$ZcAGPciDxQ0zusJW0amrceGSSMTGBQclklJl2.o3dQjcRHOw.EsR.', 'user', 'enabled', 'user');
INSERT INTO promptvault.prompt_vault_users (id, created_at, updated_at, email, first_name, last_name, password_hash,
                                            role, status, username)
VALUES (3, '2026-06-20 17:30:12.675128', '2026-06-20 17:30:12.675154', 'user2@promptvault.me', 'Regular2', 'User',
        '$2a$10$tefXK9aR1ApEX1ihE8f.H.b.bxss2xH04s3MO/wCnCJo4iJoJnD7q', 'user', 'enabled', 'user2');

INSERT INTO promptvault.policy_keywords (id, created_at, updated_at, text)
VALUES (1, '2026-06-20 17:08:48.301861', '2026-06-20 17:08:48.301901', 'Api Key');
INSERT INTO promptvault.policy_keywords (id, created_at, updated_at, text)
VALUES (2, '2026-06-20 17:08:52.587549', '2026-06-20 17:08:52.587608', 'password');
INSERT INTO promptvault.policy_keywords (id, created_at, updated_at, text)
VALUES (3, '2026-06-20 17:30:41.282924', '2026-06-20 17:30:41.282945', 'credit card');
INSERT INTO promptvault.policy_keywords (id, created_at, updated_at, text)
VALUES (4, '2026-06-20 17:30:47.437866', '2026-06-20 17:30:47.437920', 'medical data');
INSERT INTO promptvault.policy_keywords (id, created_at, updated_at, text)
VALUES (5, '2026-06-20 17:31:01.345394', '2026-06-20 17:31:01.345424', 'student data');
INSERT INTO promptvault.policy_keywords (id, created_at, updated_at, text)
VALUES (6, '2026-06-20 17:31:03.709718', '2026-06-20 17:31:03.709752', 'pii');

INSERT INTO promptvault.prompt_categories (id, created_at, updated_at, name)
VALUES (1, '2026-06-20 17:08:08.997791', '2026-06-20 17:08:08.997807', 'Category 1');
INSERT INTO promptvault.prompt_categories (id, created_at, updated_at, name)
VALUES (2, '2026-06-20 17:08:13.249575', '2026-06-20 17:08:13.249606', 'Second Category');
INSERT INTO promptvault.prompt_categories (id, created_at, updated_at, name)
VALUES (3, '2026-06-20 17:08:22.333678', '2026-06-20 17:08:22.333709', 'A 3rd Category');
INSERT INTO promptvault.prompt_categories (id, created_at, updated_at, name)
VALUES (4, '2026-06-20 17:08:24.333678', '2026-06-20 17:08:24.333709', 'Not Used Category');

INSERT INTO promptvault.prompts (id, created_at, updated_at, prompt_text, title, visibility, fk_account_id,
                                 fk_category_id)
VALUES (1, '2026-06-20 17:09:10.652098', '2026-06-20 17:09:10.652127',
        'This is my first prompt I\'m creating. It has nothing wrong.', 'My First Prompt', 'personal', 1, 1);
INSERT INTO promptvault.prompts (id, created_at, updated_at, prompt_text, title, visibility, fk_account_id,
                                 fk_category_id)
VALUES (2, '2026-06-20 17:09:47.184164', '2026-06-20 17:09:47.184186',
        'Here is some text. It contains the flagged word \'passWORD\'', 'Shared Prompt', 'shared', 1, 2);
INSERT INTO promptvault.prompts (id, created_at, updated_at, prompt_text, title, visibility, fk_account_id,
                                 fk_category_id)
VALUES (3, '2026-06-20 17:10:17.331784', '2026-06-20 17:10:17.331824', 'It\'s my private one with nothing wrong too!',
        'A 3rd Prompt', 'personal', 1, 3);
INSERT INTO promptvault.prompts (id, created_at, updated_at, prompt_text, title, visibility, fk_account_id,
                                 fk_category_id)
VALUES (4, '2026-06-20 17:11:04.283713', '2026-06-20 17:11:04.283731', 'This one has the flagged word \'api key\'',
        'Regular User Private prompt!', 'personal', 2, 2);
INSERT INTO promptvault.prompts (id, created_at, updated_at, prompt_text, title, visibility, fk_account_id,
                                 fk_category_id)
VALUES (5, '2026-06-20 17:11:17.736233', '2026-06-20 17:11:17.736260', 'Nothing to see here!', 'Regular User Shared',
        'shared', 2, 3);
INSERT INTO promptvault.prompts (id, created_at, updated_at, prompt_text, title, visibility, fk_account_id,
                                 fk_category_id)
VALUES (6, '2026-06-20 17:31:36.616452', '2026-06-20 17:31:36.616479', 'credit card password api key student data pii',
        'Contains Everything Bad', 'shared', 3, 2);

INSERT INTO promptvault.flagged_prompts (id, created_at, updated_at, fk_prompt_id)
VALUES (1, '2026-06-20 17:09:47.192844', '2026-06-20 17:09:47.192864', 2);
INSERT INTO promptvault.flagged_prompts (id, created_at, updated_at, fk_prompt_id)
VALUES (2, '2026-06-20 17:11:04.290890', '2026-06-20 17:11:04.290904', 4);
INSERT INTO promptvault.flagged_prompts (id, created_at, updated_at, fk_prompt_id)
VALUES (3, '2026-06-20 17:31:36.628744', '2026-06-20 17:31:36.628762', 6);

INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (1, 2);
INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (2, 1);
INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (3, 1);
INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (3, 2);
INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (3, 3);
INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (3, 5);
INSERT INTO promptvault.flagged_prompt_policy_keywords (flagged_prompt_id, policy_keyword_id)
VALUES (3, 6);

INSERT INTO promptvault.prompt_history_items (id, created_at, updated_at, prompt_text, response_text, fk_account_id)
VALUES (1, '2026-06-20 17:10:19.014818', '2026-06-20 17:10:19.014840', 'It\'s my private one with nothing wrong too!',
        'AI Service responded with \'TODO\' at 2026-06-20T18:10:19.011530 for prompt \'It\'s my private one with nothing wrong too!\'',
        1);
INSERT INTO promptvault.prompt_history_items (id, created_at, updated_at, prompt_text, response_text, fk_account_id)
VALUES (2, '2026-06-20 17:10:27.263649', '2026-06-20 17:10:27.263659',
        'Here is some text. It contains the flagged word \'passWORD\'',
        'AI Service responded with \'TODO\' at 2026-06-20T18:10:27.263088 for prompt \'Here is some text. It contains the flagged word \'passWORD\'\'',
        1);
INSERT INTO promptvault.prompt_history_items (id, created_at, updated_at, prompt_text, response_text, fk_account_id)
VALUES (3, '2026-06-20 17:10:38.247273', '2026-06-20 17:10:38.247289',
        'Here is some text. It contains the flagged word \'passWORD\'',
        'AI Service responded with \'TODO\' at 2026-06-20T18:10:38.246373 for prompt \'Here is some text. It contains the flagged word \'passWORD\'\'',
        2);


