# Para la resolución de la actividad individual se crearon los endpoint:

- /api/v1/flightReservation/{destino}. Utilizado para buscar reservas que tiene la
agencia para los diferentes destinos.
- /api/v1/flightsTotal. Utilizado para ver el total de ventas de cada vuelo a fin de
poder visualizar el alcance con respecto a los objetivos de venta.
- /api/v1/flightsTotalflightsReservationsBetweenDate?fecha1&fecha2. Utilizado para poder visualizar
las reservas que tiene la agencia en un determinado rango de fechas.

Para los cuales los métodos del service y los endpoint del controlador se encuentran al final de los mismos
y con un comentario indicando que corresonden a dicha actividad y los test unitarios estan en testService
al principio también comentados.

- Se deja Collection de postman en una carpeta actividadIndividual en resourses para poder crear vuelos, 
reservas y realizar las consultas correspondientes.

- En la misma carpeta se de deja PDF con el detalle de los nuevos requerimientos.
