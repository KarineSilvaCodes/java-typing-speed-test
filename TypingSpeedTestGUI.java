import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class TypingSpeedTestGUI extends JFrame {

    JLabel labelFraseParaDigitar;
    JLabel labelResultadosWPM;
    JLabel labelResultadosPrecisao;
    JLabel labelResultadosErros;

    JTextArea areaDeDigitacao;

    JButton botaoIniciar;
    
    String fraseDeTesteInicial = "A mãe é muito braba sendo GP.";

    public TypingSpeedTestGUI() {

        setTitle("Teste de Velocidade de Digitação (Swing)");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        labelFraseParaDigitar = new JLabel("<html><p style='padding: 5px;'>" + fraseDeTesteInicial + "</p></html>");
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

        painelSul.add(botaoIniciar);
        painelSul.add(labelResultadosWPM);
        painelSul.add(labelResultadosPrecisao);
        painelSul.add(labelResultadosErros);

        add(painelSul, BorderLayout.SOUTH);

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
