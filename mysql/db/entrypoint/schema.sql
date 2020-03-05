create table if not exists dozent(
	id integer not null,
    name varchar(50) not null,
    primary key(id)
);

create table if not exists einheit(
	id integer not null,
    name varchar(50) not null,
    dozent integer not null,
    primary key(id),
    foreign key(dozent)
    references dozent(id)
);

create table if not exists vorlesung(
	id integer not null,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists uebung(
	id integer not null,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists aufgabe(
	id integer not null,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists student(
	id integer not null,
    primary key (id)
);

create table if not exists fragebogen(
	id integer not null,
    name varchar(100) not null,
    beschreibung text not null,
    startzeit datetime,
    endzeit datetime,
    einheit integer not null,
    primary key(id),
    foreign key(einheit)
    references einheit(id)
);

create table if not exists frage(
	id integer not null,
    titel text not null,
    fragebogen integer not null,
    primary key(id),
    foreign key(fragebogen)
    references fragebogen(id)
);

create table if not exists langeFrage(
	id integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists kurzeFrage(
	id integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists boolscheFrage(
	id integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists multipleFrage(
	id integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists antwort(
	id integer not null,
    primary key(id)
);

create table if not exists kurzeAntwort(
	id integer not null,
    antwort varchar(100),
    frage integer not null,
    primary key(id),
    foreign key(frage)
    references kurzeFrage(id)
);

create table if not exists langeAntwort(
	id integer not null,
    antwort text,
    frage integer not null,
    primary key(id),
	foreign key(frage)
    references langeFrage(id)
);

create table if not exists boolscheAntwort(
	id integer not null,
    antwort boolean,
    frage integer not null,
    primary key(id),
	foreign key(frage)
    references boolscheFrage(id)
);

create table if not exists multipleAntwort(
	id integer not null,
    antwort integer,
    frage integer not null,
    primary key(id),
	foreign key(frage)
    references multipleFrage(id)
);

create table if not exists studentBeantwortetFragebogen(
	student integer not null,
	fragebogen integer not null,
	primary key(student, fragebogen),
	foreign key(student)
	references student(id),
	foreign key(fragebogen)
	references fragebogen(id)
);