
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
