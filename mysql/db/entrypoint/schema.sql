Create table if not exists dozent(
	ID INTEGER NOT NULL,
    Name VARCHAR(50) NOT NULL,
    PRIMARY kEY(ID)
    );

create table if not exists einheit(
	ID INTEGER NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Dozent INTEGER NOT NULL,
    primary key(ID),
    foreign key(Dozent)
    references dozent(ID)
);

create table if not exists vorlesung(
	ID integer not null,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists uebung(
	ID integer not null,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists aufgabe(
	ID integer not null,
    name varchar(100) not null,
    primary key(id),
    foreign key(id)
    references einheit(id)
);

create table if not exists student(
	ID INTEGER NOT NULL,
    primary key (ID)
    );

create table if not exists fragebogen(
	ID integer not null,
    name varchar(100) not null,
    beschreibung text not null,
    startzeit datetime,
    endzeit datetime,
    einheit INTEGER not null,
    primary key(ID),
    foreign key(einheit)
    references einheit(ID)
	);
    
create table if not exists frage(
	ID integer not null,
    titel text not null,
    fragebogen integer not null,
    primary key(ID),
    foreign key(fragebogen)
    references fragebogen(ID)
);

create table if not exists langeFrage(
	ID integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists kurzeFrage(
	ID integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
);

create table if not exists boolscheFrage(
	ID integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
    );
    
create table if not exists multipleFrage(
	ID integer not null,
    primary key(id),
    foreign key(id)
    references frage(id)
    );
    
create table if not exists antwort(
	ID integer not null,
    primary key(id)
    );

create table if not exists kurzeAntwort(
	ID integer not null,
    antwort varchar(100),
    frage integer not null,
    primary key(id),
    foreign key(frage)
    references kurzeFrage(id)
    );
    
    create table if not exists langeAntwort(
	ID integer not null,
    antwort text,
    frage integer not null,
    primary key(id),
	foreign key(frage)
    references langeFrage(id)
    );
    
	create table if not exists boolscheAntwort(
	ID integer not null,
    antwort boolean,
    frage integer not null,
    primary key(id),
	foreign key(frage)
    references boolscheFrage(id)
    );
    
	create table if not exists multipleAntwort(
	ID integer not null,
    antwort integer,
    frage integer not null,
    primary key(id),
	foreign key(frage)
    references multipleFrage(id)
    );
    
    create table if not exists student_b_fragebogen(
		student integer not null,
        fragebogen integer not null,
        primary key(student, fragebogen),
        foreign key(student)
        references student(id),
        foreign key(fragebogen)
        references fragebogen(id)
    );