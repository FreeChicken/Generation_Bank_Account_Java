package conta.model;

public abstract class Conta {

	private int numero;
	private int agencia;
	private int tipo;
	private String titular;
	private float saldo;

	public Conta(int numero, int agencia, int tipo, String titular, float saldo) {
		this.numero = numero;
		this.agencia = agencia;
		this.tipo = tipo;
		this.titular = titular;
		this.saldo = saldo;

	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public boolean sacar(float valor) {
		if (this.getSaldo() < valor) {
			System.out.println("\n Saldo Insuficiente!");
			return false;
		}

		this.setSaldo(this.getSaldo() - valor);
		return true;
	}

	public void depositar(float valor) {
		this.setSaldo(this.getSaldo() + valor);
	}
	
	//Adiçoes de código para persitencia de dados em .txt
	public String toString() {
	    if (this instanceof ContaCorrente) {
	        ContaCorrente cc = (ContaCorrente) this;
	        return cc.getNumero() + ";" + cc.getAgencia() + ";" + cc.getTipo() + ";" + cc.getTitular() + ";" + cc.getSaldo() + ";" + cc.getLimite();
	    } else if (this instanceof ContaPoupanca) {
	        ContaPoupanca cp = (ContaPoupanca) this;
	        return cp.getNumero() + ";" + cp.getAgencia() + ";" + cp.getTipo() + ";" + cp.getTitular() + ";" + cp.getSaldo() + ";" + cp.getAniversario();
	    } else {
	        return ""; 
	    }
	}

	public void visualizar() {
		String tipo = "";
		switch (this.tipo) {
		case 1:
			tipo = "Conta Corrente";
			break;
		case 2:
			tipo = "Conta Poupanca";
			break;
		}

		System.out.println("\n\n*********************************************************************************");
		System.out.println("Dados da Conta:");
		System.out.println("*********************************************************************************");
		System.out.println("Numero da Conta: " + this.numero);
		System.out.println("Agencia: " + this.agencia);
		System.out.println("Tipo da Conta: " + tipo);
		System.out.println("Titular: " + this.titular);
		System.out.println("Saldo: " + this.saldo);
	}

}