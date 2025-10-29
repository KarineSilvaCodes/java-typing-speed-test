import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.time.Duration;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.Timer;


public class TypingSpeedTestGUI extends JFrame implements ActionListener, KeyListener {

    JLabel labelFraseParaDigitar;
    JLabel labelResultadosWPM;
    JLabel labelResultadosPrecisao;
    JLabel labelResultadosErros;
    JTextArea areaDeDigitacao;
    JButton botaoIniciar;
    
    private String[] frases = {
        "A rápida raposa marrom saltou sobre o cão preguiçoso.",
        "O sol brilha intensamente no céu azul e claro.",
        "Programar em Java pode ser desafiador, mas é muito recompensador.",
        "A persistência é o caminho do êxito.",
        "O único homem que nunca comete erros é aquele que nunca faz nada."
    };

    private Random random = new Random();
    private String fraseAtual = "";

    private boolean testePodeComecar = false;
    private boolean testeEmAndamento = false;
    private Instant inicio;

    private int posicaoAtual;
    private int totalErrosCometidos;
    private Color corOriginalAreaDigitacao;
    private Timer timerContagem;
    private int contagemRegressiva;


    
    public TypingSpeedTestGUI() {

        setTitle("Teste de Velocidade de Digitação (Swing)");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        labelFraseParaDigitar = new JLabel("<html><p style='padding: 5px;'>Clique em 'Iniciar Teste' para começar! </p></html>");
        labelFraseParaDigitar.setFont(new Font("Monospaced", Font.PLAIN, 18));
        add(labelFraseParaDigitar, BorderLayout.NORTH);

        areaDeDigitacao = new JTextArea();
        areaDeDigitacao.setFont(new Font("Monospaced", Font.PLAIN, 16));
        areaDeDigitacao.setLineWrap(true);
        areaDeDigitacao.setWrapStyleWord(true);
        areaDeDigitacao.addKeyListener(this);
        this.corOriginalAreaDigitacao = areaDeDigitacao.getBackground();
        add(areaDeDigitacao, BorderLayout.CENTER);

        JPanel painelSul = new JPanel();
        painelSul.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        labelResultadosWPM = new JLabel("WPM: --");
        labelResultadosPrecisao = new JLabel("Precisão: --%");
        labelResultadosErros = new JLabel("Erros: --");

        botaoIniciar = new JButton("Iniciar Teste");
        botaoIniciar.addActionListener(this);

        painelSul.add(botaoIniciar);
        painelSul.add(labelResultadosWPM);
        painelSul.add(labelResultadosPrecisao);
        painelSul.add(labelResultadosErros);

        add(painelSul, BorderLayout.SOUTH);

        ActionListener acaoDoTimer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contagemRegressiva--;

                if (contagemRegressiva == 2) {
                    labelFraseParaDigitar.setText("<html><p style='font-family: Monospaced; padding: 5px;'>Preparar... 2</p></html>");
                } else if (contagemRegressiva == 1) {
                    labelFraseParaDigitar.setText("<html><p style='font-family: Monospaced; padding: 5px;'>Preparar... 1</p></html>");
                } else if (contagemRegressiva == 0) {
                    labelFraseParaDigitar.setText("<html><p style='font-family: Monospaced; padding: 5px;'>VAI!</p></html>");
                } else {
                    timerContagem.stop();
                    botaoIniciar.setEnabled(true);
                    iniciarNovoTeste();
                }
            }
        };

        timerContagem = new Timer(1000, acaoDoTimer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoIniciar) {
            iniciarContagemRegressiva();
        }
    }

    private void iniciarContagemRegressiva() {
        botaoIniciar.setEnabled(false);
        areaDeDigitacao.setEditable(false);
        areaDeDigitacao.setBackground(this.corOriginalAreaDigitacao);
        labelResultadosWPM.setText("WPM: --");
        labelResultadosPrecisao.setText("Precisão: --%");
        labelResultadosErros.setText("erros: --");

        contagemRegressiva = 3; 
        labelFraseParaDigitar.setText("<html><p style='font-family: Monospaced; padding: 5px;'>Preparar... 3</p></html>");

        timerContagem.start();

    }

    private void iniciarNovoTeste() {

        posicaoAtual = 0;
        totalErrosCometidos = 0;

        int indiceAleatorio = random.nextInt(frases.length);
        fraseAtual = frases[indiceAleatorio];

        atualizarDestaqueFrase();
        botaoIniciar.setText("Reiniciar Teste");

        areaDeDigitacao.setText(""); 
        areaDeDigitacao.setEditable(true);
        areaDeDigitacao.setBackground(new Color(240, 255, 240));

        testePodeComecar = true;
        testeEmAndamento = false; 

        areaDeDigitacao.requestFocusInWindow();
    }

    private void finalizarTeste() {
        Instant fim = Instant.now();
        
        testeEmAndamento = false;
        testePodeComecar = false;
        areaDeDigitacao.setEditable(false);
        areaDeDigitacao.setBackground(this.corOriginalAreaDigitacao);
        labelFraseParaDigitar.setText("<html><p style='font-family: Monospaced; padding: 5px;'>" + fraseAtual + "</p></html>");

        Duration duracao = Duration.between(inicio, fim);
        double tempoEmSegundos = duracao.toMillis() / 1000.0;

        if (tempoEmSegundos == 0.0) {
            tempoEmSegundos = 0.01;
        }
        
        int lenOriginal = fraseAtual.length();
        int palavrasTotais = fraseAtual.split("\\s+").length;
        double wpm = (palavrasTotais / tempoEmSegundos) * 60.0;

        double precisao = Math.max(0.0, ((double) (lenOriginal - totalErrosCometidos) / lenOriginal) *100.0);

        labelResultadosWPM.setText(String.format("WPM: %.2f", wpm));
        labelResultadosPrecisao.setText(String.format("Precisão: %.2f%%", precisao));
        labelResultadosErros.setText(String.format("Erros: %d", totalErrosCometidos));
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (!testePodeComecar) {
            e.consume();
            return;
        }

        if (!testeEmAndamento) {
            inicio = Instant.now();
            testeEmAndamento = true;
        }

        char charDigitado = e.getKeyChar();
        char charEsperado = fraseAtual.charAt(posicaoAtual);

        if (charDigitado != charEsperado) {
            e.consume();
            totalErrosCometidos++;
            Toolkit.getDefaultToolkit().beep();
        } else {
            posicaoAtual++;
        }

        if ( posicaoAtual == fraseAtual.length()) {
            finalizarTeste();
        } else {
            atualizarDestaqueFrase();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            e.consume();
            if (testePodeComecar) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // chamado quando uma tecla é solta.
        // não será utilizado, mas é obrigatório ter.
    }
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Aviso: Look and Feel Nimbus não encontrado. Usando o padrão.");
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            
                TypingSpeedTestGUI janela = new TypingSpeedTestGUI();

                janela.setLocationRelativeTo(null);
                janela.setVisible(true);
            }
        });
    }

    private void atualizarDestaqueFrase() {

        String textoAntes = fraseAtual.substring(0, posicaoAtual);
        String charAtivo = String.valueOf(fraseAtual.charAt(posicaoAtual));
        String textoDepois = fraseAtual.substring(posicaoAtual + 1);
        
        String html = "<html><p style='font-family: Monospaced; padding: 5px;'>" + textoAntes;
        
        html += "<span style='background-color: #FFFF99;'>" + charAtivo + "</span>";
        
        html += textoDepois + "</p></html>";
        
        // 3. Define o texto final no rótulo
        labelFraseParaDigitar.setText(html);
    }
}
