CREATE TABLE public.lancamento
(
  oid bigint NOT NULL,
  dt_final timestamp without time zone,
  dt_inicial timestamp without time zone,
  observacao character varying(1000),
  vl_total double precision,
  CONSTRAINT lancamento_pkey PRIMARY KEY (oid)
);

CREATE TABLE public.item
(
  oid bigint NOT NULL,
  descricao character varying(255),
  valor double precision,
  CONSTRAINT item_pkey PRIMARY KEY (oid)
);

CREATE TABLE public.lancamentoitem
(
  oid_lancamento bigint NOT NULL,
  oid_item bigint NOT NULL,
  CONSTRAINT pk_lancamentoitem PRIMARY KEY (oid_lancamento, oid_item),
  CONSTRAINT fk_item FOREIGN KEY (oid_item) REFERENCES public.item (oid) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_lancamento FOREIGN KEY (oid_lancamento) REFERENCES public.lancamento (oid) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.item_seq
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 999999999999999999;

CREATE SEQUENCE public.lancamento_seq
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 999999999999999999;

