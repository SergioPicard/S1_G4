INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES("BAPI-1235","Buenos Aires","Puerto Iguazú","Economy", 6500.0, "2022/02/10","2022/02/15");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES("PIBA-1420","Puerto Iguazú","Bogotá","Business",43200.0,"2022/02/10","2022/02/20");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "PIBA-1420", "Puerto Iguazú", "Bogotá","Economy", 25735.0,"2022/02/10","2022/02/21");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "BATU-5536", "Buenos Aires", "Tucumán","Economy", 7320.0,"2022/02/10","2022/02/17");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "TUPI-3369","Tucumán","Puerto Iguazú","Economy",5400.0,"2022/01/02","2022/01/16");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "TUPI-3369", "Tucumán","Puerto Iguazú","Business",12530.0,"2022/02/12","2022/02/23");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "BOCA-4213","Bogotá","Cartagena","Economy",8000.0,"2022/01/23","2022/02/05");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "CAME-0321","Cartagena","Medellín","Economy",7800.0,"2022/01/23","2022/01/31");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "BOBA-6567","Bogotá","Buenos Aires","Business",57000.0,"2022/02/15","2022/02/28");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "BOBA-6567","Bogotá","Buenos Aires","Economy",39860.0,"2022/03/01","2022/03/14");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "BOME-4442","Bogotá", "Medellín","Economy",11000.0,"2022/02/10","2022/02/24");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "MEPI-9986","Medellín","Puerto Iguazú","Business",1640.0,"2022/04/17","2022/05/02");




INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "CH-0002","Cataratas Hotel","Puerto Iguazú","Doble",6300.0,"2022/02/10","2022/03/20",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "CH-0003","Cataratas Hotel 2","Puerto Iguazú","Triple",8200.0,"2022/02/10","2022/03/23",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "HB-0001","Hotel Bristol","Buenos Aires","Single",5435.0,"2022/02/10","2022/03/19",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "BH-0002","Hotel Bristol 2","Buenos Aires","Doble",7200.0,"2022/02/12","2022/04/17",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "SH-0002","Sheraton","Tucumán", "Doble",5790.0,"2022/04/17","2022/05/23",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "SH-0001","Sheraton 2","Tucumán","Single",4150.0,"2022/01/02","2022/02/19",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "SE-0001","Selina","Bogotá","Single",3900.0,"2022/01/23","2022/11/23",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "SE-0002","Selina 2","Bogotá","Doble",5840.0,"2022/01/23","2022/10/15",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "EC-0003","El Campín","Bogotá","Triple",7020.0,"2022/02/15","2022/03/27",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "CP-0004","Central Plaza","Medellín","Múltiple",8600.0,"2022/03/01","2022/04/17",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "CP-0002","Central Plaza 2","Medellín","Doble",6400.0,"2022/02/10","2022/03/20",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "BG-0004","Bocagrande","Cartagena","Múltiple",9370.0,"2022/04/17","2022/06/22",false);

insert into persona (name, last_name, email, dni, birth_date)
values ('Abramo', 'McCathie', 'amccathie0@gnu.org', 92171282, '2023/01/13');
insert into persona (name, last_name, email, dni, birth_date)
values('Hoebart', 'Heardman', 'hheardman1@cocolog-nifty.com', 55317993, '2022/12/05');
insert into persona (name, last_name, email, dni, birth_date)
values ('Darwin', 'Greenaway', 'dgreenaway2@ucoz.com', 37837924, '2022/12/12');
insert into persona (name, last_name, email, dni, birth_date)
values ('Leoline', 'Sagg', 'lsagg3@toplist.cz', 40566618, '2022/09/16');
insert into persona (name, last_name, email, dni, birth_date)
values ('Honoria', 'Corson', 'hcorson4@lulu.com', 64511629, '2022/11/28');
insert into persona (name, last_name, email, dni, birth_date)
values ('Sigfrid', 'Sears', 'ssears5@statcounter.com', 61609629, '2022/12/09');
insert into persona (name, last_name, email, dni, birth_date)
values ('Adelle', 'Grombridge', 'agrombridge6@oracle.com', 79857129, '2022/08/12');
insert into persona (name, last_name, email, dni, birth_date)
values ('Bernelle', 'MacKean', 'bmackean7@google.ca', 91833612, '2022/10/15');
insert into persona (name, last_name, email, dni, birth_date)
values ('Isaac', 'Andrzejak', 'iandrzejak8@php.net', 34973514, '2023/01/20');
insert into persona (name, last_name, email, dni, birth_date)
values ('Wolfgang', 'Pickervance', 'wpickervance9@upenn.edu', 54732302, '2023/03/17');

INSERT INTO payment_method(dues, number, type)
VALUES (1,"1234567891234567","debitCard");

INSERT INTO payment_method(dues, number, type)
VALUES (2,"1234567891234123","creditCard");
INSERT INTO payment_method(dues, number, type)
VALUES (3,"1234567891234123","creditCard");
INSERT INTO payment_method(dues, number, type)
VALUES (4,"1234567891234123","creditCard");
INSERT INTO payment_method(dues, number, type)
VALUES (5,"1234567891234123","creditCard");
INSERT INTO payment_method(dues, number, type)
VALUES (6,"1234567891234123","creditCard");

INSERT INTO datos_reserva_hotel (date_from, dato_to, destination, hotel_code, people_amount, room_type, total,hotel_model_id, payment_method_id)
VALUES ("2022/02/10", "2022/03/20", "Puerto Iguazú", "CH-0002", 2, "Doble", 50000,1, 1);

INSERT INTO datos_reserva_hotel_people (booking_model_id, people_id)
VALUES (1, 1);
INSERT INTO datos_reserva_hotel_people (booking_model_id, people_id)
VALUES (1, 2);

INSERT INTO reserva_hotel (user_name, booking_id)
VALUES ("amccathie0@gnu.org", 1);
