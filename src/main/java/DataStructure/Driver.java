package DataStructure;

import Tests.Objects.IdentityObject;

import java.util.Scanner;

public class Driver {

    static Scanner sc = new Scanner(System.in);

    static Set<SetStructure> set = new Set<>();

    public static int getId(){
        System.out.print("Please enter element id:- ");
        int id = sc.nextInt();
        System.out.println();

        return id;
    }
    public static void addElement(){
        int id = getId();

        System.out.println("Please enter element value:- ");
        String element = sc.nextLine();
        while(element.length() == 0){
            element = sc.nextLine();
        }

        SetStructure setStructure = new SetStructure(id,element);
        set.addElement(setStructure);

        System.out.println("Element Added Successfully!");
    }

    public static void removeElement(){

        int id = getId();

        SetStructure setStructure = set.removeElement(id);
        System.out.println(setStructure);

        System.out.println("Element Removed Successfully!");

    }

    public static void displayAllElements(){
        set.displayElements();
    }

    public static void getPeek(){
        int id = getId();
        Boolean val = set.peek(id);

        if(val){
            System.out.println("True, Element present in set");
        }else{
            System.out.println("False, Element not present in set");
        }
    }

    public static void getSize(){
        System.out.println(set.size());
    }

    public static void compareSet(){

    }



    public static void main(String args[]){

        int choice = 0;
        do{
            System.out.println("********** SET Implementation ***********");
            System.out.println("Press 1, To add an element");
            System.out.println("Press 2, To remove an element");
            System.out.println("Press 3, To get an element");
            System.out.println("Press 4, To get size of set");
            System.out.println("Press 5, To display all elements in set");
            System.out.println("Press 6, To exit !");
            System.out.println("*****************************************");

            System.out.print("Enter your choice here: ");
            choice = sc.nextInt();
            System.out.println();

            if(choice == 1){
                addElement();
            }else if(choice == 2){
                removeElement();
            }else if(choice == 3){
                getPeek();
            }else if(choice == 4){
                getSize();
            }else if(choice == 5){
                compareSet();
            }else if(choice == 6){
                displayAllElements();
            }
        }while(choice < 7);
    }
}
