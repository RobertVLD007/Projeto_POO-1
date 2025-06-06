package banco;

public class Conta {

    protected double saldo;
    protected int numeroConta;
    protected int numeroAgencia;
    protected String titular;
    protected String[] ultimasTransacoes = new String[2];

    public Conta(String titular) {
        this.numeroConta = (int)(Math.random() * 90000 + 10000);
        this.numeroAgencia = 0001;
        this.titular = titular;
        this.saldo = 0;
    }

    public void sacar(double valor){
        double saldoAtual = this.saldo;

        if (valor < 0 || valor > this.saldo) {
            System.out.println("VALOR INVÁLIDO INSERIDO. INSIRA OUTRO VALOR");
        } else {
            this.saldo -= valor;

            System.out.println("SAQUE REALIZADO COM SUCESSO");
            System.out.printf("SALDO ANTERIOR: %.2f \nVALOR SACADO: %.2f \nSALDO ATUAL: %.2f\n", saldoAtual, valor, this.saldo);
            
            this.ultimasTransacoes[0] = "ULTIMO SAQUE: \nVALOR SACADO: " + valor 
                    + "\nVALOR ANTES/DEPOIS DO SAQUE: " + saldoAtual + " ==> " + this.saldo;
        }
    }
    
    public void depositar(double valor){
        double saldoAtual = this.saldo;
        if (valor < 0) {
            System.out.println("VALOR INVÁLIDO INSERIDO. INSIRA OUTRO VALOR");
        } else {
            this.saldo += valor;
            System.out.println("DEPÓSITO REALIZADO COM SUCESSO");
            System.out.printf("SALDO ANTERIOR: %.2f \nVALOR DEPOSITADO: %.2f \nSALDO ATUAL: %.2f\n", saldoAtual, valor, this.saldo);
            
            this.ultimasTransacoes[1] = "ULTIMO DEPÓSITO: \nVALOR DEPOSITADO: " + valor
                    + "\nSALDO ANTES/DEPOIS DO DEPÓSITO: " + saldoAtual + " ==> " + this.saldo;
        }
    }

    public void transferir(Conta contaDestino, double valor){
        System.out.printf("\nTRANSFERÊNCIA DE %s PARA %s \nVALOR DA TRANSFERÊNCIA: %.2f\n\n", this.getTitular(), contaDestino.getTitular(), valor);

        this.saldo -= valor;
        contaDestino.depositar(valor);
    }

    @Override
    public String toString() {
        return "\nNÚMERO DA CONTA: " + this.numeroConta 
                + "\nTITULAR DA CONTA: " + this.titular
                + "\nSALDO ATUAL: " + this.saldo + "\n\n";
    }


    public String getTransacoes(int op) {
        String resultado;

        switch(op) {
            case 1:
                resultado = this.ultimasTransacoes[0];
                break;

            case 2:
                resultado = this.ultimasTransacoes[1];
                break;

            default:
                resultado = "VALOR INVÁLIDO PARA VALIDAÇÃO";
                break;
        }

        return resultado;
    }


    public int getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }
}
