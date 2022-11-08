package br.com.barrigarest.rest.test;

public class Movimentacao {
	
	private Integer id; // id da movimentação
	private String tipo; // tipo da movimentacao (Receita / Despesa )
	private String data_transacao; // data da movimentacao não pode ser futura
	private String data_pagamento; // data pagamento
	private String descricao;
	private String envolvido;
	private float valor; 
	private Integer conta_id; // id que será incluido a movimentacao
	private boolean status; // pago ou pendente
	private Integer usuario_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipo_mov() {
		return tipo;
	}
	public void setTipo_mov(String tipo_mov) {
		this.tipo = tipo_mov;
	}
	public String getData_transacao() {
		return data_transacao;
	}
	public void setData_transacao(String data_transacao) {
		this.data_transacao = data_transacao;
	}
	public String getData_pagamento() {
		return data_pagamento;
	}
	public void setData_pagamento(String data_pagamento) {
		this.data_pagamento = data_pagamento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEnvolvido() {
		return envolvido;
	}
	public void setEnvolvido(String envolvido) {
		this.envolvido = envolvido;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public Integer getConta_id() {
		return conta_id;
	}
	public void setConta_id(Integer conta_id) {
		this.conta_id = conta_id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Integer getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}
	
	
}
