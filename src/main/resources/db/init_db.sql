CREATE TABLE employee (
  uuid CHAR(36) PRIMARY KEY NOT NULL,
  full_name TEXT not null
);

CREATE TABLE contact (
  id SERIAL,
  resume_uuid CHAR(36) NOT NULL REFERENCES employee (uuid) ON DELETE CASCADE,
  type TEXT NOT NULL,
  value TEXT NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);

CREATE TABLE section (
  id SERIAL PRIMARY KEY,
  resume_uuid CHAR(36) NOT NULL REFERENCES employee (uuid) ON DELETE CASCADE,
  type TEXT NOT NULL,
  content TEXT NOT NULL
);
CREATE UNIQUE INDEX section_idx ON section (resume_uuid, type);