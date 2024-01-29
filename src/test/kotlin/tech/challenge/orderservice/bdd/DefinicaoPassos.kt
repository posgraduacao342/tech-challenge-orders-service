package tech.challenge.orderservice.bdd

import io.cucumber.java.it.Quando
import io.cucumber.java.pt.Entao
import io.restassured.RestAssured.given
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import tech.challenge.orderservice.application.presenters.responses.pedido.PedidoResponse
import tech.challenge.orderservice.helpers.PedidoHelper

class DefinicaoPassos {

    private lateinit var response: Response

    private lateinit var pedidoResponse: PedidoResponse

    private var ENDPOINT_API_CLIENTE: String = "http://localhost:8080/pedidos"


    @Quando("cadastro um pedido")
    fun registrar_um_novo_pedido(): PedidoResponse {
        val pedidoRequest = PedidoHelper.gerarPedidoRequest()

        response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(pedidoRequest)
            .`when`()
            .post(ENDPOINT_API_CLIENTE)
        pedidoResponse = response.then().extract().`as`(PedidoResponse::class.java)

        return response.then().extract().`as`(PedidoResponse::class.java)
    }

    @Entao("o pedido é registrado com sucesso")
    fun pedido_registrado_com_sucesso() {
        response.then()
            .statusCode(HttpStatus.OK.value())
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("./schemas/ClienteSchema.json"))
    }

    @Entao("deve ser aprsentado")
    fun deve_ser_apresentado() {
        response.then()
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("./schemas/ClienteSchema.json"))
    }
}