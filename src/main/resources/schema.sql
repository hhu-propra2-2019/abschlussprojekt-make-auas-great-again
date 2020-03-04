CREATE TABLE feedback.Formular(
	id serial NOT NULL,
	Erstellt_Am DATETIME NOT NULL,
	Gueltig_Bis DATETIME NOT NULL,
	CONSTRAINT Formular_ID PRIMARY KEY (id)
);

CREATE TABLE feedback.Fragen(
	id serial NOT NULL,
	Antwortmoeglichkeiten TEXT NOT NULL,
	Typ TEXT NOT NULL,
	Fragentext TEXT NOT NULL,
	CONSTRAINT Frage_ID PRIMARY KEY (id),
	CONSTRAINT Formular_ID FOREIGN KEY (Formular) REFERENCES Formular(Formular_ID) ON DELETE SET NULL
);

CREATE TABLE feedback.Antworten(
	id serial NOT NULL,
	Frage_ID INT NOT NULL,
	Antworttext TEXT NOT NULL,
	CONSTRAINT Antwort_ID PRIMARY KEY (id)
);
