/* Os valores da
/* O banco de memória é uma lista (JList) de Strings. Cada linha começa com o endereço da memória, seguido do primeiro byte e 
* depois o segundo byte, cada bloco é separado por espaço. Ex: 0001 0000 0101.
* Para pegar um valor da memória basta escrever listMemoryModel.get(index), essa função vai retornar uma string
* Para setar um valor escreva listMemoryModel.set(index, string);
* O banco de registrador também é uma lista(JList) de String e funciona como o banco de memória
* use os métodos listRegisterModel.get(index) e listRegisterModel.set(index, string) para pegar e setar um valor.
* No método initRegister() é possível ver a posição de cada registrador.
* A caixa do código fonte é um campo de texto que pode ser editado. Use o método codigoFonteField.getText() 
* para pegar o texto. Ao carregar um arquivo no botão "Carregar Arquivo" basta setar o texto com o método codigoFonteField.setText().
*/
import javax.naming.ldap.SortControl;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;


public class TelaPrincipal extends javax.swing.JFrame{

    
    private DefaultListModel<String> listMemoryModel = new DefaultListModel<>();
    private DefaultListModel<String> listRegisterModel = new DefaultListModel<>();
    private int Step_Counter = 0;
    private int Step_Counter_memory = 0;
    Memory memory = new Memory();
    VarTable varTable = new VarTable();
    
    public class Memory{
        private int[] palavras = new int[8192];

        public Memory(){
            for(int i = 0; i < 8192; i++){
                this.palavras[i] = 0;
            }  
        }
        public void setPalavra(int data,int position){
            this.palavras[position] = data;
        }

        public int getPalavra(int position){
            return this.palavras[position];
        }
    }

    class LinhaTable {
        String name;
        ArrayList<Integer> reference;
        Boolean status;

        public LinhaTable(String nome, Boolean status, int reference) {
            this.name = nome;
            this.reference = new ArrayList<Integer>();
            this.status = status;
        }

        public void addReference(int newReference) {
            this.reference.add(newReference); 
        }
    }
    class VarTable {
        private ArrayList<LinhaTable> tabela;
        private int lineCounter;

        public VarTable() {
            this.tabela = new ArrayList<LinhaTable>();
            this.lineCounter = 0;
        }

        public boolean hasItemName(String varName) {
            if(this.tabela.size() == 0) {
                return false;
            }

            for(int i = 0; i < this.tabela.size(); i++) {
                if(this.tabela.get(i).name == varName)
                    return true;
            }
            return false;
        }

        public void addVar(String nome, Boolean status, int reference) {
            this.tabela.add(new LinhaTable(nome, status, reference));
            this.lineCounter++;
        }

        public void checkVariable(String varName) {

            if(this.tabela.size() == 0)
                this.addVar(varName, false, Step_Counter_memory);

            for(int i = 0; i < this.tabela.size(); i++) {
                if(this.hasItemName(varName)){
                    if(this.tabela.get(0).status == false) {
                        this.tabela.get(i).addReference(Step_Counter_memory);
                    } else if (this.tabela.get(i).status == true) {

                    }

                } else {
                    this.addVar(varName, false, Step_Counter_memory);
                }
            }
        }
    }

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() throws IOException{
        initComponents();
        configComponents();
        initMemoria();
        initRegister();
    }
    
    private void configComponents() throws IOException {
        outputScreenLabel.setText("testando");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jDialog3 = new javax.swing.JDialog();
        displayPanel = new javax.swing.JPanel();
        displayInputLabel = new javax.swing.JLabel();
        displayInputFiled = new javax.swing.JTextField();
        outputScreenLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        outputLabel = new javax.swing.JLabel();
        codePanel = new javax.swing.JPanel();
        codigoFonteLabel = new javax.swing.JLabel();
        inputCodeScroll = new javax.swing.JScrollPane();
        CodigoFonteField = new javax.swing.JTextArea();
        CarregarArquivo = new javax.swing.JButton();
        nextStep = new javax.swing.JButton();
        runAll = new javax.swing.JButton();
        memoryPanel = new javax.swing.JPanel();
        memoriaLabel = new javax.swing.JLabel();
        memoryScroll = new javax.swing.JScrollPane();
        memoria = new javax.swing.JList<>();
        localizaMemoryField = new javax.swing.JTextField();
        localizaMemoryButton = new javax.swing.JButton();
        nextMemoryButton = new javax.swing.JButton();
        prevMomoryButton = new javax.swing.JButton();
        terminalPanel = new javax.swing.JPanel();
        enterTerminalButton = new javax.swing.JButton();
        terminalLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        entradaTerminalField = new javax.swing.JTextArea();
        registersPanel = new javax.swing.JPanel();
        registerScroll = new javax.swing.JScrollPane();
        registers = new javax.swing.JList<>();
        registerLabel = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 0));
        setBounds(new java.awt.Rectangle(0, 0, 1, 1));
        setMaximumSize(new java.awt.Dimension(700, 400));
        setPreferredSize(new java.awt.Dimension(600, 600));

        displayPanel.setBackground(new java.awt.Color(0, 255, 204));
        displayPanel.setLayout(null);

        displayInputLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        displayInputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        displayInputLabel.setText("<html><body>Digite um Registrador ou<br>variável para ser exibido</body></html>");
        displayPanel.add(displayInputLabel);
        displayInputLabel.setBounds(40, 284, 180, 40);

        displayInputFiled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayInputFiledActionPerformed(evt);
            }
        });
        displayPanel.add(displayInputFiled);
        displayInputFiled.setBounds(40, 330, 180, 30);

        outputScreenLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        outputScreenLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outputScreenLabel.setText("Var: 15");
        displayPanel.add(outputScreenLabel);
        outputScreenLabel.setBounds(50, 90, 130, 110);

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        // imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/computerImage.jpg"))); // NOI18N
        displayPanel.add(imageLabel);
        imageLabel.setBounds(10, 40, 230, 260);

        outputLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        outputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outputLabel.setText("Output");
        displayPanel.add(outputLabel);
        outputLabel.setBounds(60, 10, 120, 29);

        codePanel.setBackground(new java.awt.Color(204, 204, 204));
        codePanel.setForeground(new java.awt.Color(153, 153, 153));

        codigoFonteLabel.setBackground(new java.awt.Color(0, 0, 0));
        codigoFonteLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        codigoFonteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codigoFonteLabel.setText("Código Fonte");
        codigoFonteLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        CodigoFonteField.setColumns(20);
        CodigoFonteField.setRows(5);
        CodigoFonteField.setText("read posA\nload posB\nmov  posA posB\nstop\nspace\nspace\nspace\nspace\nspace\npos posA\npos posB\n");
        inputCodeScroll.setViewportView(CodigoFonteField);

        CarregarArquivo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CarregarArquivo.setText("Carregar Arquivo");
        CarregarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CarregarArquivoActionPerformed(evt);
            }
        });

        nextStep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nextStep.setText("Next Step");
        nextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextStepActionPerformed(evt);
            }
        });

        runAll.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        runAll.setText("Run All");
        runAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout codePanelLayout = new javax.swing.GroupLayout(codePanel);
        codePanel.setLayout(codePanelLayout);
        codePanelLayout.setHorizontalGroup(
            codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(codePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(codePanelLayout.createSequentialGroup()
                        .addComponent(CarregarArquivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nextStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(runAll, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(inputCodeScroll, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, codePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(codigoFonteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        codePanelLayout.setVerticalGroup(
            codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(codePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(codigoFonteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputCodeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CarregarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(codePanelLayout.createSequentialGroup()
                        .addComponent(nextStep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(runAll, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        memoryPanel.setBackground(new java.awt.Color(204, 255, 204));
        memoryPanel.setForeground(new java.awt.Color(51, 51, 255));

        memoriaLabel.setBackground(new java.awt.Color(0, 0, 0));
        memoriaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        memoriaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        memoriaLabel.setText("Endereços de Memoria");
        memoriaLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        memoria.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        memoryScroll.setViewportView(memoria);

        localizaMemoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localizaMemoryFieldActionPerformed(evt);
            }
        });

        localizaMemoryButton.setText("Localizar memória");
        localizaMemoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localizaMemoryButtonActionPerformed(evt);
            }
        });

        nextMemoryButton.setBackground(new java.awt.Color(0, 255, 255));
        nextMemoryButton.setText("NEXT");
        nextMemoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextMemoryButtonActionPerformed(evt);
            }
        });

        prevMomoryButton.setBackground(new java.awt.Color(51, 255, 255));
        prevMomoryButton.setText("PREV");
        prevMomoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevMomoryButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout memoryPanelLayout = new javax.swing.GroupLayout(memoryPanel);
        memoryPanel.setLayout(memoryPanelLayout);
        memoryPanelLayout.setHorizontalGroup(
            memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, memoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(memoriaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(memoryPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(memoryPanelLayout.createSequentialGroup()
                                .addComponent(localizaMemoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(localizaMemoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(memoryPanelLayout.createSequentialGroup()
                                .addComponent(prevMomoryButton)
                                .addGap(169, 169, 169)
                                .addComponent(nextMemoryButton))
                            .addComponent(memoryScroll))))
                .addGap(119, 119, 119))
        );
        memoryPanelLayout.setVerticalGroup(
            memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(memoriaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(memoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevMomoryButton)
                    .addComponent(nextMemoryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(memoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localizaMemoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(localizaMemoryButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        terminalPanel.setBackground(new java.awt.Color(0, 51, 51));

        enterTerminalButton.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        enterTerminalButton.setText("ENTER");
        enterTerminalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterTerminalButtonActionPerformed(evt);
            }
        });

        terminalLabel.setBackground(new java.awt.Color(255, 255, 255));
        terminalLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        terminalLabel.setForeground(new java.awt.Color(255, 255, 255));
        terminalLabel.setText("Terminal");

        entradaTerminalField.setColumns(20);
        entradaTerminalField.setRows(5);
        entradaTerminalField.setText("523");
        jScrollPane4.setViewportView(entradaTerminalField);

        javax.swing.GroupLayout terminalPanelLayout = new javax.swing.GroupLayout(terminalPanel);
        terminalPanel.setLayout(terminalPanelLayout);
        terminalPanelLayout.setHorizontalGroup(
            terminalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(terminalPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(terminalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(terminalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(terminalPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enterTerminalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        terminalPanelLayout.setVerticalGroup(
            terminalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(terminalPanelLayout.createSequentialGroup()
                .addComponent(terminalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(terminalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(enterTerminalButton, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        registersPanel.setBackground(new java.awt.Color(255, 204, 204));

        registers.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        registerScroll.setViewportView(registers);

        registerLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        registerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        registerLabel.setText("Registradores");

        javax.swing.GroupLayout registersPanelLayout = new javax.swing.GroupLayout(registersPanel);
        registersPanel.setLayout(registersPanelLayout);
        registersPanelLayout.setHorizontalGroup(
            registersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registersPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(registerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerScroll)
                .addContainerGap())
        );
        registersPanelLayout.setVerticalGroup(
            registersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registersPanelLayout.createSequentialGroup()
                .addComponent(registerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(registerScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(terminalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(codePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(memoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(3314, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(codePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(memoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(terminalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(displayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(437, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        
    
    public void initMemoria(){
        
        String[] memo = new String[4096];

        listMemoryModel.clear();
        for(int i = 0, j = 0; i<4096; i++,j+=2){
            memo[i] = String.format("%04d", i) + ": " + String.format("%04d", memory.getPalavra(j)) + " " + String.format("%04d", memory.getPalavra(j+1));
            listMemoryModel.addElement(memo[i]);
        }
        memoria.setModel(listMemoryModel);
    }
    
    public void initRegister(){
        
        String[] regis = new String[8];
        
        regis[0] = "AX: 00000000 00000000";
        regis[1] = "DX: 00000000 00000000";
        regis[2] = "SP: 00000000 00000000";
        regis[3] = "SI: 00000000 00000000";
        regis[4] = "IP: 00000000 00000000";
        regis[5] = "SR: 00000000 00000000";
        regis[6] = "CS: 00000000 00000000";
        regis[7] = "DS: 00000000 00000000";
        
        for(int i = 0; i<8; i++){       
            listRegisterModel.addElement(regis[i]);
        }
        registers.setModel(listRegisterModel);
    }
    
    public void setMemoria (String str, int i){
       
        listMemoryModel.set(i, str);
    }
              
    private String[] readTextFromSourceCode(){
        String ArquivoCarregado = CodigoFonteField.getText();
        String[] CommandLine = ArquivoCarregado.split("\n");
        return CommandLine;
    }

    private void nextStepActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String[] sourceCode =  readTextFromSourceCode();
        //pega a linha e separa as instruções
        String[] lineArguments = sourceCode[Step_Counter].split(" ");
    
        //Switch pode virar um método
        switch(lineArguments[0]){
            case "read":
                memory.setPalavra(12, Step_Counter_memory); 
            break;
            case "load":
                memory.setPalavra(13, Step_Counter_memory);
                break;
        }
        Step_Counter_memory++;

        // Checando quantidade de argumentos na linha.
        if(lineArguments.length == 2) {
            
            varTable.checkVariable(lineArguments[1]);
            Step_Counter_memory++;

            System.out.println(Step_Counter_memory);
        } else if (lineArguments.length == 3) {

            varTable.checkVariable(lineArguments[1]);
            Step_Counter_memory++;

            varTable.checkVariable(lineArguments[2]);                
            Step_Counter_memory++;

            System.out.println(Step_Counter_memory);
        }

        initMemoria();
        Step_Counter++;
    }                                        

    private void runAllActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here: 
    }                                      

    private void CarregarArquivoActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        String ArquivoCarregado = new String("Testando Carregar arquivo\nSerá exibido conteúdo do arquivo aqui...") ;
        CodigoFonteField.setText(ArquivoCarregado);
    }                                               

    private void localizaMemoryFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
        String str = localizaMemoryField.getText();
        memoria.ensureIndexIsVisible(Integer.parseInt(str));
    }                                                   

    private void localizaMemoryButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
        String str = localizaMemoryField.getText();
        memoria.ensureIndexIsVisible(Integer.parseInt(str));
    }                                                    

    private void nextMemoryButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
        int i = memoria.getLastVisibleIndex() + 22;
        memoria.ensureIndexIsVisible(i);
    }                                                

    private void prevMomoryButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
        int i = memoria.getFirstVisibleIndex() - 22;
        memoria.ensureIndexIsVisible(i);
       
    }                                                

    private void displayInputFiledActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void enterTerminalButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
        String Valor = new String("Testando terminal\nComando de entrada e status serão exibido aqui");
        entradaTerminalField.setText(Valor); // utilizar para a ultima mensagem de comando
    }                                                   

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException{
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaPrincipal().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton CarregarArquivo;
    private javax.swing.JTextArea CodigoFonteField;
    private javax.swing.JPanel codePanel;
    private javax.swing.JLabel codigoFonteLabel;
    private javax.swing.JTextField displayInputFiled;
    private javax.swing.JLabel displayInputLabel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton enterTerminalButton;
    private javax.swing.JTextArea entradaTerminalField;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JScrollPane inputCodeScroll;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton localizaMemoryButton;
    private javax.swing.JTextField localizaMemoryField;
    private javax.swing.JList<String> memoria;
    private javax.swing.JLabel memoriaLabel;
    private javax.swing.JPanel memoryPanel;
    private javax.swing.JScrollPane memoryScroll;
    private javax.swing.JButton nextMemoryButton;
    private javax.swing.JButton nextStep;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JLabel outputScreenLabel;
    private javax.swing.JButton prevMomoryButton;
    private javax.swing.JLabel registerLabel;
    private javax.swing.JScrollPane registerScroll;
    private javax.swing.JList<String> registers;
    private javax.swing.JPanel registersPanel;
    private javax.swing.JButton runAll;
    private javax.swing.JLabel terminalLabel;
    private javax.swing.JPanel terminalPanel;
    // End of variables declaration                   
}