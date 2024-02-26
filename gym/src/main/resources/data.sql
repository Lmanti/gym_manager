BEGIN;

INSERT INTO user_entity (user_id, first_name, is_active, last_name, password, username)
VALUES ('2f4a2c14-f70c-4012-87fb-247346dacfc7', 'Luis', true, 'Herrera', '$2a$10$v0qfuK4M/uW56KatCURcaOjyHuTjfS7kfPsKOa9ft4w95k228Ivvi', 'Luis.Herrera8723');

INSERT INTO trainee_entity (trainee_id, address, date_of_birth, user_id)
VALUES ('4919ff9f-cde2-49f8-8510-eea716114af3', 'ejemplo', current_timestamp, '2f4a2c14-f70c-4012-87fb-247346dacfc7');

INSERT INTO training_type_entity (training_type_id, name) VALUES ('4d64eb7c-cd62-4209-a574-e531101953fa', 'Fitness');
INSERT INTO training_type_entity (training_type_id, name) VALUES ('5cade45d-8067-443b-94e1-09ba789da945', 'Yoga');
INSERT INTO training_type_entity (training_type_id, name) VALUES ('c355477f-ea74-4222-a7b8-03d28a0e533f', 'Zumba');
INSERT INTO training_type_entity (training_type_id, name) VALUES ('2f561017-7040-43a4-8d92-4cf78ff93bcd', 'Stretching');
INSERT INTO training_type_entity (training_type_id, name) VALUES ('b95129ef-d73c-48d1-a268-97df8f8163dc', 'Resistance');

INSERT INTO user_entity (user_id, first_name, is_active, last_name, password, username)
VALUES ('c66b104d-4eae-4217-b167-cbe6e6f10769', 'Melissa', true, 'Lopez', '$2a$10$0yFE3ZfzaIRQTWqEGVvPb.oh8ocHbD/2Q2Vr/2xLu2FgNu9np9qmS', 'Melissa.Lopez');

INSERT INTO trainer_entity (trainer_id, training_type_id, user_id)
VALUES ('e11609ff-d907-4171-8c6b-713af7cc4f7b', '4d64eb7c-cd62-4209-a574-e531101953fa', 'c66b104d-4eae-4217-b167-cbe6e6f10769');

INSERT INTO user_entity (user_id, first_name, is_active, last_name, password, username)
VALUES ('4389e98e-474a-47c0-a8a1-f1c4c4b91553', 'Joseph', true, 'Herrera', '$2a$10$0yFE3ZfzaIRQTWqEGVvPb.oh8ocHbD/2Q2Vr/2xLu2FgNu9np9qmS', 'Joseph.Herrera');

INSERT INTO trainer_entity (trainer_id, training_type_id, user_id)
VALUES ('48422826-4151-437f-b5d9-90d14bf58729', '4d64eb7c-cd62-4209-a574-e531101953fa', '4389e98e-474a-47c0-a8a1-f1c4c4b91553');

COMMIT;