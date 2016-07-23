import java.util.*;
/**
 * 
 */

/**
 * @author rami
 *
 */
public class MinesCore {
	private int[][] mineland;
	public MinesCore(int x,int y,int m)
	{
		mineland=new int[y][x];
		int c=0;
		Random rnd=new Random();
		while(c!=m)
		{
			int x1=Math.abs(rnd.nextInt());
			int y1=Math.abs(rnd.nextInt());
			x1%=x;
			y1%=y;
			if(mineland[y1][x1]!=-1)
			{
				mineland[y1][x1]=-1;
				c++;
			}
		}
	}
	public void numOfMines()
	{
		for(int i=0;i<mineland.length;i++)
		{
			for(int j=0;j<mineland[i].length;j++)
			{
				if(mineland[i][j]==-1) continue;
				else
				{
					if(i!=mineland.length-1&&mineland[i+1][j]==-1) mineland[i][j]++;
					if(i!=0&&mineland[i-1][j]==-1) mineland[i][j]++;
					if(j!=mineland[i].length-1&&mineland[i][j+1]==-1) mineland[i][j]++;
					if(j!=0&&mineland[i][j-1]==-1) mineland[i][j]++;
					if(j!=0&&i!=0&&mineland[i-1][j-1]==-1) mineland[i][j]++;
					if(j!=0&&i!=mineland.length-1&&mineland[i+1][j-1]==-1) mineland[i][j]++;
					if(i!=0&&j!=mineland[i].length-1&&mineland[i-1][j+1]==-1)mineland[i][j]++;
					if(i!=mineland.length-1&&j!=mineland[i].length-1&&mineland[i+1][j+1]==-1)mineland[i][j]++;
				}
			}
		}
	}
	
	public int getm(int y,int x)
	{
		return mineland[y][x];
	}
	
	/*public static void main(String[] args)
	{
		MinesCore o=new MinesCore(5,4,5);
		o.numOfMines();
		for(int i=0;i<4;i++)
			{for(int j=0;j<5;j++)
				System.out.print(o.getm(i, j)+"     ");
			System.out.println();
			}
		
	}*/

}
