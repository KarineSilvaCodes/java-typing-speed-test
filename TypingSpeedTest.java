import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;

public class TypingSpeedTest {

    public static void main (String[] args) {
        String textoParaDigitar = "A rápida raposa marrom saltou sobre o cão preguiçoso.";
        
        Scanner scanner = new Scanner(System.in);


        System.out.println("------------------------------------------------------------------");
        System.out.println("          Teste de Velocidade de Digitação WPM          ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Prepare-se para digitar a frase abaixo: ");
        System.out.println("\n>>> " + textoParaDigitar + " <<< \n");
        System.out.println("Pressione ENTER para começar o teste...");

        scanner.nextLine();

        Instant inicio = Instant.now();

        System.out.println("\nComece a digitar AGORA (pressione ENTER ao terminar):");
        String textoDigitadoPeloUsuario = scanner.nextLine();

        Instant fim = Instant.now();

        scanner.close();

        Duration duracao = Duration.between(inicio, fim);

        double tempoEmSegundos = duracao.toMillis() / 1000.0;

        int palavrasTotais = textoParaDigitar.split("\\s+").length;

        double wpm = (palavrasTotais / tempoEmSegundos) * 60.0;

        boolean acertou = textoDigitadoPeloUsuario.equals(textoParaDigitar);

        System.out.println("\n==================================================================");
        System.out.println("          R E S U L T A D O S          ");
        System.out.println("==================================================================");
        System.out.printf("Tempo Total: %.2f segundos\n", tempoEmSegundos);
        System.out.printf("WPM (Palavras Por Minuto): %.2f\n", wpm);

        if (acertou) {
            System.out.println("Precisão: 100% (Texto digitado CORRETAMENTE!)");
        } else {
            System.out.println("Precisão: Erros detectados! O texto digitado não é idêntico.");
        }
        System.out.println("==================================================================");

    }
}