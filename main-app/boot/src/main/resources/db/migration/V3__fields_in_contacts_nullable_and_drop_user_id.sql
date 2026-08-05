ALTER TABLE contacts
    ALTER COLUMN "position" DROP NOT NULL,
    ALTER COLUMN company_id DROP NOT NULL;

ALTER TABLE contacts DROP COLUMN user_id;

ALTER TABLE contacts
    RENAME COLUMN "position" TO company_position;
