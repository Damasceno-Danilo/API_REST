package br.com.barrigarest.rest.test;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.barrigarest.rest.core.BaseTest;
import br.com.barrigarest.rest.utils.DataUtils;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarrigaTest extends BaseTest{
	
	
	private static String CONTA_NOME = "Conta" + System.nanoTime();
	private static Integer CONTA_ID;
	private static Integer MOV_ID;
	
	@BeforeClass
	public static void login() {
		
		Map<String, String> login = new HashMap<>();
		login.put("email", "eudanilo@email");
		login.put("senha", "152810");
		
		String TOKEN =  given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token");	
		
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
	}
	
	
	
	@Test
	public void t01_deveIncluirContaComSucesso() {
		
		CONTA_ID = given()
			.body("{ \"nome\": \""+CONTA_NOME+"\" }")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			.extract().path("id")
		;
	}
	
	@Test
	public void t02_deveAlterarContaComSucesso() {
		
		given()
			.body("{ \"nome\": \""+CONTA_NOME+" alterada\" }")
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.statusCode(200)
		;
	}
	
	@Test
	public void t03_naoIncluirContaMesmoNome() {
		
		given()
			.body("{ \"nome\": \""+CONTA_NOME+" alterada\" }")
		.when()
			.post("/contas/1468290")
		.then()
			.statusCode(404)
		;
	}
	
	@Test
	public void t04_deveInserirMovimentacaoComSucesso() {
		
		Movimentacao mov = getMovimentacaoValida();
		
		MOV_ID =  given()
			.body(mov)
			.when()
				.post("/transacoes")
			.then()
				.statusCode(201)
				.extract().path("id")
		;
	}
	
	@Test
	public void t05_deveValidarCamposObrigatoriosNaMovimentacao() {
		
		given()
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
	;
	}
	
	@Test
	public void t06_naoDeveCadastrarMovimentacaoFutura() {
		
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao(DataUtils.setDataDiferecaDias(2));
		
		given()
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
	;
	}
	
	@Test
	public void t07_naoRemoverContaComMovimentacao() {
		
		given()
			.pathParam("id", CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
	;
	}
	
	@Test
	public void t08_deveCalcularSaldoDasContas() {
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
	;
	}
	
	
	@Test
	public void t09_deveRemoverMovimentacao() {
		
		given()
			.pathParam("id", MOV_ID)
		.when()
			.delete("transacoes/{id}")
		.then()
			.statusCode(204)
	;
	}
	
	@Test
	public void t10_naoDeveAcessarAPISemToken() {
		
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
	}
	
	private Movimentacao getMovimentacaoValida() {
		
		Movimentacao mov = new Movimentacao(); // Objeto criado através da classe Movimentacao
		
		mov.setConta_id(CONTA_ID);
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido Movimentacao");
		mov.setTipo_mov("REC");
		mov.setData_transacao(DataUtils.setDataDiferecaDias(-1)); //referenciar sempre a data anterior ao dia atual
		mov.setData_pagamento(DataUtils.setDataDiferecaDias(5)); //referenciar sempre a data futura
		mov.setValor(150f);
		mov.setStatus(true);
		return mov;
	}

}


