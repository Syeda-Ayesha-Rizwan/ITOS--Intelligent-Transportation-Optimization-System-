
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