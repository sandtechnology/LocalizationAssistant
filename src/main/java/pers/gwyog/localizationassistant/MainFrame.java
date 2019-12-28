package pers.gwyog.localizationassistant;

import pers.gwyog.localizationassistant.utils.ContainerUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    /*
     * LA主窗体的GUI布置和事件监听及实现
     *
     * @Author: JJN(GWYOG)
     */

    private static final long serialVersionUID = 1L;
    private JPanel infoPanel, replacePanel, insertPanel, renewPanel, conditionDeletePanel, translationReplacePanel, wordRatePanel; // 选项卡对应的面板
    private JPanel infoHolder, exitHolder;
    private JPanel panel2_1, panel2_2, panel2_3, panel2_3_1, panel2_3_2, panel2_warning, panel2_4, panel2_5, panel2_6,
            panel2_7;
    private JPanel panel3_1, panel3_2, panel3_3, panel3_3_1, panel3_3_2, panel3_4, panel3_5, panel3_6, panel3_7;
    private JPanel panel4_1, panel4_2, panel4_3, panel4_4, panel4_5, panel4_6, panel4_7;
    private JPanel panel5_1, panel5_2, panel5_3, panel5_4, panel5_5, panel5_6, panel5_7;
    private JPanel panel6_1, panel6_2, panel6_3, panel6_4, panel6_5, panel6_6, panel6_7;
    private JPanel panel7_1, panel7_2, panel7_3, panel7_4;
    private JButton button1, exitButton, button2_go, button2_open1, button2_open2;
    private JButton button3_open1, button3_open2, button3_go;
    private JButton button4_open1, button4_open2, button4_open3, button4_open4, button4_go;
    private JButton button5_open1, button5_open2, button5_go;
    private JButton button6_open1, button6_open2, button6_open3, button6_open4, button6_go;
    private JButton button7_open1, button7_open2, button7_go;
    private JLabel infoLabel;
    private JLabel inputPathLabel, outputPathLabel, inputStringLabel, outputStringLabel, replaceRegexTips, replacelineLabel, ingoreCharsLabel;
    private JLabel replaceSynaxTips, replaceIngoreTips;
    private JLabel replaceSkipLabel, insertStringLabel, keyFilterLabel;
    private JLabel keyFilterTips;
    private JLabel oldenLangLabel, oldzhLangLabel, newenLangLabel, newzhLangLabel, updateModeTips, additionModeLabel;
    private JLabel cleanAfterStringLabel, cleanKeepStringLabel, cleanUpdateInfoLabel;
    private JLabel orginenLangLabel, undonezhLangLabel, processzhLangLabel, replaceModeLabel;
    private JLabel inputenStatLangLabel, statOutpathLabel, statFilterLabel;
    // private JList list1,list2; //to-do: auto-merge
    private JTabbedPane tabbedPane;
    private JTextField textField2_1, textField2_2, textField2_3, textField2_4, textField2_5, textField2_6;
    private JTextField textField3_1, textField3_2, textField3_3_1, textField3_3_2, textField3_4, textField3_5,
            textField3_6;
    private JTextField textField4_1, textField4_2, textField4_3, textField4_4;
    private JTextField textField5_1, textField5_2, textField5_3, textField5_4;
    private JTextField textField6_1, textField6_2, textField6_3, textField6_4;
    private JTextField textField7_1, textField7_2, textField7_3;
    private JRadioButton updateMode1Button, updateMode2Button, updateMode3Button, updateMode4Button, updateMode5Button;
    private JRadioButton replaceMode1Button, replaceMode2Button;
    private JCheckBox checkModeCheckBox, displayChangeCheckBox, keepStringCheckBox, cleanUpdateInfoCheckBox, checkModeCheckBox2;
    private ButtonGroup updateModebuttongroup, replaceModeButtongroup;
    private FileDialog filedialog_open;

    public MainFrame() {
        super("Localization Assistant v1.7.0-alpha1");
        setSize(592, 640);
        setVisible(true);
        start();
    }

    // main函数
    public static void main(String[] args) {
        new MainFrame();
    }

    // 在构造方法中实现LA所有GUI的布置
    public void start() {

        // panels
        infoPanel = new JPanel();
        replacePanel = new JPanel();
        insertPanel = new JPanel();
        renewPanel = new JPanel();
        conditionDeletePanel = new JPanel();
        translationReplacePanel = new JPanel();
        wordRatePanel = new JPanel();
        infoHolder = new JPanel();
        exitHolder = new JPanel();
        panel2_1 = new JPanel();
        panel2_2 = new JPanel();
        panel2_3 = new JPanel();
        panel2_3_1 = new JPanel();
        panel2_3_2 = new JPanel();
        panel2_warning = new JPanel();
        panel2_4 = new JPanel();
        panel2_5 = new JPanel();
        panel2_6 = new JPanel();
        panel2_7 = new JPanel();
        panel3_1 = new JPanel();
        panel3_2 = new JPanel();
        panel3_3 = new JPanel();
        panel3_3_1 = new JPanel();
        panel3_3_2 = new JPanel();
        panel3_4 = new JPanel();
        panel3_5 = new JPanel();
        panel3_6 = new JPanel();
        panel3_7 = new JPanel();
        panel4_1 = new JPanel();
        panel4_2 = new JPanel();
        panel4_3 = new JPanel();
        panel4_4 = new JPanel();
        panel4_5 = new JPanel();
        panel4_6 = new JPanel();
        panel4_7 = new JPanel();
        panel5_1 = new JPanel();
        panel5_2 = new JPanel();
        panel5_3 = new JPanel();
        panel5_5 = new JPanel();
        panel5_6 = new JPanel();
        panel5_7 = new JPanel();
        panel5_4 = new JPanel();
        panel6_1 = new JPanel();
        panel6_2 = new JPanel();
        panel6_3 = new JPanel();
        panel6_4 = new JPanel();
        panel6_5 = new JPanel();
        panel6_6 = new JPanel();
        panel6_7 = new JPanel();
        panel7_1 = new JPanel();
        panel7_2 = new JPanel();
        panel7_3 = new JPanel();
        panel7_4 = new JPanel();
        infoHolder.setLayout(new FlowLayout(FlowLayout.CENTER));
        exitHolder.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_3_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_3_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_4.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_5.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_6.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel2_7.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel3_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_3_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_3_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_4.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_5.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_6.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3_7.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel4_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4_4.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4_5.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4_6.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4_7.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel5_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel5_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel5_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel5_5.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel5_6.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel5_7.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel5_4.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel6_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel6_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel6_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel6_4.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel6_5.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel6_6.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel6_7.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel7_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel7_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel7_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel7_4.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2_3.setSize(600, 30);
        panel3_3.setSize(600, 30);

        // TabbedPane
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.addTab("基本信息", null, infoPanel, "关于LA的基本信息");
        tabbedPane.addTab("条件替换", null, replacePanel, "根据用户需要，有条件地替换字符串");
        tabbedPane.addTab("等距添加字符串", null, insertPanel, "每隔若干个字符就插入某个字符串");
        tabbedPane.addTab("可用文本更新", null, renewPanel, "将旧版文本中可用的译文自动更新到新版文本中");
        tabbedPane.addTab("逐行条件清理", null, conditionDeletePanel, "删除指定字符串后该行的所有内容或清理变更信息");
        tabbedPane.addTab("已译词条替换", null, translationReplacePanel, "用已经翻译的词条替换未翻译的部分内容");
        tabbedPane.addTab("高频词汇统计", null, wordRatePanel, "统计英文文本中的高频词汇");

        // Buttons
        button1 = new JButton("Open");
        exitButton = new JButton("Exit");
        button2_go = new JButton("Go");
        button2_go.setSize(50, 50);
        button3_go = new JButton("Go");
        button3_go.setSize(50, 50);
        button4_go = new JButton("Go");
        button4_go.setSize(50, 50);
        button5_go = new JButton("Go");
        button5_go.setSize(50, 50);
        button6_go = new JButton("Go");
        button6_go.setSize(50, 50);
        button7_go = new JButton("Go");
        button7_go.setSize(50, 50);
        button2_open1 = new JButton("Open");
        button2_open2 = new JButton("Open");
        button3_open1 = new JButton("Open");
        button3_open2 = new JButton("Open");
        button4_open1 = new JButton("Open");
        button4_open2 = new JButton("Open");
        button4_open3 = new JButton("Open");
        button4_open4 = new JButton("Open");
        button5_open1 = new JButton("Open");
        button5_open2 = new JButton("Open");
        button6_open1 = new JButton("Open");
        button6_open2 = new JButton("Open");
        button6_open3 = new JButton("Open");
        button6_open4 = new JButton("Open");
        button7_open1 = new JButton("Open");
        button7_open2 = new JButton("Open");
        button1.addActionListener(this);
        exitButton.addActionListener(this);
        button2_go.addActionListener(this);
        button2_open1.addActionListener(this);
        button2_open2.addActionListener(this);
        button3_go.addActionListener(this);
        button3_open1.addActionListener(this);
        button3_open2.addActionListener(this);
        button4_go.addActionListener(this);
        button4_open1.addActionListener(this);
        button4_open1.setEnabled(false);
        button4_open2.addActionListener(this);
        button4_open3.addActionListener(this);
        button4_open4.addActionListener(this);
        button5_go.addActionListener(this);
        button5_open1.addActionListener(this);
        button5_open2.addActionListener(this);
        button6_go.addActionListener(this);
        button6_open1.addActionListener(this);
        button6_open2.addActionListener(this);
        button6_open3.addActionListener(this);
        button6_open4.addActionListener(this);
        button7_go.addActionListener(this);
        button7_open1.addActionListener(this);
        button7_open2.addActionListener(this);


        // Labels
        infoLabel = new JLabel();
        infoLabel.setText("<html><body>作者:JJN(GWYOG)<br/>sandtechnology重构ing....<br/>版本号:v1.7.0-alpha1<br/>日期:2019/12/27</body></html>");
        inputPathLabel = new JLabel("待操作文件:");
        outputPathLabel = new JLabel("输出到文件:");
        inputStringLabel = new JLabel("待替换的字符串:");
        outputStringLabel = new JLabel("替换成的字符串:");
        replaceRegexTips = new JLabel("<html><body>注意:待替换的字符串可以是正则表达式！！<br/>不清楚的话可以不用管，但是替换\".\"时需要输入\"\\.\"</body></html>");
        replacelineLabel = new JLabel("*(此项可为空)  操作行数:");
        replaceSynaxTips = new JLabel("用\"数字\"或是\"数字-数字\"的形式表示,中间以英文逗号分隔,为空代表操作所有行");
        ingoreCharsLabel = new JLabel("*(此项可为空)  替换忽略符:");
        replaceIngoreTips = new JLabel("该对字符中间将不作替换(以\"-\"分隔,只支持输入一对字符即\"字符-字符\"的形式):");
        replaceSkipLabel = new JLabel("间隔字符个数:");
        insertStringLabel = new JLabel("待插入的字符串:");
        replaceSynaxTips = new JLabel("用\"数字\"或是\"数字-数字\"的形式表示,中间以英文逗号分隔,为空代表操作所有行");
        keyFilterLabel = new JLabel("*(此项可为空)  Key(即'='前的那部分)过滤器:");
        keyFilterTips = new JLabel("只操作Key中包含该些字符串之一的条目.中间以英文逗号分隔,为空代表无过滤");
        oldenLangLabel = new JLabel("旧版本 en_US.lang:");
        oldzhLangLabel = new JLabel("旧版本 zh_CN.lang:");
        newenLangLabel = new JLabel("新版本 en_US.lang:");
        newzhLangLabel = new JLabel("输出到文件(新版本 zh_CN.lang):");
        updateModeTips = new JLabel("更新方式(1表示比较\"=\"前的部分,2表示比较\"=\"后的部分):");
        additionModeLabel = new JLabel("附加模式:");
        cleanAfterStringLabel = new JLabel("清空此字符串及其(可改为不及其)后该行的内容:");
        cleanKeepStringLabel = new JLabel("清空时保留该字符串:");
        cleanUpdateInfoLabel = new JLabel("清理更新信息(优先级更高):");
        replacelineLabel = new JLabel("*(此项可为空)  操作行数:");
        orginenLangLabel = new JLabel("参考用英文文本en_US.lang:");
        undonezhLangLabel = new JLabel("半成品中文文本zh_CN.lang:");
        processzhLangLabel = new JLabel("待操作的半成品中文文本zh_CN.lang:");
        replaceModeLabel = new JLabel("替换模式:");
        inputenStatLangLabel = new JLabel("待统计高频词汇的en_US.lang");
        statOutpathLabel = new JLabel("统计结果输出到文件:");
        statFilterLabel = new JLabel("统计过滤器(以英文逗号分隔,在此列表中的单词将不纳入统计):");

        // textFields
        textField2_1 = new JTextField(40);
        textField2_2 = new JTextField(40);
        textField2_3 = new JTextField(10);
        textField2_4 = new JTextField(10);
        textField2_5 = new JTextField(40);
        textField2_6 = new JTextField(40);
        textField3_1 = new JTextField(40);
        textField3_2 = new JTextField(40);
        textField3_3_1 = new JTextField(10);
        textField3_3_2 = new JTextField(10);
        textField3_4 = new JTextField(40);
        textField3_5 = new JTextField(40);
        textField3_6 = new JTextField(40);
        textField4_1 = new JTextField(40);
        textField4_1.setEnabled(false);
        textField4_2 = new JTextField(40);
        textField4_3 = new JTextField(40);
        textField4_4 = new JTextField(40);
        textField5_1 = new JTextField(40);
        textField5_2 = new JTextField(40);
        textField5_3 = new JTextField(40);
        textField5_4 = new JTextField(40);
        textField6_1 = new JTextField(40);
        textField6_2 = new JTextField(40);
        textField6_3 = new JTextField(40);
        textField6_4 = new JTextField(40);
        textField7_1 = new JTextField(40);
        textField7_2 = new JTextField(40);
        textField7_3 = new JTextField(40);
        ContainerUtils.addComponent(this, tabbedPane);
        // contentPane.setBackground(Color.white);

        // radioButtons
        updateMode1Button = new JRadioButton("方式1");
        updateMode2Button = new JRadioButton("方式2");
        updateMode3Button = new JRadioButton("先1后2");
        updateMode4Button = new JRadioButton("先2后1");
        updateMode5Button = new JRadioButton("完全匹配");
        replaceMode1Button = new JRadioButton("普通替换");
        replaceMode2Button = new JRadioButton("根据\"英文=中文\"格式的文件进行替换");
        updateMode1Button.setSelected(true);
        replaceMode1Button.setSelected(true);
        updateMode1Button.addActionListener(this);
        updateMode2Button.addActionListener(this);
        updateMode3Button.addActionListener(this);
        updateMode4Button.addActionListener(this);
        updateMode5Button.addActionListener(this);
        replaceMode1Button.addActionListener(this);
        replaceMode2Button.addActionListener(this);
        updateModebuttongroup = new ButtonGroup();
        replaceModeButtongroup = new ButtonGroup();
        updateModebuttongroup.add(updateMode1Button);
        updateModebuttongroup.add(updateMode2Button);
        updateModebuttongroup.add(updateMode3Button);
        updateModebuttongroup.add(updateMode4Button);
        updateModebuttongroup.add(updateMode5Button);
        replaceModeButtongroup.add(replaceMode1Button);
        replaceModeButtongroup.add(replaceMode2Button);

        // CheckBoxs
        checkModeCheckBox = new JCheckBox("开启校验模式");
        checkModeCheckBox.setEnabled(false);
        displayChangeCheckBox = new JCheckBox("显示变更信息");
        displayChangeCheckBox.setEnabled(false);
        keepStringCheckBox = new JCheckBox("保留该字符串");
        cleanUpdateInfoCheckBox = new JCheckBox("清理更新信息");
        checkModeCheckBox2 = new JCheckBox("开启校验模式");

        // First Main Panel
        // ContainerUtils.addComponent(panel1,button1);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        ContainerUtils.addComponent(infoPanel, Box.createVerticalStrut(170));
        ContainerUtils.addComponent(infoPanel, infoHolder);
        ContainerUtils.addComponent(infoPanel, exitHolder);
        ContainerUtils.addComponent(infoPanel, Box.createVerticalStrut(170));
        ContainerUtils.addComponent(infoHolder, infoLabel);
        ContainerUtils.addComponent(exitHolder, exitButton);
        // Second Main Panel
        replacePanel.setLayout(new BoxLayout(replacePanel, BoxLayout.Y_AXIS));
        ContainerUtils.addComponent(replacePanel, panel2_1);
        ContainerUtils.addComponent(panel2_1, inputPathLabel);
        ContainerUtils.addComponent(panel2_1, button2_open1);
        ContainerUtils.addComponent(panel2_1, textField2_1);
        ContainerUtils.addComponent(replacePanel, panel2_2);
        ContainerUtils.addComponent(panel2_2, outputPathLabel);
        ContainerUtils.addComponent(panel2_2, button2_open2);
        ContainerUtils.addComponent(panel2_2, textField2_2);
        ContainerUtils.addComponent(replacePanel, panel2_3);
        ContainerUtils.addComponent(panel2_3, panel2_3_1);
        ContainerUtils.addComponent(panel2_3, panel2_3_2);
        ContainerUtils.addComponent(panel2_3_1, inputStringLabel);
        ContainerUtils.addComponent(panel2_3_1, textField2_3);
        ContainerUtils.addComponent(panel2_3_2, outputStringLabel);
        ContainerUtils.addComponent(panel2_3_2, textField2_4);
        ContainerUtils.addComponent(replacePanel, panel2_warning);
        ContainerUtils.addComponent(panel2_warning, replaceRegexTips);
        ContainerUtils.addComponent(replacePanel, panel2_5);
        ContainerUtils.addComponent(panel2_5, replacelineLabel);
        ContainerUtils.addComponent(panel2_5, replaceSynaxTips);
        ContainerUtils.addComponent(panel2_5, textField2_5);
        ContainerUtils.addComponent(replacePanel, panel2_6);
        ContainerUtils.addComponent(panel2_6, ingoreCharsLabel);
        ContainerUtils.addComponent(panel2_6, replaceIngoreTips);
        ContainerUtils.addComponent(panel2_6, textField2_6);
        ContainerUtils.addComponent(replacePanel, panel2_7);
        ContainerUtils.addComponent(panel2_7, button2_go);
        // Third Main Panel
        insertPanel.setLayout(new BoxLayout(insertPanel, BoxLayout.Y_AXIS));
        ContainerUtils.addComponent(insertPanel, panel3_1);
        ContainerUtils.addComponent(panel3_1, inputPathLabel);
        ContainerUtils.addComponent(panel3_1, button3_open1);
        ContainerUtils.addComponent(panel3_1, textField3_1);
        ContainerUtils.addComponent(insertPanel, panel3_2);
        ContainerUtils.addComponent(panel3_2, outputPathLabel);
        ContainerUtils.addComponent(panel3_2, button3_open2);
        ContainerUtils.addComponent(panel3_2, textField3_2);
        ContainerUtils.addComponent(insertPanel, panel3_3);
        ContainerUtils.addComponent(panel3_3, panel3_3_1);
        ContainerUtils.addComponent(panel3_3, panel3_3_2);
        ContainerUtils.addComponent(panel3_3_1, replaceSkipLabel);
        ContainerUtils.addComponent(panel3_3_1, textField3_3_1);
        ContainerUtils.addComponent(panel3_3_2, insertStringLabel);
        ContainerUtils.addComponent(panel3_3_2, textField3_3_2);
        ContainerUtils.addComponent(insertPanel, panel3_4);
        ContainerUtils.addComponent(panel3_4, replacelineLabel);
        ContainerUtils.addComponent(panel3_4, replaceSynaxTips);
        ContainerUtils.addComponent(panel3_4, textField3_4);
        ContainerUtils.addComponent(insertPanel, panel3_5);
        ContainerUtils.addComponent(panel3_5, keyFilterLabel);
        ContainerUtils.addComponent(panel3_5, keyFilterTips);
        ContainerUtils.addComponent(panel3_5, textField3_5);
        ContainerUtils.addComponent(insertPanel, panel3_6);
        ContainerUtils.addComponent(panel3_6, ingoreCharsLabel);
        ContainerUtils.addComponent(panel3_6, replaceIngoreTips);
        ContainerUtils.addComponent(panel3_6, textField3_6);
        ContainerUtils.addComponent(insertPanel, panel3_7);
        ContainerUtils.addComponent(panel3_7, button3_go);
        // Fourth Main Panel
        renewPanel.setLayout(new BoxLayout(renewPanel, BoxLayout.Y_AXIS));
        ContainerUtils.addComponent(renewPanel, panel4_1);
        ContainerUtils.addComponent(panel4_1, oldenLangLabel);
        ContainerUtils.addComponent(panel4_1, button4_open1);
        ContainerUtils.addComponent(panel4_1, textField4_1);
        ContainerUtils.addComponent(renewPanel, panel4_2);
        ContainerUtils.addComponent(panel4_2, oldzhLangLabel);
        ContainerUtils.addComponent(panel4_2, button4_open2);
        ContainerUtils.addComponent(panel4_2, textField4_2);
        ContainerUtils.addComponent(renewPanel, panel4_3);
        ContainerUtils.addComponent(panel4_3, newenLangLabel);
        ContainerUtils.addComponent(panel4_3, button4_open3);
        ContainerUtils.addComponent(panel4_3, textField4_3);
        ContainerUtils.addComponent(renewPanel, panel4_4);
        ContainerUtils.addComponent(panel4_4, newzhLangLabel);
        ContainerUtils.addComponent(panel4_4, button4_open4);
        ContainerUtils.addComponent(panel4_4, textField4_4);
        ContainerUtils.addComponent(renewPanel, panel4_5);
        ContainerUtils.addComponent(panel4_5, updateModeTips);
        ContainerUtils.addComponent(panel4_5, Box.createHorizontalStrut(80));
        ContainerUtils.addComponent(panel4_5, updateMode1Button);
        ContainerUtils.addComponent(panel4_5, updateMode2Button);
        ContainerUtils.addComponent(panel4_5, updateMode3Button);
        ContainerUtils.addComponent(panel4_5, updateMode4Button);
        ContainerUtils.addComponent(panel4_5, updateMode5Button);
        ContainerUtils.addComponent(renewPanel, panel4_6);
        ContainerUtils.addComponent(panel4_6, additionModeLabel);
        ContainerUtils.addComponent(panel4_6, checkModeCheckBox);
        ContainerUtils.addComponent(panel4_6, displayChangeCheckBox);
        ContainerUtils.addComponent(renewPanel, panel4_7);
        ContainerUtils.addComponent(panel4_7, button4_go);
        ContainerUtils.addComponent(renewPanel, Box.createVerticalStrut(100));
        // Fifth Main Panel
        conditionDeletePanel.setLayout(new BoxLayout(conditionDeletePanel, BoxLayout.Y_AXIS));
        ContainerUtils.addComponent(conditionDeletePanel, panel5_1);
        ContainerUtils.addComponent(panel5_1, inputPathLabel);
        ContainerUtils.addComponent(panel5_1, button5_open1);
        ContainerUtils.addComponent(panel5_1, textField5_1);
        ContainerUtils.addComponent(conditionDeletePanel, panel5_2);
        ContainerUtils.addComponent(panel5_2, outputPathLabel);
        ContainerUtils.addComponent(panel5_2, button5_open2);
        ContainerUtils.addComponent(panel5_2, textField5_2);
        ContainerUtils.addComponent(conditionDeletePanel, panel5_3);
        ContainerUtils.addComponent(panel5_3, cleanAfterStringLabel);
        ContainerUtils.addComponent(panel5_3, textField5_3);
        ContainerUtils.addComponent(conditionDeletePanel, panel5_4);
        ContainerUtils.addComponent(panel5_4, replacelineLabel);
        ContainerUtils.addComponent(panel5_4, replaceSynaxTips);
        ContainerUtils.addComponent(panel5_4, textField5_4);
        ContainerUtils.addComponent(conditionDeletePanel, panel5_5);
        ContainerUtils.addComponent(panel5_5, cleanKeepStringLabel);
        ContainerUtils.addComponent(panel5_5, Box.createHorizontalStrut(30));
        ContainerUtils.addComponent(panel5_5, keepStringCheckBox);
        ContainerUtils.addComponent(conditionDeletePanel, panel5_6);
        ContainerUtils.addComponent(panel5_6, cleanUpdateInfoLabel);
        ContainerUtils.addComponent(panel5_6, cleanUpdateInfoCheckBox);
        ContainerUtils.addComponent(conditionDeletePanel, panel5_7);
        ContainerUtils.addComponent(panel5_7, button5_go);
        ContainerUtils.addComponent(conditionDeletePanel, Box.createVerticalStrut(60));
        // Sixth Main Panel
        translationReplacePanel.setLayout(new BoxLayout(translationReplacePanel, BoxLayout.Y_AXIS));
        ContainerUtils.addComponent(translationReplacePanel, panel6_1);
        ContainerUtils.addComponent(panel6_1, orginenLangLabel);
        ContainerUtils.addComponent(panel6_1, button6_open1);
        ContainerUtils.addComponent(panel6_1, textField6_1);
        ContainerUtils.addComponent(translationReplacePanel, panel6_2);
        ContainerUtils.addComponent(panel6_2, undonezhLangLabel);
        ContainerUtils.addComponent(panel6_2, button6_open2);
        ContainerUtils.addComponent(panel6_2, textField6_2);
        ContainerUtils.addComponent(translationReplacePanel, panel6_3);
        ContainerUtils.addComponent(panel6_3, processzhLangLabel);
        ContainerUtils.addComponent(panel6_3, button6_open3);
        ContainerUtils.addComponent(panel6_3, textField6_3);
        ContainerUtils.addComponent(translationReplacePanel, panel6_4);
        ContainerUtils.addComponent(panel6_4, outputPathLabel);
        ContainerUtils.addComponent(panel6_4, button6_open4);
        ContainerUtils.addComponent(panel6_4, textField6_4);
        ContainerUtils.addComponent(translationReplacePanel, panel6_5);
        ContainerUtils.addComponent(panel6_5, replaceModeLabel);
        ContainerUtils.addComponent(panel6_5, replaceMode1Button);
        ContainerUtils.addComponent(panel6_5, replaceMode2Button);
        ContainerUtils.addComponent(translationReplacePanel, panel6_6);
        ContainerUtils.addComponent(panel6_6, additionModeLabel);
        ContainerUtils.addComponent(panel6_6, checkModeCheckBox2);
        ContainerUtils.addComponent(translationReplacePanel, panel6_7);
        ContainerUtils.addComponent(panel6_7, button6_go);
        ContainerUtils.addComponent(translationReplacePanel, Box.createVerticalStrut(160));
        // Seventh Main Panel
        wordRatePanel.setLayout(new BoxLayout(wordRatePanel, BoxLayout.Y_AXIS));
        ContainerUtils.addComponent(wordRatePanel, panel7_1);
        ContainerUtils.addComponent(panel7_1, inputenStatLangLabel);
        ContainerUtils.addComponent(panel7_1, button7_open1);
        ContainerUtils.addComponent(panel7_1, textField7_1);
        ContainerUtils.addComponent(wordRatePanel, panel7_2);
        ContainerUtils.addComponent(panel7_2, statOutpathLabel);
        ContainerUtils.addComponent(panel7_2, button7_open2);
        ContainerUtils.addComponent(panel7_2, textField7_2);
        ContainerUtils.addComponent(wordRatePanel, panel7_3);
        ContainerUtils.addComponent(panel7_3, statFilterLabel);
        ContainerUtils.addComponent(panel7_3, textField7_3);
        ContainerUtils.addComponent(wordRatePanel, panel7_4);
        ContainerUtils.addComponent(panel7_4, button7_go);
        ContainerUtils.addComponent(wordRatePanel, Box.createVerticalStrut(300));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 实现监听到的事件
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (e.getSource() == updateMode1Button || e.getSource() == updateMode2Button || e.getSource() == updateMode3Button
                || e.getSource() == updateMode4Button || e.getSource() == updateMode5Button) {
            if (updateMode1Button.isSelected()) {
                textField4_1.setEnabled(false);
                textField4_1.setText("");
                button4_open1.setEnabled(false);
            } else {
                textField4_1.setEnabled(true);
                button4_open1.setEnabled(true);

            }
        }
        if (e.getSource() == updateMode1Button || e.getSource() == updateMode2Button || e.getSource() == updateMode3Button
                || e.getSource() == updateMode4Button || e.getSource() == updateMode5Button) {
            if (updateMode2Button.isSelected()) {
                checkModeCheckBox.setEnabled(true);
                displayChangeCheckBox.setEnabled(true);
            } else if (updateMode5Button.isSelected()) {
                checkModeCheckBox.setEnabled(false);
                checkModeCheckBox.setSelected(false);
                displayChangeCheckBox.setEnabled(true);
            } else {
                checkModeCheckBox.setEnabled(false);
                checkModeCheckBox.setSelected(false);
                displayChangeCheckBox.setEnabled(false);
                displayChangeCheckBox.setSelected(false);
            }
        }
        if (e.getSource() == replaceMode1Button) {
            orginenLangLabel.setText("参考用英文文本en_US.lang:");
            textField6_2.setEnabled(true);
            button6_open2.setEnabled(true);
        } else if (e.getSource() == replaceMode2Button) {
            orginenLangLabel.setText("每行为\"英文=中文\"形式的utf-8编码文件:");
            textField6_2.setText("");
            textField6_2.setEnabled(false);
            button6_open2.setEnabled(false);
        }
        switch (command) {
            case "Open":
                filedialog_open = new FileDialog(this, "打开文件", FileDialog.LOAD);
                filedialog_open.setVisible(true);
                String directory = filedialog_open.getDirectory();
                String filename = filedialog_open.getFile();
                if (e.getSource() == button1) {
                    System.out.println(directory + filename);
                } else if (e.getSource() == button2_open1) {
                    if (directory != null && filename != null)
                        textField2_1.setText(directory + filename);
                    ContainerUtils.autoFill(textField2_1, textField2_2);
                } else if (e.getSource() == button2_open2) {
                    if (directory != null && filename != null)
                        textField2_2.setText(directory + filename);
                } else if (e.getSource() == button3_open1) {
                    if (directory != null && filename != null)
                        textField3_1.setText(directory + filename);
                    ContainerUtils.autoFill(textField3_1, textField3_2);
                } else if (e.getSource() == button3_open2) {
                    if (directory != null && filename != null)
                        textField3_2.setText(directory + filename);
                } else if (e.getSource() == button4_open1) {
                    if (directory != null && filename != null)
                        textField4_1.setText(directory + filename);
                } else if (e.getSource() == button4_open2) {
                    if (directory != null && filename != null)
                        textField4_2.setText(directory + filename);
                    ContainerUtils.autoFill(textField4_2, textField4_4);
                } else if (e.getSource() == button4_open3) {
                    if (directory != null && filename != null)
                        textField4_3.setText(directory + filename);
                } else if (e.getSource() == button4_open4) {
                    if (directory != null && filename != null)
                        textField4_4.setText(directory + filename);
                } else if (e.getSource() == button5_open1) {
                    if (directory != null && filename != null)
                        textField5_1.setText(directory + filename);
                    ContainerUtils.autoFill(textField5_1, textField5_2);
                } else if (e.getSource() == button5_open2) {
                    if (directory != null && filename != null)
                        textField5_2.setText(directory + filename);
                } else if (e.getSource() == button6_open1) {
                    if (directory != null && filename != null)
                        textField6_1.setText(directory + filename);
                } else if (e.getSource() == button6_open2) {
                    if (directory != null && filename != null)
                        textField6_2.setText(directory + filename);
                } else if (e.getSource() == button6_open3) {
                    if (directory != null && filename != null)
                        textField6_3.setText(directory + filename);
                    ContainerUtils.autoFill(textField6_3, textField6_4);
                } else if (e.getSource() == button6_open4) {
                    if (directory != null && filename != null)
                        textField6_4.setText(directory + filename);
                } else if (e.getSource() == button7_open1) {
                    if (directory != null && filename != null)
                        textField7_1.setText(directory + filename);
                    ContainerUtils.autoFill(textField7_1, textField7_2);
                } else if (e.getSource() == button7_open2) {
                    if (directory != null && filename != null)
                        textField7_2.setText(directory + filename);
                }
                break;
            case "Exit":
                System.exit(0);
            case "Go":
                if (e.getSource() == button2_go) {
                    String fileInput = textField2_1.getText();
                    String fileOutput = textField2_2.getText();
                    String sOrigin = textField2_3.getText();
                    String sTo = textField2_4.getText();
                    if (!textField2_5.getText().contains("，")) {
                        String[] rowNumberSplit = {"Blank"};
                        if (!textField2_5.getText().trim().equals(""))
                            rowNumberSplit = textField2_5.getText().split(",");
                        String[] specialIgnoreSymbol = textField2_6.getText().split("-");
                        if (textField2_6.getText().length() != 3 && textField2_6.getText().length() != 0
                                && (!(textField2_6.getText().length() == 3 && specialIgnoreSymbol.length != 2)))
                            new MessageWindow(this, "错误！", "替换忽略符输入格式错误！", -1);
                        else {
                            if (!textField2_1.getText().isEmpty())
                                if (!textField2_1.getText().equals(textField2_2.getText())
                                        || JOptionPane.showOptionDialog(null, "系统检测到您的输入和输出是同一个文件，这样可能会导致数据丢失，请问是否继续？",
                                        "确认是否继续", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                                        null, null) == 0) {
                                    FileModifier.conditionalReplace(this, fileInput, fileOutput, new FileModifier.StartAndEndHolder(sOrigin, sTo), specialIgnoreSymbol.length == 2 ? new FileModifier.StartAndEndHolder(specialIgnoreSymbol[0], specialIgnoreSymbol[1]) : null);
                                }
                        }
                    } else
                        new MessageWindow(this, "错误！", "错误,请使用英文逗号分隔！", -1);
                } else if (e.getSource() == button3_go) {
                    String fileInput = textField3_1.getText();
                    String fileOutput = textField3_2.getText();
                    String sInterval = textField3_3_1.getText();
                    String sAdd = textField3_3_2.getText();
                    if (!textField3_4.getText().contains("，") && !textField3_5.getText().contains("，")) {
                        String[] rowNumberSplit = {"Blank"};
                        if (!textField3_4.getText().trim().equals(""))
                            rowNumberSplit = textField3_4.getText().split(",");
                        String[] keyFilter = textField3_5.getText().split(",");
                        String[] specialIgnoreSymbol = textField3_6.getText().split("-");
                        if (textField3_6.getText().length() != 3 && textField3_6.getText().length() != 0
                                && (!(textField3_6.getText().length() == 3 && specialIgnoreSymbol.length != 2)))
                            new MessageWindow(this, "错误！", "替换忽略符输入格式错误！", -1);
                        else {
                            if (!textField3_1.getText().isEmpty())
                                if (!textField3_1.getText().equals(textField3_2.getText())
                                        || JOptionPane.showOptionDialog(null, "系统检测到您的输入和输出是同一个文件，这样可能会导致数据丢失，请问是否继续？",
                                        "确认是否继续", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                                        null, null) == 0) {
                                    FileModifier fileModifier = new FileModifier(this, 2, fileInput, fileOutput, sInterval,
                                            sAdd, keyFilter, rowNumberSplit, specialIgnoreSymbol);
                                    fileModifier.functionAdd();
                                }
                        }
                    } else
                        new MessageWindow(this, "错误！", "错误,请使用英文逗号分隔！", -1);
                } else if (e.getSource() == button4_go) {
                    String fileInput1 = textField4_1.getText();
                    String fileInput2 = textField4_2.getText();
                    String fileInput3 = textField4_3.getText();
                    String fileOutput = textField4_4.getText();
                    int updateType = updateMode1Button.isSelected() ? 1
                            : updateMode2Button.isSelected() ? 2
                            : updateMode3Button.isSelected() ? 3
                            : updateMode4Button.isSelected() ? 4 : updateMode5Button.isSelected() ? 5 : -1;
                    int checkModeStatus = (updateType == 2 && checkModeCheckBox.isSelected()) ? 1 : 0;
                    int informationModeStatus = ((updateType == 2 || updateType == 5) && displayChangeCheckBox.isSelected()) ? 1
                            : 0;
                    updateType = updateType * 100 + checkModeStatus * 10 + informationModeStatus;
                    if (!textField4_2.getText().isEmpty())
                        if (!textField4_2.getText().equals(textField4_4.getText())
                                || JOptionPane.showOptionDialog(null, "系统检测到您的输入和输出是同一个文件，这样可能会导致数据丢失，请问是否继续？", "确认是否继续",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) == 0) {
                            FileModifier fileModifier = new FileModifier(this, fileInput1, fileInput2, fileInput3,
                                    fileOutput, updateType);
                            fileModifier.functionUpdateLocalization();
                        }
                } else if (e.getSource() == button5_go) {
                    String fileInput1 = textField5_1.getText();
                    String fileOutput = textField5_2.getText();
                    String sRemoveFlag = textField5_3.getText();
                    int flag1 = keepStringCheckBox.isSelected() ? 1 : 0;
                    int flag2 = cleanUpdateInfoCheckBox.isSelected() ? 1 : 0;
                    if (!textField5_4.getText().contains("，")) {
                        String[] rowNumberSplit = {"Blank"};
                        if (!textField5_4.getText().trim().equals(""))
                            rowNumberSplit = textField5_4.getText().split(",");
                        if (!textField5_1.getText().isEmpty())
                            if (!textField5_1.getText().equals(textField5_2.getText())
                                    || JOptionPane.showOptionDialog(null, "系统检测到您的输入和输出是同一个文件，这样可能会导致数据丢失，请问是否继续？", "确认是否继续",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) == 0) {
                                FileModifier fileModifier = new FileModifier(this, fileInput1, fileOutput, sRemoveFlag,
                                        rowNumberSplit, flag1 * 10 + flag2);
                                fileModifier.functionConditionalRemove();
                            }
                    } else
                        new MessageWindow(this, "错误！", "错误,请使用英文逗号分隔！", -1);
                } else if (e.getSource() == button6_go) {
                    String fileInput1 = textField6_1.getText();
                    String fileInput2 = textField6_2.getText();
                    String fileInput3 = textField6_3.getText();
                    String fileOutput = textField6_4.getText();
                    int updateType = replaceMode1Button.isSelected() ? 0 : 1;
                    int checkModeStatus = checkModeCheckBox2.isSelected() ? 1 : 0;
                    if (updateType == 1 || !textField6_2.getText().isEmpty())
                        if (!textField6_2.getText().equals(textField6_4.getText())
                                || JOptionPane.showOptionDialog(null, "系统检测到您的输入和输出是同一个文件，这样可能会导致数据丢失，请问是否继续？", "确认是否继续",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) == 0) {
                            FileModifier fileModifier = new FileModifier(this, fileInput1, fileInput2, fileInput3,
                                    fileOutput, updateType * 10 + checkModeStatus);
                            fileModifier.functionAutoReplaceEnglishTextWithChineseTranslation();
                        }
                } else if (e.getSource() == button7_go) {
                    String fileInput1 = textField7_1.getText();
                    String fileOutput = textField7_2.getText();
                    if (!textField7_3.getText().contains("，")) {
                        String[] filter;
                        filter = textField7_3.getText().split(",");
                        if (!textField7_1.getText().isEmpty())
                            if (!textField7_1.getText().equals(textField7_2.getText()) || JOptionPane.showOptionDialog(null,
                                    "系统检测到您的输入和输出是同一个文件，这样可能会导致数据丢失，请问是否继续？", "确认是否继续", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, null, null) == 0) {
                                FileModifier fileModifier = new FileModifier(this, fileInput1, fileOutput, filter);
                                fileModifier.functionWordCount();
                            }
                    } else
                        new MessageWindow(this, "错误！", "错误,请使用英文逗号分隔！", -1);
                }
                break;
        }
    }

}
