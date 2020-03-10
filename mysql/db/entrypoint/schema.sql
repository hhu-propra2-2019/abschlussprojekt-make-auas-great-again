create table if not exists dozent(
    id bigint unsigned not null auto_increment,
    name varchar(50) not null,
    primary key(id)
);

create table if not exists einheit(
    id bigint unsigned not null auto_increment,
    name varchar(50) not null,
    dozent bigint unsigned not null,
    primary key(id),
    foreign key(dozent)
    references dozent(id)
);

create table if not exists vorlesung(
    id bigint unsigned not null auto_increment,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists uebung(
    id bigint unsigned not null auto_increment,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists aufgabe(
    id bigint unsigned not null auto_increment,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists student(
    id bigint unsigned not null auto_increment,
    primary key (id)
);

create table if not exists fragebogen(
    id bigint unsigned not null auto_increment,
    name varchar(100) not null,
    beschreibung text not null,
    startzeit datetime,
    endzeit datetime,
    einheit bigint unsigned not null,
    primary key(id),
    foreign key(einheit)
    references einheit(id)
);

create table if not exists frage(
    id bigint unsigned not null auto_increment,
    titel text not null,
    fragebogen bigint unsigned not null,
    primary key(id),
    foreign key(fragebogen)
    references fragebogen(id)
);

create table if not exists langeFrage(
    id bigint unsigned not null auto_increment,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists kurzeFrage(
    id bigint unsigned not null auto_increment,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists boolscheFrage(
    id bigint unsigned not null auto_increment,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists multipleFrage(
    id bigint unsigned not null auto_increment,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists kurzeAntwort(
    id bigint unsigned not null auto_increment,
    antwort varchar(100),
    frage bigint unsigned not null,
    primary key(id),
    foreign key(frage)
    references kurzeFrage(id)
);

create table if not exists langeAntwort(
    id bigint unsigned not null auto_increment,
    antwort text,
    frage bigint unsigned not null,
    primary key(id),
    foreign key(frage)
    references langeFrage(id)
);

create table if not exists boolscheAntwort(
    id bigint unsigned not null auto_increment,
    antwort bool,
    frage bigint unsigned not null,
    primary key(id),
    foreign key(frage)
    references boolscheFrage(id)
);

create table if not exists multipleAntwort(
    id bigint unsigned not null auto_increment,
    antwort integer,
    frage bigint unsigned not null,
    primary key(id),
    foreign key(frage)
    references multipleFrage(id)
);

create table if not exists studentBeantwortetFragebogen(
    student bigint unsigned not null,
    fragebogen bigint unsigned not null,
    primary key(student, fragebogen),
    foreign key(student)
    references student(id),
    foreign key(fragebogen)
    references fragebogen(id)
);

create table if not exists studentBelegtEinheit(
    student bigint unsigned not null,
    einheit bigint unsigned not null,
    primary key (student, einheit),
    foreign key(student)
    references student(id),
    foreign key(einheit)
    references einheit(id)
);