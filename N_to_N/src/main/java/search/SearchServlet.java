package search;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//key값이 name인 데이터의 value를 String name에 저장
		String name = request.getParameter("k_name");

	    request.setAttribute("k_name", name);
		
		String IP = "175.116.84.203"; 
		int PORT = 6379; 
		int TIME_OUT = 1000; 
		String PASSWORD = "ss12!("; 
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig(); 
		JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD ); 
		Jedis jedis = pool.getResource(); 
		String redis_name = jedis.get(name); 
		
		if (jedis != null) { 
			jedis.close(); 
			jedis = null; 
		} 
		pool.close(); 
		
		request.setAttribute("redis_name", redis_name);
	       
	       
	    try {
	    	Document doc = Jsoup.connect("https://dict.naver.com/name-to-roman/translation/?query="+name+"&x=0&y=0&where=name").get(); 

			Elements tables = doc.getElementsByTag("td");
			String naver_name = tables.get(0).getElementsByTag("a").attr("title");
		       request.setAttribute("naver_name", naver_name);
	    } catch(Exception e1) {
	    	System.out.println(e1);
	    }
	    

	    ServletContext app = this.getServletContext();
	    RequestDispatcher dispatcher = app.getRequestDispatcher("/index.jsp");
	    try {
	    	dispatcher.forward(request, response);
	    } catch (ServletException e) {
	        System.out.println(e);
	    }
	}
}
