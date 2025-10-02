INSERT INTO catalog (catalog_id,location) VALUES ('d846a5a7-2e1c-4c79-809c-4f3f471e826d','SFPL');
INSERT INTO catalog (catalog_id,location) VALUES ('8f7a9a5f-5106-423a-97c7-2f905dcdffb9','NLA');
INSERT INTO catalog (catalog_id,location) VALUES ('3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0','SEATTLE');
INSERT INTO catalog (catalog_id,location) VALUES ('a5e7c3bb-0a69-496e-b02b-0e97d58c1b92','OPL');
INSERT INTO catalog (catalog_id,location) VALUES ('3d43a4e6-f63c-4e8a-9798-bd4321a9280d','VPL');
INSERT INTO catalog (catalog_id,location) VALUES ('92fa3579-038a-4522-bf65-762b0a5d9b58','BRITISH');
INSERT INTO catalog (catalog_id,location) VALUES ('1c98f401-cb3a-47eb-9865-1cc5b6b96fa0','LAC');
INSERT INTO catalog (catalog_id,location) VALUES ('6a1bc14e-b0a2-42a0-bf58-b8a59f82a6be','TPL');
INSERT INTO catalog (catalog_id,location) VALUES ('d7a71c60-0e52-4ed9-bf6e-b99e3e9b4b42','LAPL');
INSERT INTO catalog (catalog_id,location) VALUES ('bc91a8a0-0e5a-4d3d-8539-992ce2d6345e','OCLC');



INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('a06c9f39-34a7-4b71-80f1-0c3196276607', 'J.D.', 'Salinger', '1919-01-01', 'Famous for "The Catcher in the Rye"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('b3b25fd4-c2c4-4bc3-b019-d29ad2169c4e', 'Aldous', 'Huxley', '1894-07-26', 'Known for "Brave New World"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('f23e2d1a-d9da-4cf2-8709-f0b05730fa2c', 'J.R.R.', 'Tolkien', '1892-01-03', 'Famous for "The Hobbit" and "The Lord of the Rings"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('d80cfcf9-df70-4c8d-953d-233616ed8d7a', 'George', 'Orwell', '1903-06-25', 'Known for "1984" and "Animal Farm"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('e62f23f3-7430-4293-bd43-78c66a4a72f5', 'Harper', 'Lee', '1926-04-28', 'Author of "To Kill a Mockingbird"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('d25a93e2-7ff3-4a4b-9ed2-34f55c4ef0d5', 'F. Scott', 'Fitzgerald', '1896-09-24', 'Known for "The Great Gatsby"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('c195fd3a-b279-4a79-8130-29db7a134c1b', 'Jane', 'Austen', '1775-12-16', 'Famous for "Pride and Prejudice"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('f9a79b89-089d-45b9-bd3b-b4ed7cb9b340', 'Mark', 'Twain', '1835-11-30', 'Known for "Adventures of Huckleberry Finn" and "The Adventures of Tom Sawyer"');
INSERT INTO authors (author_id, first_name, last_name, birth_date, info) VALUES ('b56747f8-3580-4ad4-bc35-b47c967b8d2b', 'Leo', 'Tolstoy', '1828-09-09', 'Famous for "War and Peace" and "Anna Karenina"');




INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES ('550e8400-e29b-41d4-a716-446655440001', 'd846a5a7-2e1c-4c79-809c-4f3f471e826d', 'The Catcher in the Rye', 'HARDCOVER', 15, '978-0-316-76948-0', 'ENGLISH', 'AVAILABLE', 'A story of teenage rebellion.', 'a06c9f39-34a7-4b71-80f1-0c3196276607');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440002', '8f7a9a5f-5106-423a-97c7-2f905dcdffb9', 'Brave New World', 'PAPERPACK', 10, '978-0-06-085052-4', 'ENGLISH', 'NOT_AVAILABLE', 'A dystopian future.', 'b3b25fd4-c2c4-4bc3-b019-d29ad2169c4e');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440003', '3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0', 'The Hobbit', 'EBOOK', 25, '978-0-618-00221-3', 'ENGLISH', 'AVAILABLE', 'A fantasy adventure.', 'f23e2d1a-d9da-4cf2-8709-f0b05730fa2c');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440004', 'a5e7c3bb-0a69-496e-b02b-0e97d58c1b92', 'The Lord of the Rings', 'HARDCOVER', 30, '978-0-618-12902-3', 'ENGLISH', 'RESERVE', 'An epic fantasy trilogy.', 'f23e2d1a-d9da-4cf2-8709-f0b05730fa2c');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440005', '3d43a4e6-f63c-4e8a-9798-bd4321a9280d', '1984', 'PAPERPACK', 12, '978-0-452-28423-4', 'ENGLISH', 'LOST', 'A dystopian novel about surveillance.', 'd80cfcf9-df70-4c8d-953d-233616ed8d7a');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440006', '92fa3579-038a-4522-bf65-762b0a5d9b58', 'Animal Farm', 'EBOOK', 20, '978-0-452-28424-1', 'ENGLISH', 'AVAILABLE', 'A political allegory.', 'd80cfcf9-df70-4c8d-953d-233616ed8d7a');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440007', 'd846a5a7-2e1c-4c79-809c-4f3f471e826d', 'To Kill a Mockingbird', 'AUDIOBOOK', 18, '978-0-06-112008-4', 'ENGLISH', 'NOT_AVAILABLE', 'A classic of modern American literature.', 'e62f23f3-7430-4293-bd43-78c66a4a72f5');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440008', 'd846a5a7-2e1c-4c79-809c-4f3f471e826d', 'The Great Gatsby', 'HARDCOVER', 22, '978-0-7432-7356-5', 'ENGLISH', 'AVAILABLE', 'A novel set in the Roaring Twenties.', 'd25a93e2-7ff3-4a4b-9ed2-34f55c4ef0d5');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440009', 'd846a5a7-2e1c-4c79-809c-4f3f471e826d', 'Pride and Prejudice', 'PAPERPACK', 14, '978-0-14-143951-8', 'ENGLISH', 'RESERVE', 'A romantic novel of manners.', 'c195fd3a-b279-4a79-8130-29db7a134c1b');
INSERT INTO books (book_id, catalog_id, title, type, quantities, isbn, language, status, description, author_id) VALUES('550e8400-e29b-41d4-a716-446655440010', '3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0', 'Adventures of Huckleberry Finn', 'EBOOK', 19, '978-0-14-310732-3', 'ENGLISH', 'AVAILABLE', 'A novel about the adventures of a boy and a runaway slave.', 'f9a79b89-089d-45b9-bd3b-b4ed7cb9b340');

