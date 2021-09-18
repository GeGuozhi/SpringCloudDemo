package com.ggz.childMavenProject;

public class SonOfStudent extends Student {

    public SonOfStudent(int age) {
        super(age);
    }

    public void testSonAndParent(){
        testParent();
    }
}
