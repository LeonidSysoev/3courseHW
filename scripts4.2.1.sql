ALTER TABLE student
    ADD CONSTRAINT student_constraint CHECK (age >= 16);


ALTER TABLE student
    ADD CONSTRAINT student_name UNIQUE (name);

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT (20)