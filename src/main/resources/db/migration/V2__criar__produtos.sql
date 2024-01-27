CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE produtos (
                          id UUID NOT NULL DEFAULT uuid_generate_v4(),
                          nome varchar(255) NOT NULL,
                          preco money NOT NULL,
                          imagem varchar(255),
                          descricao varchar(500),
                          categoria varchar(50) NOT NULL,
                          data_criacao timestamptz NOT NULL DEFAULT now(),
                          data_delecao timestamptz,
                          data_atualizacao timestamptz NOT NULL DEFAULT now(),
                          CONSTRAINT produtos_pkey PRIMARY KEY (id)
);