-- liquibase formatted sql

-- changeset lSysoev:1
CREATE INDEX student_name_index ON student (name)

-- changeset lSysoev:2
CREATE INDEX faculty_nc_idx ON faculty (name, color)