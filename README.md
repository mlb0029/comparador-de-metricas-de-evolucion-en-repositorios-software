# Evaluación de la actividad de un proyecto software
Aplicación Java que toma como entrada un conjunto de direcciones de repositorios públicos o privados y calcula  medidas de la evolución que permiten comparar los repositorios.

## Metricas de evolución
 - I1 total number of issues.
 - I2  commits  per  issue.  I1  divided  by  total  number  of
commits.
- I3 percentage of issues closed( number of closed issues ∗ 100/I1).
- TI1 average of days to close an issue.
- TC1 average of days between commits.
- TC2 days between the first and the last commit.
- TC3 change activity range per month: total number of
commits divided by lifespan number of months.
- C1 peak change: count number of commits in the peak
month divided by total number of commits.

## Trabajos previos
- [Activity-Api](https://github.com/dba0010/Activiti-Api )
- [Soporte de Métricas con Independencia del Lenguaje para la Inferencia de Refactorizaciones](https://www.researchgate.net/profile/Yania_Crespo/publication/221595114_Soporte_de_Metricas_con_Independencia_del_Lenguaje_para_la_Inferencia_de_Refactorizaciones/links/09e4150b5f06425e32000000/Soporte-de-Metricas-con-Independencia-del-Lenguaje-para-la-Inferencia-de-Refactorizaciones.pdf)
- [Software Project Assessment in the Course of Evolution -  Jacek Ratzinger](http://www.inf.usi.ch/jazayeri/docs/Thesis_Jacek_Ratzinger.pdf)

## APIs Investigadas para consexión con GitLab
Estas APIs de envoltura nos ahorran trabajo adaptando [GitLab REST API](https://docs.gitlab.com/ee/api/) a Java.
- [java-gitlab-api](https://github.com/timols/java-gitlab-api)
- [gitlab4j-api](https://github.com/gmessner/gitlab4j-api)

## Herramientas utilizadas
- [Eclipse IDE for Java EE Developers](https://www.eclipse.org/)
- [Apache Maven 3.5.4](https://maven.apache.org/)
- [Apache Tomcat 9.0.12](http://tomcat.apache.org/)
- [Java SE 11 (JDK)](https://www.oracle.com/technetwork/java/javase/overview/index.html)
- [java-gitlab-api](https://github.com/timols/java-gitlab-api)
- [gitlab4j-api](https://github.com/gmessner/gitlab4j-api)