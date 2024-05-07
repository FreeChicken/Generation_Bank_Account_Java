package conta.controller;

//imports para persitencia de dados em .txt
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;
import java.util.Scanner;

import java.util.ArrayList;
import conta.model.Conta;
import conta.repository.ContaRepository;

public class ContaController implements ContaRepository {

	private ArrayList<Conta> listaContas = new ArrayList<Conta>();
	int numero = 0;
	
	//Adiçoes de código para persitencia de dados em .txt
	private static final String BANCO_DADOS = "contas.txt";

	public void carregarContasDoArquivo() {
		try (Scanner scanner = new Scanner(new File(BANCO_DADOS))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(";");
				int numero = Integer.parseInt(parts[0]);
				int agencia = Integer.parseInt(parts[1]);
				int tipo = Integer.parseInt(parts[2]);
				String titular = parts[3];
				float saldo = Float.parseFloat(parts[4]);
				if (tipo == 1) {
					float limite = Float.parseFloat(parts[5]);
					listaContas.add(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
				} else if (tipo == 2) {
					int aniversario = Integer.parseInt(parts[5]);
					listaContas.add(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
				}
			}
		} catch (IOException e) {
		}
	}

	@Override
	public void procurarPorNumero(int numero) {
		var conta = buscarNaCollection(numero);

		if (conta != null)
			conta.visualizar();
		else
			System.out.println("\nA Conta número: " + numero + " não foi encontrada!");

	}

	@Override
	public void listarTodas() {
		for (var conta : listaContas) {
			conta.visualizar();
		}

	}

	@Override
	public void cadastrar(Conta conta) {
		listaContas.add(conta);
		salvarContasEmArquivo();
		System.out.println("\nA Conta número: " + conta.getNumero() + " foi criada com sucesso!");

	}

	@Override
	public void atualizar(Conta conta) {
		var buscaConta = buscarNaCollection(conta.getNumero());

		if (buscaConta != null) {
			listaContas.set(listaContas.indexOf(buscaConta), conta);
			System.out.println("\nA Conta numero: " + conta.getNumero() + " foi atualizada com sucesso!");
			salvarContasEmArquivo();

		} else
			System.out.println("\nA Conta numero: " + conta.getNumero() + " não foi encontrada!");

	}

	@Override
	public void deletar(int numero) {
		var conta = buscarNaCollection(numero);

		if (conta != null) {
			if (listaContas.remove(conta) == true)
				;
			System.out.println("\nA Conta numero: " + numero + " foi deletada com sucesso!");
			salvarContasEmArquivo();
		} else
			System.out.println("\nA Conta numero: " + numero + " não foi encontrada!");

	}

	@Override
	public void sacar(int numero, float valor) {
		var conta = buscarNaCollection(numero);

		if (conta != null) {
			if (conta.sacar(valor) == true)
				System.out.println("\nO Saque na Conta numero: " + numero + " foi atualizada com sucesso!");
			salvarContasEmArquivo();
		} else
			System.out.println("\nA Conta numero: " + numero + " não foi encontrada!");

	}

	@Override
	public void depositar(int numero, float valor) {
		var conta = buscarNaCollection(numero);

		if (conta != null) {
			conta.depositar(valor);
			System.out.println("\nO Deposito na Conta numero: " + numero + " foi atualizada com sucesso!");
			salvarContasEmArquivo();
		} else
			System.out.println("\nA Conta numero: " + numero
					+ " não foi encontrada ou a Conta de destino não é uma Conta Corrente!");

	}

	@Override
	public void transferir(int numeroOrigem, int numeroDestino, float valor) {
		var contaOrigem = buscarNaCollection(numeroOrigem);
		var contaDestino = buscarNaCollection(numeroDestino);

		if (contaOrigem != null && contaDestino != null) {
			if (contaOrigem.sacar(valor) == true) {
				contaDestino.depositar(valor);
				System.out.println("\nA Transferencia oi efetuada com sucesso!");
				salvarContasEmArquivo();
			}
		} else
			System.out.println("\nA Conta de Origem e/ou Destino não foi encontrada!");

	}

	//Adiçoes de código para persitencia de dados em .txt
	private void salvarContasEmArquivo() {
		try (FileWriter writer = new FileWriter(BANCO_DADOS)) {
			for (Conta conta : listaContas) {
				writer.write(conta.toString());
				writer.write("\n");
			}
		} catch (IOException e) {
		}
	}

	public int gerarNumero() {
		return ++numero;
	}

	public Conta buscarNaCollection(int numero) {
		for (var conta : listaContas) {
			if (conta.getNumero() == numero) {
				return conta;

			}
		}
		return null;
	}
}