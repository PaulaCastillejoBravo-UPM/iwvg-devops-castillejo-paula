CREATE TABLE users (
id UUID PRIMARY KEY,
first_name VARCHAR(100),
family_name VARCHAR(100),
mobile VARCHAR(30),
email VARCHAR(255),
address VARCHAR(255),
city VARCHAR(100),
postal_code INTEGER,
password VARCHAR(255)
);

INSERT INTO users (
id,
first_name,
family_name,
email,
mobile,
address,
city,
postal_code,
password
) VALUES
(
'11111111-1111-1111-1111-111111111111',
'Alice',
'Smith',
'alice@example.com',
'600000001',
'Calle Mayor 1',
'Madrid',
28001,
'password'
),
(
'22222222-2222-2222-2222-222222222222',
'Bob',
'Smith',
'bob@example.com',
'600000002',
'Calle Mayor 2',
'Madrid',
28002,
'password'
),
(
'33333333-3333-3333-3333-333333333333',
'Charlie',
'Smith',
'charlie@example.com',
'600000003',
'Calle Mayor 3',
'Madrid',
28003,
'password'
);