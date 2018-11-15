public class Principal2 {

    public static void main(String[] args) {
        System.out.println("Valor Final da equação: " + interpreter("10-3^2+54"));
    }

    public static double interpreter(final String str) {
        return new Object() {
            int pos = -1, ch;//inicia a posição em -1
            // função para proximo char, se posição for menor que o tamanho do vetor ele resolve, se não, para
            void proxChar() {
                if(++pos < str.length()) {
                    ch=str.charAt(pos);
                }else {
                    ch = -1;
                }
                System.out.println("Char Lido para conta:" + (char)ch);
            }

            boolean verifica(int charParaAnal) {
                while (ch == ' ') {
                    proxChar();// se for espaço ele pula para o character da frente
                }
                if (ch == charParaAnal) {// compara char lido com token pre definido
                    System.out.println("Token lido para realização da conta:  " + (char)ch);// se char lido = token
                    proxChar();// pega follow do token para fazer a conta correta com a expressão matematica
                    return true;// se for token valido retorna true
                }
                return false;// se for char invalido retorn false
            }

            double Analisagramatica() {
                proxChar();
                double x = AnalisaExp();
                if (pos < str.length()) {
                    throw new RuntimeException("Token não esperado: " + (char)ch);
                }
                return x;
            }

            double AnalisaExp() {
                double x = AnalisaRegra();
                // apresenta valor do resultado da conta feita pela expressão, utilizando o token + ou - para chegar no valor correto, na ordem correta
                for (;;) {
                    if      (verifica('+')) {
                        double a = AnalisaRegra();
                        System.out.println("Valor a ser somado: " + x);
                        System.out.println("Valor a somar: " + a);
                        System.out.println("Conta a ser realizada: " + x + " + " + a);
                        x = x + a; // adição
                        System.out.println(x);
                    }
                    else if (verifica('-')) {
                        double a = AnalisaRegra();
                        System.out.println("Valor a ser diminuido: " + x);
                        System.out.println("Valor a diminuir :" + a);
                        System.out.println("Conta a ser realizada: " + x + " - " + a);
                        x = x - a; // subtração
                        System.out.println("Valor final após subtração: " + x);
                    }
                    else return x;
                }
            }

            double AnalisaRegra() {
                double x = AnalisaOPUN();
                // faz a conta da regra * e / trazendo o valor do nextchar e do char lido anteriormente, calculando o resultado
                for (;;) {
                    if      (verifica('*')) {
                        double a =AnalisaOPUN();
                        System.out.println("Valor a ser multiplicado: " + x);
                        System.out.println("Valor a multiplicar " + a);
                        System.out.println("Conta a ser realizada: " + x + " * " + a);
                        x = x * a; // multiplicação
                        System.out.println("Valor a após a multiplicação: " + x);
                    }
                    else if (verifica('/')) {
                        double a = AnalisaOPUN();
                        System.out.println("Valor a ser dividido: " + x);
                        System.out.println("Valor a ser dividir: " + a);
                        System.out.println("Conta a ser realizada: " + x + " / " + a);
                        x = x / a; // divisão
                        System.out.println("Valor final da divisão: " + x);
                    }
                    else return x;
                }
            }

            double AnalisaOPUN() {
                if (verifica('+')) {
                    return AnalisaOPUN(); // unOP+
                }
                if (verifica('-')) {
                    return -AnalisaOPUN(); // unOP-
                }

                double x;
                int PosInicio = this.pos;
                if (ch >= '0' && ch <= '9') { // if numeros
                    while (ch >= '0' && ch <= '9') proxChar();//função follow para descobrir qual é o valor que segue para realizar a conta, se é um numero composto ou não
                    x = Double.parseDouble(str.substring(PosInicio, this.pos));// faz a concateção do valor até o final dos numeros inteiros
                } else {
                    throw new RuntimeException("Character não esperado identificado: " + (char)ch);
                }
                // verificação e conta da exponencial
                if (verifica('^')) {
                    double a = AnalisaOPUN();
                    System.out.println("Valor a ser potencializado: " + x);
                    System.out.println("Valor da potencia: " + a);
                    System.out.println("Conta a ser realizada: " + x + " ^ " + a);
                    x = Math.pow(x, a);
                }

                System.out.println("Valor a após verificação: " + x);
                return x;
            }
        }.Analisagramatica();
    }
}
