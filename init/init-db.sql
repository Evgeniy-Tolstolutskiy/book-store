SELECT 'CREATE DATABASE bookstore'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'bookstore')\gexec