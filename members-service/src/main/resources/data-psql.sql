
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(1, 'WORK', '515-555-5555');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(1, 'MOBILE', '514-555-4444');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(2, 'WORK', '515-555-5587');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(2, 'MOBILE', '514-555-1234');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(5, 'WORK', '515-555-5554');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(5, 'MOBILE', '514-555-3967');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(5, 'HOME', '450-555-9876');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(9, 'WORK', '515-555-1122');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(9, 'MOBILE', '514-555-3344');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(4, 'HOME', '450-555-7788');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(4, 'WORK', '515-555-6677');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(10, 'MOBILE', '514-555-8899');
INSERT INTO user_phonenumbers(user_id, type, number) VALUES(10, 'HOME', '450-555-9900');




INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-010000000000', 'John', 'Doe', 'john.doe@example.com', '123 Maple Street', 'Toronto', 'Ontario', 'Canada', 'M4B1B3');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-020000000000', 'Jane', 'Smith', 'jane.smith@example.com', '456 Oak Avenue', 'Montreal', 'Quebec', 'Canada', 'H2X1Y4');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-030000000000', 'Michael', 'Brown', 'michael.brown@example.com', '789 Pine Lane', 'Vancouver', 'British Columbia', 'Canada', 'V5K0A1');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-040000000000', 'Emily', 'Davis', 'emily.davis@example.com', '321 Birch Road', 'Calgary', 'Alberta', 'Canada', 'T2P1J9');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-050000000000', 'William', 'Johnson', 'william.johnson@example.com', '654 Cedar Street', 'Ottawa', 'Ontario', 'Canada', 'K1A0B1');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-060000000000', 'Sophia', 'Martinez', 'sophia.martinez@example.com', '987 Spruce Drive', 'Edmonton', 'Alberta', 'Canada', 'T5J3E9');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-070000000000', 'Liam', 'Wilson', 'liam.wilson@example.com', '246 Elm Court', 'Winnipeg', 'Manitoba', 'Canada', 'R3C4T3');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-080000000000', 'Olivia', 'Taylor', 'olivia.taylor@example.com', '135 Willow Blvd', 'Halifax', 'Nova Scotia', 'Canada', 'B3H1W5');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-090000000000', 'Noah', 'Anderson', 'noah.anderson@example.com', '753 Ash Avenue', 'St. Johns', 'Newfoundland', 'Canada', 'A1C2E5');
INSERT INTO users (user_id, first_name, last_name, email, street_name, city, province, country, postal_code)
VALUES ('c195fd3a-3580-4b71-80f1-001000000000', 'Ava', 'Thomas', 'ava.thomas@example.com', '852 Poplar Way', 'Regina', 'Saskatchewan', 'Canada', 'S4P3Y2');





INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440001', '2023-01-01', '2027-01-01', 'REGULAR', 'c195fd3a-3580-4b71-80f1-010000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440002', '2023-02-01', '2027-02-01', 'STUDENT',  'c195fd3a-3580-4b71-80f1-020000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440003', '2023-03-01', '2030-03-01', 'VIP', 'c195fd3a-3580-4b71-80f1-030000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440004', '2023-04-01', '2028-04-01', 'SENIOR',  'c195fd3a-3580-4b71-80f1-040000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440005', '2023-05-01', '2026-05-01', 'FAMILY',  'c195fd3a-3580-4b71-80f1-050000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440006', '2023-06-01', '2029-06-01', 'REGULAR',  'c195fd3a-3580-4b71-80f1-060000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440007', '2023-07-01', '2028-07-01', 'STUDENT',  'c195fd3a-3580-4b71-80f1-070000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440008', '2023-08-01', '2030-08-01', 'VIP',  'c195fd3a-3580-4b71-80f1-080000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440009', '2023-09-01', '2030-09-01', 'SENIOR',  'c195fd3a-3580-4b71-80f1-090000000000');
INSERT INTO memberrecords (member_id, start_date, end_date, type, user_id)VALUES ('43b06e4a-c1c5-41d4-a716-446655440010', '2023-10-01', '2027-10-01', 'FAMILY',  'c195fd3a-3580-4b71-80f1-001000000000');


