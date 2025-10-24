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
import java.util.Random;



public class TypingSpeedTestGUI extends JFrame implements ActionListener {

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

    public TypingSpeedTestGUI() {

        setTitle("Teste de Velocidade de Digitação (Swing)");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        labelFraseParaDigitar = new JLabel("<html><p style='padding: 5px;'>Clique em 'Iniciar' para começar! </p></html>");
        labelFraseParaDigitar.setFont(new Font("Arial", Font.PLAIN, 18));
        add(labelFraseParaDigitar, BorderLayout.NORTH);

        areaDeDigitacao = new JTextArea();
        areaDeDigitacao.setFont(new Font("Monospaced", Font.PLAIN, 16));
        areaDeDigitacao.setLineWrap(true);
        areaDeDigitacao.setWrapStyleWord(true);
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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == botaoIniciar) {

            iniciarNovoTeste();
        }
    }

    private void iniciarNovoTeste() {

        int indiceAleatorio = random.nextInt(frases.length);
        fraseAtual = frases[indiceAleatorio];

        labelFraseParaDigitar.setText("<html><p style='paddinh: 5px; '>" + fraseAtual + "</p></html>");

        botaoIniciar.setText("Reiniciar Teste");

        labelResultadosWPM.setText("WPM: --");
        labelResultadosPrecisao.setText("Precisão: --%");
        labelResultadosErros.setText("Erros: --");

        areaDeDigitacao.setText("");

        areaDeDigitacao.requestFocusInWindow();
    }
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            
                TypingSpeedTestGUI janela = new TypingSpeedTestGUI();
                janela.setVisible(true);
            }
        });
    }
}
