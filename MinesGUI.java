import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

/**
 * 
 */

/**
 * @author rami
 *
 */
public class MinesGUI extends JFrame implements ActionListener , MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
	private JTextField timecounter=new JTextField(3); 
	
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
	
	private Border border = new BevelBorder(BevelBorder.LOWERED);
	private Border border1 = new LineBorder(Color.LIGHT_GRAY);
	
	private JButton[][] bu;
	private JLabel[][] la;
	
	private ImageIcon bomb=new ImageIcon("Bomb-16x16.png");
	private ImageIcon fire=new ImageIcon("Fire-Toy-16x16.png");
	private ImageIcon flag=new ImageIcon("flag_1197_16.png");
	
	private static File file=new File("minesdatagame");
	
	private static String topbn,topin,topen;
	
	private static int ff=0,ca,n,m,x,topb,topi,tope;
	
	private int cc,fc,tc,tm;
	
	private MinesCore mc;
	
	private JFrame custom=new JFrame();
	private JFrame topscores=new JFrame();
	
	private Thread th=new Thread(){
		public void run() {
			while(true){
			if(cc>0&&cc<(n*m-x))
			{
				if(tm==0)
				{
				  tc++;
				  timecounter.setText(Integer.toString(tc));
				}
			}
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
	 }
	};
	
	private WindowListener wa=new WindowAdapter(){
		public void windowDeiconified(WindowEvent e)
		{
			tm=0;
		}
		
		public void windowIconified(WindowEvent e)
		{
			tm=1;
		}
	};
	
	
	public static void readDataFile()
	{
		try{
			Scanner sc=new Scanner(file);
			topbn=sc.next();
			topb=sc.nextInt();
			topin=sc.next();
			topi=sc.nextInt();
			topen=sc.next();
			tope=sc.nextInt();
			ca=sc.nextInt();
			if(ca==4)
			{
				n=sc.nextInt();
				m=sc.nextInt();
				x=sc.nextInt();
			}
		}
		catch (Exception ex) {
				System.out.println(ex);
			}
	}
	
	public void writeRecord()
	{
		try{
			FileWriter out=new FileWriter(file);
			out.write(topbn);
			out.write(" ");
			out.write(Integer.toString(topb));
			out.write(" ");
			out.write(topin);
			out.write(" ");
			out.write(Integer.toString(topi));
			out.write(" ");
			out.write(topen);
			out.write(" ");
			out.write(Integer.toString(tope));
			out.write(" ");
			out.write(Integer.toString(ca));
			if(ca==4)
			{
				out.write(" ");
				out.write(Integer.toString(n));
				out.write(" ");
				out.write(Integer.toString(m));
				out.write(" ");
				out.write(Integer.toString(x));
				out.write(" ");
			}
			out.close();
		}
		catch (IOException ex) {
				System.out.println(ex);
			}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		readDataFile();
		MinesGUI frame=new MinesGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setResizable(false);
	    frame.setVisible(true);
        	while(true)
        	{
        		if(ff==1)
        		{
        		   MinesGUI frame1=new MinesGUI();
		           frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	               frame1.pack();
	               frame1.setResizable(false);
	               frame.setVisible(false);
	               frame.dispose();
	               frame=frame1;
	               frame.setVisible(true);
                   ff=0;
        		}
        	}
        	
	}
	
	
	
	
	public void initNum()
	{
		switch(ca)
		{
			case 1:m=n=9;
			     x=10;
			     fc=x;
			     cc=tc=tm=0;
			     writeRecord();
			     break;
			case 2:m=n=16;
			     x=40;
			     fc=x;
			     cc=tc=tm=0;
			     writeRecord();
			     break;
			case 3:x=99;
			     n=30;
			     m=16;
			     fc=x;
			     cc=tc=tm=0;
			     writeRecord();
			     break;
			case 4:fc=x;
			     cc=tc=tm=0;
			     writeRecord();
			     break;
			default:
		}
	}
	
	
	
	
	
	public MinesGUI()
	{
		initNum();
		
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
	    flagcount.setText(Integer.toString(fc));
	    flagcount.setEditable(false);
	    
	    timecounter.setForeground(Color.RED);
	    timecounter.setBackground(Color.BLACK);
	    timecounter.setText(Integer.toString(tc));
	    timecounter.setEditable(false);
	    
	    game.setPreferredSize(new Dimension(50,20));
	    game.setFont(new Font("Times New Roman",Font.BOLD,17));
	    game.setText("-");
	    
	    p1.setBorder(border);
	    p1.setLayout(new BorderLayout());
	    p1.add(p3,BorderLayout.WEST);
	    p1.add(p4,BorderLayout.CENTER);
	    p1.add(p5,BorderLayout.EAST);
	    p3.add(flagcount);
	    p4.add(game);
	    p5.add(timecounter);
	    
		th.start();
	    
	    p2.setBorder(border);
	    p2.setLayout(null);
	    p2.setPreferredSize(new Dimension(n*20,m*20));
	    
	    creatCells();
	    
	    p6.add(columns);
	    p6.add(tcolumns);
	    
	    p7.add(rows);
	    p7.add(trows);
	    
	    p8.add(mines);
	    p8.add(tmines);
	    
	    p9.setLayout(new GridLayout(1,3));
	    p9.add(p6);
	    p9.add(p7);
	    p9.add(p8);
	    
	    p10.add(btnok);
	    p10.add(btcancel);
	    
	    custom.getContentPane().setLayout(new GridLayout(2,1));
	    custom.getContentPane().add(p9);
	    custom.getContentPane().add(p10);
	    
	    custom.setTitle("Custom");
	    custom.pack();
	    custom.setResizable(false);
	    custom.setVisible(false);
	    
	    rbeginner.setText(Integer.toString(topb));
	    rintermediate.setText(Integer.toString(topi));
	    rexpert.setText(Integer.toString(tope));
	    lbeginner.setText(topbn);
	    lintermediate.setText(topin);
	    lexpert.setText(topen);
	    
	    p11.setLayout(new GridLayout(3,3));
	    p11.add(sbeginner);
	    p11.add(lbeginner);
	    p11.add(rbeginner);
	    p11.add(sintermediate);
	    p11.add(lintermediate);
	    p11.add(rintermediate);
	    p11.add(sexpert);
	    p11.add(lexpert);
	    p11.add(rexpert);
	    
	    p12.add(smile);
	    
	    p13.add(reset);
	    p13.add(btncancel2);
	    p13.add(ngame);
	    
	    topscores.getContentPane().add(p12, BorderLayout.NORTH);
	    topscores.getContentPane().add(p11, BorderLayout.CENTER);
	    topscores.getContentPane().add(p13, BorderLayout.SOUTH);
	    
	    topscores.setTitle("Custom");
	    topscores.pack();
	    topscores.setResizable(false);
	    topscores.setVisible(false);
	    
	    getContentPane().setLayout(new BorderLayout());
	    getContentPane().add(p1, BorderLayout.NORTH);
	    getContentPane().add(p2, BorderLayout.CENTER);
	    
	    jext.addActionListener(this);
	    intermediate.addActionListener(this);
	    beginner.addActionListener(this);
	    expert.addActionListener(this);
	    newgame.addActionListener(this);
	    game.addActionListener(this);
	    mcustom.addActionListener(this);
	    btcancel.addActionListener(this);
	    btnok.addActionListener(this);
	    btncancel2.addActionListener(this);
	    reset.addActionListener(this);
	    ngame.addActionListener(this);
	    mtopscores.addActionListener(this);
	   
	    addWindowListener(wa);
	    
	}

	
	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() instanceof JMenuItem)
		{
			if("Exit".equals(e.getActionCommand()))
			{
				System.exit(0);
			}
			else if("Intermediate".equals(e.getActionCommand()))
			{
				if(ca!=2)
				{
				  ff=1;
				  ca=2;
				}
			}
			else if("Beginner".equals(e.getActionCommand()))
			{
				if(ca!=1)
				{
					ff=1;
					ca=1;
				}
			}
			else if("Expert".equals(e.getActionCommand()))
			{
				if(ca!=3)
				{
					ff=1;
					ca=3;
				}
			}
			else if("New Game".equals(e.getActionCommand()))
			{
				newGame();
			}
			else if("Custom".equals(e.getActionCommand()))
			{
				tcolumns.setText(Integer.toString(n));
				trows.setText(Integer.toString(m));
				tmines.setText(Integer.toString(x));
				custom.setVisible(true);
			}
			else if("Top Scores".equals(e.getActionCommand()))
			{
				topscores.setVisible(true);
			}
		}
		
		if (e.getSource() instanceof JButton)
		{
			if(game.equals(e.getSource()))
			{
				newGame();
			}
			else if(btcancel.equals(e.getSource()))
			{
				custom.setVisible(false);
			}
			else if(btnok.equals(e.getSource()))
			{
				int n1=Integer.parseInt(tcolumns.getText());
				int m1=Integer.parseInt(trows.getText());
				int x1=Integer.parseInt(tmines.getText());
				custom.setVisible(false);
				if(n1==n&&m1==m&&x1==x){}
				else if(n1==9&&m1==9&&x1==10)
				{
					ff=1;
					ca=1;
				}
				else if(n1==16&&m1==16&&x1==40)
				{
					ff=1;
					ca=2;
				}
				else if(n1==30&&m1==16&&x1==99)
				{
					ff=1;
					ca=3;
				}
				else{
					if(n1<=30&&n1>=9)
					{
						n=n1;
					}
					else if(n1>30) n=30;
					else if(n1<9) n=9;
					
					if(m1<=24&&m1>=9)
					{
						m=m1;
					}
					else if(m1>24) m=24;
					else if(m1<9) m=9;
					
					if(x1<=n*m/3&&x1>=5)
					{
						x=x1;
					}
					else if(x1>n*m/3) x=n*m/3;
					else if(x1<5) x=5;
					
					ca=4;
					ff=1;
				}
			}
			else if(btncancel2.equals(e.getSource()))
			{
				topscores.setVisible(false);
			}
			else if(reset.equals(e.getSource()))
			{
				topb=topi=tope=999;
				topen=topin=topbn="Anonymous";
				writeRecord();
				rbeginner.setText(Integer.toString(topb));
	            rintermediate.setText(Integer.toString(topi));
	            rexpert.setText(Integer.toString(tope));
	            lbeginner.setText(topbn);
	            lintermediate.setText(topin);
	            lexpert.setText(topen);
			}
			else if(ngame.equals(e.getSource()))
			{
				topscores.setVisible(false);
				newGame();
			}
			
		}
		
	}
	
	
	
	
	
	public void newGame()
	{
		if(!(tm==1||cc==(n*m-x)))
		{
			for(int i=0;i<n;i++)
		     for(int j=0;j<m;j++)
		     {
		     	bu[i][j].removeMouseListener(this);
    	       	la[i][j].removeMouseListener(this);
		     }
		}
		game.setText("-");
		
		mc=new MinesCore(m,n,x);
		mc.numOfMines();
		
		fc=x;
		cc=tc=tm=0;
		timecounter.setText(Integer.toString(tc));
		flagcount.setText(Integer.toString(fc));
		
		for(int i=0;i<n;i++)
		 for(int j=0;j<m;j++)
		 {
			la[i][j].setVisible(false);
			la[i][j].setIcon(null);
			la[i][j].addMouseListener(this);
			
			bu[i][j].setIcon(null);
			bu[i][j].addMouseListener(this);
			bu[i][j].setSelected(false);
			bu[i][j].setVisible(true);
			
			switch(mc.getm(i,j))
		 	{
		 		case 0:la[i][j].setText(null);
		 	         break;
		 	    case -1:la[i][j].setIcon(bomb);
		 	         break;
		 	    case 1:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.RED);
		 	         break;
		 	    case 2:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.GREEN);
		 	         break;
		 	    case 3:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.BLUE);
		 	         break;
		 	    case 4:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.YELLOW);
		 	         break;
		 	    case 5:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.ORANGE);
		 	         break;
		 	    case 6:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.PINK);
		 	         break;
		 	    case 7:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.MAGENTA);
		 	         break;
		 	    case 8:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.CYAN);
		 	         break;
		 	    default:          
		    }
		 }
	}
	
	
	
	
	public void creatCells()
	{
		mc=new MinesCore(m,n,x);
		mc.numOfMines();
		
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
		 	
		 	switch(mc.getm(i,j))
		 	{
		 		case 0:
		 	         break;
		 	    case -1:la[i][j].setIcon(bomb);
		 	         break;
		 	    case 1:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.RED);
		 	         break;
		 	    case 2:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.GREEN);
		 	         break;
		 	    case 3:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.BLUE);
		 	         break;
		 	    case 4:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.YELLOW);
		 	         break;
		 	    case 5:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.ORANGE);
		 	         break;
		 	    case 6:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.PINK);
		 	         break;
		 	    case 7:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.MAGENTA);
		 	         break;
		 	    case 8:la[i][j].setText(Integer.toString(mc.getm(i,j)));
		 	         la[i][j].setForeground(Color.CYAN);
		 	         break;
		 	    default:          
		    }         
		 }
		
	}
	
	
	
	
	
	public void mouseClicked(MouseEvent e)
	{
		if (e.getSource() instanceof JButton){
		JButton b=(JButton)e.getSource();
		if((e.getButton())==(MouseEvent.BUTTON1))
		{
			if(!b.isSelected())
			{
		        openCells(b);
			}
		}
		else if((e.getButton())==(MouseEvent.BUTTON3))
		{
			if(!b.isSelected())
			{
				//System.out.println("nt");
			   bu[b.getX()/20][b.getY()/20].setIcon(flag);
			   bu[b.getX()/20][b.getY()/20].setSelected(true);
			   
			   fc--;
			   flagcount.setText(Integer.toString(fc));
			}
			else if(b.isSelected())
			{
				//System.out.println("is");
				bu[b.getX()/20][b.getY()/20].setIcon(null);
			    bu[b.getX()/20][b.getY()/20].setSelected(false);
			    
			    fc++;
			    flagcount.setText(Integer.toString(fc));
			}
		}
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
    
    
    
    
    public void openCells(JButton b)
    {
    	int x1=b.getX();
    	int y1=b.getY();
    	
    	if(b.isSelected())
    	{
    		
    	}
    	else if(mc.getm(x1/20,y1/20)==-1)
    	{
    		bu[x1/20][y1/20].setVisible(false);
    		la[x1/20][y1/20].setIcon(fire);
    		la[x1/20][y1/20].setVisible(true);
    		
    		tm=1;
    		showMines();
    	}
    	else if(mc.getm(x1/20,y1/20)!=0)
    	{
    		bu[x1/20][y1/20].setVisible(false);
    		la[x1/20][y1/20].setVisible(true);
    		
    		cc++;
    		checkEnd();
    	}
    	else if(mc.getm(x1/20,y1/20)==0)
    	{
    		bu[x1/20][y1/20].setVisible(false);
    		la[x1/20][y1/20].setVisible(true);
    		
    		cc++;
    		checkEnd();
    		
    		if(y1/20!=m-1&&bu[x1/20][(y1/20)+1].isVisible())  
    		  openCells(bu[x1/20][(y1/20)+1]);
			
			if(y1/20!=0&&bu[x1/20][(y1/20)-1].isVisible())  
			  openCells(bu[x1/20][(y1/20)-1]);
			
			if(x1/20!=n-1&&bu[(x1/20)+1][y1/20].isVisible()) 
			  openCells(bu[(x1/20)+1][y1/20]);
			
			if(x1/20!=0&&bu[(x1/20)-1][y1/20].isVisible())  
			  openCells(bu[(x1/20)-1][y1/20]);
			
			if(x1/20!=0&&y1/20!=0&&bu[(x1/20)-1][(y1/20)-1].isVisible()) 
			  openCells(bu[(x1/20)-1][(y1/20)-1]);
			
			if(x1/20!=0&&y1/20!=m-1&&bu[(x1/20)-1][(y1/20)+1].isVisible()) 
			  openCells(bu[(x1/20)-1][(y1/20)+1]);
			
			if(y1/20!=0&&x1/20!=n-1&&bu[(x1/20)+1][(y1/20)-1].isVisible())
			  openCells(bu[(x1/20)+1][(y1/20)-1]);
			
			if(y1/20!=m-1&&x1/20!=n-1&&bu[(x1/20)+1][(y1/20)+1].isVisible())
			  openCells(bu[(x1/20)+1][(y1/20)+1]);
    	}
    }
    
    
    
    
    public void showMines()
    {
    	
    	for(int i=0;i<n;i++)
    	 for(int j=0;j<m;j++)
    	 {	
    		if(mc.getm(i,j)==-1)
    		{
    			if(!bu[i][j].isSelected())
    			{
    				bu[i][j].setVisible(false);
    		        la[i][j].setVisible(true);
    			}
    		}
    		else if(bu[i][j].isSelected())
    	    {
    	    	la[i][j].setText("X");
    	    	la[i][j].setForeground(Color.BLACK);
                bu[i][j].setVisible(false);
    		    la[i][j].setVisible(true);   
    	    }
    	    bu[i][j].removeMouseListener(this);
    	    la[i][j].removeMouseListener(this);
    	 }
    	 game.setText("#");
    }
    
    
    public void checkEnd()
    {
    	if(cc==(n*m-x))
    	{
    		for(int i=0;i<n;i++)
    	     for(int j=0;j<m;j++)
    	       {
    	       	  bu[i][j].removeMouseListener(this);
    	       	  la[i][j].removeMouseListener(this);
    	       	  if(!bu[i][j].isSelected()&&mc.getm(i,j)==-1)
    	       	      {
    	       	      	bu[i][j].setIcon(flag);
    	       	      	flagcount.setText(Integer.toString(0));
    	       	      }
    	       }
    	    game.setText("D");
    	    if(ca==1)
    	    {
    	    	if(topb>tc)
    	    	{
    	    		String temp=JOptionPane.showInputDialog("You win,Plz Enter ur name",topbn);
    	    		if(temp!=null)
    	    		{
    	    			topbn=temp;
    	    		    topb=tc;
    	    		    writeRecord();
    	    		    lbeginner.setText(topbn);
    	    		    rbeginner.setText(Integer.toString(topb));
    	    		    topscores.setVisible(true);
    	    		}
    	    	}
    	    }
    	    else if(ca==2)
    	    {
    	    	if(topi>tc)
    	    	{
    	    		String temp=JOptionPane.showInputDialog("You win,Plz Enter ur name",topin);
    	    		if(temp!=null)
    	    		{
    	    			topin=temp;
    	    		    topi=tc;
    	    		    writeRecord();
    	    		    lintermediate.setText(topin);
    	    		    rintermediate.setText(Integer.toString(topi));
    	    		    topscores.setVisible(true);
    	    		}
    	    	}
    	    }
    	    else if(ca==3)
    	    {
    	    	if(tope>tc)
    	    	{
    	    		String temp=JOptionPane.showInputDialog("You win,Plz Enter ur name",topen);
    	    		if(temp!=null)
    	    		{
    	    			topen=temp;
    	    		    tope=tc;
    	    		    writeRecord();
    	    		    lexpert.setText(topen);
    	    		    rexpert.setText(Integer.toString(tope));
    	    		    topscores.setVisible(true);
    	    		}
    	    	}
    	    }
    	}
    }
  

}
