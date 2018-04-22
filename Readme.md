SwingSet2 - JTable demo

The project demonstrates how can you use FEST library with kotlin.
It contains set of tests which cover 'JTable demo' panel of SwingSet2 demo application form 'JDK Demos and Samples'

Requirements:
  - Ubuntu 16.04 / Windows
  - jdk 1.8
  - english keyboard layout

You can run tests separately using idea runner.
Also you can run all tests via maven: 'mvn test' (or just click 'test' in maven projects window)

You can easily use the project to test your application.
1. Put you application into 'test/resources/targetApp' directory
2. Edit Config.kt. You need to set
    - jarPath(path 'test/resources/targetApp/%YOUR_APP%');
    - name of class which contains 'static void main(String[] args)' method;
    - describe how to find the main frame of your app.
3. Describe you app in a PageObject style. Use SwingSet2 object as an example.
4. Write your tests.

