INSERT INTO customer (id, name) VALUES (1, 'Alice'), (2, 'Bob');

INSERT INTO transaction (id, customer_id, amount, transaction_date) VALUES
(1, 1, 120, '2023-06-10'),
(2, 1, 75, '2023-07-12'),
(3, 1, 200, '2023-08-01'),
(4, 2, 60, '2023-06-15');
