import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MineSweeperServer extends JFrame implements MineSweeperConstants{
	public MineSweeperServer()
	{
		JTextArea jtaLog;
        JScrollPane scrollPane = new JScrollPane(
        jtaLog = new JTextArea());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setTitle("MineSweeper Server");
        setVisible(true);
        
        try{
        	ServerSocket serverSocket = new ServerSocket(8000);
            jtaLog.append(new Date() +": Server started at socket 8000\n");
            int sessionNo = 1;
            while(true)
            {
            	jtaLog.append(new Date() +": Wait for players to join session " + sessionNo + '\n');
            	Socket player1 = serverSocket.accept();
            	jtaLog.append(new Date() + ": Player 1 joined session " +sessionNo + '\n');
                jtaLog.append("Player 1's IP address" +player1.getInetAddress().getHostAddress() + '\n');
                new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
                Socket player2 = serverSocket.accept();
                jtaLog.append(new Date() +": Player 2 joined session " + sessionNo + '\n');
                jtaLog.append("Player 2's IP address" +player2.getInetAddress().getHostAddress() + '\n');
                new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);
                jtaLog.append(new Date() + ": Start a thread for session " +sessionNo++ + '\n');
                HandleASession thread = new HandleASession(player1, player2);
                thread.start();
            }
        }
        catch(IOException ex) {
          System.err.println(ex);
        }
	}
	
	public static void main(String[] args) {
		MineSweeperServer frame = new MineSweeperServer();
    }
    
    class HandleASession extends Thread implements MineSweeperConstants
    {
    	private Socket player1;
        private Socket player2;
    	
    	private DataInputStream isFromPlayer1;
        private DataOutputStream osToPlayer1;
        private DataInputStream isFromPlayer2;
        private DataOutputStream osToPlayer2;
    	
    	private boolean continueToPlay = true;
    	
    	private int bu[][];
    	
    	private int n,m,x,cc,cc1,cc2;
    	
    	private MinesCore mc;
    	
    	public void run() {
    		try{
    			isFromPlayer1 = new DataInputStream(player1.getInputStream());
                osToPlayer1 = new DataOutputStream(player1.getOutputStream());
                isFromPlayer2 = new DataInputStream(player2.getInputStream());
                osToPlayer2 = new DataOutputStream(player2.getOutputStream());
                
                osToPlayer1.writeInt(1);
                
                while(true)
                {
                	int x1=isFromPlayer1.readInt();
                	int y1=isFromPlayer1.readInt();
                	int clickedMbutton=isFromPlayer1.readInt();
                	
                	if(clickedMbutton==0)
                	{
                		if(mc.getm(x1,y1)==-1)
                	    {
                	    	osToPlayer1.writeInt(clickedMbutton);
                            osToPlayer2.writeInt(clickedMbutton);
                	    	osToPlayer1.writeInt(7);
                            osToPlayer2.writeInt(7);
                	    	sendMove(osToPlayer2, x1, y1);
                            sendMove(osToPlayer1, x1, y1);
                            bu[x1][y1]=1;
                            showMines();
                            osToPlayer1.writeInt(6);
                            osToPlayer2.writeInt(6);
                	    	osToPlayer1.writeInt(PLAYER2_WON);
                            osToPlayer2.writeInt(PLAYER2_WON);
                            break;
                	    }
                	    else 
                	    {
                	    	osToPlayer1.writeInt(clickedMbutton);
                            osToPlayer2.writeInt(clickedMbutton);
                	    	int i=openCells(x1,y1,PLAYER1);
                	    	osToPlayer1.writeInt(6);
                            osToPlayer2.writeInt(6);
                	    	if(i==1) break;
                	    	else
                	    	{
                	    		osToPlayer2.writeInt(CONTINUE);
                	    		osToPlayer1.writeInt(0);
                	    	}
                	    		
                	    	
                	    }
                    }
                    else if(clickedMbutton==1)
                    {
                    	if(bu[x1][y1]!=3)
                    	{
                    		if(bu[x1][y1]!=2)
                    		{
                    			bu[x1][y1]=2;
                                osToPlayer2.writeInt(clickedMbutton);
                                osToPlayer1.writeInt(clickedMbutton);
                    			osToPlayer2.writeInt(bu[x1][y1]);
                    			osToPlayer2.writeInt(x1);
                    			osToPlayer2.writeInt(y1);
                    			osToPlayer1.writeInt(bu[x1][y1]);
                    			osToPlayer1.writeInt(x1);
                    			osToPlayer1.writeInt(y1);
                                osToPlayer2.writeInt(CONTINUE);
                                osToPlayer1.writeInt(0);
                    		}
                    		else if(bu[x1][y1]==2)
                    		{
                    			bu[x1][y1]=0;
                                osToPlayer2.writeInt(clickedMbutton);
                                osToPlayer1.writeInt(clickedMbutton);
                    			osToPlayer2.writeInt(bu[x1][y1]);
                    			osToPlayer2.writeInt(x1);
                    			osToPlayer2.writeInt(y1);
                    			osToPlayer2.writeInt(2);
                    			osToPlayer1.writeInt(bu[x1][y1]);
                    			osToPlayer1.writeInt(x1);
                    			osToPlayer1.writeInt(y1);
                    			osToPlayer1.writeInt(2);
                                osToPlayer2.writeInt(CONTINUE);
                                osToPlayer1.writeInt(0);
                    		}
                    	}
                    }
                    
                    x1=isFromPlayer2.readInt();
                	y1=isFromPlayer2.readInt();
                	clickedMbutton=isFromPlayer2.readInt();
                	if(clickedMbutton==0)
                	{
                		if(mc.getm(x1,y1)==-1)
                	    {
                	    	osToPlayer1.writeInt(clickedMbutton);
                            osToPlayer2.writeInt(clickedMbutton);
                	    	osToPlayer1.writeInt(7);
                            osToPlayer2.writeInt(7);
                	    	sendMove(osToPlayer2, x1, y1);
                            sendMove(osToPlayer1, x1, y1);
                            bu[x1][y1]=1;
                            showMines();
                            osToPlayer1.writeInt(6);
                            osToPlayer2.writeInt(6);
                	    	osToPlayer1.writeInt(PLAYER1_WON);
                            osToPlayer2.writeInt(PLAYER1_WON);
                            break;
                	    }
                	    else 
                	    {
                	    	osToPlayer1.writeInt(clickedMbutton);
                            osToPlayer2.writeInt(clickedMbutton);
                	    	int i=openCells(x1,y1,PLAYER2);
                	    	osToPlayer1.writeInt(6);
                            osToPlayer2.writeInt(6);
                	    	if(i==1) break;
                	    	else
                	    	{
                	    		osToPlayer1.writeInt(CONTINUE);
                	    		osToPlayer2.writeInt(0);
                	    	}
                	    	
                	    }
                    }
                    else if(clickedMbutton==1)
                    {
                    	if(bu[x1][y1]!=2)
                    	{
                    		if(bu[x1][y1]!=3)
                    		{
                    			bu[x1][y1]=3;
                    			osToPlayer1.writeInt(clickedMbutton);
                    			osToPlayer2.writeInt(clickedMbutton);
                    			osToPlayer1.writeInt(bu[x1][y1]);
                    			osToPlayer1.writeInt(x1);
                    			osToPlayer1.writeInt(y1);
                    			osToPlayer2.writeInt(bu[x1][y1]);
                    			osToPlayer2.writeInt(x1);
                    			osToPlayer2.writeInt(y1);
                                osToPlayer1.writeInt(CONTINUE);
                                osToPlayer2.writeInt(0);
                    		}
                    		else if(bu[x1][y1]==3)
                    		{
                    			bu[x1][y1]=0;
                    			osToPlayer1.writeInt(clickedMbutton);
                    			osToPlayer2.writeInt(clickedMbutton);
                    			osToPlayer1.writeInt(bu[x1][y1]);
                    			osToPlayer1.writeInt(x1);
                    			osToPlayer1.writeInt(y1);
                    			osToPlayer1.writeInt(3);
                    			osToPlayer2.writeInt(bu[x1][y1]);
                    			osToPlayer2.writeInt(x1);
                    			osToPlayer2.writeInt(y1);
                    			osToPlayer2.writeInt(3);
                                osToPlayer1.writeInt(CONTINUE);
                                osToPlayer2.writeInt(0);
                    		}
                    	}
                    }
                }
    		}
    		catch(IOException ex) {
    			System.err.println(ex);
    		}    		
        }
        
        public HandleASession(Socket player1, Socket player2)
        {
        	n=30;
        	m=16;
        	x=99;
        	cc=cc1=cc2=0;
        	
        	this.player1 = player1;
            this.player2 = player2;
            
            mc=new MinesCore(m,n,x);
            mc.numOfMines();
            
            bu=new int[n][m];
        }
        
        private void sendMove(DataOutputStream out, int x1, int y1) throws IOException 
        {
        	out.writeInt(x1); 
        	out.writeInt(y1);
        	out.writeInt(mc.getm(x1,y1));
        }
        
        private int openCells(int x1,int y1,int player) 
        {
        	try{
        	if(mc.getm(x1,y1)!=0)
        	{
        		osToPlayer1.writeInt(7);
                osToPlayer2.writeInt(7);
        		sendMove(osToPlayer2,x1, y1);
        		sendMove(osToPlayer1,x1, y1);
        		bu[x1][y1]=1;
        		cc++;
        		if(player==PLAYER1)
        		{
        			cc1++;
        		}
        		else if(player==PLAYER2)
        		{
        			cc2++;
        		}
        		if(cc==(n*m-x))
                	    	{
                	    		osToPlayer1.writeInt(6);
                                osToPlayer2.writeInt(6);
                	    		if(winner()==PLAYER1)
                	    		{
                	    			osToPlayer1.writeInt(PLAYER1_WON);
                                    osToPlayer2.writeInt(PLAYER1_WON);
                                    return 1;
                	    		}
                	    		else if(winner()==PLAYER2)
                	    		{
                	    			osToPlayer1.writeInt(PLAYER2_WON);
                                    osToPlayer2.writeInt(PLAYER2_WON);
                                    return 1;
                	    		}
                	    		else
                	    		{
                	    			osToPlayer1.writeInt(DRAW);
                                    osToPlayer2.writeInt(DRAW);
                                    return 1;
                	    		}
                	    	}
        	}
        	else if(mc.getm(x1,y1)==0)
        	{
        		osToPlayer1.writeInt(7);
                osToPlayer2.writeInt(7);
        		sendMove(osToPlayer2,x1, y1);
        		sendMove(osToPlayer1,x1, y1);
        		bu[x1][y1]=1;
        		cc++;
        		if(player==PLAYER1)
        		{
        			cc1++;
        		}
        		else if(player==PLAYER2)
        		{
        			cc2++;
        		}
        		if(cc==(n*m-x))
                	    	{
                	    		osToPlayer1.writeInt(6);
                                osToPlayer2.writeInt(6);
                	    		if(winner()==PLAYER1)
                	    		{
                	    			osToPlayer1.writeInt(PLAYER1_WON);
                                    osToPlayer2.writeInt(PLAYER1_WON);
                                    return 1;
                	    		}
                	    		else if(winner()==PLAYER2)
                	    		{
                	    			osToPlayer1.writeInt(PLAYER2_WON);
                                    osToPlayer2.writeInt(PLAYER2_WON);
                                    return 1;
                	    		}
                	    		else
                	    		{
                	    			osToPlayer1.writeInt(DRAW);
                                    osToPlayer2.writeInt(DRAW);
                                    return 1;
                	    		}
                	    	}
        		
        		if(y1!=m-1&&bu[x1][y1+1]==0)  
    		      openCells(x1,y1+1,player);
    		      
			
			    if(y1!=0&&bu[x1][y1-1]==0)  
			      openCells(x1,y1-1,player);			      
			
			    if(x1!=n-1&&bu[x1+1][y1]==0) 
			      openCells(x1+1,y1,player);
			
			    if(x1!=0&&bu[x1-1][y1]==0)  
			      openCells(x1-1,y1,player);
			      
			
			    if(x1!=0&&y1!=0&&bu[x1-1][y1-1]==0) 
			      openCells(x1-1,y1-1,player);
			      
			
			    if(x1!=0&&y1!=m-1&&bu[x1-1][y1+1]==0) 
			      openCells(x1-1,y1+1,player);
			      
			
			    if(y1!=0&&x1!=n-1&&bu[x1+1][y1-1]==0)
			      openCells(x1+1,y1-1,player);
			      
			
			    if(y1!=m-1&&x1!=n-1&&bu[x1+1][y1+1]==0)
			      openCells(x1+1,y1+1,player);
			    

        	} 
        	}
        	catch(IOException ex) {
    			System.err.println(ex);
    		}
    		return 0;    		
        }
        
        private int winner()
        {
        	if(cc1>cc2) return PLAYER1;
        	else if(cc2>cc1) return PLAYER2;
        	else return DRAW;
        }
        
        private void showMines() 
        {
        	try{
        	for(int i=0;i<n;i++)
		     for(int j=0;j<m;j++)
		     {
		     	if(bu[i][j]==0&&mc.getm(i,j)==-1)
		     	{
		     		osToPlayer1.writeInt(9);
                    osToPlayer2.writeInt(9);
                    osToPlayer1.writeInt(i);
                    osToPlayer2.writeInt(i);
                    osToPlayer1.writeInt(j);
                    osToPlayer2.writeInt(j);
                    osToPlayer1.writeInt(1);
                    osToPlayer2.writeInt(1);
				}
				else if(mc.getm(i,j)!=-1&&(bu[i][j]==2||bu[i][j]==3))
				{
					osToPlayer1.writeInt(9);
                    osToPlayer2.writeInt(9);
                    osToPlayer1.writeInt(i);
                    osToPlayer2.writeInt(i);
                    osToPlayer1.writeInt(j);
                    osToPlayer2.writeInt(j);
                    osToPlayer1.writeInt(2);
                    osToPlayer2.writeInt(2);
				}
			}
			osToPlayer1.writeInt(10);
            osToPlayer2.writeInt(10);
		}
		catch(IOException ex) {
    			System.err.println(ex);
    		}
		
	}
    }
}
