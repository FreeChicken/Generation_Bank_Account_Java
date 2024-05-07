package conta;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conta.controller.ContaController;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;
import conta.util.Cores;

public class Menu {

	public static Scanner read = new Scanner(System.in);

	public static void main(String[] args) {

		ContaController contas = new ContaController();
		//Adiçoes de código para persitencia de dados em .txt
		contas.carregarContasDoArquivo();

		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor;

		/*
		 * ContaCorrente cc1 = new ContaCorrente(contas.gerarNumero(), 123, 1,
		 * "João Snow", 1000f, 100.0f); contas.cadastrar(cc1);
		 * 
		 * ContaCorrente cc2 = new ContaCorrente(contas.gerarNumero(), 124, 1,
		 * "Ariana Stark", 2000f, 100.0f); contas.cadastrar(cc2);
		 * 
		 * ContaPoupanca cp1 = new ContaPoupanca(contas.gerarNumero(), 125, 2,
		 * "Sonsa Stark", 4000f, 12); contas.cadastrar(cp1);
		 * 
		 * ContaPoupanca cp2 = new ContaPoupanca(contas.gerarNumero(), 126, 2,
		 * "Dayana Targaryen", 8000f, 15); contas.cadastrar(cp2);
		 */

		contas.listarTodas();

		while (true) {

			System.out.println(Cores.TEXT_BLUE_BOLD_BRIGHT + Cores.ANSI_BLACK_BACKGROUND
					+ "*****************************************************");
			System.out.println("                BANCO DO BRAZIL COM Z                ");
			System.out.println("*****************************************************");
			System.out.println("            1 - Criar Conta                          ");
			System.out.println("            2 - Listar todas as Contas               ");
			System.out.println("            3 - Buscar Conta por Numero              ");
			System.out.println("            4 - Atualizar Dados da Conta             ");
			System.out.println("            5 - Apagar Conta                         ");
			System.out.println("            6 - Sacar                                ");
			System.out.println("            7 - Depositar                            ");
			System.out.println("            8 - Transferir valores entre Contas      ");
			System.out.println("            9 - Sair                                 ");
			System.out.println("*****************************************************");
			System.out.println("Entre com a opcao desejada:                          ");
			System.out.println("                                                     " + Cores.TEXT_RESET);

			try {

				opcao = read.nextInt();

			} catch (InputMismatchException e) {
				System.out.println("\nDigite valores inteiros!");
				read.nextLine();
				opcao = 0;
			}

			if (opcao == 9) {
				System.out.println(Cores.TEXT_WHITE_BOLD + "\nBanco do Brazil com Z - O seu Futuro começa aqui!");
				read.close();
				System.exit(0);
			}

			switch (opcao) {
			case 1:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Criar Conta\n\n");

				System.out.println("Digite o Numero da Agência: ");
				agencia = read.nextInt();
				System.out.println("Digite o Nome do Titular: ");
				read.skip("\\R?");
				titular = read.nextLine();

				do {
					System.out.println("Digite o Tipo da Conta (1-CC ou 2-CP): ");
					tipo = read.nextInt();
				} while (tipo < 1 && tipo > 2);

				System.out.println("Digite o Saldo da Conta (R$): ");
				saldo = read.nextInt();

				switch (tipo) {
				case 1 -> {
					System.out.println("Digite o Limite de Crédito (R$): ");
					limite = read.nextFloat();
					contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
				}
				case 2 -> {
					System.out.println("Digite o dia do Aniversario da Conta: ");
					aniversario = read.nextInt();
					contas.cadastrar(
							new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
				}
				}
				keyPress();
				break;
			case 2:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Listar todas as Contas\n\n");
				contas.listarTodas();
				keyPress();
				break;
			case 3:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Consultar dados da Conta - por número\n\n");
				System.out.println("Digite o número da conta: ");
				numero = read.nextInt();
				contas.procurarPorNumero(numero);
				keyPress();
				break;
			case 4:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Atualizar dados da Conta\n\n");

				System.out.println("Digite o Número da conta: ");
				numero = read.nextInt();
				var buscaConta = contas.buscarNaCollection(numero);

				if (buscaConta != null) {
					tipo = buscaConta.getTipo();
					System.out.println("Digite o Número da Agência: ");
					agencia = read.nextInt();
					System.out.println("Digite o Nome do Titular: ");
					read.skip("\\R?");
					titular = read.nextLine();

					System.out.println("Digite o Saldo da Conta (R$): ");
					saldo = read.nextFloat();

					switch (tipo) {
					case 1 -> {
						System.out.println("Digite o Limite de Crédito: ");
						limite = read.nextFloat();

						contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
					}
					case 2 -> {
						System.out.println("Digite o Dia do Aniversario da Conta: ");
						aniversario = read.nextInt();

						contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
					}
					default -> {
						System.out.println("Tipo de Conta Inválida!");
					}
					}
				} else {
					System.out.println("A Conta não foi encontrada!");
				}
				keyPress();
				break;
			case 5:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Apagar a Conta\n\n");
				System.out.println("Digite o número da conta: ");
				numero = read.nextInt();

				contas.deletar(numero);

				keyPress();
				break;
			case 6:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Saque\n\n");

				System.out.println("Digite o número da conta: ");
				numero = read.nextInt();

				do {
					System.out.println("Digite o Valor do Saque (R$): ");
					valor = read.nextFloat();
				} while (valor <= 0);

				contas.sacar(numero, valor);

				keyPress();
				break;
			case 7:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Depósito\n\n");

				System.out.println("Digite o número da conta: ");
				numero = read.nextInt();

				do {
					System.out.println("Digite o Valor do Depósito (R$): ");
					valor = read.nextFloat();
				} while (valor <= 0);

				contas.depositar(numero, valor);

				keyPress();
				break;
			case 8:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Transferência entre Contas\n\n");

				System.out.println("Digite o número da conta de Origem: ");
				numero = read.nextInt();
				System.out.println("Digite o número da conta de Destino: ");
				numeroDestino = read.nextInt();

				do {
					System.out.println("Digite o Valor da Transferência (R$): ");
					valor = read.nextFloat();
				} while (valor <= 0);

				contas.transferir(numero, numeroDestino, valor);

				keyPress();
				break;
			default:
				System.out.println(Cores.TEXT_RED_BOLD + "\nOpção Inválida!\n");
				keyPress();
				break;
			}
		}
	}

	public static void sobre() {
		System.out.println("\n*********************************************************");
		System.out.println("Projeto Desenvolvido por: Anderson Alves");
		System.out.println("Generation Brasil - ");
		System.out.println("github.com/");
		System.out.println("*********************************************************");
	}

	public static void keyPress() {
		try {
			System.out.println(Cores.TEXT_RESET + "\n\nPressione 'Enter' para Contuar...");
			System.in.read();
		} catch (IOException e) {
			System.out.println("Você pressionou uma tecla diferente de enter!");
		}
	}
}