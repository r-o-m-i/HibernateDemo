package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import dto.Applicant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Register() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		Applicant ap = (Applicant)request.getAttribute("applicant");
		
//		creating hibernate connection.
		
		Configuration config = new Configuration().configure();
		config.addAnnotatedClass(dto.Applicant.class);
		
		StandardServiceRegistryBuilder ssrBuilder = new StandardServiceRegistryBuilder();
		StandardServiceRegistry registry = ssrBuilder.applySettings(config.getProperties()).build();
		
		SessionFactory factory = config.buildSessionFactory(registry);
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.persist(ap);
		
		transaction.commit();
		
		CriteriaBuilder criteriaBuilder = factory.getCriteriaBuilder();
		CriteriaQuery<Applicant> criteria = criteriaBuilder.createQuery(Applicant.class);
		Root<Applicant> root = criteria.from(Applicant.class);
		
		criteria.select(root);
		
		Query<Applicant> query = session.createQuery(criteria);
		List<Applicant> applicantList = query.list();
		
		PrintWriter out = response.getWriter();
//		out.print("<h1/>");
		out.print(ap.toString() + "<br>added successfully to the database.<hr>");
		out.print("<h1>Applicants List.</h1>");

		for(Applicant a: applicantList)
		{
			out.print(a.toString() + "\t\t<br>");
		}
		
		out.print("<hr> <a href='ApplicationFE.jsp'>Click here to register again.</a>");
		
		session.close();
		factory.close();
	}

}
