-- liquibase formatted sql

-- changeset softdoc_document:1.0-1-001

create table document (
	id bigint primary key auto_increment,
	title varchar(255) not null,
	description varchar(255) null,
	content longtext null,
	created_date datetime not null,
	last_updated_date datetime not null
);

-- rollback drop table document;