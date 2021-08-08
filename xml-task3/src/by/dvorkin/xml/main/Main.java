package by.dvorkin.xml.main;

import by.dvorkin.xml.xml.XMLHelper;

public class Main {
    public static void main(String[] args) {
        XMLHelper helper = new XMLHelper();

        helper.validate(helper.getSourceXML());
        System.out.println("Validated source XML-file");

        helper.xmlToObjects();
        System.out.printf("Created %d objects based on source XML-file \n", helper.getComponentSize());

        helper.addPercentToPrice(-10);
        System.out.println("Percentage markup performed");

        helper.sortComponentsByPrice(true);
        System.out.println("Sorted objects by price field");

        helper.createAllSeparatedXML();
        System.out.println("Created new XML-files by group and validated them");

        System.out.println("\nDone!");

        System.out.println(helper.showComponents());
    }
}
