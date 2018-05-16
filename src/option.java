package minigame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class option extends JPanel implements ActionListener {

	//ゲームスコア画面その他
		static JFrame option;
		static JPanel P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12;
		JLabel nextpartsboard, holdboard, scoreboard, lineboard, speedboard, htpboard;
		static JLabel nextparts, holdparts, score, line, speed, htp1, htp2, htp3, htp4;
	
	public option() {
		//ゲームスコア画面
		option = new JFrame("tetris");
		option.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		option.pack();
		Insets op = option.getInsets();
		option.setBounds(388, 0, 200 + op.left + op.right , Stage.PANEL_H + op.top + op.bottom);
		option.setResizable(false);
		option.getContentPane().add(this);
		setBackground(Color.WHITE);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		P1 = new JPanel();
		add(P1);		
		nextpartsboard = new JLabel("-------- next --------");
		nextpartsboard.setFont(new Font("Times",Font.PLAIN,24));
		P1.add(nextpartsboard);
		P1.setBackground(Color.white);
		
		P2 = new JPanel();
		add(P2);	
		nextparts = new JLabel("");
		P2.add(nextparts);
		P2.setBackground(Color.white);
		
		P3 = new JPanel();
		add(P3);
		holdboard = new JLabel("-------- hold --------");
		holdboard.setFont(new Font("Times",Font.PLAIN,24));
		P3.add(holdboard);
		P3.setBackground(Color.white);
		
		P4 = new JPanel();
		add(P4);	
		holdparts = new JLabel("");
		holdparts.setIcon(Actor.parts7);
		P4.add(holdparts);
		P4.setBackground(Color.white);
		
		P5 = new JPanel();
		add(P5);		
		scoreboard = new JLabel("-------- score --------");
		scoreboard.setFont(new Font("Times",Font.PLAIN,24));
		P5.add(scoreboard);
		P5.setBackground(Color.white);
		
		P6 = new JPanel();
		add(P6);	
		score = new JLabel("0");
		score.setFont(new Font("Times",Font.PLAIN,20));
		P6.add(score);
		P6.setBackground(Color.white);
		
		P7 = new JPanel();
		add(P7);
		lineboard = new JLabel("--------- line ---------");
		lineboard.setFont(new Font("Times",Font.PLAIN,24));
		P7.add(lineboard);
		P7.setBackground(Color.white);
		
		P8 = new JPanel();
		add(P8);	
		line = new JLabel("0");
		line.setFont(new Font("Times",Font.PLAIN,20));
		P8.add(line);
		P8.setBackground(Color.white);
		
		P9 = new JPanel();
		add(P9);
		speedboard = new JLabel("-------- speed --------");
		speedboard.setFont(new Font("Times",Font.PLAIN,24));
		P9.add(speedboard);
		P9.setBackground(Color.white);
		
		P10 = new JPanel();
		add(P10);
		speed = new JLabel("200");
		speed.setFont(new Font("Times",Font.PLAIN,20));
		P10.add(speed);
		P10.setBackground(Color.white);
		
		P11 = new JPanel();
		add(P11);
		htpboard = new JLabel("---- How to Play ----");
		htpboard.setFont(new Font("Times",Font.PLAIN,24));
		P11.add(htpboard);
		P11.setBackground(Color.white);
		
		P12 = new JPanel();
		add(P12);
		htp1 = new JLabel(); htp2 = new JLabel(); 
		htp1.setText("[←]左移動  [→]右移動  [↓]下移動");
		htp2.setText(" [z]ホールド  [x]左回転  [c]右回転");
		P12.add(htp1); P12.add(htp2);
		P12.setBackground(Color.white);
	}
	
	public void paintComponent(Graphics g){
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
