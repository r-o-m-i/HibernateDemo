package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import dto.Applicant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
/**
 * Servlet Filter implementation class ApplicantFilter
 */
public class ApplicantFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public ApplicantFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setContentType("text/html");
		String uid = request.getParameter("uid");

		if(uid.length()<12)
		{
			PrintWriter out = response.getWriter();
			request.getRequestDispatcher("ApplicationFE.jsp").include(request, response);
			out.append("<h1 style='color:red;position:fixed;top:90vh' >UID: " + uid + " is invalid.</h1>");	
		}
		
		else {
			Applicant ap = new Applicant();
			ap.setFname(request.getParameter("fname"));
			ap.setLname(request.getParameter("lname"));
			ap.setUid(Long.valueOf(uid));
			
//			creating hibernate connection.
			
			Configuration config = new Configuration().configure();
			config.addAnnotatedClass(dto.Applicant.class);
			
			StandardServiceRegistryBuilder ssrBuilder = new StandardServiceRegistryBuilder();
			StandardServiceRegistry registry = ssrBuilder.applySettings(config.getProperties()).build();
			
			SessionFactory factory = config.buildSessionFactory(registry);
			Session session = factory.openSession();
			
			CriteriaBuilder criteriaBuilder = factory.getCriteriaBuilder();
			CriteriaQuery<Applicant> criteria = criteriaBuilder.createQuery(Applicant.class);
			Root<Applicant> root = criteria.from(Applicant.class);
			
			criteria.select(root).where(criteriaBuilder.equal(root.get("uid"), ap.getUid()));
			
			Query<Applicant> query = session.createQuery(criteria);
			List<Applicant> duplicate = query.list();
			
			if(!duplicate.isEmpty())
			{
				PrintWriter out = response.getWriter();
				request.getRequestDispatcher("ApplicationFE.jsp").include(request, response);
				out.append("<h1 style='color:red;position:fixed;top:90vh' >UID: " + ap.getUid() + " already Registered.</h1>");
				session.close();
				factory.close();
			}
			
			else
			{
				request.setAttribute("applicant", ap);
				session.close();
				factory.close();
				// pass the request along the filter chain
				chain.doFilter(request, response);
			}
		}
		
	}

}
