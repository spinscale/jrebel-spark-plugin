package org.zeroturnaround.javarebel.plugin.spark;

import org.zeroturnaround.javarebel.*;

import java.lang.reflect.Method;

public class SparkPlugin implements Plugin {

  public void preinit() {
      ReloaderFactory.getInstance().addClassReloadListener(
              new ClassEventListener() {
                  public void onClassEvent(int eventType, Class klass) {
                      try {
                          Class sparkClass = Class.forName("spark.Spark");

                          // clear routes
                          Method clearRoutesMethod = sparkClass.getDeclaredMethod("clearRoutes");
                          clearRoutesMethod.setAccessible(true);
                          clearRoutesMethod.invoke(null);

                          // reload routes
                          String applicationClassStr = System.getProperty("rebel.spark.app.class");
                          String appMethod = System.getProperty("rebel.spark.app.method");

                          Class applicationClass = Class.forName(applicationClassStr);
                          Method method = applicationClass.getMethod(appMethod);
                          method.setAccessible(true);
                          method.invoke(null);

                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }

                  public int priority() {
                      return 0;
                  }
              }
      );
  }

  public boolean checkDependencies(ClassLoader classLoader, ClassResourceSource classResourceSource) {
    return classResourceSource.getClassResource("spark.Spark") != null;
  }

  public String getId() {
    return "jr-spark-groovy";
  }

  public String getName() {
    return "JRebel Spark Groovy Plugin";
  }

  public String getDescription() {
    return "Reload instance configuration for changes of spark framework";
  }

  public String getAuthor() {
    return "Alexander Reelsen";
  }

  public String getWebsite() {
    return null;
  }

  public String getSupportedVersions() {
    return null;
  }

  public String getTestedVersions() {
    return null;
  }
}
