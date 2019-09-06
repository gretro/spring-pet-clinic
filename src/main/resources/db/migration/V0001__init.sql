create sequence hibernate_sequence start 1 increment 1;

create table vet_specialties (
    id int8 not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    modified_at timestamp,
    modified_by varchar(255),
    name varchar(255) not null,
    slug varchar(255) not null unique,
    primary key (id)
);

create index ix_vet_specialties_slug on vet_specialties (slug);

create table vets (
    id int8 not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    first_name varchar(255) not null,
    modified_at timestamp,
    modified_by varchar(255),
    last_name varchar(255) not null,
    slug varchar(255) not null unique,
    primary key (id)
);

create index ix_vets_slug on vets (slug);

create table vets__vet_specialties (
    vet_id int8 not null,
    vet_specialty_id int8 not null,
    primary key (vet_id, vet_specialty_id),
    foreign key (vet_id) references vets (id),
    foreign key (vet_specialty_id) references vet_specialties (id)
);

create table customers (
    id int8 not null,
    slug varchar(255) not null unique,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    modified_at timestamp,
    modified_by varchar(255),
    primary key (id)
);

create index ix_customers_slug on customers (slug);

create table pets (
    id int8 not null,
    slug varchar(255) not null unique,
    name varchar(255) not null,
    owner_id int8 not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    modified_at timestamp,
    modified_by varchar(255),
    primary key (id),
    foreign key (owner_id) references customers (id)
);

create index ix_pets_slug on pets (slug);

