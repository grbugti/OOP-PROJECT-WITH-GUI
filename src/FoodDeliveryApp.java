import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FoodDeliveryApp extends JFrame {

    // ─── Colors & Fonts ───────────────────────────────────────────────────────
    private static final Color BG_DARK        = new Color(18, 18, 18);
    private static final Color BG_PANEL       = new Color(30, 30, 30);
    private static final Color BG_CARD        = new Color(40, 40, 40);
    private static final Color ACCENT_RED     = new Color(220, 38, 38);
    private static final Color ACCENT_ORANGE  = new Color(251, 146, 60);
    private static final Color TEXT_WHITE     = new Color(255, 255, 255);
    private static final Color TEXT_GRAY      = new Color(160, 160, 160);
    private static final Color TEXT_LIGHT     = new Color(229, 229, 229);
    private static final Color SUCCESS_GREEN  = new Color(34, 197, 94);
    private static final Color BORDER_COLOR   = new Color(55, 55, 55);
    private static final Color JAZZ_GREEN     = new Color(0, 168, 89);
    private static final Color EASY_PURPLE    = new Color(130, 0, 200);
    private static final Color CARD_BLUE      = new Color(30, 100, 220);
    private static final Color COD_YELLOW     = new Color(234, 179, 8);

    private static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_PRICE  = new Font("Segoe UI", Font.BOLD, 14);

    // ─── Data ─────────────────────────────────────────────────────────────────
    private Restaurant restaurant;
    private Cart       cart;
    private Customer   customer;
    private String     selectedPayment = "Cash on Delivery";

    // ─── Panels ───────────────────────────────────────────────────────────────
    private JPanel     mainPanel;
    private CardLayout cardLayout;

    // Login
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;

    // Menu
    private JPanel menuItemsPanel;
    private JLabel cartCountLabel;

    // Cart
    private DefaultTableModel cartTableModel;
    private JLabel            totalLabel;

    // Payment
    private JPanel   paymentMethodsPanel;
    private JTextField epAccountField;
    private JTextField jcAccountField;
    private JTextField cardNumberField;
    private JTextField cardExpiryField;
    private JTextField cardCVVField;
    private JPanel   epFields;
    private JPanel   jcFields;
    private JPanel   cardFields;
    private JPanel   codFields;
    private JLabel   paymentTotalLabel;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public FoodDeliveryApp() {
        setupData();
        setupWindow();
        buildUI();
        setVisible(true);
    }

    private void setupData() {
        restaurant = new Restaurant("GRide Food Delivery");
        restaurant.addFood(new FoodItem("Zinger Burger", 550));
        restaurant.addFood(new FoodItem("Pizza",         1200));
        restaurant.addFood(new FoodItem("Fries",         300));
        restaurant.addFood(new FoodItem("Cold Drink",    150));
        restaurant.addFood(new FoodItem("Chicken Wrap",  450));
        restaurant.addFood(new FoodItem("Garlic Bread",  220));
        cart = new Cart();
    }

    private void setupWindow() {
        setTitle("GRide – Food Delivery System");
        setSize(980, 680);
        setMinimumSize(new Dimension(860, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
    }

    private void buildUI() {
        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);
        mainPanel.setBackground(BG_DARK);

        mainPanel.add(buildLoginPanel(),    "LOGIN");
        mainPanel.add(buildMenuPanel(),     "MENU");
        mainPanel.add(buildCartPanel(),     "CART");
        mainPanel.add(buildPaymentPanel(),  "PAYMENT");
        mainPanel.add(buildSuccessPanel(),  "SUCCESS");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  1. LOGIN PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildLoginPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_DARK);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(36, 50, 36, 50)
        ));
        card.setMaximumSize(new Dimension(440, 560));

        JLabel logoIcon = new JLabel("🍔", SwingConstants.CENTER);
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        logoIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("GRide Food Delivery", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        title.setForeground(ACCENT_ORANGE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Fast delivery at your doorstep", SwingConstants.CENTER);
        subtitle.setFont(FONT_SMALL);
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        sep.setMaximumSize(new Dimension(340, 1));

        nameField    = makeTextField("Enter your name...");
        phoneField   = makeTextField("e.g. 0300-1234567");
        addressField = makeTextField("Enter delivery address...");

        JButton startBtn = makePrimaryButton("START ORDERING  →", ACCENT_RED);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(e -> handleLogin());
        phoneField.addActionListener(e -> handleLogin());
        addressField.addActionListener(e -> handleLogin());

        card.add(logoIcon);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(20));
        card.add(sep);
        card.add(Box.createVerticalStrut(20));
        card.add(makeLabel("Your Name"));
        card.add(Box.createVerticalStrut(5));
        card.add(nameField);
        card.add(Box.createVerticalStrut(14));
        card.add(makeLabel("Phone Number"));
        card.add(Box.createVerticalStrut(5));
        card.add(phoneField);
        card.add(Box.createVerticalStrut(14));
        card.add(makeLabel("Delivery Address"));
        card.add(Box.createVerticalStrut(5));
        card.add(addressField);
        card.add(Box.createVerticalStrut(26));
        card.add(startBtn);

        outer.add(card);
        return outer;
    }

    private void handleLogin() {
        String name    = nameField.getText().trim();
        String phone   = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || name.equals("Enter your name...")) {
            showError("Please enter your name."); return;
        }
        if (phone.isEmpty() || phone.equals("e.g. 0300-1234567")) {
            showError("Please enter your phone number."); return;
        }
        if (address.isEmpty() || address.equals("Enter delivery address...")) {
            showError("Please enter your delivery address."); return;
        }
        customer = new Customer(name, phone);
        cardLayout.show(mainPanel, "MENU");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  2. MENU PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildMenuPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_PANEL);
        topBar.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel restaurantLabel = new JLabel("🍽  " + restaurant.getRestaurantName());
        restaurantLabel.setFont(FONT_HEADER);
        restaurantLabel.setForeground(TEXT_WHITE);

        cartCountLabel = new JLabel("🛒  Cart (0)");
        cartCountLabel.setFont(FONT_BODY);
        cartCountLabel.setForeground(ACCENT_ORANGE);
        cartCountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cartCountLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showCart(); }
        });

        topBar.add(restaurantLabel, BorderLayout.WEST);
        topBar.add(cartCountLabel,  BorderLayout.EAST);

        JLabel menuTitle = new JLabel("  Our Menu");
        menuTitle.setFont(FONT_HEADER);
        menuTitle.setForeground(TEXT_GRAY);
        menuTitle.setBorder(new EmptyBorder(18, 20, 8, 20));

        menuItemsPanel = new JPanel(new GridLayout(0, 2, 14, 14));
        menuItemsPanel.setBackground(BG_DARK);
        menuItemsPanel.setBorder(new EmptyBorder(4, 20, 20, 20));
        populateMenuItems();

        JScrollPane scroll = new JScrollPane(menuItemsPanel);
        scroll.setBackground(BG_DARK);
        scroll.getViewport().setBackground(BG_DARK);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BG_DARK);
        center.add(menuTitle, BorderLayout.NORTH);
        center.add(scroll,    BorderLayout.CENTER);

        root.add(topBar,  BorderLayout.NORTH);
        root.add(center,  BorderLayout.CENTER);
        return root;
    }

    private void populateMenuItems() {
        menuItemsPanel.removeAll();
        String[] emojis = {"🍔", "🍕", "🍟", "🥤", "🌯", "🧄"};
        ArrayList<FoodItem> menu = restaurant.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            String emoji = i < emojis.length ? emojis[i] : "🍽";
            menuItemsPanel.add(buildMenuCard(menu.get(i), emoji));
        }
        menuItemsPanel.revalidate();
        menuItemsPanel.repaint();
    }

    private JPanel buildMenuCard(FoodItem item, String emoji) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        emojiLabel.setPreferredSize(new Dimension(52, 52));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(BG_CARD);

        JLabel nameLabel  = new JLabel(item.getName());
        nameLabel.setFont(FONT_BODY);
        nameLabel.setForeground(TEXT_WHITE);
        JLabel priceLabel = new JLabel("Rs. " + (int) item.getPrice());
        priceLabel.setFont(FONT_PRICE);
        priceLabel.setForeground(ACCENT_ORANGE);

        info.add(nameLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(priceLabel);

        JButton addBtn = new JButton("+  Add");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addBtn.setBackground(ACCENT_RED);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorder(new EmptyBorder(8, 14, 8, 14));
        addBtn.setFocusPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> {
            cart.addToCart(item);
            updateCartCount();
            showToast(item.getName() + " added to cart ✓");
        });
        addBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { addBtn.setBackground(new Color(185, 28, 28)); }
            public void mouseExited(MouseEvent e)  { addBtn.setBackground(ACCENT_RED); }
        });

        card.add(emojiLabel, BorderLayout.WEST);
        card.add(info,       BorderLayout.CENTER);
        card.add(addBtn,     BorderLayout.EAST);
        return card;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  3. CART PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildCartPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_PANEL);
        topBar.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel backBtn = new JLabel("← Back to Menu");
        backBtn.setFont(FONT_BODY);
        backBtn.setForeground(ACCENT_ORANGE);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { cardLayout.show(mainPanel, "MENU"); }
        });

        JLabel cartTitle = new JLabel("Your Cart 🛒");
        cartTitle.setFont(FONT_HEADER);
        cartTitle.setForeground(TEXT_WHITE);

        topBar.add(backBtn,   BorderLayout.WEST);
        topBar.add(cartTitle, BorderLayout.CENTER);

        String[] cols = {"#", "Item", "Price (Rs.)", "Remove"};
        cartTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return c == 3; }
        };

        JTable cartTable = new JTable(cartTableModel);
        styleTable(cartTable);
        cartTable.getColumn("Remove").setCellRenderer(new ButtonRenderer());
        cartTable.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox(), cartTable));

        JScrollPane tableScroll = new JScrollPane(cartTable);
        tableScroll.setBackground(BG_CARD);
        tableScroll.getViewport().setBackground(BG_CARD);
        tableScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(BG_PANEL);
        bottomBar.setBorder(new EmptyBorder(16, 24, 16, 24));

        totalLabel = new JLabel("Total:  Rs. 0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(ACCENT_ORANGE);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnRow.setBackground(BG_PANEL);
        JButton clearBtn    = makeSecondaryButton("Clear Cart");
        JButton checkoutBtn = makePrimaryButton("Proceed to Payment  →", SUCCESS_GREEN);

        clearBtn.addActionListener(e -> { cart.clearCart(); refreshCartTable(); });
        checkoutBtn.addActionListener(e -> {
            if (cart.getItemCount() == 0) { showError("Your cart is empty!"); return; }
            showPayment();
        });

        btnRow.add(clearBtn);
        btnRow.add(checkoutBtn);
        bottomBar.add(totalLabel, BorderLayout.WEST);
        bottomBar.add(btnRow,     BorderLayout.EAST);

        root.add(topBar,      BorderLayout.NORTH);
        root.add(tableScroll, BorderLayout.CENTER);
        root.add(bottomBar,   BorderLayout.SOUTH);
        return root;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  4. PAYMENT PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildPaymentPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_PANEL);
        topBar.setBorder(new EmptyBorder(16, 24, 16, 24));
        JLabel backBtn = new JLabel("← Back to Cart");
        backBtn.setFont(FONT_BODY);
        backBtn.setForeground(ACCENT_ORANGE);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showCart(); }
        });
        JLabel payTitle = new JLabel("Choose Payment Method 💳");
        payTitle.setFont(FONT_HEADER);
        payTitle.setForeground(TEXT_WHITE);
        topBar.add(backBtn,  BorderLayout.WEST);
        topBar.add(payTitle, BorderLayout.CENTER);

        // ── Split: left = methods, right = details ────────────────────────────
        JPanel splitPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        splitPanel.setBackground(BG_DARK);

        // LEFT: payment method buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BG_DARK);
        leftPanel.setBorder(new EmptyBorder(24, 24, 24, 12));

        JLabel chooseLabel = new JLabel("Select Method");
        chooseLabel.setFont(FONT_HEADER);
        chooseLabel.setForeground(TEXT_GRAY);
        chooseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(chooseLabel);
        leftPanel.add(Box.createVerticalStrut(16));
        leftPanel.add(buildPaymentMethodBtn("💜  Easypaisa",     EASY_PURPLE,  "Easypaisa"));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(buildPaymentMethodBtn("💚  JazzCash",       JAZZ_GREEN,   "JazzCash"));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(buildPaymentMethodBtn("💳  Debit / Credit Card", CARD_BLUE, "Card"));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(buildPaymentMethodBtn("💵  Cash on Delivery", COD_YELLOW, "Cash on Delivery"));
        leftPanel.add(Box.createVerticalGlue());

        // RIGHT: dynamic detail panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BG_DARK);
        rightPanel.setBorder(new EmptyBorder(24, 12, 24, 24));

        paymentMethodsPanel = new JPanel(new CardLayout());
        paymentMethodsPanel.setBackground(BG_DARK);

        paymentMethodsPanel.add(buildEasypaisaFields(), "Easypaisa");
        paymentMethodsPanel.add(buildJazzCashFields(),  "JazzCash");
        paymentMethodsPanel.add(buildCardFields(),      "Card");
        paymentMethodsPanel.add(buildCODFields(),       "Cash on Delivery");

        // Bottom: total + confirm
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(BG_PANEL);
        bottomBar.setBorder(new EmptyBorder(16, 24, 16, 24));

        paymentTotalLabel = new JLabel("Total:  Rs. 0");
        paymentTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        paymentTotalLabel.setForeground(ACCENT_ORANGE);

        JButton confirmBtn = makePrimaryButton("✔  Confirm & Place Order", SUCCESS_GREEN);
        confirmBtn.addActionListener(e -> handleConfirmOrder());

        bottomBar.add(paymentTotalLabel, BorderLayout.WEST);
        bottomBar.add(confirmBtn,        BorderLayout.EAST);

        // show first method by default
        showPaymentFields("Easypaisa");

        rightPanel.add(paymentMethodsPanel, BorderLayout.CENTER);
        splitPanel.add(leftPanel);
        splitPanel.add(rightPanel);

        root.add(topBar,     BorderLayout.NORTH);
        root.add(splitPanel, BorderLayout.CENTER);
        root.add(bottomBar,  BorderLayout.SOUTH);
        return root;
    }

    private JButton buildPaymentMethodBtn(String text, Color color, String methodKey) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));
        btn.setForeground(color.brighter());
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(color, 1, true),
            new EmptyBorder(14, 18, 14, 18)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> {
            selectedPayment = methodKey;
            showPaymentFields(methodKey);
        });
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40)); }
        });
        return btn;
    }

    private void showPaymentFields(String key) {
        ((CardLayout) paymentMethodsPanel.getLayout()).show(paymentMethodsPanel, key);
        selectedPayment = key;
    }

    // ── Easypaisa fields ─────────────────────────────────────────────────────
    private JPanel buildEasypaisaFields() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_DARK);

        JPanel header = makeMethodHeader("💜 Easypaisa", EASY_PURPLE,
            "Send payment to: 0311-1234567\nAccount Name: GRide Pvt Ltd");

        JLabel accLabel = makeLabel("Your Easypaisa Number");
        epAccountField  = makeTextField("e.g. 0311-XXXXXXX");
        JLabel hint = new JLabel("  Screenshot required after payment");
        hint.setFont(FONT_SMALL);
        hint.setForeground(TEXT_GRAY);

        p.add(header);
        p.add(Box.createVerticalStrut(20));
        p.add(accLabel);
        p.add(Box.createVerticalStrut(6));
        p.add(epAccountField);
        p.add(Box.createVerticalStrut(10));
        p.add(hint);
        p.add(Box.createVerticalGlue());
        return p;
    }

    // ── JazzCash fields ──────────────────────────────────────────────────────
    private JPanel buildJazzCashFields() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_DARK);

        JPanel header = makeMethodHeader("💚 JazzCash", JAZZ_GREEN,
            "Send payment to: 0301-9876543\nAccount Name: GRide Pvt Ltd");

        JLabel accLabel = makeLabel("Your JazzCash Number");
        jcAccountField  = makeTextField("e.g. 0301-XXXXXXX");
        JLabel hint = new JLabel("  Screenshot required after payment");
        hint.setFont(FONT_SMALL);
        hint.setForeground(TEXT_GRAY);

        p.add(header);
        p.add(Box.createVerticalStrut(20));
        p.add(accLabel);
        p.add(Box.createVerticalStrut(6));
        p.add(jcAccountField);
        p.add(Box.createVerticalStrut(10));
        p.add(hint);
        p.add(Box.createVerticalGlue());
        return p;
    }

    // ── Card fields ───────────────────────────────────────────────────────────
    private JPanel buildCardFields() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_DARK);

        JPanel header = makeMethodHeader("💳 Debit / Credit Card", CARD_BLUE,
            "Visa, Mastercard, UnionPay accepted\nSecure 256-bit encrypted payment");

        cardNumberField = makeTextField("XXXX - XXXX - XXXX - XXXX");
        cardExpiryField = makeTextField("MM / YY");
        cardCVVField    = makeTextField("CVV");

        JPanel row = new JPanel(new GridLayout(1, 2, 12, 0));
        row.setBackground(BG_DARK);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        row.add(cardExpiryField);
        row.add(cardCVVField);

        p.add(header);
        p.add(Box.createVerticalStrut(20));
        p.add(makeLabel("Card Number"));
        p.add(Box.createVerticalStrut(6));
        p.add(cardNumberField);
        p.add(Box.createVerticalStrut(14));
        p.add(makeLabel("Expiry Date  &  CVV"));
        p.add(Box.createVerticalStrut(6));
        p.add(row);
        p.add(Box.createVerticalGlue());
        return p;
    }

    // ── Cash on Delivery fields ───────────────────────────────────────────────
    private JPanel buildCODFields() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_DARK);

        JPanel header = makeMethodHeader("💵 Cash on Delivery", COD_YELLOW,
            "Pay when your order arrives!\nPlease keep exact change ready.");

        JLabel note1 = new JLabel("  ✓  No advance payment needed");
        JLabel note2 = new JLabel("  ✓  Rider will collect at doorstep");
        JLabel note3 = new JLabel("  ✓  Estimated time: 30–45 minutes");
        for (JLabel l : new JLabel[]{note1, note2, note3}) {
            l.setFont(FONT_BODY);
            l.setForeground(TEXT_LIGHT);
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        p.add(header);
        p.add(Box.createVerticalStrut(20));
        p.add(note1);
        p.add(Box.createVerticalStrut(10));
        p.add(note2);
        p.add(Box.createVerticalStrut(10));
        p.add(note3);
        p.add(Box.createVerticalGlue());
        return p;
    }

    private JPanel makeMethodHeader(String title, Color color, String subtext) {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 25));
        header.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80), 1, true),
            new EmptyBorder(14, 16, 14, 16)
        ));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_HEADER);
        titleLabel.setForeground(color.brighter());

        for (String line : subtext.split("\n")) {
            JLabel l = new JLabel(line);
            l.setFont(FONT_SMALL);
            l.setForeground(TEXT_GRAY);
            header.add(l);
        }
        header.add(titleLabel, 0);
        return header;
    }

    private void handleConfirmOrder() {
        // Validation per payment method
        switch (selectedPayment) {
            case "Easypaisa":
                String ep = epAccountField.getText().trim();
                if (ep.isEmpty() || ep.equals("e.g. 0311-XXXXXXX")) {
                    showError("Please enter your Easypaisa number."); return;
                }
                break;
            case "JazzCash":
                String jc = jcAccountField.getText().trim();
                if (jc.isEmpty() || jc.equals("e.g. 0301-XXXXXXX")) {
                    showError("Please enter your JazzCash number."); return;
                }
                break;
            case "Card":
                String cn = cardNumberField.getText().trim();
                String ex = cardExpiryField.getText().trim();
                String cv = cardCVVField.getText().trim();
                if (cn.isEmpty() || cn.equals("XXXX - XXXX - XXXX - XXXX")) {
                    showError("Please enter card number."); return;
                }
                if (ex.isEmpty() || ex.equals("MM / YY")) {
                    showError("Please enter expiry date."); return;
                }
                if (cv.isEmpty() || cv.equals("CVV")) {
                    showError("Please enter CVV."); return;
                }
                break;
        }
        showSuccess();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  5. SUCCESS PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildSuccessPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_DARK);
        outer.setName("SUCCESS_OUTER");
        return outer;
    }

    private void showSuccess() {
        // Refresh success card content
        JPanel outer = null;
        for (Component c : mainPanel.getComponents())
            if (c instanceof JPanel && "SUCCESS_OUTER".equals(c.getName())) { outer = (JPanel) c; break; }
        if (outer == null) return;
        outer.removeAll();

        JScrollPane scroll = buildSuccessCard();
        outer.add(scroll);
        outer.revalidate();
        outer.repaint();
        cardLayout.show(mainPanel, "SUCCESS");
    }

    private JScrollPane buildSuccessCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(32, 44, 32, 44)
        ));

        JLabel iconLabel = new JLabel("✅", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        iconLabel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel heading = new JLabel("Order Placed Successfully!", SwingConstants.CENTER);
        heading.setFont(FONT_TITLE);
        heading.setForeground(SUCCESS_GREEN);
        heading.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subheading = new JLabel("Thank you for ordering from GRide 🎉", SwingConstants.CENTER);
        subheading.setFont(FONT_BODY);
        subheading.setForeground(TEXT_GRAY);
        subheading.setAlignmentX(CENTER_ALIGNMENT);

        // ── Customer Info box ─────────────────────────────────────────────────
        JPanel infoBox = buildInfoBox();

        // ── Items ordered ─────────────────────────────────────────────────────
        JPanel itemsBox = new JPanel();
        itemsBox.setLayout(new BoxLayout(itemsBox, BoxLayout.Y_AXIS));
        itemsBox.setBackground(new Color(25, 25, 25));
        itemsBox.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(12, 16, 12, 16)
        ));
        itemsBox.setMaximumSize(new Dimension(500, 500));
        itemsBox.setAlignmentX(CENTER_ALIGNMENT);

        JLabel itemsTitle = new JLabel("Items Ordered");
        itemsTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        itemsTitle.setForeground(TEXT_GRAY);
        itemsBox.add(itemsTitle);
        itemsBox.add(Box.createVerticalStrut(8));

        for (FoodItem fi : cart.getCartItems()) {
            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(new Color(25, 25, 25));
            JLabel n = makeInfoVal("• " + fi.getName());
            JLabel p = makeInfoVal("Rs. " + (int) fi.getPrice());
            p.setHorizontalAlignment(SwingConstants.RIGHT);
            row.add(n, BorderLayout.WEST);
            row.add(p, BorderLayout.EAST);
            itemsBox.add(row);
            itemsBox.add(Box.createVerticalStrut(5));
        }

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        sep.setMaximumSize(new Dimension(500, 1));
        itemsBox.add(Box.createVerticalStrut(4));
        itemsBox.add(sep);
        itemsBox.add(Box.createVerticalStrut(6));

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setBackground(new Color(25, 25, 25));
        JLabel tKey = new JLabel("Total Amount");
        tKey.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tKey.setForeground(TEXT_WHITE);
        JLabel tVal = new JLabel("Rs. " + String.format("%.0f", cart.getTotal()));
        tVal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tVal.setForeground(ACCENT_ORANGE);
        tVal.setHorizontalAlignment(SwingConstants.RIGHT);
        totalRow.add(tKey, BorderLayout.WEST);
        totalRow.add(tVal, BorderLayout.EAST);
        itemsBox.add(totalRow);

        // ── Payment badge ─────────────────────────────────────────────────────
        Color badgeColor = getBadgeColor(selectedPayment);
        JLabel payBadge = new JLabel("  Paid via: " + selectedPayment + "  ", SwingConstants.CENTER);
        payBadge.setFont(new Font("Segoe UI", Font.BOLD, 13));
        payBadge.setForeground(badgeColor.brighter());
        payBadge.setBackground(new Color(badgeColor.getRed(), badgeColor.getGreen(), badgeColor.getBlue(), 40));
        payBadge.setOpaque(true);
        payBadge.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(badgeColor, 1, true),
            new EmptyBorder(6, 14, 6, 14)
        ));
        payBadge.setAlignmentX(CENTER_ALIGNMENT);

        JLabel deliveryNote = new JLabel("📞  Delivery Contact: +92 3369240331", SwingConstants.CENTER);
        deliveryNote.setFont(FONT_SMALL);
        deliveryNote.setForeground(TEXT_GRAY);
        deliveryNote.setAlignmentX(CENTER_ALIGNMENT);

        JLabel etaNote = new JLabel("🕒  Estimated Delivery: 30–45 minutes", SwingConstants.CENTER);
        etaNote.setFont(FONT_SMALL);
        etaNote.setForeground(TEXT_GRAY);
        etaNote.setAlignmentX(CENTER_ALIGNMENT);

        // ── Thank You message ─────────────────────────────────────────────────
        JSeparator thankSep = new JSeparator();
        thankSep.setForeground(BORDER_COLOR);
        thankSep.setMaximumSize(new Dimension(500, 1));
        thankSep.setAlignmentX(CENTER_ALIGNMENT);

        String cName = (customer != null) ? customer.getCustomerName() : "Customer";
        JLabel thankYouLabel = new JLabel("🙏  Thank you, " + cName + "! We truly appreciate your order.", SwingConstants.CENTER);
        thankYouLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        thankYouLabel.setForeground(new Color(255, 215, 0));
        thankYouLabel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel thankYouLine2 = new JLabel("We are always here to serve you with the best food! ❤", SwingConstants.CENTER);
        thankYouLine2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        thankYouLine2.setForeground(new Color(255, 215, 0));
        thankYouLine2.setAlignmentX(CENTER_ALIGNMENT);

        // ── Again Order button ────────────────────────────────────────────────
        JButton againOrderBtn = makePrimaryButton("🔄  Order Again", ACCENT_ORANGE);
        againOrderBtn.setAlignmentX(CENTER_ALIGNMENT);
        againOrderBtn.addActionListener(e -> {
            cart.clearCart();
            updateCartCount();
            selectedPayment = "Easypaisa";
            cardLayout.show(mainPanel, "MENU");
        });

        JButton newOrderBtn = makeSecondaryButton("New Order with Different Account");
        newOrderBtn.setAlignmentX(CENTER_ALIGNMENT);
        newOrderBtn.addActionListener(e -> {
            cart.clearCart();
            updateCartCount();
            nameField.setText("Enter your name...");
            phoneField.setText("e.g. 0300-1234567");
            addressField.setText("Enter delivery address...");
            nameField.setForeground(TEXT_GRAY);
            phoneField.setForeground(TEXT_GRAY);
            addressField.setForeground(TEXT_GRAY);
            customer = null;
            selectedPayment = "Easypaisa";
            cardLayout.show(mainPanel, "LOGIN");
        });

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(heading);
        card.add(Box.createVerticalStrut(4));
        card.add(subheading);
        card.add(Box.createVerticalStrut(20));
        card.add(infoBox);
        card.add(Box.createVerticalStrut(14));
        card.add(itemsBox);
        card.add(Box.createVerticalStrut(14));
        card.add(payBadge);
        card.add(Box.createVerticalStrut(10));
        card.add(deliveryNote);
        card.add(Box.createVerticalStrut(4));
        card.add(etaNote);
        card.add(Box.createVerticalStrut(20));
        card.add(thankSep);
        card.add(Box.createVerticalStrut(16));
        card.add(thankYouLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(thankYouLine2);
        card.add(Box.createVerticalStrut(20));
        card.add(againOrderBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(newOrderBtn);

        JScrollPane sp = new JScrollPane(card);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_DARK);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setPreferredSize(new Dimension(580, 560));
        return sp;
    }

    private JPanel buildInfoBox() {
        String addr = (addressField != null && !addressField.getText().equals("Enter delivery address..."))
                      ? addressField.getText() : "N/A";

        JPanel box = new JPanel(new GridLayout(3, 2, 10, 8));
        box.setBackground(new Color(25, 25, 25));
        box.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(12, 16, 12, 16)
        ));
        box.setMaximumSize(new Dimension(500, 100));
        box.setAlignmentX(CENTER_ALIGNMENT);

        box.add(makeInfoKey("Customer:"));
        box.add(makeInfoVal(customer != null ? customer.getCustomerName() : "-"));
        box.add(makeInfoKey("Phone:"));
        box.add(makeInfoVal(customer != null ? customer.getPhoneNumber() : "-"));
        box.add(makeInfoKey("Address:"));
        box.add(makeInfoVal(addr));
        return box;
    }

    private Color getBadgeColor(String method) {
        switch (method) {
            case "Easypaisa":       return EASY_PURPLE;
            case "JazzCash":        return JAZZ_GREEN;
            case "Card":            return CARD_BLUE;
            default:                return COD_YELLOW;
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SHARED HELPERS
    // ══════════════════════════════════════════════════════════════════════════
    private void showPayment() {
        refreshCartTable();
        paymentTotalLabel.setText("Total:  Rs. " + String.format("%.0f", cart.getTotal()));
        showPaymentFields("Easypaisa");
        cardLayout.show(mainPanel, "PAYMENT");
    }

    private void styleTable(JTable table) {
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_LIGHT);
        table.setFont(FONT_BODY);
        table.setRowHeight(42);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(50, 50, 60));
        table.setSelectionForeground(TEXT_WHITE);
        table.setFillsViewportHeight(true);
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_PANEL);
        header.setForeground(TEXT_GRAY);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBorder(BorderFactory.createEmptyBorder());
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(140);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    private void refreshCartTable() {
        cartTableModel.setRowCount(0);
        ArrayList<FoodItem> items = cart.getCartItems();
        for (int i = 0; i < items.size(); i++) {
            cartTableModel.addRow(new Object[]{
                i + 1,
                items.get(i).getName(),
                String.format("%.0f", items.get(i).getPrice()),
                "🗑 Remove"
            });
        }
        totalLabel.setText("Total:  Rs. " + String.format("%.0f", cart.getTotal()));
        updateCartCount();
    }

    private void showCart() {
        refreshCartTable();
        cardLayout.show(mainPanel, "CART");
    }

    private void updateCartCount() {
        if (cartCountLabel != null)
            cartCountLabel.setText("🛒  Cart (" + cart.getItemCount() + ")");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showToast(String msg) {
        JWindow toast = new JWindow(this);
        JLabel label  = new JLabel("  " + msg + "  ", SwingConstants.CENTER);
        label.setFont(FONT_BODY);
        label.setForeground(Color.WHITE);
        label.setBackground(SUCCESS_GREEN);
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(10, 20, 10, 20));
        toast.add(label);
        toast.pack();
        toast.setLocation(
            getX() + (getWidth()  - toast.getWidth())  / 2,
            getY() + getHeight()  - toast.getHeight()  - 60
        );
        toast.setVisible(true);
        new Timer(1800, e -> toast.dispose()).start();
    }

    // ── Widget factories ──────────────────────────────────────────────────────
    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_GRAY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField tf = new JTextField(placeholder);
        tf.setFont(FONT_BODY);
        tf.setBackground(new Color(55, 55, 55));
        tf.setForeground(TEXT_GRAY);
        tf.setCaretColor(TEXT_WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        tf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(placeholder)) { tf.setText(""); tf.setForeground(TEXT_WHITE); }
            }
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) { tf.setText(placeholder); tf.setForeground(TEXT_GRAY); }
            }
        });
        return tf;
    }

    private JButton makePrimaryButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setBorder(new EmptyBorder(12, 28, 12, 28));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        return btn;
    }

    private JButton makeSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BODY);
        btn.setBackground(BG_CARD);
        btn.setForeground(TEXT_GRAY);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(10, 20, 10, 20)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel makeInfoKey(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT_GRAY);
        return l;
    }

    private JLabel makeInfoVal(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_WHITE);
        return l;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  TABLE BUTTON RENDERER / EDITOR
    // ══════════════════════════════════════════════════════════════════════════
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(FONT_SMALL);
            setBackground(new Color(80, 30, 30));
            setForeground(new Color(255, 100, 100));
            setBorder(new EmptyBorder(4, 10, 4, 10));
            setFocusPainted(false);
        }
        public Component getTableCellRendererComponent(JTable t, Object v,
                boolean sel, boolean foc, int r, int c) {
            setText(v != null ? v.toString() : "");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int     editRow;

        public ButtonEditor(JCheckBox cb, JTable table) {
            super(cb);
            button = new JButton();
            button.setOpaque(true);
            button.setFont(FONT_SMALL);
            button.setBackground(new Color(80, 30, 30));
            button.setForeground(new Color(255, 100, 100));
            button.setBorder(new EmptyBorder(4, 10, 4, 10));
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                fireEditingStopped();
                if (editRow >= 0 && editRow < cart.getItemCount()) {
                    cart.removeItem(editRow);
                    refreshCartTable();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable t, Object v,
                boolean sel, int row, int col) {
            editRow = row;
            button.setText(v != null ? v.toString() : "");
            return button;
        }
        public Object getCellEditorValue() { return button.getText(); }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}
        UIManager.put("OptionPane.background",        BG_CARD);
        UIManager.put("Panel.background",             BG_CARD);
        UIManager.put("OptionPane.messageForeground", TEXT_WHITE);
        SwingUtilities.invokeLater(FoodDeliveryApp::new);
    }
}
