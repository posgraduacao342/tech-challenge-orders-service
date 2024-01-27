CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE itens (
                       id UUID NOT NULL DEFAULT uuid_generate_v4(),
                       id_pedido UUID NOT NULL,
                       id_produto UUID NOT NULL,
                       observacoes varchar(500),
                       quantidade integer NOT NULL,
                       CONSTRAINT pedidoProdutos_pkey PRIMARY KEY (id),
                       CONSTRAINT fk_pedidos FOREIGN KEY (id_pedido) REFERENCES pedidos (id),
                       CONSTRAINT fk_produtos FOREIGN KEY (id_produto) REFERENCES produtos (id)
);