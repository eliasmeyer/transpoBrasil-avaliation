package launch;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;

public final class Main {
    private static final String PORT = "PORT";
    private static final String CONTEXT_APP = "/transpoBrasil";
    private static final String WEB_DIR_LOCATION = "src/main/webapp/";

    private Main() {
    }

    public static void main(String[] args) throws ServletException, LifecycleException, IOException {

        //Tomcat instance
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(System.getenv(PORT)));

        StandardContext ctx = (StandardContext) tomcat.addWebapp(CONTEXT_APP, new File(WEB_DIR_LOCATION).getCanonicalPath());
        System.out.println("configuring app with basedir: " + new File(WEB_DIR_LOCATION).getCanonicalPath());

        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
