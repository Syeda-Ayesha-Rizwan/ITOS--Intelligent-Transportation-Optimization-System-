
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║  ITOS — Intelligent Transportation Optimization System           ║
 * ║  Calculus-Driven Smart Mobility | Pakistan Edition v3.0          ║
 * ║  Applications of Calculus in Software Engineering                ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
public class ITOS extends JFrame {

    // ═══════════════════════════════════════════════════════
    // PREMIUM DARK THEME PALETTE
    // ═══════════════════════════════════════════════════════
    static final Color C_BG        = new Color(8,   10,  22);   // deepest bg
    static final Color C_BG2       = new Color(13,  16,  32);   // nav/header
    static final Color C_BG3       = new Color(18,  22,  44);   // card bg
    static final Color C_BG4       = new Color(24,  30,  56);   // input bg
    static final Color C_BORDER    = new Color(36,  46,  80);   // subtle border
    static final Color C_BORDER2   = new Color(52,  66, 110);   // active border

    static final Color C_ECO       = new Color(0,   229, 160);  // primary green
    static final Color C_ECO_DIM   = new Color(0,    80,  56);  // eco bg tint
    static final Color C_BLUE      = new Color(61,  158, 255);  // info blue
    static final Color C_BLUE_DIM  = new Color(10,   50, 100);
    static final Color C_AMBER     = new Color(255, 184,  48);  // warning amber
    static final Color C_AMBER_DIM = new Color(80,   55,   8);
    static final Color C_RED       = new Color(255,  77,  77);  // danger red
    static final Color C_RED_DIM   = new Color(80,   18,  18);
    static final Color C_PURPLE    = new Color(168, 125, 255);  // accent purple
    static final Color C_PURPLE_DIM= new Color(45,   25,  90);
    static final Color C_CYAN      = new Color(0,   212, 229);  // cyan
    static final Color C_PINK      = new Color(255, 107, 157);  // pink

    static final Color C_TXT       = new Color(225, 232, 248);  // primary text
    static final Color C_TXT2      = new Color(140, 158, 200);  // secondary text
    static final Color C_TXT3      = new Color(72,   86, 128);  // muted text

    // Light theme
    static final Color L_BG        = new Color(245, 247, 255);
    static final Color L_BG2       = new Color(255, 255, 255);
    static final Color L_BG3       = new Color(235, 240, 252);
    static final Color L_BG4       = new Color(225, 232, 248);
    static final Color L_BORDER    = new Color(200, 210, 235);
    static final Color L_TXT       = new Color(20,   28,  60);
    static final Color L_TXT2      = new Color(70,   90, 140);

    static final DecimalFormat DF2 = new DecimalFormat("0.00");
    static final DecimalFormat DF1 = new DecimalFormat("0.0");
    static final DecimalFormat DF0 = new DecimalFormat("0");

    // ═══════════════════════════════════════════════════════
    // FONTS
    // ═══════════════════════════════════════════════════════
    static final Font F_TITLE  = new Font("Segoe UI", Font.BOLD,  16);
    static final Font F_LABEL  = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font F_BOLD   = new Font("Segoe UI", Font.BOLD,  13);
    static final Font F_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    static final Font F_TINY   = new Font("Segoe UI", Font.PLAIN,  9);
    static final Font F_MONO   = new Font("Courier New", Font.PLAIN, 11);
    static final Font F_MONO_B = new Font("Courier New", Font.BOLD,  12);
    static final Font F_VAL    = new Font("Courier New", Font.BOLD,  21);
    static final Font F_BIG    = new Font("Courier New", Font.BOLD,  32);

    // ═══════════════════════════════════════════════════════
    // PAKISTAN VEHICLE DATA
    // ═══════════════════════════════════════════════════════
    double petrolPrice = 255.00, dieselPrice = 265.00, octanePrice = 285.00, electricPrice = 30.00;

    final String[] pakVehicles    = {"Suzuki Mehran","Honda Civic","Toyota Corolla","Toyota Hilux","Hybrid","Electric","Honda CD70"};
    final double[] cityMileage    = {14,11,12,8,22,7,45};
    final double[] highwayMileage = {18,15,16,11,28,8.5,60};
    final String[] pakIcons       = {"🚗","🚙","🚘","🛻","🔋","⚡","🏍️"};
    final int[]    pakOptSpeeds   = {70,80,80,65,75,60,55};
    final Color[]  pakColors      = {C_ECO,C_BLUE,C_CYAN,C_AMBER,C_PURPLE,C_PINK,C_ECO};

    final String[] CAL_VEHICLES   = {"Sedan","SUV","Truck","Hybrid","Motorcycle"};
    final double[] V_A = {0.004,0.006,0.009,0.003,0.002};
    final double[] V_B = {200,220,300,180,120};
    final double[] V_C = {5,7,10,3,2};

    final String[] SCENARIOS      = {"Normal","Heavy Load","Uphill","City Traffic","Rainy"};
    final double[] S_AM = {1.0,1.3,1.2,1.1,1.15};
    final double[] S_BM = {1.0,1.2,1.3,1.4,1.1};

    // ═══════════════════════════════════════════════════════
    // STATE
    // ═══════════════════════════════════════════════════════
    int    vehicleIdx = 0, scenarioIdx = 0, currentPakVehicle = 0;
    String currentFuelType = "Petrol";
    double speed = 80, distance = 100, alpha = 1.0, beta = 0.5;
    double policySpeed = 80, popMillions = 1.0;
    double expA = 0.004, expB = 200, expC = 5;
    boolean darkMode = true, useCalculusModel = false;
    List<String> history = new ArrayList<>();

    // ═══════════════════════════════════════════════════════
    // UI COMPONENTS
    // ═══════════════════════════════════════════════════════
    GraphPanel graphPanel, expGraph;
    ScoreGauge gauge;
    ModernProgressBar fuelBar, timeBar, co2Bar;
    JProgressBar scoreBar;

    JLabel lbFuelCost, lbSavings, lbTime, lbCO2, lbOptSpeed, lbTotalCost;
    JLabel lbMathModel, lbAnalytic, lbRecommendation, lbScore;
    JLabel lbSpeedVal, lbDistVal, lbAlphaVal, lbBetaVal;
    JLabel lbPolicyFuel, lbPolicyCO2, lbPolicyNat, lbPolicyRec;
    JLabel lbExpAVal, lbExpBVal, lbExpCVal, lbExpAnalytic;
    JLabel lbFuelBar, lbTimeBar, lbCO2Bar;
    JLabel[] explainLines;
    JTextArea instructionArea;

    JSlider slSpeed, slDist, slAlpha, slBeta, slPolSpd, slPop, slExpA, slExpB, slExpC;
    JComboBox<String> cmbVehicle, cmbFuel, cmbCalVehicle, cmbScenario;
    JButton modelToggleBtn;

    JPanel mainCards;
    CardLayout cardLayout;
    JButton[] navBtns;
    final String[] NAV = {"🏠 Dashboard","🏛 Policy","🧪 Experiment","📖 About"};
    final String[] NAV_IDS = {"Dashboard","Policy","Experiment","About"};
    int activeNav = 0;

    // ═══════════════════════════════════════════════════════
    // MATH ENGINE
    // ═══════════════════════════════════════════════════════
    static double fuel(double v, double a, double b, double c) {
        return (v<=0) ? 9999 : a*v*v + b/v + c;
    }
    static double optNumerical(double d, double al, double be, double a, double b, double c) {
        double best=1, bestCost=Double.MAX_VALUE;
        for (double v=10;v<=200;v+=0.5) {
            double cost = al*fuel(v,a,b,c)*d + be*(d/v);
            if (cost<bestCost){bestCost=cost;best=v;}
        }
        return best;
    }
    static double optAnalytic(double b, double a) { return Math.pow(b/(2*a), 1.0/3.0); }

    double getA(){return V_A[vehicleIdx]*S_AM[scenarioIdx];}
    double getB(){return V_B[vehicleIdx]*S_BM[scenarioIdx];}
    double getC(){return V_C[vehicleIdx];}

    double pakMileage(){
        double m;
        if(speed<=60) m=cityMileage[currentPakVehicle];
        else if(speed<=100){double r=(speed-60)/40.0;m=cityMileage[currentPakVehicle]*(1-r)+highwayMileage[currentPakVehicle]*r;}
        else{m=highwayMileage[currentPakVehicle];if(speed>120)m*=(1-(speed-120)/100.0);}
        return Math.max(3,m);
    }
    double pakFuelL(){return distance/pakMileage();}
    double fuelPrice(){
        switch(currentFuelType){case"Diesel":return dieselPrice;case"Octane":return octanePrice;case"Electric":return electricPrice;default:return petrolPrice;}
    }
    double pakCost(){return pakFuelL()*fuelPrice();}
    double pakTime(){return distance/speed;}
    double pakCO2(){return pakFuelL()*2.3;}
    int    pakOptSpd(){return pakOptSpeeds[currentPakVehicle];}
    double pakSavings(){
        int os=pakOptSpd();
        double om;
        if(os<=60)om=cityMileage[currentPakVehicle];
        else if(os<=100){double r=(os-60)/40.0;om=cityMileage[currentPakVehicle]*(1-r)+highwayMileage[currentPakVehicle]*r;}
        else om=highwayMileage[currentPakVehicle];
        return Math.max(0,(pakFuelL()-distance/om)*fuelPrice());
    }
    int pakScore(){
        int d=Math.abs((int)speed-pakOptSpd());
        return d<=5?98:d<=10?85:d<=15?75:d<=20?65:d<=30?50:35;
    }
    double curFuelPerKm(){return useCalculusModel?fuel(speed,getA(),getB(),getC()):1.0/pakMileage();}
    double curFuelL()    {return useCalculusModel?curFuelPerKm()*distance:pakFuelL();}
    double curTime()     {return distance/speed;}
    double curCO2()      {return curFuelL()*2.31;}
    double curCost()     {return curFuelL()*fuelPrice();}
    double curOptSpd()   {return useCalculusModel?optNumerical(distance,alpha,beta,getA(),getB(),getC()):pakOptSpd();}
    int    curScore()    {return useCalculusModel?calcScore():pakScore();}
    int calcScore(){
        double opt=curOptSpd();
        double oF=fuel(opt,getA(),getB(),getC());
        double oT=alpha*oF*distance*fuelPrice()+beta*(distance/opt)*500;
        double uT=alpha*curFuelPerKm()*distance*fuelPrice()+beta*curTime()*500;
        return (int)Math.min(100,Math.round(oT/uT*100));
    }

    // ═══════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════
    public ITOS() {
        super("ITOS — Intelligent Transportation Optimization System  |  Calculus in SE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1420, 860);
        setMinimumSize(new Dimension(1200, 720));
        setLocationRelativeTo(null);
        getContentPane().setBackground(C_BG);
        setLayout(new BorderLayout());
        installShortcuts();
        add(buildHeader(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
        add(buildToolbar(), BorderLayout.SOUTH);
        setVisible(true);
        SwingUtilities.invokeLater(this::updateAll);
    }

    void installShortcuts(){
        bindKey("ctrl X", e->exportCSV());
        bindKey("ctrl R", e->saveHTML());
        bindKey("ctrl H", e->toggleTheme());
        bindKey("F1",     e->showHelp());
    }
    void bindKey(String ks, ActionListener al){
        String k=ks.replace(" ","_");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ks),k);
        getRootPane().getActionMap().put(k,new AbstractAction(){public void actionPerformed(ActionEvent e){al.actionPerformed(e);}});
    }

    // ═══════════════════════════════════════════════════════
    // HEADER
    // ═══════════════════════════════════════════════════════
    JPanel buildHeader(){
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(C_BG2);
        h.setBorder(BorderFactory.createMatteBorder(0,0,1,0,C_BORDER));
        h.setPreferredSize(new Dimension(0,56));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT,14,10));
        left.setOpaque(false);

        // Logo circle
        JPanel logo = new JPanel(){
            protected void paintComponent(Graphics g){
                Graphics2D g2=(Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp=new GradientPaint(0,0,C_ECO,getWidth(),getHeight(),C_BLUE);
                g2.setPaint(gp);g2.fillOval(1,1,getWidth()-2,getHeight()-2);
                g2.setColor(C_BG2);g2.fillOval(5,5,getWidth()-10,getHeight()-10);
                g2.setColor(C_ECO);g2.setFont(new Font("Segoe UI",Font.BOLD,13));
                g2.drawString("🚗",8,24);
            }
        };
        logo.setPreferredSize(new Dimension(36,36));logo.setOpaque(false);

        JPanel titles = new JPanel(new GridLayout(2,1,0,1));titles.setOpaque(false);
        JLabel t1=lbl("ITOS — Intelligent Transportation Optimization System",C_TXT,F_TITLE);
        JLabel t2=lbl("Applications of Calculus in Software Engineering  ·  Pakistan Edition v3.0",C_TXT3,F_SMALL);
        titles.add(t1);titles.add(t2);
        left.add(logo);left.add(titles);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,10));right.setOpaque(false);
        right.add(badge("CALCULUS",C_ECO,C_ECO_DIM));
        right.add(badge("SE PROJECT",C_BLUE,C_BLUE_DIM));
        right.add(badge("GOVT GRADE",C_PURPLE,C_PURPLE_DIM));
        right.add(badge("PAKISTAN 🇵🇰",C_AMBER,C_AMBER_DIM));

        h.add(left,BorderLayout.WEST);h.add(right,BorderLayout.EAST);
        return h;
    }

    // ═══════════════════════════════════════════════════════
    // BODY: NAV + CARDS
    // ═══════════════════════════════════════════════════════
    JPanel buildBody(){
        JPanel body=new JPanel(new BorderLayout());body.setBackground(C_BG);
        body.add(buildNav(),BorderLayout.NORTH);
        cardLayout=new CardLayout();
        mainCards=new JPanel(cardLayout);mainCards.setBackground(C_BG);
        mainCards.add(buildDashboard(),"Dashboard");
        mainCards.add(buildPolicy(),"Policy");
        mainCards.add(buildExperiment(),"Experiment");
        mainCards.add(buildAbout(),"About");
        body.add(mainCards,BorderLayout.CENTER);
        return body;
    }

    JPanel buildNav(){
        JPanel nav=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        nav.setBackground(C_BG2);
        nav.setBorder(BorderFactory.createMatteBorder(0,0,1,0,C_BORDER));
        navBtns=new JButton[NAV.length];
        for(int i=0;i<NAV.length;i++){
            final int idx=i; final String id=NAV_IDS[i];
            JButton b=new JButton(NAV[i]);
            b.setFont(F_BOLD);b.setForeground(i==0?C_ECO:C_TXT3);
            b.setBackground(C_BG2);b.setFocusPainted(false);b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0,0,2,0,i==0?C_ECO:C_BG2),
                    BorderFactory.createEmptyBorder(10,18,8,18)));
            b.addActionListener(e->{activeNav=idx;for(int j=0;j<navBtns.length;j++){navBtns[j].setForeground(j==idx?C_ECO:C_TXT3);navBtns[j].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0,j==idx?C_ECO:C_BG2),BorderFactory.createEmptyBorder(10,18,8,18)));}cardLayout.show(mainCards,id);if("Policy".equals(id))updatePolicy();if("Experiment".equals(id))updateExperiment();});
            nav.add(b);navBtns[i]=b;
        }
        return nav;
    }

    // ═══════════════════════════════════════════════════════
    // TOOLBAR
    // ═══════════════════════════════════════════════════════
    JPanel buildToolbar(){
        JPanel tb=new JPanel(new FlowLayout(FlowLayout.CENTER,8,7));
        tb.setBackground(C_BG2);
        tb.setBorder(BorderFactory.createMatteBorder(1,0,0,0,C_BORDER));

        Object[][] btns={
                {"📊 Export CSV",    C_ECO,    C_ECO_DIM,   (Runnable)this::exportCSV},
                {"📄 HTML Report",   C_BLUE,   C_BLUE_DIM,  (Runnable)this::saveHTML},
                {"🖨️ Print Report",  C_AMBER,  C_AMBER_DIM, (Runnable)this::printReport},
                {"💰 Fuel Prices",   C_ECO,    C_ECO_DIM,   (Runnable)this::updatePrices},
                {"📊 Compare",       C_PURPLE, C_PURPLE_DIM,(Runnable)this::showCompare},
                {"🔍 Analyze",       C_BLUE,   C_BLUE_DIM,  (Runnable)this::showAnalysis},
                {"🔄 Reset",         C_TXT3,   C_BORDER,    (Runnable)this::resetAll},
                {"❓ Help",          C_TXT3,   C_BORDER,    (Runnable)this::showHelp},
                {"🌓 Theme",         C_TXT3,   C_BORDER,    (Runnable)this::toggleTheme},
        };
        for(Object[] bt:btns){
            JButton b=toolBtn((String)bt[0],(Color)bt[1],(Color)bt[2]);
            b.addActionListener(e->((Runnable)bt[3]).run());
            tb.add(b);
        }
        modelToggleBtn=toolBtn("🇵🇰 Pakistan Model",C_PINK,C_RED_DIM);
        modelToggleBtn.addActionListener(e->toggleModel());
        tb.add(modelToggleBtn);
        return tb;
    }

    JButton toolBtn(String text, Color fg, Color bg){
        JButton b=new JButton(text);
        b.setFont(F_SMALL);b.setForeground(fg);b.setBackground(bg);
        b.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(fg.darker(),1,true),BorderFactory.createEmptyBorder(5,11,5,11)));
        b.setFocusPainted(false);b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){b.setBackground(bg.brighter());}
            public void mouseExited(MouseEvent e){b.setBackground(bg);}
        });
        return b;
    }

    // ═══════════════════════════════════════════════════════
    // DASHBOARD
    // ═══════════════════════════════════════════════════════
    JPanel buildDashboard(){
        JPanel p=new JPanel(new BorderLayout(0,0));p.setBackground(C_BG);
        p.add(buildLeftPanel(),BorderLayout.WEST);
        p.add(buildCenterPanel(),BorderLayout.CENTER);
        p.add(buildRightPanel(),BorderLayout.EAST);
        return p;
    }

    // ── LEFT PANEL ──────────────────────────────────────────
    JPanel buildLeftPanel(){
        JPanel p=new JPanel();p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setBackground(C_BG2);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,0,1,C_BORDER),
                BorderFactory.createEmptyBorder(14,14,14,14)));
        p.setPreferredSize(new Dimension(300,0));

        // Instructions box
        JPanel instBox=new JPanel(new BorderLayout());instBox.setBackground(C_BG3);
        instBox.setBorder(BorderFactory.createCompoundBorder(new LineBorder(C_BORDER,1,true),BorderFactory.createEmptyBorder(10,12,10,12)));
        instructionArea=new JTextArea(
                "╔═══════════════════════════════╗\n"+
                        "║   SMART DRIVING ASSISTANT     ║\n"+
                        "╚═══════════════════════════════╝\n\n"+
                        "① Select vehicle & fuel type\n"+
                        "② Adjust speed & distance\n"+
                        "③ View real-time analysis\n"+
                        "④ Follow recommendations\n\n"+
                        "F(v) = av² + b/v + c\n"+
                        "v* = (b/2a)^(1/3)\n\n"+
                        "Optimal: 60-90 km/h"
        );
        instructionArea.setEditable(false);instructionArea.setFont(F_MONO);
        instructionArea.setForeground(C_TXT2);instructionArea.setBackground(C_BG3);
        instructionArea.setMaximumSize(new Dimension(280,200));
        instBox.add(instructionArea);instBox.setAlignmentX(0);p.add(instBox);
        p.add(vgap(10));

        // Vehicle combo
        p.add(sectionLbl("🚗  VEHICLE"));p.add(vgap(5));
        cmbVehicle=new JComboBox<>();
        for(int i=0;i<pakVehicles.length;i++) cmbVehicle.addItem(pakIcons[i]+" "+pakVehicles[i]);
        styleCombo(cmbVehicle,C_ECO);
        cmbVehicle.addActionListener(e->{currentPakVehicle=cmbVehicle.getSelectedIndex();updateAll();});
        cmbVehicle.setAlignmentX(0);p.add(cmbVehicle);p.add(vgap(10));

        // Fuel combo
        p.add(sectionLbl("⛽  FUEL TYPE"));p.add(vgap(5));
        cmbFuel=new JComboBox<>(new String[]{"Petrol","Diesel","Octane","Electric"});
        styleCombo(cmbFuel,C_AMBER);
        cmbFuel.addActionListener(e->{currentFuelType=(String)cmbFuel.getSelectedItem();updateAll();});
        cmbFuel.setAlignmentX(0);p.add(cmbFuel);p.add(vgap(12));

        // Calculus vehicle (shown only in calc mode)
        p.add(sectionLbl("🔬  CALC VEHICLE"));p.add(vgap(5));
        cmbCalVehicle=new JComboBox<>(CAL_VEHICLES);styleCombo(cmbCalVehicle,C_BLUE);
        cmbCalVehicle.addActionListener(e->{vehicleIdx=cmbCalVehicle.getSelectedIndex();updateAll();});
        cmbCalVehicle.setAlignmentX(0);p.add(cmbCalVehicle);p.add(vgap(5));
        cmbScenario=new JComboBox<>(SCENARIOS);styleCombo(cmbScenario,C_PURPLE);
        cmbScenario.addActionListener(e->{scenarioIdx=cmbScenario.getSelectedIndex();updateAll();});
        cmbScenario.setAlignmentX(0);p.add(cmbScenario);p.add(vgap(12));

        // Sliders
        p.add(hRule());p.add(vgap(10));
        lbSpeedVal=lbl("80 km/h",C_ECO,F_MONO_B);
        slSpeed=mkSlider(20,200,80);
        slSpeed.addChangeListener(e->{speed=slSpeed.getValue();lbSpeedVal.setText((int)speed+" km/h");updateAll();});
        p.add(sliderPanel("⚡  SPEED",lbSpeedVal,slSpeed,C_ECO));p.add(vgap(10));

        lbDistVal=lbl("100 km",C_BLUE,F_MONO_B);
        slDist=mkSlider(10,500,100);
        slDist.addChangeListener(e->{distance=slDist.getValue();lbDistVal.setText((int)distance+" km");updateAll();});
        p.add(sliderPanel("📏  DISTANCE",lbDistVal,slDist,C_BLUE));p.add(vgap(10));

        p.add(hRule());p.add(vgap(10));
        p.add(sectionLbl("⚖  OPTIMIZATION WEIGHTS (α, β)"));p.add(vgap(8));

        lbAlphaVal=lbl("1.0",C_AMBER,F_MONO_B);
        slAlpha=mkSlider(1,30,10);
        slAlpha.addChangeListener(e->{alpha=slAlpha.getValue()/10.0;lbAlphaVal.setText(DF1.format(alpha));updateAll();});
        p.add(sliderPanel("Fuel Weight α",lbAlphaVal,slAlpha,C_AMBER));p.add(vgap(8));

        lbBetaVal=lbl("0.5",C_PURPLE,F_MONO_B);
        slBeta=mkSlider(1,30,5);
        slBeta.addChangeListener(e->{beta=slBeta.getValue()/10.0;lbBetaVal.setText(DF1.format(beta));updateAll();});
        p.add(sliderPanel("Time Weight β",lbBetaVal,slBeta,C_PURPLE));

        p.add(Box.createVerticalGlue());
        return p;
    }

    // ── CENTER PANEL ─────────────────────────────────────────
    JPanel buildCenterPanel(){
        JPanel p=new JPanel(new BorderLayout(0,10));p.setBackground(C_BG);
        p.setBorder(BorderFactory.createEmptyBorder(12,12,8,8));

        // 6 metric cards
        JPanel cards=new JPanel(new GridLayout(2,3,8,8));cards.setOpaque(false);
        lbFuelCost=lbl("Rs. 0",C_ECO,F_VAL);
        lbSavings =lbl("Rs. 0",C_BLUE,F_VAL);
        lbTime     =lbl("0 h",C_AMBER,F_VAL);
        lbCO2      =lbl("0 kg",C_RED,F_VAL);
        lbOptSpeed =lbl("0",C_ECO,F_VAL);
        lbTotalCost=lbl("Rs. 0",C_PURPLE,F_VAL);
        cards.add(metricCard("💰 FUEL COST",lbFuelCost,"PKR",C_ECO));
        cards.add(metricCard("💎 SAVINGS",lbSavings,"PKR",C_BLUE));
        cards.add(metricCard("⏱ TRAVEL TIME",lbTime,"hours",C_AMBER));
        cards.add(metricCard("🌿 CO₂",lbCO2,"kg",C_RED));
        cards.add(metricCard("🎯 OPTIMAL SPEED",lbOptSpeed,"km/h",C_ECO));
        cards.add(metricCard("📊 TOTAL COST",lbTotalCost,"PKR",C_PURPLE));

        graphPanel=new GraphPanel();graphPanel.setPreferredSize(new Dimension(0,270));

        // Math row
        JPanel mathRow=new JPanel(new GridLayout(1,2,8,0));mathRow.setOpaque(false);
        lbMathModel=lbl("",C_TXT2,F_MONO);lbAnalytic=lbl("",C_ECO,F_MONO_B);
        JPanel mr=darkBox(new BorderLayout(0,4));
        mr.add(lbl("📐 MATHEMATICAL MODEL:",C_TXT3,F_TINY),BorderLayout.NORTH);
        JPanel mrI=new JPanel(new GridLayout(2,1,0,2));mrI.setOpaque(false);
        mrI.add(lbMathModel);mrI.add(lbAnalytic);mr.add(mrI);mathRow.add(mr);

        lbRecommendation=lbl("",C_TXT,F_MONO_B);
        JPanel rr=new JPanel(new BorderLayout(6,0));
        rr.setBackground(new Color(0,229,160,12));
        rr.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0,229,160,80),1,true),BorderFactory.createEmptyBorder(10,12,10,12)));
        rr.add(lbl("💡 RECOMMENDATION",C_ECO,F_TINY),BorderLayout.NORTH);
        rr.add(lbRecommendation,BorderLayout.CENTER);
        mathRow.add(rr);

        p.add(cards,BorderLayout.NORTH);
        p.add(graphPanel,BorderLayout.CENTER);
        p.add(mathRow,BorderLayout.SOUTH);
        return p;
    }

    // ── RIGHT PANEL ──────────────────────────────────────────
    JPanel buildRightPanel(){
        JPanel p=new JPanel();p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setBackground(C_BG2);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,1,0,0,C_BORDER),
                BorderFactory.createEmptyBorder(14,14,14,14)));
        p.setPreferredSize(new Dimension(240,0));

        p.add(sectionLbl("📊  EFFICIENCY SCORE"));p.add(vgap(8));
        gauge=new ScoreGauge();gauge.setAlignmentX(CENTER_ALIGNMENT);p.add(gauge);
        scoreBar=new JProgressBar(0,100);scoreBar.setValue(75);scoreBar.setStringPainted(true);
        scoreBar.setFont(F_TINY);scoreBar.setBackground(C_BG3);scoreBar.setForeground(C_ECO);
        scoreBar.setBorder(new LineBorder(C_BORDER,1));scoreBar.setAlignmentX(CENTER_ALIGNMENT);
        lbScore=lbl("Score: --",C_TXT3,F_TINY);lbScore.setAlignmentX(CENTER_ALIGNMENT);
        p.add(vgap(6));p.add(scoreBar);p.add(vgap(3));p.add(lbScore);

        p.add(vgap(12));p.add(hRule());p.add(vgap(10));
        p.add(sectionLbl("📈  PERFORMANCE METERS"));p.add(vgap(8));

        fuelBar=new ModernProgressBar(C_ECO);timeBar=new ModernProgressBar(C_AMBER);co2Bar=new ModernProgressBar(C_BLUE);
        lbFuelBar=lbl("0%",C_ECO,F_TINY);lbTimeBar=lbl("0%",C_AMBER,F_TINY);lbCO2Bar=lbl("0%",C_BLUE,F_TINY);
        p.add(meterRow("⛽ Fuel Economy",fuelBar,lbFuelBar,C_ECO));p.add(vgap(8));
        p.add(meterRow("⏱ Time Efficiency",timeBar,lbTimeBar,C_AMBER));p.add(vgap(8));
        p.add(meterRow("🌿 CO₂ Impact",co2Bar,lbCO2Bar,C_BLUE));

        p.add(vgap(12));p.add(hRule());p.add(vgap(10));
        p.add(sectionLbl("💡  LIVE ANALYSIS"));p.add(vgap(6));
        explainLines=new JLabel[6];
        for(int i=0;i<6;i++){
            explainLines[i]=lbl("",C_TXT3,F_TINY);explainLines[i].setAlignmentX(0);
            p.add(explainLines[i]);p.add(vgap(3));
        }

        p.add(Box.createVerticalGlue());
        return p;
    }

    // ═══════════════════════════════════════════════════════
    // POLICY SIMULATOR
    // ═══════════════════════════════════════════════════════
    JPanel buildPolicy(){
        JPanel root=new JPanel(new BorderLayout(16,0));root.setBackground(C_BG);
        root.setBorder(BorderFactory.createEmptyBorder(20,24,20,24));

        JPanel left=darkCard();left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(360,0));
        left.add(sectionLbl("🏛️  GOVERNMENT POLICY SIMULATOR"));left.add(vgap(4));
        left.add(lbl("Model national speed limit impact on fuel & CO₂",C_TXT3,F_TINY));left.add(vgap(12));left.add(hRule());left.add(vgap(12));

        JLabel pvLbl=lbl("80 km/h",C_ECO,F_MONO_B);
        slPolSpd=mkSlider(40,140,80);
        slPolSpd.addChangeListener(e->{policySpeed=slPolSpd.getValue();pvLbl.setText((int)policySpeed+" km/h");updatePolicy();});
        left.add(sliderPanel("🚦  Speed Limit",pvLbl,slPolSpd,C_ECO));left.add(vgap(10));

        JLabel popLbl=lbl("1.0 M",C_BLUE,F_MONO_B);
        slPop=mkSlider(1,500,10);
        slPop.addChangeListener(e->{popMillions=slPop.getValue()/10.0;popLbl.setText(DF1.format(popMillions)+" M");updatePolicy();});
        left.add(sliderPanel("🚗  Vehicle Population",popLbl,slPop,C_BLUE));left.add(vgap(20));

        JTextArea polMath=new JTextArea(
                "Mathematical Basis:\n"+
                        "  F(v) = av² + b/v + c\n"+
                        "  Policy at speed v_p:\n"+
                        "  Fuel diff = F(80) - F(v_p)\n"+
                        "  National = diff × pop × trips\n"+
                        "  CO₂ = fuel_saved × 2.31 kg/L"
        );
        polMath.setEditable(false);polMath.setFont(F_MONO);polMath.setForeground(C_TXT2);polMath.setBackground(C_BG4);
        polMath.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        left.add(polMath);left.add(Box.createVerticalGlue());

        JPanel right=darkCard();right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));
        right.add(sectionLbl("📊  IMPACT ANALYSIS RESULTS"));right.add(vgap(12));

        lbPolicyFuel=lbl("—",C_ECO,F_VAL);
        lbPolicyCO2 =lbl("—",C_RED,F_VAL);
        lbPolicyNat =lbl("—",C_BLUE,F_VAL);
        right.add(impactCard("Fuel per Vehicle / Day",lbPolicyFuel,C_ECO));right.add(vgap(10));
        right.add(impactCard("National CO₂ Change",lbPolicyCO2,C_RED));right.add(vgap(10));
        right.add(impactCard("National Fuel Saved / Day",lbPolicyNat,C_BLUE));right.add(vgap(16));
        right.add(hRule());right.add(vgap(12));

        lbPolicyRec=lbl("—",C_TXT2,F_MONO);
        JPanel recBox=new JPanel(new BorderLayout(0,4));recBox.setBackground(new Color(0,229,160,10));
        recBox.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0,229,160,70),1,true),BorderFactory.createEmptyBorder(10,12,10,12)));
        recBox.add(lbl("🏛️  Policy Recommendation",C_ECO,F_TINY),BorderLayout.NORTH);
        recBox.add(lbPolicyRec,BorderLayout.CENTER);
        recBox.setMaximumSize(new Dimension(Integer.MAX_VALUE,90));recBox.setAlignmentX(0);
        right.add(recBox);right.add(Box.createVerticalGlue());

        root.add(left,BorderLayout.WEST);root.add(right,BorderLayout.CENTER);
        return root;
    }

    // ═══════════════════════════════════════════════════════
    // EXPERIMENT MODE
    // ═══════════════════════════════════════════════════════
    JPanel buildExperiment(){
        JPanel root=new JPanel(new BorderLayout(16,0));root.setBackground(C_BG);
        root.setBorder(BorderFactory.createEmptyBorder(20,24,20,24));

        JPanel left=darkCard();left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(360,0));

        left.add(sectionLbl("🧪  EXPERIMENT: MODIFY F(v) CONSTANTS"));left.add(vgap(4));
        left.add(lbl("Explore how a, b, c affect the optimal speed",C_TXT3,F_TINY));left.add(vgap(12));left.add(hRule());left.add(vgap(12));

        lbExpAVal=lbl("0.004",C_ECO,F_MONO_B);
        slExpA=mkSlider(1,50,4);
        slExpA.addChangeListener(e->{expA=slExpA.getValue()/1000.0;lbExpAVal.setText(String.format("%.4f",expA));updateExperiment();});
        left.add(sliderPanel("a  (air resistance — av²)",lbExpAVal,slExpA,C_ECO));left.add(vgap(10));

        lbExpBVal=lbl("200",C_AMBER,F_MONO_B);
        slExpB=mkSlider(50,800,200);
        slExpB.addChangeListener(e->{expB=slExpB.getValue();lbExpBVal.setText(DF0.format(expB));updateExperiment();});
        left.add(sliderPanel("b  (engine inefficiency — b/v)",lbExpBVal,slExpB,C_AMBER));left.add(vgap(10));

        lbExpCVal=lbl("5.0",C_BLUE,F_MONO_B);
        slExpC=mkSlider(1,300,50);
        slExpC.addChangeListener(e->{expC=slExpC.getValue()/10.0;lbExpCVal.setText(DF1.format(expC));updateExperiment();});
        left.add(sliderPanel("c  (constant rolling friction)",lbExpCVal,slExpC,C_BLUE));left.add(vgap(16));left.add(hRule());left.add(vgap(12));

        // Derivation box
        JPanel deriv=new JPanel(new GridLayout(7,1,0,4));deriv.setBackground(C_BG4);
        deriv.setBorder(BorderFactory.createCompoundBorder(new LineBorder(C_BORDER,1,true),BorderFactory.createEmptyBorder(12,14,12,14)));
        deriv.setMaximumSize(new Dimension(Integer.MAX_VALUE,180));deriv.setAlignmentX(0);
        JLabel[] dLines={
                lbl("FULL CALCULUS DERIVATION:",C_ECO,F_MONO_B),
                lbl("  F(v) = a·v² + b/v + c",C_TXT,F_MONO),
                lbl("  dF/dv = 2av − b/v²",C_BLUE,F_MONO),
                lbl("  Set dF/dv = 0:",C_TXT2,F_MONO),
                lbl("  2av = b/v²  →  v³ = b/2a",C_AMBER,F_MONO),
                lbl("  d²F/dv² = 2a + 2b/v³ > 0",C_TXT2,F_MONO),
                lbl("",C_ECO,F_MONO_B),
        };
        for(JLabel dl:dLines){dl.setAlignmentX(0);deriv.add(dl);}
        lbExpAnalytic=dLines[6];
        left.add(deriv);left.add(Box.createVerticalGlue());

        expGraph=new GraphPanel();expGraph.setMode(true);expGraph.setBackground(C_BG3);
        expGraph.setBorder(BorderFactory.createLineBorder(C_BORDER,1));

        root.add(left,BorderLayout.WEST);root.add(expGraph,BorderLayout.CENTER);
        return root;
    }

    // ═══════════════════════════════════════════════════════
    // ABOUT
    // ═══════════════════════════════════════════════════════
    JPanel buildAbout(){
        JPanel root=new JPanel(new BorderLayout());root.setBackground(C_BG);
        root.setBorder(BorderFactory.createEmptyBorder(24,32,24,32));
        JTextArea about=new JTextArea(
                "╔══════════════════════════════════════════════════════════════════════════════╗\n"+
                        "║        ITOS — INTELLIGENT TRANSPORTATION OPTIMIZATION SYSTEM v3.0           ║\n"+
                        "║                      Pakistan Edition — Calculus in SE                      ║\n"+
                        "╚══════════════════════════════════════════════════════════════════════════════╝\n\n"+
                        "📐 MATHEMATICAL FOUNDATION (CALCULUS CORE):\n"+
                        "═══════════════════════════════════════════════════════════\n"+
                        "   Fuel Model:       F(v) = a·v² + b/v + c + d·L\n"+
                        "   1st Derivative:   dF/dv = 2av − b/v²\n"+
                        "   Critical Point:   set dF/dv = 0  →  v³ = b/(2a)\n"+
                        "   Optimal Speed:    v* = (b/(2a))^(1/3)  [CLOSED FORM]\n"+
                        "   2nd Derivative:   d²F/dv² = 2a + 2b/v³ > 0  [MINIMUM PROVEN]\n"+
                        "   Trade-off:        G(v) = α·F(v) + β·T(v)   T(v) = D/v\n\n"+
                        "💻 SOFTWARE ENGINEERING MODULES:\n"+
                        "═══════════════════════════════════════════════════════════\n"+
                        "   • FuelModelEngine      → F(v) = av² + b/v + c\n"+
                        "   • OptimizationEngine   → Numerical & Analytic solvers\n"+
                        "   • SimulationEngine     → Scenarios & conditions\n"+
                        "   • RecommendationEngine → AI-style driving advice\n"+
                        "   • PolicySimulator      → National speed limit impact\n"+
                        "   • EnvironmentModule    → CO₂ & sustainability\n"+
                        "   • EconomicsModule      → PKR savings calculator\n"+
                        "   • GraphEngine          → Custom Swing canvas charts\n\n"+
                        "🇵🇰 PAKISTAN-SPECIFIC FEATURES:\n"+
                        "═══════════════════════════════════════════════════════════\n"+
                        "   • 7 real Pakistani vehicles (Mehran, Civic, Hilux, CD70...)\n"+
                        "   • Live fuel prices in PKR (Petrol/Diesel/Octane/Electric)\n"+
                        "   • City vs highway mileage models\n"+
                        "   • National policy impact in millions of vehicles\n"+
                        "   • CO₂ savings in metric tonnes\n\n"


        );
        about.setEditable(false);about.setFont(F_MONO);about.setForeground(C_TXT2);about.setBackground(C_BG);
        about.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        JScrollPane sc=new JScrollPane(about);sc.setBorder(new LineBorder(C_BORDER,1));
        sc.getViewport().setBackground(C_BG);sc.setBackground(C_BG);
        root.add(sc,BorderLayout.CENTER);
        return root;
    }

    // ═══════════════════════════════════════════════════════
    // UPDATE ALL
    // ═══════════════════════════════════════════════════════
    void updateAll(){
        if(lbFuelCost==null) return;

        double fuelL=curFuelL(),timeH=curTime(),co2=curCO2(),cost=curCost();
        double optSpd=curOptSpd();int score=curScore();
        double savings=useCalculusModel?Math.max(0,(cost-fuel(optSpd,getA(),getB(),getC())*distance*fuelPrice())):pakSavings();
        double totalCost=alpha*cost+beta*timeH*500;

        // Metrics
        lbFuelCost.setText("Rs. "+DF2.format(cost));
        lbSavings .setText("Rs. "+DF2.format(savings));
        lbTime    .setText(DF2.format(timeH)+" h");
        lbCO2     .setText(DF1.format(co2)+" kg");
        lbOptSpeed.setText(DF0.format(optSpd));
        lbTotalCost.setText("Rs. "+DF0.format(totalCost));

        // Math
        if(useCalculusModel){
            double a=getA(),b=getB(),c=getC();
            lbMathModel.setText(String.format("F(v)=%.4f·v²+%.0f/v+%.0f",a,b,c));
            lbAnalytic .setText(String.format("v*=(b/2a)^(1/3)=%.1f km/h | dF/dv=%.4f",optAnalytic(b,a),2*a*speed-b/(speed*speed)));
        }else{
            lbMathModel.setText(String.format("Pakistan: %s @ %.1f km/L",pakVehicles[currentPakVehicle],pakMileage()));
            lbAnalytic .setText(String.format("Optimal: %.0f km/h | Save Rs. %.0f | CO₂: -%.1f kg",optSpd,savings,Math.abs(co2-pakFuelL()*2.31)));
        }

        // Score
        gauge.setScore(score);scoreBar.setValue(score);
        scoreBar.setForeground(score>=80?C_ECO:score>=60?C_AMBER:C_RED);
        lbScore.setText("Score: "+score+"/100  "+scoreEmoji(score));

        // Bars
        int fEff=(int)Math.min(100,Math.max(0,(optSpd/speed)*80));
        int tEff=(int)Math.min(100,Math.max(0,100-Math.abs(speed-optSpd)/optSpd*60));
        int cEff=(int)Math.max(0,Math.min(100,100-co2/8));
        fuelBar.setValue(fEff);lbFuelBar.setText(fEff+"%");
        timeBar.setValue(tEff);lbTimeBar.setText(tEff+"%");
        co2Bar .setValue(cEff);lbCO2Bar .setText(cEff+"%");

        // Recommendation
        double diff=speed-optSpd;
        String rec=Math.abs(diff)<3?"✅ PERFECT! You are at the optimal speed — maximum fuel efficiency!":
                diff>0?String.format("⬇ Reduce speed by %.0f km/h → Save Rs. %.0f per trip (%.1f L fuel)",diff,savings,fuelL-curFuelL()):
                        String.format("⬆ Increase speed by %.0f km/h → Improve engine efficiency",-diff);
        lbRecommendation.setText(rec);
        lbRecommendation.setForeground(Math.abs(diff)<3?C_ECO:diff>0?C_RED:C_AMBER);

        // Explain lines
        if(explainLines!=null){
            double a=useCalculusModel?getA():0.004,b=useCalculusModel?getB():200;
            explainLines[0].setText("• Air resistance a·v² = "+DF2.format(a*speed*speed)+" at "+DF0.format(speed)+" km/h");
            explainLines[1].setText("• Engine term b/v  = "+DF2.format(b/speed)+" at "+DF0.format(speed)+" km/h");
            explainLines[2].setText("• dF/dv at current = "+DF4.format(2*a*speed-b/(speed*speed)));
            explainLines[3].setText("• v* optimal speed = "+DF0.format(optSpd)+" km/h");
            explainLines[4].setText("• Total fuel used  = "+DF2.format(fuelL)+" litres");
            explainLines[5].setText("• CO₂ emitted      = "+DF1.format(co2)+" kg");
        }

        // Graph
        if(graphPanel!=null){
            if(useCalculusModel) graphPanel.setCalcData(speed,getA(),getB(),getC(),optSpd);
            else graphPanel.setPakData(speed,currentPakVehicle,pakOptSpd(),pakMileage());
            graphPanel.repaint();
        }

        // History
        history.add(String.format("[%.0f km/h | %.0f km | %s | Rs.%.0f | Score:%d]",speed,distance,useCalculusModel?"Calc":"PAK",cost,score));
        if(history.size()>100) history.remove(0);
    }

    void updatePolicy(){
        if(lbPolicyFuel==null) return;
        double a=0.004,b=200,c=5;
        double pFuel=fuel(policySpeed,a,b,c)*100;
        double baseFuel=fuel(80,a,b,c)*100;
        double diff=baseFuel-pFuel;
        double natDiff=diff*popMillions*1e6;
        double co2Nat=natDiff*2.31;
        lbPolicyFuel.setText(DF2.format(pFuel)+" L");
        lbPolicyCO2 .setText(String.format("%.2f Mt CO₂",Math.abs(co2Nat/1e9)));
        lbPolicyNat .setText(String.format("%.2f M L",Math.abs(natDiff/1e6)));
        String rec=policySpeed<60?"⚠ Too slow — safety & congestion risk":
                policySpeed<=90?"✅ Optimal range — calculus confirms minimum fuel":
                        policySpeed<=110?"⚠ Slightly high — 12-20% above optimal fuel use":"❌ Too fast — major fuel & CO₂ penalty";
        lbPolicyRec.setText(rec);
        lbPolicyRec.setForeground(policySpeed<=90?C_ECO:policySpeed<=110?C_AMBER:C_RED);
    }

    void updateExperiment(){
        if(lbExpAnalytic==null) return;
        double vstar=optAnalytic(expB,expA);
        double fmin=fuel(vstar,expA,expB,expC);
        double d2=2*expA+2*expB/(vstar*vstar*vstar);
        lbExpAnalytic.setText(String.format("→ v* = %.1f km/h | F(v*)=%.3f | d²F/dv²=%.4f>0 ✓",vstar,fmin,d2));
        if(expGraph!=null){expGraph.setExpData(expA,expB,expC);expGraph.repaint();}
    }

    void toggleModel(){
        useCalculusModel=!useCalculusModel;
        modelToggleBtn.setText(useCalculusModel?"🔬 Calculus Model":"🇵🇰 Pakistan Model");
        modelToggleBtn.setForeground(useCalculusModel?C_PURPLE:C_PINK);
        updateAll();
    }

    void toggleTheme(){
        darkMode=!darkMode;
        // Simple toggle — swap backgrounds of main panels
        Color bg=darkMode?C_BG:new Color(240,245,255);
        getContentPane().setBackground(bg);
        if(graphPanel!=null){graphPanel.setDark(darkMode);graphPanel.repaint();}
        if(expGraph!=null){expGraph.setDark(darkMode);expGraph.repaint();}
        SwingUtilities.updateComponentTreeUI(this);
    }

    // ═══════════════════════════════════════════════════════
    // DIALOGS
    // ═══════════════════════════════════════════════════════
    void exportCSV(){
        JFileChooser fc=new JFileChooser();fc.setSelectedFile(new File("ITOS_"+System.currentTimeMillis()+".csv"));
        if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
            try(PrintWriter w=new PrintWriter(fc.getSelectedFile())){
                w.println("ITOS Pakistan Report,"+new Date());
                w.println("Model,"+(useCalculusModel?"Calculus":"Pakistan"));
                w.println("Vehicle,"+(useCalculusModel?CAL_VEHICLES[vehicleIdx]:pakVehicles[currentPakVehicle]));
                w.println("Speed (km/h),"+(int)speed);
                w.println("Distance (km),"+(int)distance);
                w.println("Fuel Cost (PKR),"+DF2.format(curCost()));
                w.println("Savings (PKR),"+DF2.format(pakSavings()));
                w.println("Time (h),"+DF2.format(curTime()));
                w.println("CO2 (kg),"+DF1.format(curCO2()));
                w.println("Optimal Speed,"+DF0.format(curOptSpd()));
                w.println("Score,"+curScore());
                showMsg("✅ CSV exported to: "+fc.getSelectedFile().getName());
            }catch(Exception e){showMsg("Error: "+e.getMessage());}
        }
    }

    void saveHTML(){
        JFileChooser fc=new JFileChooser();fc.setSelectedFile(new File("ITOS_Report_"+System.currentTimeMillis()+".html"));
        if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
            String html="<!DOCTYPE html><html><head><title>ITOS Report</title>"
                    +"<style>body{background:#07091a;color:#dde4f8;font-family:monospace;margin:40px}"
                    +"h1{color:#00e5a0}h2{color:#3d9eff}.card{background:#141830;border:1px solid #1e2545;border-radius:8px;padding:16px;margin:12px 0}"
                    +".val{font-size:22px;font-weight:bold}.eco{color:#00e5a0}.blue{color:#3d9eff}.amber{color:#ffb830}.red{color:#ff4d4d}</style></head><body>"
                    +"<h1>🇵🇰 ITOS Report</h1><p>Generated: "+new Date()+"</p>"
                    +"<div class='card'><div class='val eco'>"+DF0.format(curOptSpd())+" km/h</div><div>Optimal Speed</div></div>"
                    +"<div class='card'><div class='val eco'>Rs. "+DF2.format(curCost())+"</div><div>Fuel Cost</div></div>"
                    +"<div class='card'><div class='val blue'>"+curScore()+"/100</div><div>Efficiency Score</div></div>"
                    +"<div class='card'><div class='val amber'>"+DF1.format(curCO2())+" kg</div><div>CO₂ Emissions</div></div>"
                    +"<h2>History</h2><pre>"+String.join("\n",history.subList(Math.max(0,history.size()-20),history.size()))+"</pre>"
                    +"</body></html>";
            try{Files.write(fc.getSelectedFile().toPath(),html.getBytes());showMsg("✅ HTML report saved!");}
            catch(Exception e){showMsg("Error: "+e.getMessage());}
        }
    }

    void printReport(){
        String txt=String.format(
                "╔═══════════════════════════════════════╗\n"
                        +"║        ITOS PAKISTAN REPORT           ║\n"
                        +"╚═══════════════════════════════════════╝\n\n"
                        +"Vehicle    : %s\nSpeed      : %.0f km/h\nDistance   : %.0f km\n"
                        +"Fuel Cost  : Rs. %.2f\nSavings    : Rs. %.2f\nTime       : %.2f h\n"
                        +"CO₂        : %.1f kg\nOptimal v* : %.0f km/h\nScore      : %d/100\n",
                useCalculusModel?CAL_VEHICLES[vehicleIdx]:pakVehicles[currentPakVehicle],
                speed,distance,curCost(),pakSavings(),curTime(),curCO2(),curOptSpd(),curScore());
        JTextArea ta=new JTextArea(txt);ta.setFont(F_MONO);ta.setEditable(false);
        JOptionPane.showMessageDialog(this,new JScrollPane(ta),"Print Preview",JOptionPane.PLAIN_MESSAGE);
    }

    void updatePrices(){
        JPanel p=new JPanel(new GridLayout(4,2,10,8));p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        JTextField tf1=new JTextField(""+petrolPrice),tf2=new JTextField(""+dieselPrice),
                tf3=new JTextField(""+octanePrice),tf4=new JTextField(""+electricPrice);
        p.add(new JLabel("Petrol (Rs./L):")); p.add(tf1);
        p.add(new JLabel("Diesel (Rs./L):")); p.add(tf2);
        p.add(new JLabel("Octane (Rs./L):")); p.add(tf3);
        p.add(new JLabel("Electric (Rs./kWh):")); p.add(tf4);
        if(JOptionPane.showConfirmDialog(this,p,"Update Fuel Prices",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            try{petrolPrice=Double.parseDouble(tf1.getText());dieselPrice=Double.parseDouble(tf2.getText());
                octanePrice=Double.parseDouble(tf3.getText());electricPrice=Double.parseDouble(tf4.getText());
                updateAll();showMsg("✅ Fuel prices updated!");}
            catch(Exception ex){showMsg("❌ Invalid number entered");}
        }
    }

    void showCompare(){
        StringBuilder sb=new StringBuilder("SPEED COMPARISON ("+distance+" km)\n");
        sb.append(String.format("%-8s %-16s %-10s %-10s\n","Speed","Fuel Cost(PKR)","Time(h)","CO₂(kg)"));
        sb.append("─".repeat(46)+"\n");
        double oldSpd=speed;
        for(int v:new int[]{40,60,80,100,120,140}){
            speed=v;
            sb.append(String.format("%-8d %-16.2f %-10.2f %-10.1f\n",v,curCost(),curTime(),curCO2()));
        }
        speed=oldSpd;updateAll();
        sb.append("\nVEHICLE COMPARISON\n");
        sb.append(String.format("%-22s %-16s %-12s\n","Vehicle","Mileage(km/L)","Cost(PKR)"));
        sb.append("─".repeat(52)+"\n");
        int oldVeh=currentPakVehicle;
        for(int i=0;i<pakVehicles.length;i++){currentPakVehicle=i;sb.append(String.format("%-22s %-16.1f %-12.2f\n",pakVehicles[i],pakMileage(),pakCost()));}
        currentPakVehicle=oldVeh;updateAll();
        JTextArea ta=new JTextArea(sb.toString());ta.setFont(F_MONO);ta.setEditable(false);
        ta.setBackground(C_BG3);ta.setForeground(C_TXT);
        JDialog d=new JDialog(this,"Comparison",true);d.setSize(560,480);d.setLocationRelativeTo(this);
        d.add(new JScrollPane(ta),BorderLayout.CENTER);
        JButton ok=toolBtn("Close",C_ECO,C_ECO_DIM);ok.addActionListener(e->d.dispose());
        JPanel bp=new JPanel();bp.setBackground(C_BG2);bp.add(ok);d.add(bp,BorderLayout.SOUTH);
        d.getContentPane().setBackground(C_BG);d.setVisible(true);
    }

    void showAnalysis(){
        double vstar=optAnalytic(getB(),getA()),d2=2*getA()+2*getB()/(vstar*vstar*vstar);
        String txt=String.format(
                "DETAILED CALCULUS ANALYSIS\n"+"═".repeat(45)+"\n\n"
                        +"Model: F(v) = %.4f·v² + %.0f/v + %.0f\n\n"
                        +"STEP 1: dF/dv = 2av − b/v²\n"
                        +"       = 2×%.4f×v − %.0f/v²\n\n"
                        +"STEP 2: Set dF/dv=0: 2av³ = b\n"
                        +"        v³ = %.0f/(2×%.4f) = %.1f\n\n"
                        +"STEP 3: v* = %.1f km/h\n\n"
                        +"STEP 4: d²F/dv² = 2a + 2b/v³\n"
                        +"       = %.4f > 0 ✓ → MINIMUM CONFIRMED\n\n"
                        +"At current speed %.0f km/h:\n"
                        +"  F(v) = %.4f L/km\n"
                        +"  dF/dv = %.6f (deviation from zero)\n"
                        +"  Total fuel: %.2f L | Cost: Rs. %.2f",
                getA(),getB(),getC(),getA(),getB(),getB(),getA(),Math.pow(getB()/(2*getA()),1.0/3.0),
                vstar,d2,speed,curFuelPerKm(),2*getA()*speed-getB()/(speed*speed),curFuelL(),curCost());
        JTextArea ta=new JTextArea(txt);ta.setFont(F_MONO);ta.setEditable(false);ta.setBackground(C_BG3);ta.setForeground(C_TXT);
        JDialog d=new JDialog(this,"Calculus Analysis",true);d.setSize(500,440);d.setLocationRelativeTo(this);
        d.getContentPane().setBackground(C_BG);d.add(new JScrollPane(ta),BorderLayout.CENTER);
        JButton ok=toolBtn("Close",C_ECO,C_ECO_DIM);ok.addActionListener(e->d.dispose());
        JPanel bp=new JPanel();bp.setBackground(C_BG2);bp.add(ok);d.add(bp,BorderLayout.SOUTH);
        d.setVisible(true);
    }

    void resetAll(){
        speed=80;distance=100;alpha=1.0;beta=0.5;
        currentPakVehicle=0;currentFuelType="Petrol";vehicleIdx=0;scenarioIdx=0;
        if(slSpeed!=null)slSpeed.setValue(80);if(slDist!=null)slDist.setValue(100);
        if(slAlpha!=null)slAlpha.setValue(10);if(slBeta!=null)slBeta.setValue(5);
        if(cmbVehicle!=null)cmbVehicle.setSelectedIndex(0);if(cmbFuel!=null)cmbFuel.setSelectedIndex(0);
        updateAll();showMsg("✅ All settings reset to defaults.");
    }

    void showHelp(){
        String h="🇵🇰 ITOS HELP GUIDE\n\n"
                +"HOW TO USE:\n"
                +"  ① Select vehicle & fuel type in left panel\n"
                +"  ② Adjust speed & distance sliders\n"
                +"  ③ Watch metrics update in real time\n"
                +"  ④ Follow the green recommendation\n\n"
                +"KEYBOARD SHORTCUTS:\n"
                +"  Ctrl+X  → Export CSV\n"
                +"  Ctrl+R  → Save HTML Report\n"
                +"  Ctrl+H  → Toggle Theme\n"
                +"  F1      → This Help\n\n"
                +"TABS:\n"
                +"  Dashboard  → Main optimizer & graphs\n"
                +"  Policy     → National speed limit simulator\n"
                +"  Experiment → Modify F(v) constants live\n"
                +"  About      → Full mathematical derivation\n\n"
                +"💡 TIP: Click '🔬 Calculus Model' to switch between\n"
                +"   real Pakistan vehicle data and the pure\n"
                +"   mathematical calculus model F(v)=av²+b/v+c";
        JTextArea ta=new JTextArea(h);ta.setEditable(false);ta.setFont(F_MONO);ta.setMargin(new Insets(12,12,12,12));
        ta.setBackground(C_BG3);ta.setForeground(C_TXT);
        JOptionPane.showMessageDialog(this,new JScrollPane(ta),"Help Guide",JOptionPane.PLAIN_MESSAGE);
    }

    void showMsg(String m){JOptionPane.showMessageDialog(this,m);}

    // ═══════════════════════════════════════════════════════
    // HELPER BUILDERS
    // ═══════════════════════════════════════════════════════
    static final DecimalFormat DF4=new DecimalFormat("0.0000");

    JLabel lbl(String t,Color c,Font f){JLabel l=new JLabel(t);l.setForeground(c);l.setFont(f);return l;}
    Component vgap(int h){return Box.createVerticalStrut(h);}
    JLabel sectionLbl(String t){JLabel l=lbl(t,C_TXT3,F_TINY);l.setAlignmentX(0);return l;}

    JLabel badge(String t,Color fg,Color bg){
        JLabel l=lbl(t,fg,F_TINY);l.setBackground(bg);l.setOpaque(true);
        l.setBorder(BorderFactory.createCompoundBorder(new LineBorder(fg.darker(),1,true),BorderFactory.createEmptyBorder(2,8,2,8)));
        return l;
    }

    JSeparator hRule(){
        JSeparator s=new JSeparator();s.setForeground(C_BORDER);
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE,8));return s;
    }

    JSlider mkSlider(int mn,int mx,int v){
        JSlider s=new JSlider(mn,mx,v);s.setBackground(C_BG2);s.setForeground(C_ECO);
        s.setBorder(BorderFactory.createEmptyBorder());
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE,26));return s;
    }

    void styleCombo(JComboBox<?> cb, Color accent){
        cb.setFont(F_LABEL);cb.setBackground(C_BG4);cb.setForeground(C_TXT);
        cb.setBorder(BorderFactory.createCompoundBorder(new LineBorder(C_BORDER,1,true),BorderFactory.createEmptyBorder(2,4,2,4)));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE,32));
    }

    JPanel sliderPanel(String label, JLabel valLbl, JSlider sl, Color accent){
        JPanel p=new JPanel(new BorderLayout(0,2));p.setBackground(C_BG2);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE,52));p.setAlignmentX(0);
        JPanel top=new JPanel(new BorderLayout());top.setBackground(C_BG2);
        JLabel lbL=lbl(label,C_TXT2,F_SMALL);top.add(lbL,BorderLayout.WEST);top.add(valLbl,BorderLayout.EAST);
        p.add(top,BorderLayout.NORTH);p.add(sl,BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(0,0,4,0));
        return p;
    }

    JPanel metricCard(String title, JLabel valLbl, String unit, Color accent){
        JPanel p=new JPanel(new BorderLayout(0,3));
        p.setBackground(C_BG3);
        p.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(C_BORDER,1,true),
                BorderFactory.createMatteBorder(0,3,0,0,accent)));
        p.add(lbl(title,C_TXT3,F_TINY),BorderLayout.NORTH);
        JPanel vr=new JPanel(new FlowLayout(FlowLayout.LEFT,4,4));vr.setBackground(C_BG3);
        vr.add(valLbl);vr.add(lbl(unit,C_TXT3,F_TINY));
        p.add(vr,BorderLayout.CENTER);
        return p;
    }

    JPanel meterRow(String label, ModernProgressBar bar, JLabel pct, Color accent){
        JPanel p=new JPanel(new BorderLayout(0,3));p.setOpaque(false);p.setMaximumSize(new Dimension(Integer.MAX_VALUE,38));p.setAlignmentX(0);
        JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);
        top.add(lbl(label,C_TXT3,F_TINY),BorderLayout.WEST);top.add(pct,BorderLayout.EAST);
        p.add(top,BorderLayout.NORTH);p.add(bar,BorderLayout.CENTER);
        return p;
    }

    JPanel darkBox(LayoutManager lm){
        JPanel p=new JPanel(lm);p.setBackground(C_BG3);
        p.setBorder(BorderFactory.createCompoundBorder(new LineBorder(C_BORDER,1,true),BorderFactory.createEmptyBorder(10,12,10,12)));
        return p;
    }

    JPanel darkCard(){
        JPanel p=new JPanel();p.setBackground(C_BG3);
        p.setBorder(BorderFactory.createCompoundBorder(new LineBorder(C_BORDER,1,true),BorderFactory.createEmptyBorder(16,16,16,16)));
        return p;
    }

    JPanel impactCard(String title, JLabel valLbl, Color accent){
        JPanel p=new JPanel(new BorderLayout(0,4));p.setBackground(C_BG4);
        p.setBorder(BorderFactory.createCompoundBorder(new LineBorder(C_BORDER,1,true),BorderFactory.createMatteBorder(0,3,0,0,accent)));
        p.add(lbl(title,C_TXT3,F_TINY),BorderLayout.NORTH);p.add(valLbl,BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE,66));p.setAlignmentX(0);
        return p;
    }

    String scoreEmoji(int s){return s>=90?"🥇":s>=75?"🥈":s>=60?"🥉":s>=45?"⚠":"❌";}

    // ═══════════════════════════════════════════════════════
    // CUSTOM GRAPH PANEL
    // ═══════════════════════════════════════════════════════
    class GraphPanel extends JPanel {
        boolean expMode=false, isDark=true, isPak=false;
        double curSpeed=80, optSpeed=75;
        double a=0.004,b=200,c=5;
        int pakVehicleIdx=0; double pakMil=12; int pakOpt=70;
        double expA=0.004,expB=200,expC=5;

        GraphPanel(){setBackground(C_BG3);}
        void setDark(boolean d){isDark=d;}
        void setMode(boolean e){expMode=e;}
        void setCalcData(double sp,double a,double b,double c,double opt){curSpeed=sp;this.a=a;this.b=b;this.c=c;optSpeed=opt;isPak=false;}
        void setPakData(double sp,int vIdx,int opt,double mil){curSpeed=sp;pakVehicleIdx=vIdx;pakOpt=opt;pakMil=mil;isPak=true;}
        void setExpData(double a,double b,double c){expA=a;expB=b;expC=c;}

        @Override
        protected void paintComponent(Graphics g0){
            super.paintComponent(g0);
            Graphics2D g=(Graphics2D)g0;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int W=getWidth(),H=getHeight();
            Color bgC=isDark?C_BG3:new Color(235,240,252);
            g.setColor(bgC);g.fillRect(0,0,W,H);

            int pL=56,pR=24,pT=28,pB=44;
            int gW=W-pL-pR,gH=H-pT-pB;
            if(gW<10||gH<10)return;

            // Title
            g.setFont(F_SMALL);g.setColor(C_TXT3);
            String title=expMode?"Experiment: F(v) = a·v² + b/v + c":
                    isPak?pakVehicles[Math.min(pakVehicleIdx,pakVehicles.length-1)]+" — Speed vs Fuel Efficiency":
                            "Calculus Model: F(v) = av² + b/v + c";
            g.drawString(title,pL+4,pT-10);

            // Grid lines
            g.setStroke(new BasicStroke(1f));
            for(int i=0;i<=5;i++){int y=pT+(int)((double)i/5*gH);g.setColor(new Color(36,46,80));g.drawLine(pL,y,pL+gW,y);}
            for(int i=0;i<=8;i++){int x=pL+(int)((double)i/8*gW);g.setColor(new Color(36,46,80));g.drawLine(x,pT,x,pT+gH);}

            // Axes
            g.setColor(C_BORDER2);g.setStroke(new BasicStroke(1.5f));g.drawRect(pL,pT,gW,gH);

            // Compute curve
            int N=181;double[] ys=new double[N];
            double minY=Double.MAX_VALUE,maxY=0;
            double ea=expMode?expA:a,eb=expMode?expB:b,ec=expMode?expC:c;
            for(int i=0;i<N;i++){
                double v=20+i;
                ys[i]=isPak?pakCurveVal(v):fuel(v,ea,eb,ec);
                if(ys[i]<minY)minY=ys[i];if(ys[i]>maxY)maxY=ys[i];
            }
            double range=maxY-minY+1e-9;minY-=range*0.06;maxY+=range*0.1;range=maxY-minY;

            // Y axis labels
            g.setFont(new Font("Courier New",Font.PLAIN,9));g.setColor(C_TXT3);
            for(int i=0;i<=5;i++){
                double val=maxY-range*i/5;int y=pT+(int)((double)i/5*gH);
                String s=DF1.format(val);g.drawString(s,pL-g.getFontMetrics().stringWidth(s)-3,y+4);
            }
            // X axis labels
            for(int i=0;i<=8;i++){int v=20+(int)(i*180.0/8);int x=pL+(int)((double)i/8*gW);g.drawString(v+"",x-10,pT+gH+14);}
            g.setFont(F_TINY);g.setColor(C_TXT3);
            g.drawString("Speed (km/h)",pL+gW/2-30,pT+gH+28);
            g.drawString(isPak?"km/L":"L/km",4,pT+gH/2);

            // Fill area under curve
            int[] pxs=new int[N],pysI=new int[N];
            for(int i=0;i<N;i++){
                pxs[i]=pL+(int)((double)i/(N-1)*gW);
                pysI[i]=pT+(int)((1-(ys[i]-minY)/range)*gH);
                pysI[i]=Math.max(pT,Math.min(pT+gH,pysI[i]));
            }
            // Gradient fill
            GradientPaint gp=new GradientPaint(0,pT,new Color(61,158,255,40),0,pT+gH,new Color(61,158,255,4));
            g.setPaint(gp);
            int[] fillX=new int[N+2],fillY=new int[N+2];
            for(int i=0;i<N;i++){fillX[i]=pxs[i];fillY[i]=pysI[i];}
            fillX[N]=pxs[N-1];fillY[N]=pT+gH;fillX[N+1]=pxs[0];fillY[N+1]=pT+gH;
            g.fillPolygon(fillX,fillY,N+2);

            // Main curve
            g.setPaint(null);g.setColor(C_BLUE);g.setStroke(new BasicStroke(2.5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            for(int i=1;i<N;i++) g.drawLine(pxs[i-1],pysI[i-1],pxs[i],pysI[i]);

            // Optimal point
            double ov=isPak?pakOpt:optSpeed;
            int oIdx=(int)Math.max(0,Math.min(N-1,ov-20));
            int ox2=pxs[oIdx],oy2=pysI[oIdx];
            // Vertical dashed line to optimal
            g.setColor(new Color(0,229,160,80));g.setStroke(new BasicStroke(1.2f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,new float[]{5,4},0));
            g.drawLine(ox2,pT,ox2,pT+gH);
            // Optimal dot
            g.setColor(C_ECO);g.setStroke(new BasicStroke(2f));
            g.fillOval(ox2-7,oy2-7,14,14);g.setColor(new Color(255,255,255,180));g.drawOval(ox2-7,oy2-7,14,14);
            g.setFont(F_TINY);g.setColor(C_ECO);g.drawString("v*="+DF0.format(ov)+" km/h",ox2+10,oy2-5);

            // Current speed point
            int cIdx=(int)Math.max(0,Math.min(N-1,curSpeed-20));
            int cx2=pxs[cIdx],cy2=pysI[cIdx];
            Color curColor=curSpeed>ov?C_RED:curSpeed<ov-3?C_AMBER:C_ECO;
            g.setColor(curColor);g.setStroke(new BasicStroke(2f));
            g.fillOval(cx2-6,cy2-6,12,12);g.setColor(Color.WHITE);g.drawOval(cx2-6,cy2-6,12,12);
            g.setColor(curColor);g.setFont(F_TINY);g.drawString(DF0.format(curSpeed)+" km/h",cx2+8,cy2+4);

            // Legend
            int lx=pL+gW-130,ly=pT+10;
            drawLegend(g,lx,ly,C_ECO,"Optimal v*");
            drawLegend(g,lx,ly+14,curColor,"Current speed");
            drawLegend(g,lx,ly+28,C_BLUE,"F(v) curve");
        }

        double pakCurveVal(double v){
            double m;
            if(v<=60)m=cityMileage[pakVehicleIdx];
            else if(v<=100){double r=(v-60)/40.0;m=cityMileage[pakVehicleIdx]*(1-r)+highwayMileage[pakVehicleIdx]*r;}
            else{m=highwayMileage[pakVehicleIdx];if(v>120)m*=(1-(v-120)/80.0);}
            return Math.max(4,m);
        }

        void drawLegend(Graphics2D g,int x,int y,Color c,String t){
            g.setColor(new Color(8,10,22,160));g.fillRoundRect(x-4,y-10,130,14,3,3);
            g.setColor(c);g.fillOval(x,y-6,8,8);
            g.setFont(F_TINY);g.drawString(t,x+12,y+2);
        }
    }



