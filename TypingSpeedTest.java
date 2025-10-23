import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;
import java.util.Random;

public class TypingSpeedTest {

    public static void main (String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean jogarDeNovo = true; 

        do {
            
            String[] frases = {
                 "A rápida raposa marrom saltou sobre o cão preguiçoso.",
                 "O sol brilha intensamente no céu azul e claro.",
                 "Programar em Java pode ser desafiador, mas é muito recompensador.",
                 "A persistência é o caminho do êxito.",
                 "O único homem que nunca comete erros é aquele que nunca faz nada."
        };
        
        Random random = new Random();
        int indiceAleatorio = random.nextInt(frases.length);
        String textoParaDigitar = frases[indiceAleatorio];


        System.out.println("------------------------------------------------------------------");
        System.out.println("          Teste de Velocidade de Digitação WPM          ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Prepare-se para digitar a frase abaixo: ");
        System.out.println("\n>>> " + textoParaDigitar + " <<< \n");


        System.out.println("\nComece a digitar AGORA (pressione ENTER ao terminar):");
        Instant inicio = Instant.now();

        String textoDigitadoPeloUsuario = scanner.nextLine();

        Instant fim = Instant.now();

        Duration duracao = Duration.between(inicio, fim);
        double tempoEmSegundos = duracao.toMillis() / 1000.0;
        int palavrasTotais = textoParaDigitar.split("\\s+").length;
        double wpm = (palavrasTotais / tempoEmSegundos) * 60.0;

        int erros = 0;
        int lenOriginal = textoParaDigitar.length();
        int lenDigitado = textoDigitadoPeloUsuario.length();

        int lenComparacao = Math.min(lenOriginal, lenDigitado);

        for (int i = 0; i < lenComparacao; i++) {
            
            if (textoParaDigitar.charAt(i) != textoDigitadoPeloUsuario.charAt(i)) {
                erros++;
            }

        }

        erros += Math.abs(lenOriginal - lenDigitado);

        double precisao = Math.max(0.0, ((double) (lenOriginal - erros) / lenOriginal) * 100.0);

        System.out.println("\n==================================================================");
        System.out.println("          R E S U L T A D O S          ");
        System.out.println("==================================================================");
        System.out.printf("Tempo Total: %.2f segundos\n", tempoEmSegundos);
        System.out.printf("WPM (Palavras Por Minuto): %.2f\n", wpm);
        System.out.printf("Precisão: %.2f%%\n", precisao);
        System.out.printf("Total de Erros: %d\n", erros);
        System.out.println("==================================================================");

        System.out.println("\nDeseja jogar novamente? (Digite 'S' para sim ou qualquer outra tecla para sair)");
        String resposta = scanner.nextLine();

        if (!resposta.equalsIgnoreCase("S")) {
            jogarDeNovo = false;    
        }

    } while (jogarDeNovo);

    System.out.println("\nObrigado por jogar! Até a próxima. :)");
    scanner.close();

    }
}