package cn.lxa.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class verCodeServlet extends HttpServlet {

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			int result = 0 ;
			Random r = new Random();
			BufferedImage bi = new BufferedImage(240,60,BufferedImage.TYPE_INT_BGR);
			Graphics2D g = (Graphics2D)bi.getGraphics();
			g.setColor(new Color(230,230,250));
			g.fillRect(0,0,240,60);
			int a;
			int b;
			do{
				a = r.nextInt(20)+1;
				b = r.nextInt(10)+1;
				}while(a<b);
			String symbol = "+-X";
			char[] sym = symbol.toCharArray();
			char c = sym[r.nextInt(3)];
				if(c == 'X'){
					result = a*b;
				}else if(c == '+'){
					result = a+b;
				}else if(c == '-'){
					result = a-b;
				}
			String sym1 = String.valueOf(c);
			//����������
			for(int i = 0 ;i<14;i++){
				g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
				final int x = r.nextInt(236)+1;
				final int y = r.nextInt(58)+1;
				final int w = r.nextInt(236)+1;
				final int h = r.nextInt(58)+1;
				g.drawLine(x,y,w,h);
			}
			 float yawpRate = 0.05f;// ������
		        int area = (int) (yawpRate * 240 * 60);
//		        for (int i = 0; i < area; i++) {
//		            int x = r.nextInt(100);
//		            int y = r.nextInt(60);
//		            bi.setRGB(x-1, y-1,r.nextInt(256));
//		        }
			g.setFont(new Font("Comic Sans MS",Font.BOLD,36));
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.drawString(""+a,30,45);
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.drawString(sym1, 80, 42);
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.drawString(""+b,130,42);
			HttpSession session = request.getSession();
			session.setAttribute("result", result);
			System.out.println(result);
			try {
				ImageIO.write(bi,"JPEG",response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


