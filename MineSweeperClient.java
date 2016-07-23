import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;

public class MineSweeperClient extends JFrame implements MineSweeperConstants,ActionListener , MouseListener ,Runnable
{
	private DataInputStream isFromServer;
    private DataOutputStream osToServer;
    
    private boolean myTurn = false;
    private boolean continueToPlay = true;
    private boolean waiting = true;
    
    private JButton game=new JButton();
	private JButton btnok=new JButton("OK");
	private JButton btcancel=new JButton("Cancel");
	private JButton btncancel2=new JButton("Close");
	private JButton reset=new JButton("Reset");
	private JButton ngame=new JButton("New Game");
	
	private JTextField tcolumns=new JTextField(3);
	private JTextField trows=new JTextField(3);
	private JTextField tmines=new JTextField(3);
	private JTextField flagcount=new JTextField(3);
	private JTextField flagcount2=new JTextField(3);
	private JTextField timecounter=new JTextField(3);
	private JTextField flagcount1=new JTextField(3);
	
	private JLabel columns=new JLabel("Columns(9-30):");
	private JLabel rows=new JLabel("Rows(9-24):");
	private JLabel mines=new JLabel("Mines:");
	private JLabel sbeginner=new JLabel("Beginner: ");
	private JLabel sintermediate=new JLabel("Intermediate: ");
	private JLabel sexpert=new JLabel("Expert: ");
	private JLabel rbeginner=new JLabel();
	private JLabel rintermediate=new JLabel();
	private JLabel rexpert=new JLabel();
	private JLabel lbeginner=new JLabel();
	private JLabel lintermediate=new JLabel();
	private JLabel lexpert=new JLabel();
	private JLabel smile=new JLabel(new ImageIcon("cool_15.gif"));
	private JLabel jlblStatus=new JLabel();
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu help=new JMenu("Help");
	private JMenuItem jext=new JMenuItem("Exit");
	private JMenuItem beginner=new JMenuItem("Beginner");
	private JMenuItem intermediate=new JMenuItem("Intermediate");
	private JMenuItem expert=new JMenuItem("Expert");
	private JMenuItem newgame=new JMenuItem("New Game");
	private JMenuItem mcustom=new JMenuItem("Custom");
	private JMenuItem mtopscores=new JMenuItem("Top Scores");
	
	private JPanel p1=new JPanel();
	private JPanel p2=new JPanel();
	private JPanel p3=new JPanel();
	private JPanel p4=new JPanel();
	private JPanel p5=new JPanel();
	private JPanel p6=new JPanel();
	private JPanel p7=new JPanel();
	private JPanel p8=new JPanel();
	private JPanel p9=new JPanel();
	private JPanel p10=new JPanel();
	private JPanel p11=new JPanel();
	private JPanel p12=new JPanel();
	private JPanel p13=new JPanel();
	private JPanel p14=new JPanel();
	private JPanel p15=new JPanel();
	private JPanel p16=new JPanel();
	private JPanel p17=new JPanel();
	private JPanel p18=new JPanel();
	private JPanel p19=new JPanel();
	private JPanel p20=new JPanel();
	private JPanel p21=new JPanel();
	private JPanel p22=new JPanel();
	
	private Border border = new BevelBorder(BevelBorder.LOWERED);
	private Border border1 = new LineBorder(Color.LIGHT_GRAY);
	
	private JButton[][] bu;
	private JLabel[][] la;
	
	private ImageIcon bomb=new ImageIcon("Bomb-16x16.png");
	private ImageIcon fire=new ImageIcon("Fire-Toy-16x16.png");
	private ImageIcon redflag=new ImageIcon("flag_1197_16.png");
	private ImageIcon blueflag=new ImageIcon("United Nations.png");
	private ImageIcon myflag;
	private ImageIcon otherflag;
	
	private int n,m,x,buttonMclicked,x1,y1,rfc,bfc,fc,tc;
	
	public static void main(String[] args)
	{
		MineSweeperClient frame=new MineSweeperClient();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
	
	public MineSweeperClient()
	{
		n=30;
        m=16;
        x=99;
        rfc=bfc=tc=0;
        fc=x;
        
		setTitle("MineSweeper");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Bomb-16x16.png"));
		
		setJMenuBar(jmb);
		
		fileMenu.setMnemonic('F');
		help.setMnemonic('H');
		
		intermediate.setMnemonic('I');
		beginner.setMnemonic('B');
		expert.setMnemonic('E');
		mcustom.setMnemonic('C');
		mtopscores.setMnemonic('S');
		
		intermediate.setEnabled(false);
		beginner.setEnabled(false);
		mcustom.setEnabled(false);
		mtopscores.setEnabled(false);
		newgame.setEnabled(false);
	    
	    newgame.setAccelerator(KeyStroke.getKeyStroke("F2"));
	    jext.setAccelerator(KeyStroke.getKeyStroke("ESCAPE"));
	    
	    jmb.add(fileMenu);
	    jmb.add(help);
	    
	    fileMenu.add(newgame);
	    fileMenu.insertSeparator(1);
	    fileMenu.add(beginner);
	    fileMenu.add(intermediate);
	    fileMenu.add(expert);
	    fileMenu.add(mcustom);
	    fileMenu.insertSeparator(6);
	    fileMenu.add(jext);
	    help.add(mtopscores);
	    
	    flagcount.setForeground(Color.RED);
	    flagcount.setBackground(Color.BLACK);
	    flagcount.setText(Integer.toString(rfc));
	    flagcount.setEditable(false);
	    flagcount.setPreferredSize(new Dimension(50,20));
	    
	    flagcount2.setForeground(Color.RED);
	    flagcount2.setBackground(Color.BLACK);
	    flagcount2.setText(Integer.toString(bfc));
	    flagcount2.setEditable(false);
	    flagcount2.setPreferredSize(new Dimension(50,20));
	    
	    flagcount1.setForeground(Color.RED);
	    flagcount1.setBackground(Color.BLACK);
	    flagcount1.setText(Integer.toString(fc));
	    flagcount1.setEditable(false);
	    flagcount1.setPreferredSize(new Dimension(50,20));
	    
	    timecounter.setForeground(Color.RED);
	    timecounter.setBackground(Color.BLACK);
	    timecounter.setText(Integer.toString(tc));
	    timecounter.setEditable(false);
	    timecounter.setPreferredSize(new Dimension(50,20));
	    
	    game.setPreferredSize(new Dimension(50,20));
	    game.setFont(new Font("Times New Roman",Font.BOLD,17));
	    game.setText("-");
	    
	    
	    p1.setBorder(border);
	    p1.setLayout(new BorderLayout());
	    p1.add(p3,BorderLayout.WEST);
	    p1.add(p4,BorderLayout.CENTER);
	    p1.add(p5,BorderLayout.EAST);
	    p3.setLayout(new GridLayout(3,1));
	    p3.add(p17);
	    p3.add(p18);
	    p3.add(p19);
	    p18.add(flagcount);
	    p4.setLayout(new GridLayout(3,1));
	    p4.add(p16);
	    p4.add(p14);
	    p4.add(p15);
	    p16.add(timecounter);
	    p14.add(game);
	    p15.add(flagcount1);
	    p5.setLayout(new GridLayout(3,1));
	    p5.add(p20);
	    p5.add(p21);
	    p5.add(p22);
	    p21.add(flagcount2);
	    
	    p2.setBorder(border);
	    p2.setLayout(null);
	    p2.setPreferredSize(new Dimension(n*20,m*20));
	    
	    creatCells();
	    
	    getContentPane().setLayout(new BorderLayout());
	    getContentPane().add(p1, BorderLayout.NORTH);
	    getContentPane().add(p2, BorderLayout.CENTER);
	    getContentPane().add(jlblStatus,BorderLayout.SOUTH);
	    
	    jext.addActionListener(this);
	    intermediate.addActionListener(this);
	    beginner.addActionListener(this);
	    expert.addActionListener(this);
	    newgame.addActionListener(this);
	    game.addActionListener(this);
	    mcustom.addActionListener(this);
	    
	    connectToServer();
	}
	
	public void creatCells()
	{
		bu=new JButton[n][m];
		la=new JLabel[n][m];
		
		for(int i=0;i<n;i++)
		 for(int j=0;j<m;j++)
		 {
		 	bu[i][j]=new JButton();
		 	bu[i][j].setSize(20,20);
		 	bu[i][j].setLocation(i*20,j*20);
		 	bu[i][j].setVisible(true);
		 	p2.add(bu[i][j]);
		 	bu[i][j].addMouseListener(this);
		 	
		 	la[i][j]=new JLabel();
		 	la[i][j].setSize(20,20);
		 	la[i][j].setLocation(i*20,j*20);
		 	la[i][j].setBorder(border1);
		 	la[i][j].setVisible(false);
		 	p2.add(la[i][j]);
		 	la[i][j].setFont(new Font("Times New Roman",Font.BOLD,17));
		 	la[i][j].addMouseListener(this);
		 }
	}
	
	public void mouseEntered(MouseEvent e)
    {
            
    }
    public void mouseExited(MouseEvent e)
    {
            
    }
    public void mousePressed(MouseEvent e)
    {
    	if((e.getButton())==(MouseEvent.BUTTON1))
    	{
    		if (e.getSource() instanceof JButton)
    		 if(!((JButton)(e.getSource())).isSelected())
    		  game.setText("O");
    	}
    	else if((e.getButton())==(MouseEvent.BUTTON2))
    	{
    		if (e.getSource() instanceof JButton)
    		{
    			JButton b=(JButton)e.getSource();
    		    int x1=b.getX();
    		    int y1=b.getY();
    		    bu[b.getX()/20][b.getY()/20].setVisible(false);
    		    if(x1/20!=0)bu[b.getX()/20-1][b.getY()/20].setVisible(false);
    		    if(y1/20!=0)bu[b.getX()/20][b.getY()/20-1].setVisible(false);
    		    if(x1/20!=n-1)bu[b.getX()/20+1][b.getY()/20].setVisible(false);
    		    if(y1/20!=m-1)bu[b.getX()/20][b.getY()/20+1].setVisible(false);
    		    if(y1/20!=0&&x1/20!=0)bu[b.getX()/20-1][b.getY()/20-1].setVisible(false);
    		    if(y1/20!=m-1&&x1/20!=n-1)bu[b.getX()/20+1][b.getY()/20+1].setVisible(false);
    		    if(y1/20!=m-1&&x1/20!=0)bu[b.getX()/20-1][b.getY()/20+1].setVisible(false);
    		    if(y1/20!=0&&x1/20!=n-1)bu[b.getX()/20+1][b.getY()/20-1].setVisible(false);
    		}
    		else if (e.getSource() instanceof JLabel)
    		{
    			JLabel b=(JLabel)e.getSource();
    		    int x1=b.getX();
    		    int y1=b.getY();
    		    if(x1/20!=0)bu[b.getX()/20-1][b.getY()/20].setVisible(false);
    		    if(y1/20!=0)bu[b.getX()/20][b.getY()/20-1].setVisible(false);
    		    if(x1/20!=n-1)bu[b.getX()/20+1][b.getY()/20].setVisible(false);
    		    if(y1/20!=m-1)bu[b.getX()/20][b.getY()/20+1].setVisible(false);
    		    if(y1/20!=0&&x1/20!=0)bu[b.getX()/20-1][b.getY()/20-1].setVisible(false);
    		    if(y1/20!=m-1&&x1/20!=n-1)bu[b.getX()/20+1][b.getY()/20+1].setVisible(false);
    		    if(y1/20!=m-1&&x1/20!=0)bu[b.getX()/20-1][b.getY()/20+1].setVisible(false);
    		    if(y1/20!=0&&x1/20!=n-1)bu[b.getX()/20+1][b.getY()/20-1].setVisible(false);
    		}
    	}  
    }
    public void mouseReleased(MouseEvent e)
    {
    	if((e.getButton())==(MouseEvent.BUTTON1))
    	{
    		game.setText("-");
    	}
    	else if((e.getButton())==(MouseEvent.BUTTON2))
    	{
    		if (e.getSource() instanceof JButton)
    		{
    			JButton b=(JButton)e.getSource();
    		    int x1=b.getX();
    		    int y1=b.getY();
    		    if(!la[b.getX()/20][b.getY()/20].isVisible())bu[b.getX()/20][b.getY()/20].setVisible(true);
    		    if(x1/20!=0&&!la[b.getX()/20-1][b.getY()/20].isVisible())bu[b.getX()/20-1][b.getY()/20].setVisible(true);
    		    if(y1/20!=0&&!la[b.getX()/20][b.getY()/20-1].isVisible())bu[b.getX()/20][b.getY()/20-1].setVisible(true);
    		    if(x1/20!=n-1&&!la[b.getX()/20+1][b.getY()/20].isVisible())bu[b.getX()/20+1][b.getY()/20].setVisible(true);
    		    if(y1/20!=m-1&&!la[b.getX()/20][b.getY()/20+1].isVisible())bu[b.getX()/20][b.getY()/20+1].setVisible(true);
    		    if(y1/20!=0&&x1/20!=0&&!la[b.getX()/20-1][b.getY()/20-1].isVisible())bu[b.getX()/20-1][b.getY()/20-1].setVisible(true);
    		    if(y1/20!=m-1&&x1/20!=n-1&&!la[b.getX()/20+1][b.getY()/20+1].isVisible())bu[b.getX()/20+1][b.getY()/20+1].setVisible(true);
    		    if(y1/20!=m-1&&x1/20!=0&&!la[b.getX()/20-1][b.getY()/20+1].isVisible())bu[b.getX()/20-1][b.getY()/20+1].setVisible(true);
    		    if(y1/20!=0&&x1/20!=n-1&&!la[b.getX()/20+1][b.getY()/20-1].isVisible())bu[b.getX()/20+1][b.getY()/20-1].setVisible(true);
    		}
    		else if (e.getSource() instanceof JLabel)
    		{
    			JLabel b=(JLabel)e.getSource();
    		    int x1=b.getX();
    		    int y1=b.getY();
    		    if(x1/20!=0&&!la[b.getX()/20-1][b.getY()/20].isVisible())bu[b.getX()/20-1][b.getY()/20].setVisible(true);
    		    if(y1/20!=0&&!la[b.getX()/20][b.getY()/20-1].isVisible())bu[b.getX()/20][b.getY()/20-1].setVisible(true);
    		    if(x1/20!=n-1&&!la[b.getX()/20+1][b.getY()/20].isVisible())bu[b.getX()/20+1][b.getY()/20].setVisible(true);
    		    if(y1/20!=m-1&&!la[b.getX()/20][b.getY()/20+1].isVisible())bu[b.getX()/20][b.getY()/20+1].setVisible(true);
    		    if(y1/20!=0&&x1/20!=0&&!la[b.getX()/20-1][b.getY()/20-1].isVisible())bu[b.getX()/20-1][b.getY()/20-1].setVisible(true);
    		    if(y1/20!=m-1&&x1/20!=n-1&&!la[b.getX()/20+1][b.getY()/20+1].isVisible())bu[b.getX()/20+1][b.getY()/20+1].setVisible(true);
    		    if(y1/20!=m-1&&x1/20!=0&&!la[b.getX()/20-1][b.getY()/20+1].isVisible())bu[b.getX()/20-1][b.getY()/20+1].setVisible(true);
    		    if(y1/20!=0&&x1/20!=n-1&&!la[b.getX()/20+1][b.getY()/20-1].isVisible())bu[b.getX()/20+1][b.getY()/20-1].setVisible(true);
    		}
    	}    
    }
    
    public void mouseClicked(MouseEvent e)
	{
		if (e.getSource() instanceof JButton){
		JButton b=(JButton)e.getSource();
		if((e.getButton())==(MouseEvent.BUTTON1))
		{
			if(!b.isSelected()&&myTurn)
			{
		        myTurn = false;
                x1 = b.getX()/20;
                y1 = b.getY()/20;
                buttonMclicked=0;
                jlblStatus.setText("Waiting for the other player to move");
                waiting = false;
			}
		}
		else if((e.getButton())==(MouseEvent.BUTTON3))
		{
			if(!b.isSelected()&&myTurn)
			{
				myTurn = false;
                x1 = b.getX()/20;
                y1 = b.getY()/20;
                buttonMclicked=1;
                jlblStatus.setText("Waiting for the other player to move");
                waiting = false;
			}
			else if(b.isSelected()&&myTurn)
			{
				if((bu[b.getX()/20][b.getY()/20].getIcon()).equals(myflag))
				{
					myTurn = false;
                    x1 = b.getX()/20;
                    y1 = b.getY()/20;
                    buttonMclicked=1;
			        jlblStatus.setText("Waiting for the other player to move");
                    waiting = false;
			}
			}
		}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
	}
	
	private void connectToServer() {
    try {
      Socket connectToServer = new Socket("localhost", 8000);

      isFromServer = new DataInputStream(connectToServer.getInputStream());

      osToServer =new DataOutputStream(connectToServer.getOutputStream());
    }
    catch (Exception ex) {
      System.err.println(ex);
    }

    Thread thread = new Thread(this);
    thread.start();
  }
  
      public void run()
      {
      	try{
      		int player = isFromServer.readInt();
      		if (player == PLAYER1)
      		{
      			myflag=redflag;
      			otherflag=blueflag;
      			jlblStatus.setText("Player 1 with red flag: Waiting for player 2 to join");
      			isFromServer.readInt();
      			jlblStatus.setText("Player 1 with red flag: Player 2 has joined. I start first");
      			myTurn = true;
      		}
      		else if (player == PLAYER2)
      		{
      			myflag=blueflag;
      			otherflag=redflag;
      			jlblStatus.setText("Player 2 with blue flag: Waiting for player 1 to move");
      		}
      		if (player == PLAYER1)
      			{
      				waitForPlayerAction();
      				sendMove();
      				receiveInfoFromServer();
      			}
      			else if (player == PLAYER2)
      			{
      				receiveInfoFromServer();
      				waitForPlayerAction();
      				sendMove();
      			}
      		while (continueToPlay)
      		{
      			if (player == PLAYER1)
      			{
      				receiveInfoFromServer();
      				waitForPlayerAction();
      				sendMove();
      				receiveInfoFromServer();
      			}
      			else if (player == PLAYER2)
      			{
      				receiveInfoFromServer();
      				receiveInfoFromServer();
      				waitForPlayerAction();
      				sendMove();
      			}
      		}
      	}
      	catch (Exception ex) { 
          }
      }
      
      private void waitForPlayerAction() throws InterruptedException {
         while (waiting) {
             Thread.sleep(100);
          }

         waiting = true;
        }
      
      
      private void sendMove() throws IOException {
         osToServer.writeInt(x1); 
         osToServer.writeInt(y1);
         osToServer.writeInt(buttonMclicked); 
      }
      
      
      private void receiveInfoFromServer() throws IOException
      {
      	receiveMove();
      	int status = isFromServer.readInt();
      	if (status == PLAYER1_WON)
      	{
      		continueToPlay = false;
      		if(myflag.equals(redflag))
      		{
      			jlblStatus.setText("I won! ");
      		}
      		else if(myflag.equals(blueflag))
      		{
      			jlblStatus.setText("Player 1  has won!");
      		}
      	}
      	else if (status == PLAYER2_WON)
      	{
      		continueToPlay = false;
      		if(myflag.equals(blueflag))
      		{
      			jlblStatus.setText("I won! ");
      		}
      		else if(myflag.equals(redflag))
      		{
      			jlblStatus.setText("Player 2  has won!");
      		}
      	}
      	else if (status == DRAW)
      	{
      		continueToPlay = false;
            jlblStatus.setText("Game is over, no winner!");
      	}
      	else if(status==CONTINUE)
      	{
            jlblStatus.setText("My turn");
            myTurn = true; 
      	}
      } 
      
      private void receiveMove() throws IOException
      {
      	int bbb=isFromServer.readInt();
      	if(bbb==0){
      	while(isFromServer.readInt()==7){
      	    int row = isFromServer.readInt();
            int column = isFromServer.readInt();
            int te=isFromServer.readInt();
            bu[row][column].setVisible(false);
            whatte(te,row,column);
            la[row][column].setVisible(true);
            if(te==-1)
            {
            	while(isFromServer.readInt()==9)
            	{
            		row=isFromServer.readInt();
            		column=isFromServer.readInt();
            		int wr=isFromServer.readInt();
            		if(wr==1)
            		{
            			la[row][column].setIcon(bomb);
					}
					else if(wr==2)
					{
						la[row][column].setText("X");
					}
					bu[row][column].setVisible(false);
					la[row][column].setVisible(true);
				}
			}
      	}
      	}
      	else if(bbb==1)
      	{
      		int flag=isFromServer.readInt();
      		int x1=isFromServer.readInt();
      		int y1=isFromServer.readInt();
      		if(flag==2)
      		{
      			bu[x1][y1].setIcon(redflag);
			    bu[x1][y1].setSelected(true);
			    rfc++;
			    flagcount.setText(Integer.toString(rfc));
			    flagcount1.setText(Integer.toString(--fc));
      		}
      		else if(flag==3)
      		{
      			bu[x1][y1].setIcon(blueflag);
			    bu[x1][y1].setSelected(true);
			    bfc++;
			    flagcount2.setText(Integer.toString(bfc));
			    flagcount1.setText(Integer.toString(--fc));
      		}
      		else if(flag==0)
      		{
      			int wasf=isFromServer.readInt();
      			bu[x1][y1].setIcon(null);
			    bu[x1][y1].setSelected(false);
			    if(wasf==2) {rfc--; flagcount.setText(Integer.toString(rfc));}
			    else if(wasf==3) {bfc--; flagcount2.setText(Integer.toString(bfc));}
			    flagcount1.setText(Integer.toString(++fc));
      		}
      	
      	}
      	
      }
      
      private void whatte(int te,int i,int j)
      {
      	switch(te)
		 	{
		 		case 0:
		 	         break;
		 	    case -1:la[i][j].setIcon(fire);
		 	         break;
		 	    case 1:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.RED);
		 	         break;
		 	    case 2:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.GREEN);
		 	         break;
		 	    case 3:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.BLUE);
		 	         break;
		 	    case 4:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.YELLOW);
		 	         break;
		 	    case 5:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.ORANGE);
		 	         break;
		 	    case 6:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.PINK);
		 	         break;
		 	    case 7:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.MAGENTA);
		 	         break;
		 	    case 8:la[i][j].setText(Integer.toString(te));
		 	         la[i][j].setForeground(Color.CYAN);
		 	         break;
		 	    default:          
		    }         
      } 
        
        
}
