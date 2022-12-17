DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS "user" (
    user_id    INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at DATE         NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE         NULL
);

DROP FUNCTION IF EXISTS fn_update_updated_at_column;

CREATE OR REPLACE FUNCTION fn_update_updated_at_column() RETURNS TRIGGER AS
'
    BEGIN
        NEW.updated_at := CURRENT_DATE;

        RETURN NEW;
    END;
'
LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tr_update_updated_at_column ON "public"."user";

CREATE TRIGGER tr_update_updated_at_column
BEFORE UPDATE ON "user"
FOR EACH ROW EXECUTE PROCEDURE fn_update_updated_at_column();
