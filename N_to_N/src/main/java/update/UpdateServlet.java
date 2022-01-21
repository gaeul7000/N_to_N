package update;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stubString name = request.getParameter("k_name");

		String name = request.getParameter("k_name");
		String e_name = request.getParameter("e_name");
		
		String IP = "175.116.84.203"; 
		int PORT = 6379; 
		int TIME_OUT = 1000; 
		String PASSWORD = "ss12!("; 
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig(); 
		JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD ); 
		Jedis jedis = pool.getResource(); 
		jedis.set(name, e_name);
		
		if (jedis != null) { 
			jedis.close(); 
			jedis = null; 
		} 
		
		pool.close(); 
	       
	    ServletContext app = this.getServletContext();
	    RequestDispatcher dispatcher = app.getRequestDispatcher("/index.jsp");
	    try {
	        dispatcher.forward(request, response);
	    } catch (ServletException e) {
	        System.out.println(e);
	    }
	}
}
