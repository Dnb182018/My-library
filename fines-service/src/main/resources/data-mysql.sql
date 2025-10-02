

INSERT INTO fine (fine_id, issue_date, status) VALUES('b3a9b040-1c2e-4c3b-9127-9f6a001a7c2b', '2025-01-15', 'PAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('cf7f8e5e-6f9b-4a5c-8d5e-3a4f001b8e4c', '2025-01-20', 'PAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('9a8b2f13-5c4e-47d3-8c99-84b0f3d8a9c1', '2025-02-01', 'UNPAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('c2f56e1b-1b3a-40b4-a7cf-0c9f1c1e2d6b', '2025-02-10', 'UNPAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f', '2025-03-10', 'PAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('d4e5f6a7-b8c9-0d1e-2f3a-4b5c6d7e8f9a', '2025-03-15', 'UNPAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('e5f6a7b8-c9d0-1e2f-3a4b-5c6d7e8f9a0b', '2025-03-20', 'UNPAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('f6a7b8c9-d0e1-2f3a-4b5c-6d7e8f9a0b1c', '2025-03-25', 'PAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('a7b8c9d0-e1f2-3a4b-5c6d-7e8f9a0b1c2d', '2025-04-01', 'UNPAID');
INSERT INTO fine (fine_id, issue_date, status) VALUES('b8c9d0e1-f2a3-4b5c-6d7e-8f9a0b1c2d3e', '2025-04-05', 'PAID');






INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(1, 150.00, 'USD', '2025-01-18', 'COMPLETE', 'CREDIT');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(2, 200.00, 'CAD', '2025-01-22', 'COMPLETE', 'PAYPAL');
-- INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(3, 120.00, 'USD', '2025-02-05', 'PENDING', 'DEBIT');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(4, 180.00, 'USD', '2025-02-12', 'FAILED', 'VISA');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(5, 100.00, 'CAD', '2025-03-01', 'COMPLETE', 'CREDIT');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(6, 175.50, 'USD', '2025-03-08', 'PENDING', 'PAYPAL');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(7, 250.00, 'USD', '2025-03-15', 'FAILED', 'DEBIT');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(8, 300.00, 'CAD', '2025-03-22', 'COMPLETE', 'VISA');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(9, 80.00, 'USD', '2025-03-25', 'PENDING', 'CREDIT');
INSERT INTO fine_payment (fine_id, amount, currency, payment_date, status, payment_method) VALUES(10, 60.50, 'CAD', '2025-03-28', 'COMPLETE', 'PAYPAL');


