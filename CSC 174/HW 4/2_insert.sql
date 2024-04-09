INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('Leonardo da Vinci', '1452-04-15', '1519-05-02', 'An Italian polymath of the High Renaissance who was active as a painter, draughtsman, engineer, scientist, theorist, sculptor, and architect', 'Italy');

INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('Vincent van Gogh', '1853-03-30', '1890-07-29', 'A Dutch Post-Impressionist painter who is among the most famous and influential figures in the history of Western art', 'France');

INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('Raphael', '1483-01-01', '1520-04-6', 'An Italian painter and architect of the High Renaissance', 'Italy');

INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('Michelangelo', '1475-03-06', '1564-02-18', 'An Italian sculptor, painter, architect, and poet of the High Renaissance', 'Republic of Florence');

INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('Claude Monet', '1840-11-14', '1926-12-05', 'French painter and founder of impressionist painting', 'France');

INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('Louise Bourgeois', '1911-12-25', '2010-05-31', 'French-American artist', 'France');

INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('John Doe', '1840-11-14', '1926-12-05', 'French painter and founder of impressionist painting', 'France');

INSERT INTO artist
(aname, date_born, date_died, description, country_of_origin)
VALUES('John Smith', '1911-12-25', '2010-05-31', 'French-American artist', 'France');


INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(1, 1505, 'Mona Lisa', 'The Mona Lisa is a half-length portrait painting by Italian artist Leonardo da Vinci', 'Leonardo da Vinci');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(2, 1889, 'The Stary Night', 'The Starry Night is an oil-on-canvas painting by the Dutch Post-Impressionist painter Vincent van Gogh', 'Vincent van Gogh');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(3, 1511, 'The School of Athens', 'The School of Athens is a fresco by the Italian Renaissance artist Raphael', 'Raphael');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(4, 1504, 'David of Michelangelo', 'David is a masterpiece of Italian Renaissance sculpture Michelangelo', 'Michelangelo');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(5, 1872, 'Impression, Sunrise', 'Impression, Sunrise is an 1872 painting by Claude Monet', 'Claude Monet');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(6, 1504, 'Madonna of Bruges', 'A marble sculpture by Michelangelo of the Virgin and Child.', 'Michelangelo');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(7, 1495, 'Angel', 'A statue of an angel', 'Michelangelo');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(8, 1996, 'Spider', 'Spider is a sculpture by Louise Bourgeois', 'Louise Bourgeois');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(9, 1872, 'Paiting 1', 'Sunrise painting', 'John Doe');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(10, 1504, 'Paiting 2', 'Sunset painting', 'John Doe');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(11, 1495, 'Shining Stars', 'Shining stars on dessert', 'John Smith');

INSERT INTO art_object
(id_no, `year`, title, description, artist_name)
VALUES(12, 1996, 'Paiting', 'A hezerd of ships in snow', 'John Smith');


INSERT INTO painting
(id_no, `type`, `style`, drawn_on)
VALUES(1, 'portrait', 'half-length portrait', '1503-01-01');

INSERT INTO painting
(id_no, `type`, `style`, drawn_on)
VALUES(2, 'oil-on-canvas', 'east facing window', '1889-06-01');

INSERT INTO others
(id_no, `type`, `style`)
VALUES(3, 'history', 'room view');

INSERT INTO sculpture
(id_no, material, weight, height)
VALUES(4, 'white marble', 6, 17);

INSERT INTO painting
(id_no, `type`, `style`, drawn_on)
VALUES(5, 'painting', 'sunrise of port', '1872-01-01');

INSERT INTO sculpture
(id_no, material, weight, height)
VALUES(6, 'bronze and silver', 3, 15);

INSERT INTO sculpture
(id_no, material, weight, height)
VALUES(7, 'Marble', 6, 4);

INSERT INTO sculpture
(id_no, material, weight, height)
VALUES(8, 'Marble', 2.5, 1.6);

INSERT INTO painting
(id_no, `type`, `style`, drawn_on)
VALUES(9, 'portrait', 'style 1', '1503-01-01');

INSERT INTO painting
(id_no, `type`, `style`, drawn_on)
VALUES(10, 'oil-on-canvas', 'style 2', '1889-06-01');

INSERT INTO painting
(id_no, `type`, `style`, drawn_on)
VALUES(11, 'portrait', 'style 3', '1503-01-01');

INSERT INTO painting
(id_no, `type`, `style`, drawn_on)
VALUES(12, 'oil-on-canvas', 'style 4', '1889-06-01');


INSERT INTO exhibition
(ename, start_date, end_date)
VALUES('E1', '2015-09-14', '2016-02-07');

INSERT INTO exhibition
(ename, start_date, end_date)
VALUES('E2', '2015-11-01', '2016-03-06');


INSERT INTO shown_at
(art, exhibition_name)
VALUES(4, 'E1');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(1, 'E1');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(6, 'E1');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(7, 'E1');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(12, 'E1');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(8, 'E1');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(2, 'E2');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(3, 'E2');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(5, 'E2');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(11, 'E2');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(10, 'E2');

INSERT INTO shown_at
(art, exhibition_name)
VALUES(9, 'E1');
