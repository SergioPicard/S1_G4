INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES("BAPI-1235","Buenos Aires","Puerto Iguazú","Economy", 6500.0, "2022/02/10","2022/02/15");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES("PIBA-1420","Puerto Iguazú","Bogotá","Business",43200.0,"2022/02/10","2022/02/20");

INSERT INTO vuelo(nro_vuelo,origen,destino,tipo_asiento,precio_persona,fecha_ida,fecha_vuelta)
VALUES( "PIBA-1420", "Puerto Iguazú", "Bogotá","Economy", 25735.0,"2022/02/10","2022/02/21");





INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "CH-0002","Cataratas Hotel","Puerto Iguazú","Doble",6300.0,"2022/02/10","2022/03/20",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "CH-0003","Cataratas Hotel 2","Puerto Iguazú","Triple",8200.0,"2022/02/10","2022/03/23",false);

INSERT INTO hotel(codigo_hotel,nombre,lugar,tipo_habitacion,precio_noche,disponible_desde,disponible_hasta,reservado)
VALUES( "HB-0001","Hotel Bristol","Buenos Aires","Single",5435.0,"2022/02/10","2022/03/19",false);


insert into persona (name, last_name, email, dni, birth_date)
values ('Abramo', 'McCathie', 'amccathie0@gnu.org', 92171282, '2023/01/13');
insert into persona (name, last_name, email, dni, birth_date)
values('Hoebart', 'Heardman', 'hheardman1@cocolog-nifty.com', 55317993, '2022/12/05');
insert into persona (name, last_name, email, dni, birth_date)
values ('Darwin', 'Greenaway', 'dgreenaway2@ucoz.com', 37837924, '2022/12/12');


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

--INSERT INTO datos_reserva_hotel (date_from, dato_to, destination, hotel_code, people_amount, room_type, total,hotel_model_id, payment_method_id)
--VALUES ("2022/02/10", "2022/03/20", "Puerto Iguazú", "CH-0002", 2, "Doble", 50000,1, 1);

--INSERT INTO datos_reserva_hotel_people (booking_model_id, people_id)
--VALUES (1, 1);
--INSERT INTO datos_reserva_hotel_people (booking_model_id, people_id)
--VALUES (1, 2);

--INSERT INTO reserva_hotel (user_name, booking_id)
--VALUES ("amccathie0@gnu.org", 1);

--INSERT INTO datos_reserva_vuelo(date_from, dato_to, destination, flight_number, origin, seat_type, seats, flight_model_id)
--VALUES("2022/02/10","2022/02/15","Puerto Iguazú","BAPI-1235","Buenos Aires","Economy", 1, 1);

--INSERT INTO req_reserva_vuelo(user_name, flight_reservation_id, payment_method_dto_id)
--VALUES ("amccathie0@gnu.org", 1, 6);

--INSERT INTO datos_reserva_vuelo_people(flight_reservation_res_model_id, people_id)
--VALUES (1, 1);

--INSERT INTO reserva_vuelo(total, user_name, flight_reservation_res_model_id, payment_method_id)
--VALUES (5000, "amccathie0@gnu.org", 1, 6);