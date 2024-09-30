-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.ticket
(
    ticket_id smallint NOT NULL,
    ticket_name character varying(30) NOT NULL,
    ticket_price smallint NOT NULL,
    ticket_ratio smallint NOT NULL,
    ticket_age_upper_limit smallint NOT NULL,
    ticket_age_lower_limit smallint NOT NULL,
    ticket_description character varying(256) NOT NULL,
    PRIMARY KEY (ticket_id)
);

COMMENT ON TABLE public.ticket
    IS 'To record ticket type';

CREATE TABLE IF NOT EXISTS public.station
(
    station_id smallserial NOT NULL,
    station_name character varying(30) NOT NULL,
    station_index smallint NOT NULL,
    PRIMARY KEY (station_id)
);

COMMENT ON TABLE public.station
    IS 'To record station';

CREATE TABLE IF NOT EXISTS public.restaurant
(
    restaurant_id serial NOT NULL,
    restaurant_name character varying(50) NOT NULL,
    restaurant_image_url character varying(256),
    station_id smallint NOT NULL,
    restaurant_image_blob bytea,
    PRIMARY KEY (restaurant_id)
);

COMMENT ON TABLE public.restaurant
    IS 'To record restaurant information';

CREATE TABLE IF NOT EXISTS public.station_restaurant
(
    station_restaurant_id serial NOT NULL,
    station_id smallint NOT NULL,
    restaurant_id integer NOT NULL,
    PRIMARY KEY (station_restaurant_id)
);

COMMENT ON TABLE public.station_restaurant
    IS 'To connect the station and restaurant';

CREATE TABLE IF NOT EXISTS public.attraction
(
    attraction_id serial NOT NULL,
    attraction_name character varying(50) NOT NULL,
    attraction_image_url character varying(256),
    station_id smallint NOT NULL,
    attraction_image bytea,
    PRIMARY KEY (attraction_id)
);

COMMENT ON TABLE public.attraction
    IS 'To record attractions';

CREATE TABLE IF NOT EXISTS public.station_attraction
(
    station_attraction_id serial NOT NULL,
    station_id integer NOT NULL,
    attration_id integer NOT NULL,
    PRIMARY KEY (station_attraction_id)
);

COMMENT ON TABLE public.station_attraction
    IS 'To connect the station and attraction';

CREATE TABLE IF NOT EXISTS public."user"
(
    user_id serial NOT NULL,
    user_email character varying(256) NOT NULL,
    user_name character varying(256) NOT NULL,
    user_password character varying(256) NOT NULL,
    user_age smallint NOT NULL,
    wallet_id integer NOT NULL,
    PRIMARY KEY (user_id)
);

COMMENT ON TABLE public."user"
    IS 'To record user';

CREATE TABLE IF NOT EXISTS public.user_order
(
    user_order_id serial NOT NULL,
    user_id integer NOT NULL,
    order_id integer NOT NULL,
    expired boolean NOT NULL,
    PRIMARY KEY (user_order_id)
);

COMMENT ON TABLE public.user_order
    IS 'To connect the user and order';

CREATE TABLE IF NOT EXISTS public."order"
(
    order_id integer NOT NULL,
    ticket_id smallint NOT NULL,
    schedule_id smallint NOT NULL,
    PRIMARY KEY (order_id)
);

COMMENT ON TABLE public."order"
    IS 'To record order';

CREATE TABLE IF NOT EXISTS public.schedule
(
    schedule_id smallint NOT NULL,
    schedule_name character varying(30) NOT NULL,
    schedule_tickettotal smallint NOT NULL,
    schedule_ticketless smallint NOT NULL,
    schedule_departure_time timestamp without time zone NOT NULL,
    schedule_arrival_time timestamp without time zone NOT NULL,
    schedule_departure_station_id smallint NOT NULL,
    schedule_arrival_station_id smallint NOT NULL,
    PRIMARY KEY (scedule_id)
);

COMMENT ON TABLE public.schedule
    IS 'To record schedule';

CREATE TABLE IF NOT EXISTS public.schedule_ticket
(
    schedule_ticket_id serial NOT NULL,
    schedule_id smallint NOT NULL,
    ticket_id integer,
    PRIMARY KEY (schedule_ticket_id)
);

COMMENT ON TABLE public.schedule_ticket
    IS 'To connect the schedule and ticket';

CREATE TABLE IF NOT EXISTS public.wallet
(
    wallet_id serial NOT NULL,
    user_id integer NOT NULL,
    wallet_amount integer NOT NULL,
    PRIMARY KEY (wallet_id)
);

COMMENT ON TABLE public.wallet
    IS '錢包資訊';

CREATE TABLE IF NOT EXISTS public.wallet_log
(
    id serial NOT NULL,
    wallet_id integer NOT NULL,
    wallet_transaction smallint NOT NULL,
    amount_transaction integer NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE public.wallet_log
    IS '紀錄 wallet 的 log';
END;