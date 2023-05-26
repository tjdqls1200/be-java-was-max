package was.spring.servlet.mvc.view;

import was.spring.servlet.http.HttpStatus;

import java.util.Map;

public class ModelAndViewBuilder {
    private Model model;
    private String viewName;
    private HttpStatus httpStatus;

    public ModelAndViewBuilder() {
    }

    public ModelAndViewBuilder model(Map<String,Object> model) {
        this.model = new Model(model);
        return this;
    }

    public ModelAndViewBuilder viewName(String viewName) {
        this.viewName = viewName;
        return this;
    }

    public ModelAndViewBuilder httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ModelAndView build() {
        return new ModelAndView(model, viewName, httpStatus);
    }
}
