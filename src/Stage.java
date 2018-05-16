package minigame;

import javax.swing.*;
import java.awt.*;

public class Stage extends JPanel {
	static final int TILESIZE = 32;  // 1マスの長さ
	static final int NUMTILE_X = 10 + 2; // ステージ横のマス数
	static final int NUMTILE_Y = 25 + 1; // ステージ縦のマス数
	static final int PANEL_W = TILESIZE * NUMTILE_X; //ステージ描画領域の幅
	static final int PANEL_H = TILESIZE * NUMTILE_Y; //ステージ描画領域の高さ
	static final int empty = 0, block = 1, wall = 1; //空のマスは0, 壁やブロックがある場合1とする。
	static int posX = 4; //パーツ(4*4)の一番左上の座標がどこかを表している 
	static int posY = 0; //この値は変わるので、新しく作るときは値を初期化する(newpartsメソッド)
	static int[][] field = new int [NUMTILE_X][NUMTILE_Y]; //座標 (X,Y) = (0~11 , 0~25)
	static int count; //消したライン数をカウント
	static int multiplecount; //複数同時消ししたときカウント
	static int score; //点数 1列消しで100 , 2列消しで300 , 3列消しで600 , 4列消しで1000
	
	static int firstrandomnumber; //一番最初に発生させるパーツの種類の番号
	
	static JFrame frame;
	static Stage stage; //Stageインスタンスを保持
	
	private static Actor tetrisparts = new Actor(); //Actorクラスの生成
	public static boolean gameIsOver;
	
	//コンストラクタ(初期化)
	public Stage(){
		frame = new JFrame("NE27-0235G 西脇勇斗");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		Insets is = frame.getInsets(); //ウインドウ枠の情報取得
		frame.setSize(PANEL_W + is.left + is.right , PANEL_H + is.top + is.bottom);
		frame.setResizable(false);	
		frame.getContentPane().add(this);
		resetfield(); //ゲームが始まった時に、マス目がemptyかwallかの情報を入れる
		setBackground(Color.WHITE);
		Stage.stage = this;
		//最初のパーツ生成と次パーツの表示
		Stage.firstrandomnumber = (int)(Math.random() * 7);
		Actor.createparts(Stage.firstrandomnumber);
		Actor.createnextparts();
	}
		
	//paintComponentメソッドのオーバーライド(ゲーム画面の描画)
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//パーツの固定と削除
		for(int i = 0 ; i < NUMTILE_X ; i++){
			for(int j = 0 ; j < NUMTILE_Y ; j++){
				switch(field[i][j]){
				case block:
					g.setColor(Color.gray);
					g.fillRect(TILESIZE * i, TILESIZE * j, TILESIZE, TILESIZE);
					break;
				case empty:
					g.setColor(Color.white);
					g.fillRect(TILESIZE * i, TILESIZE * j, TILESIZE, TILESIZE);
					g.setColor(Color.black);
					g.drawRect(TILESIZE * i, TILESIZE * j, TILESIZE, TILESIZE);
					break;
				}
			} 
		} 
		//外枠を黒くする
		g.setColor(Color.black);
		g.fillRect(0, 0, TILESIZE, NUMTILE_Y * TILESIZE);
		g.fillRect(0, (NUMTILE_Y - 1) * TILESIZE, NUMTILE_X * TILESIZE, TILESIZE);
		g.fillRect((NUMTILE_X - 1) * TILESIZE, 0, TILESIZE, NUMTILE_Y * TILESIZE);
		//線を描く
		for(int i = 0 ; i < NUMTILE_X ; i++){
			g.drawLine(i * TILESIZE, 0 , i * TILESIZE , TILESIZE * NUMTILE_Y);
		}
		for(int i = 0 ; i < NUMTILE_Y ; i++){
			g.drawLine(0 , i * TILESIZE , TILESIZE * NUMTILE_X , i * TILESIZE);
		}
		//パーツの描画
		paintparts(g);
	}
	
	//パーツの生成
	public void paintparts(Graphics g){
		int[][] parts = tetrisparts.getparts(); //描くパーツの情報を取得する
		for(int j = 0 ; j < 4 ; j++){
			for(int i = 0 ; i < 4 ; i++){
				switch(parts[i][j]){
				case block:
					setColor(g);
					g.fillRect(TILESIZE * posX + TILESIZE * i, TILESIZE * posY + TILESIZE * j, TILESIZE, TILESIZE);
					g.setColor(Color.black);
					g.drawRect(TILESIZE * posX + TILESIZE * i, TILESIZE * posY + TILESIZE * j, TILESIZE, TILESIZE);
					break;
				}
			}
		}		
	}
	
	//パーツごとの色を変えるメソッド
	public static void setColor(Graphics g){
		switch(Actor.setcolornumber){
		case 0:
			g.setColor(Color.blue);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		case 2:
			g.setColor(Color.green);
			break;
		case 3:
			g.setColor(Color.red);
			break;
		case 4:
			g.setColor(Color.orange);
			break;
		case 5:
			g.setColor(Color.pink);
			break;
		case 6:
			g.setColor(Color.cyan);
			break;
		}
	}
	
	//パーツを初期化する(新しいパーツの生成)
	private static void newparts(){
		posX = 4;
		posY = 0;
		tetrisparts.createparts(Actor.randomnumber);
	}	
	
	//ステージのフィールドを初期化する
	private void resetfield(){
		// 空であるか、マスであるかの情報を入れる
		for(int j = 0 ; j < NUMTILE_Y ; j++){
			for(int i = 0 ; i < NUMTILE_X ; i++){
				field[i][j] = empty; //まずは全て空(empty = 0)とする
				if(i == 0 || j == NUMTILE_Y - 1 || i == NUMTILE_X - 1){ //一番左の列、一番下の行、一番右の列
					field[i][j] = wall; //壁(wall = 1)とする
				}
			}
		}
	}
	
	//Runメソッド1回分の動き
	public static boolean gameRun(){
		if(moveparts(0,1) == false){ //自然落下ができなくなった時
			lockparts(); //そのパーツを固定する
			checkline(); //消せるラインがあるかをチェックし、あるなら消す
			newparts(); //新しいパーツを生成する
			GameController.onceHold = true; //ホールドを使わないで落下させた時、再びホールドを可能にする
			tetrisparts.createnextparts(); //次に落下させるパーツを表示させる
		}
		return gameIsOver();
	}
	
	//ホールドした時の動き
	public static void holdRun(){
		if(Actor.holdnumber != 7){
			Actor.randomnumber = Actor.holdnumber;
			Actor.holdnumber = Actor.setcolornumber;
			posX = 4;
			posY = 0;
			tetrisparts.createparts(Actor.randomnumber);
		}else{ //ゲームがスタートして一番最初にホールドするとき(holdnumberは初期値7で宣言されている)
			Actor.holdnumber = Actor.setcolornumber;
			newparts();
		}
		tetrisparts.holdparts();
		tetrisparts.createnextparts();
	}
	
	//ゲームオーバーかどうかの判定メソッド
	private static boolean gameIsOver(){
		boolean check = false; //最初はゲームオーバーではないfalseをcheckにいれておく
		for(int i = 1 ; i < NUMTILE_X - 1 ; i++){
			if(field[i][0] == block){
				check = true; //ゲームオーバーになった
			}
		}
		return check;
	}
	
	//落下または動く次のマスにすでにパーツがあるかどうかをチェックするメソッド
	public static boolean existAt(int moveX, int moveY){
		int[][] parts = tetrisparts.getparts(); //パーツ情報を取得してくる
		int x, y = 0;
		for(int j = 0 ; j < 4 ; j++){ //まずは横(y)に区切る
			for(int i = 0 ; i < 4 ; i++){ //次に縦(x)に
				if(parts[i][j] == block){
					x = posX + i + moveX; // パーツ(4*4)左上のX座標 + パーツの中でのX座標 + 動くX座標分
					y = posY + j + moveY; // パーツ(4*4)左上のY座標 + パーツの中でのY座標 + 動くY座標分
					if(field[x][y] != empty){
						return true; //あると返す
					}
				}
			}
		}
		return false; //ないと返す
	}
	
	//動くメソッド
	public static boolean moveparts(int moveX, int moveY){
		if(!existAt(moveX, moveY)){ //次の動くマスにパーツか壁があるかどうか(existAtメソッドの使用)
			posX += moveX;
			posY += moveY;
			return true;
		}
		return false;
	}
	
	//落ちたパーツをフィールドに固定するためのメソッド(ここでは、fieldに見える形として残らない)
	public static void lockparts(){
		int[][] parts = tetrisparts.getparts();
		for(int j = 0 ; j < 4 ; j++){
			for(int i = 0 ; i < 4 ; i++){
				switch(parts[i][j]){
				case block:
					field[posX + i][posY + j] = block;
					break;
				} 
			}
		}
	}
	
	//ラインが揃っているかを調べて削除するメソッド(ここでは削除するメソッドは書いてない)
	private static void checkline(){
		for(int j = NUMTILE_Y - 2 ; j > 0 ; j--){ //下の行からチェックしていく
			boolean check = true; //まずは揃っていると仮定する
			for(int i = 1 ; i < NUMTILE_X - 1 ; i++){ //左の列からチェック(wallも考慮する)
				if(field[i][j] == empty){ //一列が埋まっていないとき
					check = false; //揃っていないとする
				}
			}
			//ここまでで、空きがあればfalse,埋まっていたらtrueの状態
			if(check == true){ // そのj行目がすべて埋まっているとき
				deleteline(j); //j列目のラインを消す
				score += 100; // 1列消すと100点(4列消しで400点)
				score += multiplecount * 100; // 同時消しボーナス 1列:0 , 2列目:100 , 3列目:200 , 4列目:300点 の加算
				multiplecount++; //複数ライン消した際のカウント
				count++; //消したライン数のカウント
				option.score.setText(String.valueOf(Stage.score)); //JLabel:scoreにスコアを表示
				option.line.setText(String.valueOf(Stage.count)); //JLabel:lineに消したライン数の表示
				j++; //複数行を同時に消すための一行。deleteline()では消す他に1行ずらす作業を行っているのでズレが生じる
				if(multiplecount == 1){
					GameController.speed -= 4; //1列消すごとにspeedが4ずつ減少(早くなる)
					option.speed.setText(String.valueOf(GameController.speed));
				}
			}else{
				multiplecount = 0;
				check = true; 
			}
		}
	}
	
	//ラインを削除するメソッド
	private static void deleteline(int deleteline){
		for(int j = deleteline ; j >= 0 ; j--){ //消すライン(行)から全て上の層に対して
			for(int i = 1 ; i < NUMTILE_X - 1 ; i++){
				if(j == 0){ //一番上(j=0)は上からずれてこないので消す
					field[i][j] = empty;
				}else{
					field[i][j] = field[i][j-1]; //実際には消しているのではなく一つ下にずらしている
				}
			}
		}
	}
}
