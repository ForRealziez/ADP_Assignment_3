/*
 * 
 * File: ReadStakeholderSer
 * Date modified: 9 June 2021;
 */
package za.ac.cput.assignnment3;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Chante Lewis (216118395)
 */
public class ReadStakeholderSer {
    FileWriter fw;
    BufferedWriter bw;
  ObjectInputStream input;
 
    
    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Supplier> supplierList = new ArrayList<>();
   
    
    public void openSerializeFile(){
        try{
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("Serialize file Opened");
        }catch(IOException ioe){
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
  
    public void readSerializeFile(){
      
        System.out.println("Processing");
        try{
            while(true){
                Object readObj = (Object) input.readObject();
                if(readObj instanceof Customer){
                    Customer addCustomer = (Customer)readObj;
                    customerList.add(addCustomer);
                    Collections.sort(customerList, Comparator.comparing(customer -> customer.getStHolderId()));
                    
                }
                else if(readObj instanceof Supplier){
                   Supplier addSupplier = (Supplier)readObj;
                   supplierList.add(addSupplier);
                   Collections.sort(supplierList, Comparator.comparing(supplier -> supplier.getName()));
                }
            }
        }
        catch(IOException | ClassNotFoundException e){
        }
         
    }
    
    public void closeFile(){
        try{
        input.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }        
    }
    
    public void readCustomerList(){
            System.out.println("=================================== CUSTOMERS =====================================" );
            String srt ="ID\tName\tSurname\t\tDate of birth\tAge";
            System.out.println(srt);
            System.out.println("===================================================================================" );
            for(int i = 0; i < customerList.size(); i++){
                System.out.printf("%-5s\t%-7.5s\t%-9s\t%-10s\t%-10s\n",customerList.get(i).getStHolderId(),customerList.get(i).getFirstName(),customerList.get(i).getSurName(),customerList.get(i).getDateOfBirth(),calAge(customerList.get(i).getDateOfBirth()));
            }
                System.out.println("");
                System.out.printf("%s\n",rent());
                System.out.println("");
        }
                    
    public void readSupplierList(){
            System.out.println("=================================== SUPPLIER =====================================" );
            String srt ="ID\tName\t\t\tProd Type\tDescription";
            System.out.println(srt);
            System.out.println("==================================================================================" );
            for(int i = 0; i < supplierList.size(); i++){
                System.out.printf("%-5s\t%-20s\t%-10s\t%-15s\n",supplierList.get(i).getStHolderId(),supplierList.get(i).getName(),supplierList.get(i).getProductType(),supplierList.get(i).getProductDescription());
                
            }
        }
      
    public int calAge(String dob){
         LocalDate date1 =LocalDate.now();
         LocalDate date2 = LocalDate.parse(dob,DateTimeFormatter.ISO_LOCAL_DATE);
         Period calc = Period.between(date2,date1);
         int age = calc.getYears();
         return age;
             }
   
    
    public String dateFormatter(Customer dob){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate localDate = LocalDate.parse(dob.getDateOfBirth());
        return localDate.format(dtf);
      
    }
    
    public String rent (){
      int rent = 0;
      int cantRent =0;
        for(int i = 0; i < customerList.size(); i++){
            if(customerList.get(i).getCanRent()){
                rent++;
            }
            else{
               cantRent++;
            }
        } 
        String srt = "Number of customers who can rent : "+ '\t' + rent + '\n' + "Number of customers who cannot rent : "+ '\t' + cantRent;
        return srt;
    }
    
    public void writeCustomerFile(){
      try(BufferedWriter bw = new BufferedWriter(new FileWriter("customerOutFile.txt"))){
          System.out.println("*** customerOutFile created and opened for writing ***");
          bw.write(String.format("%s\n","================================= Customers ==================================="));
          bw.newLine();
          
          bw.write(String.format("%-7s %-7.9s %-13s %-17s %-10s\n", "ID","Name","Surname","Date of Birth","Age"));
          bw.newLine();
          
          bw.write(String.format("%s\n","==============================================================================="));
          bw.newLine();
          
          for(int i = 0; i < customerList.size(); i++)
          {
              bw.write(String.format("%-5s\t%-7.5s\t%-9s\t%-10s\t%-10s",customerList.get(i).getStHolderId(),customerList.get(i).getFirstName(),customerList.get(i).getSurName(),dateFormatter(customerList.get(i)),calAge(customerList.get(i).getDateOfBirth())));
              bw.newLine();
          }
          bw.write(String.format("%s\n",rent()));
          
          bw.close();
          System.out.println("*** customer file has been closed ***"); 
          }
          catch (IOException ioe){
          System.out.println("error writing to file: " + ioe.getMessage());
          System.exit(1);
          }
        }
    
   
    public void writeSupplierFile() throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("supplierOutFile.txt"))) {
            System.out.println("*** supplierOutFile file created and opened for writing ***");      
            bw.write(String.format("%s\n","============================= SUPPLIERS ==============================="));
            bw.newLine();
          
            bw.write(String.format("%-7s %-21s %-18s %-10s\n", "ID","Name","Prod Type","Description"));
            bw.newLine();
          
            bw.write(String.format("%s\n","======================================================================="));
            bw.newLine();
          
            for(int i = 0; i < supplierList.size(); i++){
          
                bw.write(String.format("%-5s\t%-20s\t%-10s\t%-15s\n",supplierList.get(i).getStHolderId(),supplierList.get(i).getName(),supplierList.get(i).getProductType(),supplierList.get(i).getProductDescription()));
            }
            bw.close();
            System.out.println("***  supplier file has been closed ***");             
            }
            catch (IOException ioe){
            System.out.println("error writing to file: " + ioe.getMessage());
            System.exit(1);
             }
    }
    

        public static void main(String args[]) throws IOException  {
        
        ReadStakeholderSer obj = new ReadStakeholderSer();
        obj.openSerializeFile();
        obj.readSerializeFile();
        obj.closeFile();
        obj.readCustomerList();
        obj.readSupplierList();
        obj.writeCustomerFile();
        obj.writeSupplierFile();
  
    }//end main 
}  
 
