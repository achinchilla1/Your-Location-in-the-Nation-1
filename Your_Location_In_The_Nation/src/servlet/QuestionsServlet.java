package servlet;

import java.io.IOException;




import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import FakeDatabase.FakeData;
import FindLocation.GetLocation;
import LocationModel.Location;

public class QuestionsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private FakeData database;
    private List<Location> LocationList;
  
    @Override
    public void init() throws ServletException {
        super.init();
        database = new FakeData();
        LocationList = database.getLocationList();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("Questions Servlet: doGet");

   
        req.getRequestDispatcher("/_view/questions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("Question Servlet: doPost");

        // holds the error message text, if there is any
        String errorMessage = null;

        // result of calculation goes here
        Location bestLocation = null;

        // decode POSTed form parameters and dispatch to controller
        
        if(req.getParameter("Backtoindex") != null) {
			resp.sendRedirect(req.getContextPath() + "/index");
			return;
		}
        else {
        	try {
                int crimeRateFactor = getIntFromParameter(req.getParameter("crimeRate"));
                int averageSalaryFactor = getIntFromParameter(req.getParameter("averageSalary"));
                int costOfLivingFactor = getIntFromParameter(req.getParameter("costOfLiving"));
                
                
                System.out.print(crimeRateFactor);
                System.out.print(averageSalaryFactor);
                System.out.print(costOfLivingFactor);
                
                
                
                if (crimeRateFactor + averageSalaryFactor + costOfLivingFactor != 10) {
                    errorMessage = "Please answer all the questions and make them equal to 10.";
                } else {
                    GetLocation locationGetter = new GetLocation(crimeRateFactor, averageSalaryFactor, costOfLivingFactor, LocationList);
                 
                    bestLocation = locationGetter.FindBestLocation();
                    
                    if(bestLocation != null) {
                    	 // store user object in session
            			req.getSession().setAttribute("bestLocation", bestLocation);

            			// redirect to /index page
            			resp.sendRedirect(req.getContextPath() + "/output");

            			return;
                    }
                    
                }
            
            } catch (ClassNotFoundException e) {
    		
    			e.printStackTrace();
    		}

    		// Add parameters as request attributes
    		// this creates attributes named "first" and "second for the response, and grabs the
    		// values that were originally assigned to the request attributes, also named "first" and "second"
    		// they don't have to be named the same, but in this case, since we are passing them back
    		// and forth, it's a good idea
       
            req.setAttribute("crimeRate", req.getParameter("crimeRate"));
            req.setAttribute("averageSalary", req.getParameter("averageSalary"));
            req.setAttribute("costOfLiving", req.getParameter("costOfLiving"));
           
        
            req.setAttribute("errorMessage", errorMessage);


    		// Forward to view to render the result HTML document
            req.getRequestDispatcher("/_view/questions.jsp").forward(req, resp);
        }
        
    }
    
 // gets double from the request with attribute named s
 	private int getIntFromParameter(String s) {
 		if (s == null || s.equals("")) {
 			return 0;
 		} else {
 			return Integer.parseInt(s);
 		}
 	}

}


