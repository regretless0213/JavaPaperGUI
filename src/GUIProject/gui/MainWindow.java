package GUIProject.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import GUIProject.paperUtil.QuestionGen;
import net.miginfocom.swing.MigLayout;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainWindow {

	private JFrame frmPaperone = new JFrame();
	private JTextField textFieldNum = new JTextField();
	private JTextField textFieldUs = new JTextField();
	private JPanel panelConfig = new JPanel();
	private JPanel panelInfo = new JPanel();
	// 信息标签
	private JLabel lblUserName = new JLabel("用户名：");
	private JLabel UserName = new JLabel();
	private JLabel lblNumRight = new JLabel("累计答题数：");
	private JLabel NumSum = new JLabel();
	private JLabel lblNumWrong = new JLabel("累计错题数：");
	private JLabel NumWrong = new JLabel();
	// 编辑信息标签
	private JLabel lblUserNameEdit = new JLabel("用户名：");
	private JLabel lblNumRightEdit = new JLabel("累计正确数：");
	private JLabel NumSumEdit = new JLabel();
	private JLabel lblNumWrongEdit = new JLabel("累计错题数：");
	private JLabel NumWrongEdit = new JLabel();
	// 按钮，剩余时间
	private JButton btnEdit = new JButton("修改信息");
	private JLabel lblTimeDis = new JLabel("剩余时间 :");
	static JLabel lblTime = new JLabel(" ");
	// 设定栏
	private JPanel panelConfigQue = new JPanel();
	private JLabel lblNewLabel = new JLabel("出题难度");
	private JComboBox<String> comboBoxComplex = new JComboBox<String>();
	private JLabel label = new JLabel("出题数量");
	private JLabel label_1 = new JLabel("运行模式");
	private JComboBox<String> comboBoxMode = new JComboBox<String>();
	private JLabel label_3 = new JLabel("分");
	private JLabel label_4 = new JLabel("秒");

	private JPanel panelStatus = new JPanel();
	private JLabel messageBar = new JLabel(" ");
	private JPanel panelMain = new JPanel();
	// 功能按钮
	private JButton buttonStart = new JButton("开始出题");
	private JButton buttonWrong = new JButton("错题本");
	private JButton btnDelete = new JButton("删除");
	static JButton submitButton = new JButton("提交");
	private JButton reButton = new JButton("重置");
	private JButton saveButton = new JButton("保存");
	private JScrollPane scrollPane = new JScrollPane();

	private int num;
	private int anrMode;
	static int subOnClick = 0;
	// 倒计时时间
	public static int time = 10;
	QuestionGen qg = new QuestionGen();
	// 生成的问题列表
	ArrayList<String[]> questions = new ArrayList<String[]>();
	// 错题列表
	ArrayList<String[]> Wrong = new ArrayList<String[]>();
	// 用户填写的答案列表
	ArrayList<JTextField> answers = new ArrayList<JTextField>();
	// 用户名，总答题数，总错题数
	String username = new String();
	int SumOfA;
	int SumOfE;
	// 配置文件地址
	String filename = new String("note.txt");
	File file = new File(filename);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmPaperone.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public MainWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		// 载入配置文件，包括用户名，总答题数，总错题数，错题本
		if (!file.exists()) {
			FileWriter create = new FileWriter(filename);
			create.write("username 0 0\r\n");
			create.close();
		}
		FileReader def = new FileReader(file);
		BufferedReader br = new BufferedReader(def);
		// 解析配置
		String valueStr = br.readLine();
		String[] defstr = new String[3];
		if (valueStr != null) {
			defstr = valueStr.split(" ");
			username = defstr[0];
			SumOfA = Integer.parseInt(defstr[1]);
			SumOfE = Integer.parseInt(defstr[2]);

		}
		// 装载错题本
		String WrongQ = new String();
		String[] part = new String[2];
		while ((WrongQ = br.readLine()) != null) {
			part = WrongQ.split("=");
			Wrong.add(part);
		}

		// 创建窗口布局
		frmPaperone.setTitle("PaperOne出题助手");
		frmPaperone.setBounds(100, 100, 761, 496);
		frmPaperone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPaperone.getContentPane().setLayout(new BorderLayout(0, 0));

		frmPaperone.getContentPane().add(panelConfig, BorderLayout.NORTH);

		panelConfig.setLayout(new MigLayout("", "[][grow]", "[][]"));

		panelConfig.add(panelInfo, "cell 0 0,alignx 0");
		panelInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panelInfo.add(lblUserName);

		panelInfo.add(UserName);
		UserName.setText(username);

		panelInfo.add(lblNumRight);

		panelInfo.add(NumSum);
		NumSum.setText(SumOfA + "");

		panelInfo.add(lblNumWrong);

		panelInfo.add(NumWrong);
		NumWrong.setText(SumOfE + "");

		panelInfo.add(lblTimeDis);
		lblTimeDis.setVisible(false);

		panelInfo.add(lblTime);
		lblTime.setVisible(false);

		panelInfo.add(btnEdit);

		panelConfig.add(panelConfigQue, "cell 0 1,alignx trailing");

		panelConfigQue.add(lblNewLabel);

		comboBoxComplex.setModel(new DefaultComboBoxModel<String>(new String[] { "简单", "普通", "中等", "复杂" }));
		panelConfigQue.add(comboBoxComplex);

		panelConfigQue.add(label);

		textFieldNum.setText("10");
		panelConfigQue.add(textFieldNum);
		textFieldNum.setColumns(10);

		panelConfigQue.add(label_1);

		comboBoxMode.setModel(new DefaultComboBoxModel<String>(new String[] { "答题模式", "打印模式", "错题模式" }));
		panelConfigQue.add(comboBoxMode);

		panelConfigQue.add(label_3);

		panelConfigQue.add(label_4);

		frmPaperone.getContentPane().add(panelStatus, BorderLayout.SOUTH);

		panelStatus.add(messageBar);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frmPaperone.getContentPane().add(scrollPane, BorderLayout.CENTER);

		scrollPane.add(panelMain);
		panelMain.setLayout(new MigLayout("", "[][grow]", "[][]"));
		scrollPane.setViewportView(panelMain);

		// frmPaperone.getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new MigLayout("", "[][grow]", "[][]"));
		// 开始按钮
		panelConfigQue.add(buttonStart);

		panelConfigQue.add(buttonWrong);

		// 修改信息按钮

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelMain.removeAll();
				panelMain.add(lblUserNameEdit, "cell 0 0,alignx trailing");

				textFieldUs.setText(username);
				panelMain.add(textFieldUs, "cell 1 0,alignx trailing");
				textFieldUs.setColumns(10);

				panelMain.add(lblNumRightEdit, "cell 0 1,alignx trailing");

				panelMain.add(NumSumEdit, "cell 1 1,alignx trailing");
				NumSumEdit.setText(SumOfA + "");

				panelMain.add(lblNumWrongEdit, "cell 0 2,alignx trailing");

				panelMain.add(NumWrongEdit, "cell 1 2,alignx trailing");
				NumWrongEdit.setText(SumOfE + "");

				panelMain.add(reButton, "cell 0 3,alignx trailing");
				panelMain.add(saveButton, "cell 1 3,alignx trailing");
				subOnClick = 1;
				panelMain.repaint();
			}
		});
		// 错题本按钮
		buttonWrong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 初始化
				subOnClick = 1;
				panelMain.removeAll();
				frmPaperone.repaint();
				int m = 0;
				int num = Wrong.size();
				for (int i = 0; i < num; i++) {
					String[] wrong = Wrong.get(i);
					JCheckBox chckbxNewCheckBox = new JCheckBox("");
					// chckbxNewCheckBox.setText(wrong[0] + "=" + wrong[1]);
					panelMain.add(chckbxNewCheckBox, "cell 0 " + m + ",alignx trailing");

					JLabel wrongBar = new JLabel("");
					panelMain.add(wrongBar, "cell 1 " + m + ",alignx 0");
					wrongBar.setText(wrong[0] + "=" + wrong[1]);
					m++;
				}
				// 删除按钮
				ArrayList<Integer> al = new ArrayList<>();
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						Component[] compnent = panelMain.getComponents();
						for (int i = 0; i < num * 2; i++) {
							try {
								JCheckBox tmp = (JCheckBox) compnent[i];
								if (tmp.isSelected()) {
									al.add(i / 2);
								}
							} catch (Exception e1) {
								continue;
							}
						}
						for (int d = al.size() - 1; d >= 0; d--) {
							Wrong.remove(Wrong.get(al.get(d)));
						}
						messageBar.setText("删除成功！");
						RewriteNote();
						al.clear();
						frmPaperone.repaint();
						buttonWrong.doClick();
					}
				});

				panelMain.add(btnDelete, "cell 1 " + m + ",alignx trailing");

			}
		});

		// 开始答题按钮
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 初始化
				
				time = 10;
				subOnClick = 0;
				frmPaperone.repaint();
				messageBar.setText("");
				lblTimeDis.setVisible(true);
				lblTime.setVisible(true);

				String numstr = textFieldNum.getText();
				int complex = comboBoxComplex.getSelectedIndex();
				int mode = comboBoxMode.getSelectedIndex();

				panelMain.removeAll();
				questions.clear();
				answers.clear();
				// 判断输入是否合法
				if (isNumeric(numstr)) {
					num = Integer.parseInt(numstr);
					time += 5 * num;

					for (int n = 0; n < num; n++) {
						String[] question = new String[2];
						switch (complex) { // 判断用户选择的难度
						case 0: {
							question[0] = qg.generateSimpleQuestion();
							break;
						}
						case 1: {
							question[0] = qg.generateCommonQuestion();
							break;
						}
						case 2: {
							question[0] = qg.generateMediumQuestion();
							break;
						}
						case 3: {
							question[0] = qg.generateComplexQuestion();
							break;
						}
						default:
						}
						try {
							question[0] = question[0];
							question[1] = qg.answer;
							questions.add(question);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}

					switch (mode) { // 判断用户选择的模式
					case 0: {

						
						for (int m = 0; m < num; m++) {
							JLabel questionBar = new JLabel(" ");
							panelMain.add(questionBar, "cell 0 " + m + ",alignx trailing");
							JTextField answerBar = new JTextField("", 20);
							panelMain.add(answerBar, "cell 1 " + m + " ,alignx trailing");
							questionBar.setText("Question " + (m + 1) + " :" + questions.get(m)[0]);
							answers.add(answerBar);
						}
						anrMode = 1;
						// 保存到错题本
						// JButton saveButton = new JButton("保存到错题本");
						// saveButton.addMouseListener(new MouseAdapter() {
						// public void mouseClicked(MouseEvent event) {
						//
						// }
						// });
						// saveButton.setVisible(false);
						// panelStatus.add(saveButton);

						panelMain.add(submitButton);

						break;
					}
					case 1: {
						for (int m = 0; m < num; m++) {
							JLabel questionBar = new JLabel(" ");
							panelMain.add(questionBar, "cell 0 " + m + ",alignx trailing");
							questionBar.setText(questions.get(m)[0] + "=" + questions.get(m)[1]);
						}
						break;

					}
					case 2: {
						anrMode = 2;
						num = Wrong.size();
						time = 5 * num;
						textFieldNum.setText(num + "");
						
						for (int m = 0; m < num; m++) {
							JLabel questionBar = new JLabel(" ");
							panelMain.add(questionBar, "cell 0 " + m + ",alignx trailing");
							JTextField answerBar = new JTextField("", 20);
							panelMain.add(answerBar, "cell 1 " + m + " ,alignx trailing");
							questionBar.setText("Question " + (m + 1) + " :" + Wrong.get(m)[0]);
							answers.add(answerBar);
						}
						
						panelMain.add(submitButton);
						break;
					}
					default:messageBar.setText("答题模式选择出错！");
					}
					

				} else {
					messageBar.setText("请输入数字");

				}
				try{
				MyThread.getInstance().start();
				}catch (Exception e1) {
					// TODO: handle exception
				}

			}
		});
		// 提交
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				subOnClick = 1;
				if (anrMode == 1) {
					// 累加总答题数
					SumOfA += num;
					// 记录做错的题号以及对应的正确答案
					String faultQuestion = new String("");
					String correctAnswer = new String("");
					int WrgQun = 0;
					for (int s = 0; s < num; s++) {
						String answer = new String();
						answer = answers.get(s).getText();
						if (!answer.trim().equals(questions.get(s)[1])) {
							faultQuestion += (" " + (s + 1));
							correctAnswer += (" " + questions.get(s)[1]);
							Wrong.add(questions.get(s));
							WrgQun++;
						}

					}
					if (faultQuestion.isEmpty() || faultQuestion == "") {
						messageBar.setText("恭喜你全部答对啦！真是个天才！");
						// saveButton.setVisible(false);
					} else {
						messageBar.setText("很遗憾，你的第" + faultQuestion + "题答错了.正确答案分别是：" + correctAnswer);
						// saveButton.setVisible(true);
					}
					SumOfE += WrgQun;
					RewriteNote();
					NumSum.setText(SumOfA + "");
					NumWrong.setText(SumOfE + "");
				}else if(anrMode == 2){
					String faultQuestion = new String("");
					String correctAnswer = new String("");
					
					for (int s = 0; s < num; s++) {
						String answer = new String();
						answer = answers.get(s).getText();
						if (!answer.trim().equals(Wrong.get(s)[1])) {
							faultQuestion += (" " + (s + 1));
							correctAnswer += (" " + Wrong.get(s)[1]);
						}

					}
					if (faultQuestion.isEmpty() || faultQuestion == "") {
						messageBar.setText("恭喜你全部答对啦！真是个天才！");
						// saveButton.setVisible(false);
					} else {
						messageBar.setText("很遗憾，你的第" + faultQuestion + "题答错了.正确答案分别是：" + correctAnswer);
						// saveButton.setVisible(true);
					}
				}else{
					messageBar.setText("答题模式错误！");
				}
			}
		});
		// 重置按钮
		reButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelMain.removeAll();

				SumOfA = 0;
				SumOfE = 0;
				Wrong.clear();
				messageBar.setText("重置成功");
				NumSum.setText(SumOfA + "");
				NumWrong.setText(SumOfE + "");
				RewriteNote();

				frmPaperone.repaint();

			}

		});
		// 保存按钮
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelMain.removeAll();

				username = textFieldUs.getText();
				messageBar.setText("用户名已修改为" + username);
				UserName.setText(username);
				RewriteNote();

				frmPaperone.repaint();
			}

		});

		br.close();
		def.close();

	}

	// 写入错题本函数
	public void RewriteNote() {
		try {
			FileWriter note = new FileWriter(file, false);
			note.write(username + " " + SumOfA + " " + SumOfE + "\r\n");
			for (String[] WriteNote : Wrong) {
				note.write(WriteNote[0] + "=" + WriteNote[1] + "\r\n");
			}

			note.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 判断输入合法性
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	// 倒计时


}

class MyThread extends Thread {
	private MyThread(){
		
	}
	private static MyThread instance =null;
	public static MyThread getInstance(){
		if(instance==null){
			instance = new MyThread();
		}
		return instance;
	}
	public void run() {
		while (MainWindow.time > 0) {
			MainWindow.time--;
			MainWindow.lblTime.setText(MainWindow.time + "");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (MainWindow.subOnClick == 1) {
				return;
			}

		}

		MainWindow.submitButton.doClick();

	}
}
