package br.com.transpobrasil.helper;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class JSFHelper {

    private static final JSFHelper helper = new JSFHelper();

    private JSFHelper() {
    }

    public static JSFHelper getInstance() {
        return helper;
    }

    public void showMessageSuccess(String message) {
        this.addMessage(FacesMessage.SEVERITY_INFO, message, null);
    }

    public void showMessageWarning(String message) {
        this.addMessage(FacesMessage.SEVERITY_WARN, message, null);
    }

    public void showMessageError(String message) {
        this.addMessage(FacesMessage.SEVERITY_ERROR, message, null);
    }

    public void showMessageFatal(String message) {
        this.addMessage(FacesMessage.SEVERITY_FATAL, message, null);
    }

    public FacesMessage getMessageError(String message) {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String details) {
        addMessage(FacesContext.getCurrentInstance(), null, new FacesMessage(severity, summary, details));
    }

    /*public void addMessageErrorToComponent(String component, String message) {
        addMessage(FacesContext.getCurrentInstance(), null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }*/

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    private FacesMessage addMessage(FacesContext fc, String component, FacesMessage msg) {
        if (fc != null) {
            fc.addMessage(component, msg);
        }
        return msg;
    }
}
