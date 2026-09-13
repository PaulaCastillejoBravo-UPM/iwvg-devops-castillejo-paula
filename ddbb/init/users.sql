CREATE TABLE users (
   id UUID PRIMARY KEY,
   first_name VARCHAR(100),
   family_name VARCHAR(100),
   mobile VARCHAR(30),
   email VARCHAR(255),
   identity VARCHAR(50),
   address VARCHAR(255),
   city VARCHAR(100),
   postal_code INTEGER,
   province VARCHAR(100),
   password VARCHAR(255)
);

INSERT INTO users (
    id,
    first_name,
    family_name,
    email,
    mobile,
    identity,
    address,
    city,
    postal_code,
    province,
    password
) VALUES
(
  '11111111-1111-1111-1111-111111111111',
  'Alice',
  'Smith',
  'alice@example.com',
  '600000001',
  '12345678A',
  'Calle Mayor 1',
  'Madrid',
  28001,
  'Madrid',
  'password'
),
(
  '22222222-2222-2222-2222-222222222222',
  'Bob',
  'Smith',
  'bob@example.com',
  '600000002',
  '87654321B',
  'Calle Mayor 2',
  'Madrid',
  28002,
  NULL,
  'password'
),
(
  '33333333-3333-3333-3333-333333333333',
  'Charlie',
  'Smith',
  'charlie@example.com',
  '600000003',
  '11223344C',
  'Calle Mayor 3',
  'Madrid',
  28003,
  'Madrid',
  'password'
);