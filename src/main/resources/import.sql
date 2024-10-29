-- CREATING SCHEMA
CREATE SCHEMA IF NOT EXISTS mfa;

-- POPULING privilege
--INSERT INTO mfa.privilege(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '82beb7a1-621c-4b9a-83eb-3ca196ed4345', 'READ_PRIVILEGE') ON CONFLICT DO NOTHING;
--INSERT INTO mfa.privilege(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '3f2146a9-5d43-448e-a1eb-455766d3a14a', 'WRITE_PRIVILEGE') ON CONFLICT DO NOTHING;

-- POPULING role
INSERT INTO mfa.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '7c12004d-e843-4e00-be40-01845ad75834', 'USER') ON CONFLICT DO NOTHING;
INSERT INTO mfa.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '52c57a80-4e3b-4a41-a864-58d0cea25b14', 'MODERATOR') ON CONFLICT DO NOTHING;
INSERT INTO mfa.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '8652ec73-0a53-433c-93be-420f1d90c681', 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO mfa.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), 'b8b37d04-628d-4939-b200-2a5e48909cd9', 'REVIEWER') ON CONFLICT DO NOTHING;
INSERT INTO mfa.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '927c96c5-6884-433a-9479-836efbb1ed87', 'VERIFIER') ON CONFLICT DO NOTHING;
INSERT INTO mfa.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '83366ed6-b0f2-4ef3-9658-e8bd9a8e3d39', 'OPERATOR') ON CONFLICT DO NOTHING;
INSERT INTO mfa.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '55c16ae7-b918-4b31-b920-deb4af049075', 'VIEWER') ON CONFLICT DO NOTHING;

-- POPULING role_privilege
--INSERT INTO mfa.role_privilege(role_id, privilege_id) VALUES ('7c12004d-e843-4e00-be40-01845ad75834', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
--INSERT INTO mfa.role_privilege(role_id, privilege_id) VALUES ('52c57a80-4e3b-4a41-a864-58d0cea25b14', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
--INSERT INTO mfa.role_privilege(role_id, privilege_id) VALUES ('52c57a80-4e3b-4a41-a864-58d0cea25b14', '3f2146a9-5d43-448e-a1eb-455766d3a14a') ON CONFLICT DO NOTHING;
--INSERT INTO mfa.role_privilege(role_id, privilege_id) VALUES ('8652ec73-0a53-433c-93be-420f1d90c681', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
--INSERT INTO mfa.role_privilege(role_id, privilege_id) VALUES ('8652ec73-0a53-433c-93be-420f1d90c681', '3f2146a9-5d43-448e-a1eb-455766d3a14a') ON CONFLICT DO NOTHING;

-- POPULING user
INSERT INTO mfa.user(created_at, updated_at, id, username, password, email, active, secret) VALUES (NOW(), NOW(), '6120ce60-4499-4a8d-816b-9080e3f8e6e2', '00038057', '$2a$10$v5q8rJ5T/OlmZ2NKSYB2YOOxkn9AI1K04Bn9pemlEZTAMybsq6ona', 'user.gadelha@marinha.mil.br', true, '') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user(created_at, updated_at, id, username, password, email, active, secret) VALUES (NOW(), NOW(), '67d43c5e-bd63-4e17-b386-09438e758194', '00038058', '$2a$10$v5q8rJ5T/OlmZ2NKSYB2YOOxkn9AI1K04Bn9pemlEZTAMybsq6ona', 'moderator.gadelha@marinha.mil.br', true, '') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user(created_at, updated_at, id, username, password, email, active, secret) VALUES (NOW(), NOW(), '02560770-e8b9-4c3b-b220-a2ce7be2cbda', '00038059', '$2a$10$v5q8rJ5T/OlmZ2NKSYB2YOOxkn9AI1K04Bn9pemlEZTAMybsq6ona', 'admin.gadelha@marinha.mil.br', true, '') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user(created_at, updated_at, id, username, password, email, active, secret) VALUES (NOW(), NOW(), 'ba7f72cf-9cdb-4874-9632-6fa3a243c513', '00038060', '$2a$10$v5q8rJ5T/OlmZ2NKSYB2YOOxkn9AI1K04Bn9pemlEZTAMybsq6ona', 'reviewer.gadelha@marinha.mil.br', true, '') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user(created_at, updated_at, id, username, password, email, active, secret) VALUES (NOW(), NOW(), '40063b4d-3b06-459e-a58d-637725c02927', '00038061', '$2a$10$v5q8rJ5T/OlmZ2NKSYB2YOOxkn9AI1K04Bn9pemlEZTAMybsq6ona', 'verifier.gadelha@marinha.mil.br', true, '') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user(created_at, updated_at, id, username, password, email, active, secret) VALUES (NOW(), NOW(), '7b88d564-b34b-4f07-9775-a67bd1777dec', '00038062', '$2a$10$v5q8rJ5T/OlmZ2NKSYB2YOOxkn9AI1K04Bn9pemlEZTAMybsq6ona', 'operator.gadelha@marinha.mil.br', true, '') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user(created_at, updated_at, id, username, password, email, active, secret) VALUES (NOW(), NOW(), '482d27e6-b9a6-4a71-ac3a-8671e481e9b4', '00038063', '$2a$10$v5q8rJ5T/OlmZ2NKSYB2YOOxkn9AI1K04Bn9pemlEZTAMybsq6ona', 'viewer.gadelha@marinha.mil.br', true, '') ON CONFLICT DO NOTHING;

---- POPULING user_roles
INSERT INTO mfa.user_roles(user_id, role_id) VALUES('6120ce60-4499-4a8d-816b-9080e3f8e6e2', '7c12004d-e843-4e00-be40-01845ad75834') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user_roles(user_id, role_id) VALUES('67d43c5e-bd63-4e17-b386-09438e758194', '52c57a80-4e3b-4a41-a864-58d0cea25b14') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user_roles(user_id, role_id) VALUES('02560770-e8b9-4c3b-b220-a2ce7be2cbda', '8652ec73-0a53-433c-93be-420f1d90c681') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user_roles(user_id, role_id) VALUES('ba7f72cf-9cdb-4874-9632-6fa3a243c513', 'b8b37d04-628d-4939-b200-2a5e48909cd9') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user_roles(user_id, role_id) VALUES('40063b4d-3b06-459e-a58d-637725c02927', '927c96c5-6884-433a-9479-836efbb1ed87') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user_roles(user_id, role_id) VALUES('7b88d564-b34b-4f07-9775-a67bd1777dec', '83366ed6-b0f2-4ef3-9658-e8bd9a8e3d39') ON CONFLICT DO NOTHING;
INSERT INTO mfa.user_roles(user_id, role_id) VALUES('482d27e6-b9a6-4a71-ac3a-8671e481e9b4', '55c16ae7-b918-4b31-b920-deb4af049075') ON CONFLICT DO NOTHING;
