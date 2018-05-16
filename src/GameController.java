package minigame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameController implements Runnable, KeyListener {
	static long speed = 200;
	private static Actor tetrisparts = new Actor();
	private boolean isRunning = true;
	boolean isHolding = false; //ホールドするか否か
	static boolean onceHold = true; //ホールドが無限に使えないようにする
	
	//コンストラクタ化(初期化)
	public GameController(){
		
		new option();
		option.option.addKeyListener(this);
		option.option.setVisible(true); //optionを表示
		
		new Stage();
		Stage.frame.addKeyListener(this); //キーリスナーの登録
		Stage.frame.setVisible(true); //Stageを表示
		
		new Thread(this).start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// 記述なし
	}

	@Override //キーイベントの処理(キー入力)
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_SPACE:
			isRunning = true;
			break;		
		case KeyEvent.VK_DOWN:
			Stage.moveparts(0,1);
			Stage.score += 2; //押したときに2点追加する
			option.score.setText(String.valueOf(Stage.score));
			break;
		case KeyEvent.VK_LEFT:
			Stage.moveparts(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			Stage.moveparts(1,0);
			break;
		case KeyEvent.VK_C:
			tetrisparts.Isrightturn();
			break;
		case KeyEvent.VK_X:
			tetrisparts.Isleftturn();
			break;
		case KeyEvent.VK_Z:
			isHolding = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// 記述なし		
	}

	@Override // ゲームの流れを組む手順
	public void run() {		
		while(true){
			if(isHolding){ // ホールドが押された時(true)
				if(onceHold){ // ホールドが使えるかどうか(同じパーツ落下時に何度もホールドするのを防ぐため)
					Stage.holdRun();
					isHolding = false; //ホールド状態を解除する
					onceHold = false; //２度目のホールドが使えないようにする
				}
				Stage.stage.repaint();
			}
			
			if(!Stage.gameRun()){
				Stage.stage.repaint(); //paintComponentメソッドの中身が行われる
			}else{
				Stage.stage.frame.removeKeyListener(this); //キー入力動作ができないようにする
				option.speed.setText("Game Over");
				break; //ゲームオーバー
			}
	
			try{
				Thread.sleep(speed);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
}