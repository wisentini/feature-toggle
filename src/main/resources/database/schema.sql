DROP TRIGGER IF EXISTS tr_update_updated_at_column ON public.feature;

DROP TRIGGER IF EXISTS tr_update_updated_at_column ON public."user";

DROP FUNCTION IF EXISTS fn_update_updated_at_column;

DROP TABLE IF EXISTS feature;

DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS "user" (
    user_id    INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at DATE         NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE             NULL,

    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS feature (
    feature_id     INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    name           VARCHAR(255) NOT NULL,
    is_active      BOOLEAN      NOT NULL,
    responsible_id INT              NULL,
    password       VARCHAR(255) NOT NULL,
    created_at     DATE         NOT NULL DEFAULT CURRENT_DATE,
    updated_at     DATE             NULL,

    PRIMARY KEY (feature_id),
    FOREIGN KEY (responsible_id) REFERENCES "user" (user_id)
);

CREATE OR REPLACE FUNCTION fn_update_updated_at_column() RETURNS TRIGGER AS
'
    BEGIN
        NEW.updated_at := CURRENT_DATE;

        RETURN NEW;
    END;
'
LANGUAGE plpgsql;

CREATE TRIGGER tr_update_updated_at_column
BEFORE UPDATE ON "user"
FOR EACH ROW EXECUTE PROCEDURE fn_update_updated_at_column();

CREATE TRIGGER tr_update_updated_at_column
BEFORE UPDATE ON feature
FOR EACH ROW EXECUTE PROCEDURE fn_update_updated_at_column();
