package was.spring.servlet.mvc.view;

import was.spring.servlet.http.HttpStatus;

public class ModelAndView {
    private Model model;
    private String viewName;
    private HttpStatus httpStatus;

    public ModelAndView(Model model, String viewName, HttpStatus httpStatus) {
        this.model = model;
        this.viewName = viewName;
        this.httpStatus = httpStatus;
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getViewName() {
        return viewName;
    }

    public Model getModel() {
        return model;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static ModelAndViewBuilder builder() {
        return new ModelAndViewBuilder();
    }
}
