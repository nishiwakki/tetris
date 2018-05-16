package minigame;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Actor {
	static int[][] parts;
	static int randomnumber; //ランダム発生させる数字
	static int setcolornumber; //パーツに色をつけるのは、パーツ生成とは別で行われるので、数字がランダムで変わって色が変化しないよう保持している
	static int holdnumber = 7; // 最初は0 ~ 6のどれでもないから
	
	static ImageIcon parts0 = new ImageIcon("parts0.png");
	static ImageIcon parts1 = new ImageIcon("parts1.png");
	static ImageIcon parts2 = new ImageIcon("parts2.png");
	static ImageIcon parts3 = new ImageIcon("parts3.png");
	static ImageIcon parts4 = new ImageIcon("parts4.png");
	static ImageIcon parts5 = new ImageIcon("parts5.png");
	static ImageIcon parts6 = new ImageIcon("parts6.png");
	static ImageIcon parts7 = new ImageIcon("parts7.png");
	
	public static void createnextparts(){ // 次のパーツの画像表示を行うためのメソッド
		switch(randomnumber){
		case 0:
			option.nextparts.setIcon(parts0);
			break;
		case 1:
			option.nextparts.setIcon(parts1);
			break;
		case 2:
			option.nextparts.setIcon(parts2);
			break;
		case 3:
			option.nextparts.setIcon(parts3);
			break;
		case 4:
			option.nextparts.setIcon(parts4);
			break;
		case 5:
			option.nextparts.setIcon(parts5);
			break;
		case 6:
			option.nextparts.setIcon(parts6);
			break;
		}
	}
	
	public static void holdparts(){ // ホールドさせたパーツの画像表示を行うためのメソッド
		switch(holdnumber){
		case 0:
			option.holdparts.setIcon(parts0);
			break;
		case 1:
			option.holdparts.setIcon(parts1);
			break;
		case 2:
			option.holdparts.setIcon(parts2);
			break;
		case 3:
			option.holdparts.setIcon(parts3);
			break;
		case 4:
			option.holdparts.setIcon(parts4);
			break;
		case 5:
			option.holdparts.setIcon(parts5);
			break;
		case 6:
			option.holdparts.setIcon(parts6);
			break;
		case 7:
			option.holdparts.setIcon(parts7);
			break;
		}
	}
	
	//ブロックパーツの生成
	public static void createparts(int a){
		switch(a){ // 0は空、1はブロックがあることを表している(Stageクラスで定義)
		case 0: // I(棒)
			parts = new int[][]{{0,1,0,0},
								{0,1,0,0},
								{0,1,0,0},
								{0,1,0,0}};
			break;
		case 1: // 四角
			parts = new int[][]{{0,0,0,0},
								{0,1,1,0},
								{0,1,1,0},
								{0,0,0,0}};
			break;
		case 2: // 凸
			parts = new int[][]{{0,0,0,0},
								{0,1,0,0},
								{1,1,1,0},
								{0,0,0,0}};
			break;
		case 3: // L
			parts = new int[][]{{0,1,0,0},
								{0,1,0,0},
								{0,1,1,0},
								{0,0,0,0}};
			break;
		case 4: // 逆L
			parts = new int[][]{{0,0,1,0},
								{0,0,1,0},
								{0,1,1,0},
								{0,0,0,0}};
			break;
		case 5: // Z
			parts = new int[][]{{0,0,0,0},
								{1,1,0,0},
								{0,1,1,0},
								{0,0,0,0}};
			break;
		case 6: // 逆Z
			parts = new int[][]{{0,0,0,0},
								{0,1,1,0},
								{1,1,0,0},
								{0,0,0,0}};
			break;
		}
		setcolornumber = a; //パーツのcolorは画面再描画時に行っているのでrandomnumberを保管しておく!!
		randomnumber = (int)(Math.random() * 7); //全7パーツから一つを選ぶ
	}	
	
	//現在落下中のテトリスパーツ情報を取得
	public int[][] getparts(){
		return parts;
	}
	
	//テトリスパーツを右回転させる(ここでは回転させた情報を与えただけ)
	public int[][] getrightturn(){
		int[][] rightparts = new int[4][4];
		for(int i = 0 ; i < 4 ; i++){
			for(int j =0 ; j < 4 ; j++){
				rightparts[i][j] = parts[j][3 - i];
			}
		}
		return rightparts;
	}
	
	//テトリスパーツを左回転させる
	public int[][] getleftturn(){
		int[][] leftparts = new int[4][4];
		for(int i = 0 ; i < 4 ; i++){
			for(int j =0 ; j < 4 ; j++){
				leftparts[i][j] = parts[3 - j][i];
			}
		}
		return leftparts;
	}
	
	//実際に右回転させる(ボタンに指示として与える用)
	public void rightturn(){
		parts = getrightturn();
	}
	
	//実際に左回転させる
	public void leftturn(){
		parts = getleftturn();
	}
	
	//右回転させられるかどうかをチェックするメソッド(壁めり込み禁止用)
	public boolean Isrightturn(){
		int[][] parts = getrightturn();
		for(int i = 0 ; i < 4 ; i++){
			for(int j = 0 ; j < 4 ; j++){
				if(parts[i][j] != Stage.empty && Stage.field[Stage.posX + i][Stage.posY + j] != Stage.empty){
					return false; //回転不可
				}
			}
		}
		rightturn();
		return true;
	}
	
	//左回転させられるかどうかをチェックするメソッド(壁めり込み禁止用)
	public boolean Isleftturn(){
		int[][] parts = getleftturn();
		for(int i = 0 ; i < 4 ; i++){
			for(int j = 0 ; j < 4 ; j++){
				if(parts[i][j] != Stage.empty && Stage.field[Stage.posX + i][Stage.posY + j] != Stage.empty){
					return false; //回転不可
				}
			}
		}
		leftturn();
		return true;
	}
}
