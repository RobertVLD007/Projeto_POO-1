package main;
import banco.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Conta> contas = new ArrayList<>();

    public static void main(String[] args) {
        Conta conta = null;

        System.out.println("SISTEMA BANCÁRIO INICIADO!");

        // LOOP CENTRAL
        while (true) {
            try {
                System.out.println("\nESCOLHA AS OPÇÕES DISPONÍVEIS");
                System.out.println("(1) JÁ POSSUO SESSÃO ATIVA \n(2) NOVO CLIENTE \n(0) ENCERRAR PROGRAMA");
                int opcao = sc.nextInt();
                sc.nextLine();

                if (opcao < 0 || opcao > 2) {
                    System.out.println("OPÇÃO INVÁLIDA. INSIRA OUTRA OPÇÃO");
                }

                // ENCERRANDO LOOP
                else if (opcao == 0) {
                    System.out.println("OBRIGADO POR USAR NOSSO SISTEMA!");
                    break;
                }

                // CHAMANDO METODO PARA CRIAÇÃO/PROCURA DA CONTA
                else {
                    conta = registroConta(opcao);
                }

                System.out.println("INFORMAÇÕES DA CONTA:");
                // MOSTRANDO TITULAR DA CONTA
                System.out.println(conta.toString());

                while (conta != null) {
                    System.out.println("\nESCOLHA UMA OPÇÃO:");
                    System.out.println("(1) - CHECAR SALDO \n(2) - REALIZAR SAQUE \n(3) - REALIZAR DEPÓSITO");
                    System.out.println("(4) - VISUALIZAR ÚLTIMO SAQUE \n(5) - VISUALIZAR ÚLTIMO DEPÓSITO \n(6) - REALIZAR TRANSFERÊNCIA");
                    System.out.println("(7) - CHECAR CONTAS CADASTRADAS \n(0) - ENCERRAR PROGRAMA");
                    System.out.print("=>");

                    opcao = sc.nextInt();
                    if (opcao == 0) {
                        System.out.println("OBRIGADO POR USAR NOSSO SISTEMA!");
                        break;
                    }
                    switch (opcao) {
                        // CONFERIR SALDO ATUAL DE UMA CONTA
                        case 1:
                            System.out.println("SALDO ATUAL DA CONTA: " + conta.getSaldo());
                            break;

                        // REALIZAR SAQUE
                        case 2:
                            if (conta.getSaldo() == 0) {
                                System.out.println("SALDO INEXISTENTE PARA SAQUE...\n");
                                break;
                            }

                            System.out.println("INSIRA VALOR PARA SAQUE: ");
                            double valorSaque = sc.nextDouble();
                            conta.sacar(valorSaque);
                            break;

                        // REALIZAR DEPÓSITO
                        case 3:
                            System.out.println("INSIRA VALOR PARA DEPOSITO: ");
                            double valorDeposito = sc.nextDouble();
                            conta.depositar(valorDeposito);
                            break;

                        // VISUALIZAR ULTIMO SAQUE
                        case 4:
                            System.out.println("ULTIMO SAQUE REALIZADO: \n" + conta.getTransacoes(1));
                            break;

                        // VISUALIZAR ULTIMO DEPÓSITO
                        case 5:
                            System.out.println("ULTIMO DEPÓSITO REALIZADO: \n" + conta.getTransacoes(2));
                            break;

                        // TRANSFERÊNCIA ENTRE CONTAS
                        case 6:
                            if (contas.size() == 1) {
                                System.out.println("NO MOMENTO NÃO HÁ CONTAS REGISTRADAS PARA TRANSFERÊNCIA...\n");
                            }
                            else {
                                int escolha = -1;
                                double valor = -1;

                                System.out.println("\nSEU SALDO ATUAL: " + conta.getSaldo());

                                while(valor < 0) {
                                    System.out.print("\nQUAL SERÁ O VALOR DA TRANSFERÊNCIA?");
                                    System.out.print("=>");
                                    if (sc.hasNextDouble()) {
                                        valor = sc.nextDouble();
                                        sc.nextLine();

                                        if (valor < 0 || valor > conta.getSaldo()) {
                                            System.out.println("ERRO! VALOR INSUFICIENTE!");
                                            valor = -1;
                                        }
                                    } else {
                                        System.out.println("INSIRA UM VALOR VÁLIDO");
                                        sc.nextLine();
                                    }
                                }

                                System.out.println("SELECIONE A CONTA QUE DESEJA TRANSFERIR:");
                                int i = 1;
                                for (Conta c : contas) {
                                    if (c.getNumeroConta() == conta.getNumeroConta()) {
                                        continue;
                                    }

                                    System.out.printf("CONTA %d: %d\n", i, c.getNumeroConta());
                                    i++;
                                }

                                System.out.printf("\n\nSELECIONE A CONTA DE 1 A %d", contas.size() - 1);
                                if (sc.hasNextInt()) {
                                    escolha = sc.nextInt();
                                    sc.nextLine();

                                    if (escolha < 1 || escolha > contas.size()) {
                                        System.out.println("SELECIONE UMA OPÇÃO VÁLIDA...\n");
                                        sc.nextLine();
                                    } else {
                                        conta.transferir(contas.get(escolha - 1), valor);
                                    }
                                }
                                else {
                                    System.out.println("SELECIONE UMA OPÇÃO VÁLIDA...");
                                    sc.nextLine();
                                }
                            }

                            break;

                        // LISTAGEM DE CONTAS ATIVAS
                        case 7:
                            for (Conta c : contas) {
                                System.out.println(c.toString());
                            }
                            break;

                        default:
                            System.out.println("OPÇÃO INVÁLIDA. INSIRA UMA OPÇÃO VÁLIDA");
                            break;
                    }
                }

            }


            catch (InputMismatchException e) {
                System.out.println("ENTRADA DE DADOS INVÁLIDA. TENTE NOVAMENTE... \n");
            }
            catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
            catch (Exception e) {
                System.out.println("ERRO INESPERADO. TENTE NOVAMENTE! \nMOTIVO: " + e.getMessage());
            }
        }


    }

    public static Conta registroConta(int opcao) {
        Conta c = null;

        switch (opcao) {
            case 1:
                System.out.println("PROCURANDO CONTAS....");

                if (contas.size() == 0) {
                    System.out.println("NENHUMA SESSÃO ENCONTRADA \nINICIANDO CADASTRO...");
                    c = criacaoConta();

                } else {

                    // MOSTRA TODAS AS CONTAS ATIVAS
                    int i = 1;
                    for (Conta cc : contas) {
                        System.out.printf("CONTA %d: %d\n", i, cc.getNumeroConta());
                        i++;
                    }

                    // SELEÇÃO DE CONTAS
                    int escolha = -1;
                    while (true) {
                        System.out.printf("\nSELECIONE SUA CONTA (1 a %d): ", contas.size());

                        if (sc.hasNextInt()) {
                            escolha = sc.nextInt();
                            sc.nextLine();

                            if (escolha >= 1 && escolha <= contas.size()) {
                                c = contas.get(escolha - 1);
                                break;

                            } else {
                                System.out.println("OPÇÃO INVÁLIDA. DIGITE UM NÚMERO VÁLIDO.");
                            }

                        } else {
                            System.out.println("ENTRADA INVÁLIDA. DIGITE UM NÚMERO.");
                            sc.nextLine();
                        }
                    }
                }
                break;

            case 2:
                c = criacaoConta();
                break;

            default:
                System.out.println("OPÇÃO INVÁLIDA NO MENU PRINCIPAL.");
        }

        return c;
    }


    public static Conta criacaoConta() {
        Conta c = null;

        while(true) {

            System.out.println("\nINSIRA O NOME DO TITULAR DA CONTA");
            String nome = sc.nextLine().trim();

            if (nome.isEmpty() || nome.length() < 3 || !nome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                System.out.println("INSIRA UM NOME VÁLIDO PARA CONFIRMAÇÃO...\n");
                continue;
            }
            c = new Conta(nome);
            System.out.println("CADASTRO REALIZADO COM SUCESSO!!!");
            contas.add(c);
            break;
        }

        return c;
    }
}